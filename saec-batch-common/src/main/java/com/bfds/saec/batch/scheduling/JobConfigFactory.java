package com.bfds.saec.batch.scheduling;

import com.bfds.scheduling.domain.JobConfig;

public abstract class JobConfigFactory {
	
	public static  JobConfig newJobConfig(final String jobId, final String jobName, final Boolean incoming) {
		JobConfig jc = new JobConfig();
		jc.setJobClass(SaecQuartzJob.class.getName());
		jc.setJobName(jobName);
		jc.setJobId(jobId);
		jc.setIncoming(incoming);
		if(incoming) {
			jc.getScheduleConfig().setCronExpression("0 0/5 * * * ?");
		}
		return jc;
	}
}
