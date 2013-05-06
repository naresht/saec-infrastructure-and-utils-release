package com.bfds.validation.execution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;

import com.bfds.validation.Validator;

public class PostSpringInitializeExecutor implements InitializingBean, ApplicationContextAware {

	Logger logger = LoggerFactory.getLogger(PostSpringInitializeExecutor.class);

	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			logger.info("Started Running Deploy time Validators");
			this.applicationContext.getBean(ValidatorsManager.class).executeValidators();
		} catch (final Exception e) {
			throw new ApplicationContextException("Error executing spring-initialization validators ", e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
