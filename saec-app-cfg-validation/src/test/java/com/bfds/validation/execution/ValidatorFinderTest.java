package com.bfds.validation.execution;


import com.bfds.validation.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/applicationContext-test.xml")

public class ValidatorFinderTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Test
    public void findPostInitializeValidators() {
        DefaultValidatorFinder finder = new DefaultValidatorFinder();
        finder.setApplicationContext(applicationContext);
        Collection<Validator> validators = finder.find();
        assertThat(validators).hasSize(2);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
