package com.bfds.saec.batch.scheduling;

import org.springframework.stereotype.Component;

import com.bfds.scheduling.domain.JobConfig;

@Component
public class SaecJobLauncherLocalDelegate implements SaecJobLauncher {

	@Override
	public void run(final JobConfig jobConfig) {
		SaecJobLauncherFactory.getSaecJobLauncher(jobConfig, true).run(jobConfig);
	}

}
