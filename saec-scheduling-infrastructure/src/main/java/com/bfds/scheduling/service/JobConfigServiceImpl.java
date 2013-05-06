package com.bfds.scheduling.service;

import com.bfds.scheduling.domain.Holiday;
import com.bfds.scheduling.domain.JobConfig;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.calendar.HolidayCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class JobConfigServiceImpl implements JobConfigService {
	final Logger log = LoggerFactory.getLogger(JobConfigServiceImpl.class);

    @Autowired
    @Qualifier("schedulingServiceImpl")
    SchedulingService schedulingService;

	@Override
    public List<JobConfig> getJobConfigList() {
		return JobConfig.findAllJobConfigs();
	}

    @Override
    @Transactional
    public void saveJobConfig(JobConfig jobConfig) {
        jobConfig = jobConfig.merge();
        jobConfig.flush();
        schedulingService.scheduleJob(jobConfig);
    }

    @Override
    public JobConfig findJobConfig(Long id) {
        return JobConfig.findJobConfig(id);
    }

    @Override
    @Transactional
    public void enableJobSchedule(Long id, Boolean enabled) {
        JobConfig config = JobConfig.findJobConfig(id);
        config.getScheduleConfig().setEnabled(enabled);
        config.merge();
    }

}
