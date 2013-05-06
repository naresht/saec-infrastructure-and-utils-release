package com.bfds.saec.batch.scheduling;

import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import com.bfds.scheduling.domain.FileConfig;
import com.bfds.scheduling.domain.JobConfig;

public class SaecJobParametersBuilder {

	public static final String PARAMETER_OUTPUT_RESOURCE = "outputResource";
	public static final String PARAMETER_INPUT_RESOURCE = "inputResource";
	
	public JobParametersBuilder buildJobParameters(final JobConfig jobConfig) {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		Hibernate.initialize(jobConfig.getJobParameters());
		for (Map.Entry<String, String> e : jobConfig.getJobParameters()
				.entrySet()) {
			jobParametersBuilder.addString(e.getKey(), e.getValue());
		}
		return updateJobParameters(jobConfig, jobParametersBuilder);
	}
	
	public JobParametersBuilder updateJobParameters(final JobConfig jobConfig, final JobParametersBuilder jobParametersBuilder) {
		for(Map.Entry<String, String> e : jobConfig.getJobParameters().entrySet())	{
			jobParametersBuilder.addString(e.getKey(), e.getValue());
		}	
		return jobParametersBuilder;
	}

}