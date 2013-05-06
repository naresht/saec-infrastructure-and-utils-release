package org.springframework.batch.core.launch;


import com.bfds.saec.batch.integration.JobNotificationGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultJobLauncher extends SimpleJobLauncher {

	Logger log = LoggerFactory.getLogger(DefaultJobLauncher.class);
	
    @Autowired
    JobNotificationGateway jobNotificationGateway;
    //The "uid" is to allow the execution of a Job multiple times with the same job parameters. All event to stage jobs work this way.
    public boolean addUidJobParameter;

    @Override
    public JobExecution run(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        if(addUidJobParameter) {
            JobParametersBuilder jobParametersBuilder  = new JobParametersBuilder(jobParameters);
            jobParametersBuilder.addString("uid", String.valueOf(System.currentTimeMillis()));
            jobParameters = jobParametersBuilder.toJobParameters();
        }
        JobExecution jobExecution =  super.run(job, jobParameters);
        try {
        	if(log.isInfoEnabled()) {
        		log.info(String.format("Notifying job execution completion event handlers for job :%s with execution id: %s.", job.getName(), jobExecution.getId()));
        	}
        jobNotificationGateway.jobExecutionCompleted(jobExecution);
        }catch(Exception e) {        	
        	log.error(String.format("Ignoring error from job execution completion event handlers. job :%s with execution id: %s.", job.getName(), jobExecution.getId()), e);
        }
        return jobExecution;
    }

    public void setAddUidJobParameter(boolean addUidJobParameter) {
        this.addUidJobParameter = addUidJobParameter;
    }
}
