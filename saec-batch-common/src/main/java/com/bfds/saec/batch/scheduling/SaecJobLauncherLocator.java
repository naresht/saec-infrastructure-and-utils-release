package com.bfds.saec.batch.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SaecJobLauncherLocator {
	@Autowired
	@Qualifier("saecJobLauncherLocalDelegate")
	private transient SaecJobLauncher local;
	
	@Autowired
	@Qualifier("saecJobLauncherRemoteDelegate")
	private transient SaecJobLauncher remote;
	
	public SaecJobLauncher get(boolean remote) {
		if(remote) {
			return this.remote;
		}
		return local;
	}

}
