package com.bfds.saec.batch.scheduling;

import org.springframework.scheduling.annotation.Async;

import com.bfds.scheduling.domain.JobConfig;

public class AsyncSaecJobLauncher implements SaecJobLauncher {

	private final SaecJobLauncher targetLauncher; 
	
	public AsyncSaecJobLauncher(final SaecJobLauncher targetLauncher) {
		this.targetLauncher = targetLauncher;
	}
	
	@Override
	@Async
	public void run(JobConfig jobConfig) {
		targetLauncher.run(jobConfig);
	}

}
