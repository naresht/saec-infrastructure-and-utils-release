package com.bfds.saec.batch.scheduling;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.core.io.UrlResource;

import com.bfds.scheduling.domain.JobConfig;

public class BatchFileArchiver {
	protected final Logger log = LoggerFactory.getLogger(BatchFileArchiver.class);
	
	public void archiveFile(final JobConfig jobConfig, 	final JobParameters jobParameters) throws IOException,
			MalformedURLException {
		//TODO: Get this to work.
		if(true) return;
		String resourceName = null;
		if(Boolean.TRUE.equals(jobConfig.getIncoming())) {
			resourceName = SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE;
		} else {
			resourceName = SaecJobParametersBuilder.PARAMETER_OUTPUT_RESOURCE;
		}
		final File archiveLocation = new UrlResource(addProtocol(jobConfig.getFileConfig().getArchiveLocationResourcePath())).getFile();
		final File fileToArchive = new UrlResource(jobParameters.getString(resourceName)).getFile();
		
		log.info(String.format("Archiving file %s to location %s for job %s", fileToArchive, archiveLocation, jobConfig.getJobId()));
		FileUtils.copyFileToDirectory(fileToArchive, archiveLocation);
	}
	
	private String addProtocol(String filePath) {
		return filePath.startsWith("file:") ? filePath : "file:" + filePath;
	}
	

}
