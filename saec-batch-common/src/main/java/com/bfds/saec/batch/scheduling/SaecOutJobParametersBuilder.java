package com.bfds.saec.batch.scheduling;

import java.util.Date;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import com.bfds.saec.util.FileNameGenerator;
import com.bfds.scheduling.domain.FileConfig;
import com.bfds.scheduling.domain.JobConfig;
import com.google.common.base.Preconditions;

public class SaecOutJobParametersBuilder extends SaecJobParametersBuilder {
	
	private FileNameGenerator fileNameGenerator;
	
	public SaecOutJobParametersBuilder() {
		fileNameGenerator = new FileNameGenerator();
	}
	
	public JobParametersBuilder updateJobParameters(final JobConfig jobConfig, JobParametersBuilder jobParametersBuilder) {
		jobParametersBuilder = super.updateJobParameters(jobConfig, jobParametersBuilder);
		if(!jobConfig.isFielJob()) {
			return jobParametersBuilder;
		}
		final String resourceDir = getResourceDir(jobConfig);
		final String resourceParem = Boolean.TRUE.equals(jobConfig.getIncoming()) ? PARAMETER_INPUT_RESOURCE : PARAMETER_OUTPUT_RESOURCE;
		
		jobParametersBuilder.addString(
					resourceParem,
					addProtocol( resourceDir + "/" + getOutFileName(jobConfig)))
					.toJobParameters();
				
		
		return jobParametersBuilder;
	}

	private String addProtocol(String filePath) {
		return filePath.startsWith("file:") ? filePath : "file:" + filePath;
	}
	
	protected String getOutFileName(final JobConfig jobConfig) {
		final String dda = getEventDda(jobConfig);
		Preconditions.checkNotNull(dda, "DDA is null. Set DDA in event config.");
		final String fileName = fileNameGenerator.generateFileName(jobConfig.getFileConfig().getFileNamePattern(), dda, new Date());
		return fileName;
	}

	protected String getEventDda(final JobConfig jobConfig) {
        return jobConfig.getJobParameters().get("dda");
	}
	
	protected String getResourceDir(JobConfig jobConfig) {
		return jobConfig.getFileConfig().getTempLocationResourcePath();
	}
}
