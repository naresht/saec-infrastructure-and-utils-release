package com.bfds.scheduling;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.bfds.scheduling.domain.JobConfig;

public class JobConfigTest {

	@Test
	@Ignore
	public void vlaidJobConfig() {
		JobConfig config = new JobConfig();
		//assertThat(config.validate()).containsOnly("Job ID is mandatory.", "Job Class is mandatory.", "Folder Name is mandatory.");
		
		config.setJobId("id-1");
		//assertThat(config.validate()).containsOnly( "Job Class is mandatory.", "Folder Name is mandatory.");
		
		config.setJobClass("com.a.B");
		//assertThat(config.validate()).containsOnly("Folder Name is mandatory.");
		
		config.getFileConfig().setFilePath("temp/");
		//assertThat(config.validate()).isEmpty();
	}
}
