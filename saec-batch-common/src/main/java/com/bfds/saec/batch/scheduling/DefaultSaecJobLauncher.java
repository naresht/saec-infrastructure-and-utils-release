package com.bfds.saec.batch.scheduling;

import java.util.Map;
import java.util.Set;

import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bfds.scheduling.domain.JobConfig;
@RooJavaBean
@Configurable
public class DefaultSaecJobLauncher implements SaecJobLauncher {
	protected final Logger log = LoggerFactory.getLogger(DefaultSaecJobLauncher.class);

	@Autowired
	private JobExplorer jobExplorer;
	@Autowired
	private JobLauncher launcher;
	@Autowired
	private JobRegistry jobRegistry;
	
	protected SaecJobParametersBuilder saecJobParametersBuilder;

	public DefaultSaecJobLauncher() {
		saecJobParametersBuilder = new SaecJobParametersBuilder();
	}
	/* (non-Javadoc)
	 * @see com.bfds.saec.batch.scheduling.SaecJobLauncher#run(com.bfds.scheduling.domain.JobConfig)
	 */
	@Override
	public void run(final JobConfig jobConfig) {
		
		if(!canRun(jobConfig)) { return;}
		
		final Job job = getBatchJob(jobConfig.getJobId());
		final JobParameters jobParameters = buildJobParameters(jobConfig);
		if(jobParameters == null) {
			log.info(String.format("Skipp Job execution for %s. No Job parameter to execute.", job.getName()));
			return;
		}
		doPreprocessing(jobConfig, jobParameters);
		log.info("Starting Job execution. "+job.getName());
		
		final JobExecution jobExecution =  runJob(jobParameters, job);
		doPostprocessing(jobConfig, jobParameters, jobExecution);			
		log.info("Logging Job execution status "+job.getName());
		logJobExecution(jobExecution);	
	}

	protected void doPreprocessing(final JobConfig jobConfig, final JobParameters jobParameters) {
		//Noop
	}
	
	protected void doPostprocessing(final JobConfig jobConfig, final JobParameters jobParameters, final JobExecution jobExecution) {
		//noop
	}

	protected boolean canRun(final JobConfig jobConfig) {
		if(!Boolean.TRUE.equals(jobConfig.getScheduleConfig().getEnabled())) {  
			log.info("Job Shedule not Enabled. Skipping execution. "+jobConfig.getJobId());
			return false;
		}
		final Job job_ = getBatchJob(jobConfig.getJobId());
		if(running(job_.getName())) {  
			log.info("Job is currently in progess. Cannot start another instance of the job while it is running. Skipping execution. "+job_.getName());
			return false;
		}
		
		final Map<String, String> jobConfigValidationErrors = jobConfig.validate();
		
		if(!jobConfigValidationErrors.isEmpty()) {
			log.error("Abborting JOb execution. Error in JobConfig " + jobConfig.getJobId() + jobConfigValidationErrors);
			return false;
		}
				
		return true;
	}

	protected JobConfig getJobConfig(final JobDetail jobDetail) {
		return JobConfig.findJobConfigByJobId(jobDetail.getKey().getName());
	}

	protected void logJobExecution(JobExecution jobExecution) {
		if(!completed(jobExecution)) {
			//TODO: report error 
		}		
	}

	private boolean completed(final JobExecution jobExecution) {
		return jobExecution.getExitStatus() == ExitStatus.COMPLETED;
	}

	protected boolean running(final String jobName) {
		Set<JobExecution> running = jobExplorer.findRunningJobExecutions(jobName);
		return !running.isEmpty();
	}

	protected JobExecution runJob(JobParameters jobParameters, Job job) {
	
			final Map<String, JobParameter> params = jobParameters.getParameters();
			params.put("run.count",
					new JobParameter(System.currentTimeMillis()));
	
			try {
				return launcher.run(job, new JobParameters(params));
			} catch (Exception e) {
				log.error("Error in executing Job ", e);
				throw new IllegalStateException(e);
			} 
		
	}

	protected Job getBatchJob(final String jobName) {
		try {
			return jobRegistry.getJob(jobName);
		} catch (NoSuchJobException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	protected JobParameters buildJobParameters(final JobConfig jobConfig) {
		JobParameters ret = null;
		JobParametersBuilder builder = saecJobParametersBuilder.buildJobParameters(jobConfig);
		if(builder != null) {
			ret = saecJobParametersBuilder.buildJobParameters(jobConfig).toJobParameters();
		}
		return ret;
	}
}