package com.bfds.scheduling.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.calendar.HolidayCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.bfds.scheduling.domain.Holiday;
import com.bfds.scheduling.domain.JobConfig;

@Service
public class SchedulingServiceImpl implements SchedulingService {
	final Logger log = LoggerFactory.getLogger(SchedulingServiceImpl.class);


	@Autowired
	private StdScheduler scheduler;

	public StdScheduler getScheduler() {
		return scheduler;
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void loadJobsAndTriggersConfiguratoin() {
		List<JobConfig> jobConfigList = JobConfig.findAllJobConfigs();
		for (final JobConfig config : jobConfigList) {
			scheduleJob(config);
		}
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registerHolidays() {
		if(log.isInfoEnabled()) {
			log.info("Registering Holidays.........");
		}
		List<Holiday> holidays = Holiday.findAllHolidays();
		HolidayCalendar holidayCalendar = new HolidayCalendar();
		for (Holiday holiday : holidays) {
			holidayCalendar.addExcludedDate(holiday.getHolidayDate());
		}
		try {
			scheduler.addCalendar("holidays", holidayCalendar, true, true);
		} catch (SchedulerException e) {
			throw new IllegalStateException();
		}
	}

	@Override
    public void rescheduleJob(final JobConfig config) {
		final JobDetail jobDetail = createJobDetail(config);
		final Trigger trigger = createTrigger(jobDetail, config);
		config.merge();
		rescheduleJob(trigger);
	}

	@Override
    public void scheduleJob(final JobConfig config) {
		final JobDetail jobDetail = createJobDetail(config);
		final Trigger trigger = createTrigger(jobDetail, config);
		if (jobExists(jobDetail)) {
			rescheduleJob(trigger);
		} else {
			scheduleJob(jobDetail, trigger);
		}
	}

	private void scheduleJob(JobDetail jobDetail, Trigger trigger) {
		
		try {
			if(log.isInfoEnabled()) {
				log.info(String.format("Scheduling job : %s. Start time: %s", jobDetail.getKey(), trigger.getStartTime()));
			}			
			this.getScheduler().scheduleJob(jobDetail, trigger);
		} catch (final SchedulerException e) {
			throw new IllegalStateException(e);
		}
	}

	private void rescheduleJob(Trigger trigger) {
		try {			
			if(log.isInfoEnabled()) {
				log.info(String.format("Re-Scheduling job : %s. Start time: %s", trigger.getJobKey(), trigger.getStartTime()));
			}
			this.getScheduler().rescheduleJob(trigger.getKey(),	trigger);
		} catch (SchedulerException e) {
			throw new IllegalStateException(e);
		}

	}

	private boolean jobExists(final JobDetail jobDetail) {
		try {
			return this.getScheduler().getJobDetail(jobDetail.getKey()) != null;
		} catch (SchedulerException e) {
			throw new IllegalStateException(e);
		}
	}

    private Trigger createTrigger(final JobDetail jobDetail,
			final JobConfig config) {
		Trigger trigger = null;
		try {
			trigger = newTrigger()
					.forJob(jobDetail)
					.withIdentity(
							new TriggerKey(config.getJobId(), DEFAULT_TRIGGER_GROUP))
					.withSchedule(
							cronSchedule(config.getScheduleConfig().getCronExpression()))
					//.startAt(futureDate(1, IntervalUnit.SECOND))
					.build();

		} catch (ParseException e) {
			log.error(String.format("Error creating trigger for job : %s with cron : %s", config.getJobId(), config.getScheduleConfig().getCronExpression()));
			throw new IllegalStateException(e);
		}
		return trigger;
	}

	private JobDetail createJobDetail(final JobConfig config) {
		JobDetail job;
		try {
			job = newJob((Class<Job>) Class.forName(config.getJobClass()))
					.withIdentity(
							new JobKey(config.getJobId(), DEFAULT_JOB_GROUP))
							
					.build();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
		return job;
	}

	@Override
    public boolean scheduerRunnning() {
		return this.scheduler.isStarted();
	}

	@Override
    public boolean scheduerOnStandby() {
		return this.scheduler.isInStandbyMode();
	}

	@Override
    public void safeStartScheduler() {
		try {
			this.scheduler.start();
			log.info("the Scheduler is started");
		} catch (final SchedulerException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
    public void standbyScheduler() {
		if (this.scheduler.isStarted() && !this.scheduler.isInStandbyMode()) {
			this.scheduler.standby();
			log.info("the Scheduler is on standby");
			return;
		}
		log.info("the scheduler can not be halted because it is not running");
	}

}
