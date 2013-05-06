package com.bfds.scheduling;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("jobFactory")
public class ApplicationContextJobFactory implements JobFactory, ApplicationContextAware {
	
	@Autowired
	protected JobFactory defaultJobFactory;
	
	protected ApplicationContext applicationContext;
	
	
	@Override
	public Job newJob(final TriggerFiredBundle bundle, final Scheduler scheduler)
			throws SchedulerException {
		final Job job = lookupJob(bundle.getJobDetail());
		return job != null ? job : defaultJobFactory.newJob(bundle, scheduler);
	}


	protected Job lookupJob(final JobDetail jobDetail) {
		Job ret = null;
		Map<String, ? extends Job> beansOfType = applicationContext.getBeansOfType(jobDetail.getJobClass());
		if (beansOfType.size() == 1) {
			ret = beansOfType.values().iterator().next();
		} else if(beansOfType.size() > 1) {
			ret = beansOfType.get(getBeanName(jobDetail));
		}
		return ret;
	}


	protected String getBeanName(final JobDetail jobDetail) {
		final JobKey key = jobDetail.getKey();
		String id = (key.getName() + key.getGroup()).replace(".", "_");
		return id;
	}


	@Override
	public void setApplicationContext(final ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
