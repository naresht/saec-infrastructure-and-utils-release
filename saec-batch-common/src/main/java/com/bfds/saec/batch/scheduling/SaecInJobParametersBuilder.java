package com.bfds.saec.batch.scheduling;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.util.StringUtils;

import com.bfds.saec.util.FileUtils;
import com.bfds.saec.util.VariableSubstitutionUtil;
import com.bfds.scheduling.domain.FileConfig;
import com.bfds.scheduling.domain.JobConfig;
import com.google.common.base.Preconditions;

public class SaecInJobParametersBuilder extends SaecJobParametersBuilder {
	
	private VariableSubstitutionUtil substitutionUtil;
	
	public SaecInJobParametersBuilder() {
		substitutionUtil = new VariableSubstitutionUtil();
	}
	
	/* (non-Javadoc)
	 * @see com.bfds.saec.batch.scheduling.ISaecJobParametersBuilder#buildJobParameters(com.bfds.scheduling.domain.JobConfig)
	 */
	@Override
	public JobParametersBuilder updateJobParameters(final JobConfig jobConfig, JobParametersBuilder jobParametersBuilder) {
		jobParametersBuilder = super.updateJobParameters(jobConfig, jobParametersBuilder);
		if(!jobConfig.isFielJob()) {
			return jobParametersBuilder;
		}
		
		final String resourceDir = getResourceDir(jobConfig);
		final String resourceParem = Boolean.TRUE.equals(jobConfig.getIncoming()) ? PARAMETER_INPUT_RESOURCE : PARAMETER_OUTPUT_RESOURCE;
		
		final String fileName = getInFileName(jobConfig);
		if(!StringUtils.hasText(fileName)) {
			return null;
		}
		
		jobParametersBuilder.addString( resourceParem, addProtocol( FileConfig.getResourcePath(fileName, resourceDir)));			
		return jobParametersBuilder;
	}

	protected String getInFileName(final JobConfig jobConfig) {
		File directory = new File(jobConfig.getFileConfig()
				.getLocationResourcePathFolder());
		String filePattern = jobConfig.getFileConfig().getFileNamePattern();
		final Set<String> placeHolders = substitutionUtil.parsePlaceHolders(filePattern);
		
		if (placeHolders.contains(VariableSubstitutionUtil.DDA_PLACEHOLDER)) {
			final String dda = getEventDda(jobConfig);
			Preconditions.checkNotNull(dda,"DDA is null. Set DDA in event config.");
			filePattern = filePattern.replace(VariableSubstitutionUtil.DDA_PLACEHOLDER, dda);
		}
		for (String placeholder : placeHolders) {
			if (substitutionUtil.getSupportedDatePlaceholder().contains(placeholder) && placeholder.contains("-")) {
				filePattern = filePattern.replace(placeholder, "[0-9\\-]*");
			} 
			else if(substitutionUtil.getSupportedPlaceholders().contains(placeholder)) {
				filePattern = filePattern.replace(placeholder, "[0-9]*");
			}
			else{
				throw new IllegalArgumentException("Invalid <Placeholder> for InFileName");
			}
		}
		
		List<File> files = FileUtils.getFilesWithFilePattern(directory,filePattern);
		return files.size() > 0 ? files.get(0).getName() : null;
	}

	private String addProtocol(String filePath) {
		return filePath.startsWith("file:") ? filePath : "file:" + filePath;
	}

	protected String getEventDda(final JobConfig jobConfig) {
        return jobConfig.getJobParameters().get("dda");
		//return ".*";
	}
	
	protected String getResourceDir(JobConfig jobConfig) {
		return jobConfig.getFileConfig().getTempLocationResourcePath();
	}
}
