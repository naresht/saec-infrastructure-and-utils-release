package com.bfds.saec.batch;

import com.bfds.saec.batch.integration.JobExecutionCompletedEventHandler;
import com.bfds.saec.batch.integration.JobNotificationGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/batch-integration.xml"})
public class JobNotificationTest {

    @Autowired
    JobNotificationGateway jobNotificationGateway;

    @Test
    public void test() throws Exception {
        //jobNotificationGateway.jobExecutionCompleted(new JobExecution(1L));


    }
}
