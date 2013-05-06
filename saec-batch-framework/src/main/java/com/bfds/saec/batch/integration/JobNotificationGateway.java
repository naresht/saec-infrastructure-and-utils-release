package com.bfds.saec.batch.integration;

import org.springframework.batch.core.JobExecution;
import org.springframework.integration.annotation.Gateway;
import org.springframework.stereotype.Component;

//@Component
public interface JobNotificationGateway {

    @Gateway(requestChannel = "completed-jobs-channel")
    void jobExecutionCompleted(JobExecution jobExecutionId);

}
