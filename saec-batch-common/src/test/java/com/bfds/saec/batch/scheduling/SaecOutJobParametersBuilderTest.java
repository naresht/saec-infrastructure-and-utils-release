package com.bfds.saec.batch.scheduling;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.JobParameters;

import com.bfds.saec.batch.scheduling.JobConfigFactory;
import com.bfds.saec.batch.scheduling.SaecJobParametersBuilder;
import com.bfds.saec.batch.scheduling.SaecOutJobParametersBuilder;
import com.bfds.scheduling.domain.JobConfig;
public class SaecOutJobParametersBuilderTest {

	SaecJobParametersBuilder builder; 
	@Before
	public void setUp() {
		builder = new SaecOutJobParametersBuilder() {

			@Override
			protected String getEventDda(JobConfig jc) {
				return "1234";
			}		
		};
	}
	
	
	@Before
	public void init() throws Exception {
		(new File("target/X/saec/db/out")).mkdirs();		
		(new File("target/X/saec/temp/db/out")).mkdirs();
		
		(new File("target/X/saec/ss/out")).mkdirs();		
		(new File("target/X/saec/temp/ss/out")).mkdirs();
	}
	
	@After
	public void cleanup() throws IOException {
		FileUtils.forceDelete(new File("target/X/"));
	}
	
	
	@Test
	public void testOutJobWithoutFileProcessing() {
		JobConfig jc = JobConfigFactory.newJobConfig("id", "name", false);
		JobParameters params = builder.buildJobParameters(jc).toJobParameters();
		
		assertThat(params.isEmpty()).isTrue();						
	}
	
	@Test
	public void testInJobWithoutParameterSubstitution() {
		JobConfig jc = JobConfigFactory.newJobConfig("id", "name", false);
		jc.getFileConfig().setRootFolder("target/X/");
		jc.getFileConfig().setFilePath("saec/db/out/issue-void.txt");
		jc.getFileConfig().setTempFolder("target/X/temp/");
		JobParameters params = builder.buildJobParameters(jc).toJobParameters();
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_OUTPUT_RESOURCE)).isNotEmpty();	
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_OUTPUT_RESOURCE)).isEqualTo("file:target/X/temp/saec/db/out/issue-void.txt");
	}
	
	@Test
	public void testInJobWithParameterSubstitution() {
		JobConfig jc = JobConfigFactory.newJobConfig("id", "name", false);
		jc.getFileConfig().setRootFolder("target/X/");
		jc.getFileConfig().setFilePath("saec/ss/out/issue-void_<dda>.txt");
		jc.getFileConfig().setTempFolder("target/X/temp");
		JobParameters params = builder.buildJobParameters(jc).toJobParameters();
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_OUTPUT_RESOURCE)).isNotEmpty();	
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_OUTPUT_RESOURCE)).isEqualTo("file:target/X/temp/saec/ss/out/issue-void_1234.txt");
	}
}
