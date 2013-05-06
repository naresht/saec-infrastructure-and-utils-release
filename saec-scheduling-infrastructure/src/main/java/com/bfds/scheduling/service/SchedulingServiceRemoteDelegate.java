package com.bfds.scheduling.service;


import com.bfds.scheduling.domain.JobConfig;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SchedulingServiceRemoteDelegate implements SchedulingService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${batch.admin.url}")
    private String serverUrl;// =  "http://localhost:8080/batchadmin";

    @Override
    public void loadJobsAndTriggersConfiguratoin() {
    	restTemplate.put( serverUrl+ "/scheduler?refresh", "");       
    }

    @Override
    public void registerHolidays() {
    	restTemplate.put( serverUrl+ "/scheduler/holidays", "");   
    }

    @Override
    public void rescheduleJob(JobConfig config) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void scheduleJob(JobConfig config) {
        //To change body of implemented methods use File | Settings | File Templates.
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
    public boolean scheduerRunnning() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean scheduerOnStandby() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void safeStartScheduler() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void standbyScheduler() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
