package com.bfds.saec.batch.scheduling;

import com.bfds.scheduling.domain.JobConfig;

public interface SaecJobLauncher {
	void run(final JobConfig jobConfig);
}
