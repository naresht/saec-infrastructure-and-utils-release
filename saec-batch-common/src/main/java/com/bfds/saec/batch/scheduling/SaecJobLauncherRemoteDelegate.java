package com.bfds.saec.batch.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bfds.scheduling.domain.JobConfig;

@Component
public class SaecJobLauncherRemoteDelegate implements SaecJobLauncher {
	@Autowired
    private RestTemplate restTemplate;

	@Value("${batch.admin.url}")
    private String serverUrl;// =  "http://localhost:8080/batchadmin";

	@Override
	public void run(final JobConfig jobConfig) {
		restTemplate.postForObject(serverUrl+ "/job/"+jobConfig.getId(), "{}", String.class);
	}

}
