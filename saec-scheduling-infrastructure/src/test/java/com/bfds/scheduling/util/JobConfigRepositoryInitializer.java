package com.bfds.scheduling.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bfds.scheduling.service.SchedulingService;
import com.bfds.scheduling.domain.JobConfig;

@Repository
public class JobConfigRepositoryInitializer {

	@Autowired
    @Qualifier("schedulingServiceImpl")
	private SchedulingService schedulingService;
	
	@PostConstruct
	public void initialize() {
		loadJobConfig();
		schedulingService.loadJobsAndTriggersConfiguratoin();
	}

	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void loadJobConfig() {
		loadDbIssueVoidJobConfig();
		
		loadDbStopPaymentJobConfig();
		
		loadHolidays();
//		loadDbCashedCheckJobConfig();
//		loadDbStopAcknowledgementJobConfig();
//		
//		loadDbStopConfirmationJobConfig();
//		loadSsIssueVoidJobConfig();
//		loadSsStopPaymentJobConfig();
//		
//		loadSsibCashedCheckJobConfig();
//		
//		loadCorpAddressPreScrubJobConfig();
//		loadCorpAddressResearchJobConfig();
//		loadIndividualAddressPreScrubJobConfig();
//		
//		loadIndividualAddressResearchJobConfig();
//		loadOutCorpAddressPreScrubJobConfig();
//		loadOutCorpAddressResearchJobConfig();
//		
//		loadOutCorpAddressResearchJobConfig();
//		loadrunOutIndividualAddressResearchJobJobConfig();
//		loadIfdsIssueVoidJobConfig();
//		
//		loadIfdsCheckStatusJobConfig();
//		loadStaleDateJobConfig();
	}

	private void loadHolidays() {

		
	}


	private void loadDbIssueVoidJobConfig() {
		JobConfig jc = new JobConfig();
		jc.setJobId("db_issue_void");
		jc.setJobName("DB Issue Void");
		jc.setIncoming(false);
		jc.setJobClass(com.bfds.scheduling.Job.class.getName());
		//jc.setVendorName("Deutsche Bank");
		jc.getScheduleConfig().setCronExpression("* * * * * ? ");
		jc.getScheduleConfig().setStartLookupTime("10:30");
		jc.getScheduleConfig().setEndLookupTime("14:30");
		jc.getScheduleConfig().setCyclicFlag(Boolean.FALSE);
		jc.getFileConfig().setFilePath("file:c:/temp/s10/DB/out/issueVoid/");
		jc.persist();
	}
	
	private void loadDbStopPaymentJobConfig() {
		JobConfig jc = new JobConfig();
		jc.setJobId("dbStopPaymentJob");
		jc.setJobName("DB Stop Payment");
		//jc.setIncoming(false);
		jc.setJobClass(com.bfds.scheduling.Job.class.getName());		
		//jc.setVendorName("Deutsche Bank");
		jc.getScheduleConfig().setCronExpression("* * * * * ? ");
		jc.getScheduleConfig().setStartLookupTime("12:30");
		jc.getScheduleConfig().setEndLookupTime("16:30");
		jc.getScheduleConfig().setCyclicFlag(Boolean.FALSE);
		jc.getFileConfig().setFilePath("file:c:/temp/s10/DB/out/stopPayment/");
		jc.persist();		
	}	
}
