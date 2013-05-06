package com.bfds.scheduling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class JobConfigServiceLocator {
	
	@Autowired
	@Qualifier("jobConfigServiceImpl")
    private transient JobConfigService localJobConfigService;	

	@Autowired
	@Qualifier("jobConfigServiceRemoteDelegate")
    private transient JobConfigService remoteJobConfigService;
	
	public JobConfigService get(boolean remote) {
		if(remote) {
			return remoteJobConfigService;
		}
		return localJobConfigService;
	}

}
