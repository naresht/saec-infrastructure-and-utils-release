package com.bfds.saec.batch.scheduling;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import static org.fest.assertions.Assertions.assertThat;
import com.bfds.scheduling.domain.JobConfig;
@RunWith(MockitoJUnitRunner.class)
public class SaecOutFileJobLauncherTest {

	@org.junit.Before
	public void init() throws Exception {
		(new File("target/X/saec/out")).mkdirs();		
		(new File("target/X/archive/saec/out")).mkdirs();
		(new File("target/X/temp")).mkdirs();
		(new File("target/X/temp/job1.txt")).createNewFile();
	}
	
	@After
	public void cleanup() throws IOException {
		//FileUtils.forceDelete(new File("target/X/"));
	}
	@Test
	public void testOutgoingFileJob() throws Exception {
		JobExplorer jobExplorer = mock(JobExplorer.class);
		JobRegistry jobRegistry = mock(JobRegistry.class);
		final Job job = mock(Job.class);
		JobLauncher launcher = mock(JobLauncher.class);
		SaecOutJobParametersBuilder parametersBuilder = mock(SaecOutJobParametersBuilder.class);
		
		JobConfig jobConfig = getOugoingJobConfig();
		
		when(jobRegistry.getJob("job1")).thenAnswer(new Answer<Job>() {
			@Override
			public Job answer(InvocationOnMock invocation) throws Throwable {
				return job;
			}
			
		});
		when(jobExplorer.findRunningJobExecutions("job1")).thenReturn(new HashSet());
		when(job.getName()).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return "job1";
			}
			
		});
		when(parametersBuilder.getOutFileName(jobConfig)).thenReturn("job1.txt");
		JobParametersBuilder params = getOugoingJobParams();
		when(parametersBuilder.buildJobParameters(jobConfig)).thenReturn(params);
		when(launcher.run(job, params.toJobParameters())).thenReturn(new JobExecution(10L));
		
		DefaultSaecJobLauncher l = new SaecOutFileJobLauncher() {
			@Override
			protected void logJobExecution(JobExecution jobExecution) {
			}			
		};
		
		l.setJobExplorer(jobExplorer);
		l.setJobRegistry(jobRegistry);
		l.setLauncher(launcher);
		l.setSaecJobParametersBuilder(parametersBuilder);
		
		
		l.run(jobConfig);
	
		assertThat((new File("target/X/saec/out/job1.txt")).exists()).isTrue();
		assertThat((new File("target/X/saec/out/job1.txt.CNF")).exists()).isTrue();
		assertThat((new File("target/X/saec/out/job1.txt.CNF").getName())).endsWith(".CNF");
		
		
	}

	private JobParametersBuilder getOugoingJobParams() {
		return new JobParametersBuilder().addString(
				SaecJobParametersBuilder.PARAMETER_OUTPUT_RESOURCE,
				"file:target/X/temp/job1.txt");
	}

	private JobConfig getOugoingJobConfig() {
		JobConfig jobConfig = new JobConfig();
		jobConfig.setJobId("job1");
		jobConfig.getFileConfig().setCreateCnfFile(Boolean.TRUE);
		jobConfig.getScheduleConfig().setEnabled(Boolean.TRUE);
		jobConfig.getFileConfig().setRootFolder("target/X");
		jobConfig.getFileConfig().setFilePath("saec/out/job1.txt");
		jobConfig.getFileConfig().setArchiveFolderRoot("target/X/archive");
		jobConfig.getFileConfig().setArchiveFilePath("saec/out");
		jobConfig.getFileConfig().setTempFolder("target/X/temp");
		return jobConfig;
	}

}
