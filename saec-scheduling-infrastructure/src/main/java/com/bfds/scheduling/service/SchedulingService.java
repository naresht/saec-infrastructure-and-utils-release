package com.bfds.scheduling.service;

import com.bfds.scheduling.domain.JobConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SchedulingService {

    public static String DEFAULT_JOB_GROUP = "saec.batch.trigger";
    public static String DEFAULT_TRIGGER_GROUP = "saec.batch.job";

    void loadJobsAndTriggersConfiguratoin();

    void registerHolidays();

    void rescheduleJob(JobConfig config);

    void scheduleJob(JobConfig config);

    boolean scheduerRunnning();

    boolean scheduerOnStandby();

    void safeStartScheduler();

    void standbyScheduler();
}
