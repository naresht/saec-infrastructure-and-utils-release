package com.bfds.scheduling.domain;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;
@RooIntegrationTest(entity = JobConfig.class)
public class JobConfigIntegrationTest {

    @Test
    public void varifyJobParameters() {
    	JobConfig config = newJobConfig("corpAddressPreScrubJob", "Corporate Address Pre Scrub", true);
    	config.setVendorName("InfoAge");
    	config.getScheduleConfig().setCronExpression("0 0 18 * * ?");
    	config.getFileConfig().setFilePath("/a/b");
    	
    	
    	config.getJobParameters().put("param1", "param1-value");
    	config.getJobParameters().put("param2", "param2-value");
    	
    	config.persist();
    	config.flush();
    	config.clear();
    	
    	config = JobConfig.findAllJobConfigs().get(0);
    	
    	assertThat(config.getJobParameters()).hasSize(2);
    	assertThat(config.getJobParameters().keySet()).containsOnly("param1", "param2");
    	assertThat(config.getJobParameters().values()).containsOnly("param1-value", "param2-value");
    	
    	assertThat(config.getJobParameters().get("param1")).isEqualTo("param1-value");
    	assertThat(config.getJobParameters().get("param2")).isEqualTo("param2-value");
    
    	
    }
    
    public static  JobConfig newJobConfig(final String jobId, final String jobName, final Boolean incoming) {
		JobConfig jc = new JobConfig();
		jc.setJobClass(JobConfig.class.getName());
		jc.setJobName(jobName);
		jc.setJobId(jobId);
		jc.setIncoming(incoming);
		if(incoming) {
			jc.getScheduleConfig().setCronExpression("0 0/5 * * * ?");
		}
		return jc;
	}
}
