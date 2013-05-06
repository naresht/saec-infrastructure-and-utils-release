package com.bfds.saec.batch.file.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextLookupUtil implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> T lookupWithDefaultFallback(String beanName, String defaultBeanName) {
        T bean = null;
        try {
            bean = (T) applicationContext.getBean(beanName );
        }catch(NoSuchBeanDefinitionException e) {
            bean = (T) applicationContext.getBean(defaultBeanName);
        }
        return bean;
    }


}
