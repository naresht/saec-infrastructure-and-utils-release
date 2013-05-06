package com.bfds.scheduling.service;


import com.bfds.scheduling.domain.JobConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
public class JobConfigServiceRemoteDelegate implements JobConfigService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${batch.admin.url}")
    private String serverUrl;// =  "http://localhost:8080/batchadmin";

    @Override
    public List<JobConfig> getJobConfigList() {
        String response =  restTemplate.getForObject( serverUrl+ "/jobconfig/list", String.class);
        List<JobConfig> ret = jsonToObject(response, new TypeReference<List<JobConfig>>() { });
        return ret;
    }

    private <T> T jsonToObject(String json, TypeReference typeRef) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(json, typeRef);
        } catch (IOException ex) {
            throw new ResourceAccessException("I/O error: " + ex.getMessage(), ex);
        }
    }


    @Override
    public void saveJobConfig(JobConfig jobConfig) {
    	ObjectMapper mapper = new ObjectMapper();
    	StringWriter w = new StringWriter();
    	
    	 try {
    		 mapper.writeValue(w, jobConfig);
         } catch (IOException ex) {
             throw new ResourceAccessException("I/O error: " + ex.getMessage(), ex);
         }
    	restTemplate.postForObject(serverUrl+ "/jobconfig/"+jobConfig.getId(), w.getBuffer().toString(), String.class);
    }

    @Override
    public JobConfig findJobConfig(Long id) {
        JobConfig ret =  restTemplate.getForObject( serverUrl+ "/jobconfig/"+id, JobConfig.class);
        return ret;
    }

    @Override
    public void enableJobSchedule(Long id, Boolean enabled) {
    	JobConfig ret = findJobConfig(id);
    	ret.getScheduleConfig().setEnabled(enabled);
    	saveJobConfig(ret);
    }
}
