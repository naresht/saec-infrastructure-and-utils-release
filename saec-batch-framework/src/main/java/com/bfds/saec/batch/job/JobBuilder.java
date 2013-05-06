package com.bfds.saec.batch.job;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;


public interface JobBuilder {
    void registerJobDefinition(JobItemGroup itemGroup, BeanDefinitionRegistry registry);
}
