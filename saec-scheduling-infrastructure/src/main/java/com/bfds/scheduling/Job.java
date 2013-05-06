package com.bfds.scheduling;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class Job implements org.quartz.Job {
	final Logger log = LoggerFactory.getLogger(Job.class);
	
	@Autowired
	JobFactory jobFactory;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.info(String.format("Running Job %s", context.getJobDetail().getKey()));	
		throw new UnsupportedOperationException("com.bfds.scheduling.Job#execute() not supported."+
				                                " Override this implementation by creating your own sub class..");
		
	}
}
