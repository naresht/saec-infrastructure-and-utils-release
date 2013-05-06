package com.bfds.saec.batch.scheduling;

import com.bfds.scheduling.domain.JobConfig;

public abstract class SaecJobLauncherFactory {
	
	private static SaecJobLauncher inBatchLauncher = new SaecInFileJobLauncher();
	private static SaecJobLauncher outBatchLauncher = new SaecOutFileJobLauncher();
	private static SaecJobLauncher batchLauncher = new DefaultSaecJobLauncher();
	
	public static SaecJobLauncher getSaecJobLauncher(JobConfig jobConfig) {		
	 return SaecJobLauncherFactory.getSaecJobLauncher(jobConfig, false);
	}
	
	/**
	 * @param jobConfig - The {@link JobConfig} for which a {@link DefaultSaecJobLauncher} is required. 
	 * @param async - If True the returned {@link DefaultSaecJobLauncher} will asynchronously invoke the 
	 * batch job. 
	 * @return
	 */
	public static SaecJobLauncher getSaecJobLauncher(JobConfig jobConfig, Boolean async) {		
		if(!jobConfig.isFielJob()) {
			return batchLauncher;
		} else {
			if(Boolean.TRUE.equals(jobConfig.getIncoming())) {
				return inBatchLauncher;
			} else {
				return outBatchLauncher;
			}
		}
	}
}
