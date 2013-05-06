package com.bfds.saec.batch.scheduling;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import com.bfds.scheduling.domain.JobConfig;

@Component
public class SaecInFileJobLauncher extends DefaultSaecJobLauncher {
	protected final Logger log = LoggerFactory.getLogger(SaecInFileJobLauncher.class);
	
	private BatchFileArchiver archiver; 
	
	public SaecInFileJobLauncher() {
		saecJobParametersBuilder = new SaecInJobParametersBuilder();
		archiver = new BatchFileArchiver();
	}
	void importFileToTempLocation(final JobConfig jobConfig, final JobParameters jobParameters) {
		if(jobParameters == null) {
			log.info(String.format("Skippping JOb %s no parameter to run job", jobConfig.getJobId()));
		}
		try {
			final File targetFile = getJobTargetFile(jobParameters);
			final File srcFile = new File(getSourceFileDirectory(jobConfig), targetFile.getName());
			log.info(String.format("Importing file %s to location %s for job %s", srcFile, targetFile, jobConfig.getJobId()));
			FileUtils.copyFile(srcFile, targetFile);
		} catch (Exception e) {
			log.error("Error in doPreprocessing() of Job " + jobConfig.getJobId() + ". " + e.getMessage()); 
			throw new IllegalStateException(e);
		}
	}
	private File getSourceFileDirectory(final JobConfig jobConfig)
			throws IOException, MalformedURLException {
		return new UrlResource(addProtocol(jobConfig.getFileConfig().getLocationResourcePathFolder())).getFile();
	}
	private File getJobTargetFile(final JobParameters jobParameters)
			throws IOException, MalformedURLException {
		return new UrlResource(jobParameters.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).getFile();
	}

	protected void doPreprocessing(final JobConfig jobConfig, final JobParameters jobParameters) {
		importFileToTempLocation(jobConfig, jobParameters);
	}
	
	protected void doPostprocessing(final JobConfig jobConfig, final JobParameters jobParameters, final JobExecution jobExecution) {
		try {			
			archiver.archiveFile(jobConfig, jobParameters);	
			moveSourceFileToProcessed(jobConfig, jobParameters);
		} catch (Exception e) {
			log.error("Error in doPostprocessing() of Job " + jobConfig.getJobId()); 
			throw new IllegalStateException(e);
		}		
	}
	
	private void moveSourceFileToProcessed(final JobConfig jobConfig, final JobParameters jobParameters) throws MalformedURLException, IOException {
		final File srcFile = new File(getSourceFileDirectory(jobConfig), getJobTargetFile(jobParameters).getName());
		final File destFile = new File(getSourceFileDirectory(jobConfig), getJobTargetFile(jobParameters).getName() + ".processed");
		FileUtils.moveFile(srcFile, destFile);
		
	}
	
	private String addProtocol(String filePath) {
		return filePath.startsWith("file:") ? filePath : "file:" + filePath;
	}
	
}
