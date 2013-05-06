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
import com.bfds.saec.batch.scheduling.SaecInJobParametersBuilder;
import com.bfds.saec.batch.scheduling.SaecJobParametersBuilder;
import com.bfds.scheduling.domain.JobConfig;
public class SaecInJobParametersBuilderTest {

	SaecJobParametersBuilder builder = new SaecInJobParametersBuilder() {

		@Override
		protected String getEventDda(JobConfig jc) {
			return "1234";
		}
		
	};
	
	@Before
	public void init() throws Exception {
		(new File("target/X/saec/db/in")).mkdirs();		
		(new File("target/X/saec/temp/db/in")).mkdirs();
		(new File("target/X/saec/db/in/cashed-check.txt")).createNewFile();
		
		(new File("target/X/saec/ss/in")).mkdirs();		
		(new File("target/X/saec/temp/ss/in")).mkdirs();
		(new File("target/X/saec/ss/in/cashed-check_1234_20120320.txt")).createNewFile();
		(new File("target/X/saec/ss/in/Damasco_all_Domestic_1234_2012-03-20.txt")).createNewFile();
	}
	
	@After
	public void cleanup() throws IOException {
		FileUtils.forceDelete(new File("target/X/"));
	}
	
	@Test
	public void testInJobWithoutFileProcessing() {
		JobConfig jc = JobConfigFactory.newJobConfig("id", "name", true);
		JobParameters params = builder.buildJobParameters(jc).toJobParameters();
		
		assertThat(params.isEmpty()).isTrue();						
	}
	
	
	@Test
	public void testInJobWithoutParameterSubstitution() {
		JobConfig jc = JobConfigFactory.newJobConfig("id", "name", true);
		jc.getFileConfig().setRootFolder("target/X/");
		jc.getFileConfig().setFilePath("saec/db/in/cashed-check.txt");
		jc.getFileConfig().setTempFolder("target/X/temp/");
		
		JobParameters params = builder.buildJobParameters(jc).toJobParameters();		
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).isNotEmpty();	
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).isEqualTo("file:target/X/temp/saec/db/in/cashed-check.txt");
	}
	
	/**
	 * Test to verify that {@link JobConfig#getJobParameters()} are added to {@link JobParameters}
	 */
	@Test
	public void testInJobWithJobConfigParameters() {
		JobConfig jc = JobConfigFactory.newJobConfig("id", "name", true);
		jc.getFileConfig().setRootFolder("target/X/");
		jc.getFileConfig().setFilePath("saec/db/in/cashed-check.txt");
		jc.getFileConfig().setTempFolder("target/X/temp/");
		jc.getJobParameters().put("param1", "param1_value");
		
		JobParameters params = builder.buildJobParameters(jc).toJobParameters();		
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).isNotEmpty();	
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).isEqualTo("file:target/X/temp/saec/db/in/cashed-check.txt");
		assertThat(params.getString("param1")).isEqualTo("param1_value");
	}
	
	@Test
	public void testInJobWithParameterSubstitution() {
		JobConfig jc = JobConfigFactory.newJobConfig("id", "name", true);
		jc.getFileConfig().setRootFolder("target/X/");
		jc.getFileConfig().setFilePath("saec/ss/in/cashed-check_<dda>_<ddMMyyyyHH>.txt");
		jc.getFileConfig().setTempFolder("target/X/temp/");
		
		JobParameters params = builder.buildJobParameters(jc).toJobParameters();		
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).isNotEmpty();	
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).isEqualTo("file:target/X/temp/saec/ss/in/cashed-check_1234_20120320.txt");
	}
	
	/**
	 * PIA- 109,To varify the DamascoInbound job will pick the inbound file with given format.
	 */
	@Test
	public void testInJobWithDateParameterSubstitution() {
		JobConfig jc = JobConfigFactory.newJobConfig("id", "name", true);
		jc.getFileConfig().setRootFolder("target/X/");
		jc.getFileConfig().setFilePath("saec/ss/in/Damasco_all_Domestic_<dda>_<yyyy-MM-dd>.txt");
		jc.getFileConfig().setTempFolder("target/X/temp/");
		
		JobParameters params = builder.buildJobParameters(jc).toJobParameters();		
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).isNotEmpty();	
		
		assertThat(params.getString(SaecJobParametersBuilder.PARAMETER_INPUT_RESOURCE)).isEqualTo("file:target/X/temp/saec/ss/in/Damasco_all_Domestic_1234_2012-03-20.txt");
	}
	
}
