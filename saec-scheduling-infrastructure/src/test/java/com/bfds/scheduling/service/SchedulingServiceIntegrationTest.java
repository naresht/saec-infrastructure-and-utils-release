package com.bfds.scheduling.service;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Calendar;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bfds.scheduling.domain.Holiday;
import com.bfds.scheduling.domain.JobConfig;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
//@MockStaticEntityMethods
@Configurable
public class SchedulingServiceIntegrationTest {

	@Autowired
    @Qualifier("schedulingServiceImpl")
	SchedulingService schedulingService;
	
	@Autowired
	private StdScheduler scheduler;
	
	
	
	@Test
	public void startStopScheduler() throws SchedulerException {
		assertThat(schedulingService.scheduerRunnning()).isTrue();
		assertThat(schedulingService.scheduerOnStandby()).isFalse();
		schedulingService.standbyScheduler();
		assertThat(schedulingService.scheduerOnStandby()).isTrue();
		schedulingService.safeStartScheduler();
		assertThat(schedulingService.scheduerOnStandby()).isFalse();
	}
	
	@Test
	public void verifyJobScheduling() throws Exception {
		generateData();
		schedulingService.registerHolidays();
		schedulingService.loadJobsAndTriggersConfiguratoin();
		//Thread.sleep(10000);
		JobDetail jd = scheduler.getJobDetail(new JobKey("dbIssueVoidJob_", SchedulingService.DEFAULT_JOB_GROUP));
		
		assertThat(jd).isNotNull();
		
		CronTrigger tr = (CronTrigger) scheduler.getTrigger(new TriggerKey("dbIssueVoidJob_", SchedulingService.DEFAULT_TRIGGER_GROUP));
		
		assertThat(tr).isNotNull();
		assertThat(tr.getCronExpression()).isEqualTo("* * * * * ? ");
		
		JobConfig jc = JobConfig.findJobConfigByJobId(jd.getKey().getName());
		
		assertThat(jc).isNotNull();
		assertThat(jc.getScheduleConfig().getCronExpression()).isEqualTo("* * * * * ? ");
		
		jc.getScheduleConfig().setCronExpression("0 0 12 1/1 * ? *");
		
		schedulingService.rescheduleJob(jc);
		
		tr = (CronTrigger) scheduler.getTrigger(new TriggerKey("dbIssueVoidJob_", SchedulingService.DEFAULT_TRIGGER_GROUP));
		
		assertThat(tr).isNotNull();
		assertThat(tr.getCronExpression()).isEqualTo("0 0 12 1/1 * ? *");
		
		jc = JobConfig.findJobConfigByJobId(jd.getKey().getName());
		
		assertThat(jc).isNotNull();
		assertThat(jc.getScheduleConfig().getCronExpression()).isEqualTo("0 0 12 1/1 * ? *");
		
	}
	
	@Test
	public void verifyHolidays() throws SchedulerException {
		Calendar cal = scheduler.getCalendar("holidays");
		for(Holiday holiday : Holiday.findAllHolidays()) {
			assertThat(cal.isTimeIncluded(holiday.getHolidayDate().getTime())).isFalse();
		}
	}
	
	private void generateData() {
		Holiday h = new Holiday(new Date(112, 1, 9));
		h.persist();
		
		loadDbIssueVoidJobConfig();
		loadDbStopPaymentJobConfig();
		h.flush();
		h.clear();
		
	}
	private void loadDbIssueVoidJobConfig() {
		JobConfig jc = new JobConfig();
		jc.setJobId("dbIssueVoidJob_");
		jc.setJobName("DB Issue Void_");
		jc.setIncoming(false);
		jc.setJobClass(com.bfds.scheduling.Job.class.getName());
		//jc.setVendorName("Deutsche Bank");
		jc.getScheduleConfig().setCronExpression("* * * * * ? ");
		jc.getScheduleConfig().setStartLookupTime("10:30");
		jc.getScheduleConfig().setEndLookupTime("14:30");
		jc.getScheduleConfig().setCyclicFlag(Boolean.FALSE);
		jc.getFileConfig().setFilePath("file:c:/temp/s10/DB/out/issueVoid_/");
		jc.persist();
	}
	
	private void loadDbStopPaymentJobConfig() {
		JobConfig jc = new JobConfig();
		jc.setJobId("dbStopPaymentJob_");
		jc.setJobName("DB Stop Payment_");
		jc.setIncoming(false);
		jc.setJobClass(com.bfds.scheduling.Job.class.getName());		
		//jc.setVendorName("Deutsche Bank");
		jc.getScheduleConfig().setCronExpression("* * * * * ? ");
		jc.getScheduleConfig().setStartLookupTime("12:30");
		jc.getScheduleConfig().setEndLookupTime("16:30");
		jc.getScheduleConfig().setCyclicFlag(Boolean.FALSE);
		jc.getFileConfig().setFilePath("file:c:/temp/s10/DB/out/stopPayment_/");
		jc.persist();		
	}
	
//	private List<JobConfig> getJobConfigList() {		
//		final List<JobConfig> ret = new ArrayList<JobConfig>();
//		JobConfig c = new JobConfig();
//		c.setJobId("testJob");
//		c.setJobClass("com.bfds.scheduling.Job");
//		ret.add(c);
//		return ret;
//	}
}
