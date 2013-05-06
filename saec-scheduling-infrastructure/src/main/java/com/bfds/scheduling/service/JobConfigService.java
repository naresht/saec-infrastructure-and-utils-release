package com.bfds.scheduling.service;

import com.bfds.scheduling.domain.JobConfig;

import java.util.List;

public interface JobConfigService {

    List<JobConfig> getJobConfigList();

    void saveJobConfig(JobConfig jobConfig);

    JobConfig  findJobConfig(Long id);

    void enableJobSchedule(final Long id, final Boolean enabled);
}
