package com.bfds.scheduling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SchedulingServiceLocator {
	
	@Autowired
	@Qualifier("schedulingServiceImpl")
	private transient SchedulingService localSchedulingService;
	
	@Autowired
	@Qualifier("schedulingServiceRemoteDelegate")
	private transient SchedulingService remoteSchedulingService;
	
	public SchedulingService get(boolean remote) {
		if(remote) {
			return remoteSchedulingService;
		}
		return localSchedulingService;
	}

}
