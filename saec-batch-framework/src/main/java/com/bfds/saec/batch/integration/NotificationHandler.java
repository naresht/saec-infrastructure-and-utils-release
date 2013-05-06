package com.bfds.saec.batch.integration;


import org.springframework.batch.core.JobExecution;

public interface NotificationHandler {
    void onJobCompleted(JobExecution jobExecution);
}
