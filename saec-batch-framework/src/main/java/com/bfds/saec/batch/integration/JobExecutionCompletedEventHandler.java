package com.bfds.saec.batch.integration;


import org.springframework.batch.core.JobExecution;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component("jobExecutionCompletedEventHandler")
public class JobExecutionCompletedEventHandler implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void onJobCompleted(JobExecution jobExecution) {
        NotificationHandler handler =  getRegisteredNotificationHandler(jobExecution);
        if(handler != null) {
            handler.onJobCompleted(jobExecution);
        }
    }

    /**
     * 
     * this method returns the appropriate notificationhandler based on the job in current execution,since we have only one
     * Rec object and two batch jobs for DSTO the actual handler name is not compatible with the others.so we are manually
     * passing the handlername here.
     * @param jobExecution
     * @return NotificationHandler
     */
    private NotificationHandler getRegisteredNotificationHandler(JobExecution jobExecution) {
    	String handlerName =  jobExecution.getJobInstance().getJobName()  + "NotificationHandler";
        try {
            return applicationContext.getBean(handlerName, NotificationHandler.class);
        }catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext= applicationContext;
    }
}
