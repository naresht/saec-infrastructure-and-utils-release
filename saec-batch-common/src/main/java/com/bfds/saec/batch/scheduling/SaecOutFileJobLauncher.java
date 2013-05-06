package com.bfds.saec.batch.scheduling;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import com.bfds.scheduling.domain.JobConfig;

@Component
public class SaecOutFileJobLauncher extends DefaultSaecJobLauncher {
	protected final Logger log = LoggerFactory.getLogger(SaecOutFileJobLauncher.class);
	private BatchFileArchiver archiver; 
	
	public SaecOutFileJobLauncher() {
		saecJobParametersBuilder = new SaecOutJobParametersBuilder();
		archiver = new BatchFileArchiver();
	}
	
	protected void doPostprocessing(final JobConfig jobConfig, final JobParameters jobParameters, final JobExecution jobExecution) {
		exportFileFromTempLocation(jobConfig, jobParameters);
		if(Boolean.TRUE.equals(jobConfig.getFileConfig().getCreateCnfFile())) {
			createCnfFile(jobConfig, jobParameters);
		}
		try {
			archiver.archiveFile(jobConfig, jobParameters);			
		} catch (Exception e) {
			log.error("Error in doPostprocessing() of Job " + jobConfig.getJobId()); 
			throw new IllegalStateException(e);
		}
		
	}

	private void createCnfFile(JobConfig jobConfig, JobParameters jobParameters) {
		try {			
	        final File destDir = new UrlResource(addProtocol(jobConfig.getFileConfig().getLocationResourcePathFolder())).getFile();
	        final File srcFile = new File(destDir, new UrlResource(jobParameters.getString(SaecJobParametersBuilder.PARAMETER_OUTPUT_RESOURCE)).getFile().getName()+".CNF");
	        log.info(String.format("Creating .cnf file for file %s in location %s for job %s", srcFile, destDir, jobConfig.getJobId()));
	        if(!srcFile.createNewFile()) {
	        	log.error(String.format("Error creating .cnf file for file %s in location %s for job %s", srcFile, destDir, jobConfig.getJobId()));
	        }
		} catch (Exception e) {
			log.error("Error in doPostprocessing() of Job " + jobConfig.getJobId()); 
			throw new IllegalStateException(e);
		}
		
	}

	private void exportFileFromTempLocation(final JobConfig jobConfig,
			final JobParameters jobParameters) {
		if(!Boolean.TRUE.equals(jobConfig.getIncoming())) {
			try {
				final File srcFile = new UrlResource(jobParameters.getString(SaecJobParametersBuilder.PARAMETER_OUTPUT_RESOURCE)).getFile();
		        final File destDir = new UrlResource(addProtocol(jobConfig.getFileConfig().getLocationResourcePathFolder())).getFile();
		        log.info(String.format("Exporting file %s to location %s for job %s", srcFile, destDir, jobConfig.getJobId()));
		        FileUtils.copyFileToDirectory(srcFile, destDir);
			} catch (Exception e) {
				log.error("Error in doPostprocessing() of Job " + jobConfig.getJobId()); 
				throw new IllegalStateException(e);
			}
		}
	}
	
	private String addProtocol(String filePath) {
		return filePath.startsWith("file:") ? filePath : "file:" + filePath;
	}
	
}
