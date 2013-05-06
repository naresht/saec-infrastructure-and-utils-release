package com.bfds.saec.batch.scheduling;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.bfds.scheduling.domain.JobConfig;

@Configurable
@Component
@DisallowConcurrentExecution
public class SaecQuartzJob  implements org.quartz.Job {

	protected final Logger log = LoggerFactory.getLogger(SaecQuartzJob.class);
	
	
	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		
		final JobDetail jobDetail = context.getJobDetail();
		
		
		final JobConfig jobConfig = getJobConfig(jobDetail);
		if(log.isInfoEnabled()) {
			log.info(String.format("Starting scheduled job : %s. Scheduled Fire time: %s, Fire time: %s, next fire time: %s", jobConfig.getJobId(), context.getScheduledFireTime(), context.getFireTime(), context.getNextFireTime()));
		}
		SaecJobLauncherFactory.getSaecJobLauncher(jobConfig).run(jobConfig);
	}
	
	protected JobConfig getJobConfig(final JobDetail jobDetail) {
		return JobConfig.findJobConfigByJobId(jobDetail.getKey().getName());
	}

}