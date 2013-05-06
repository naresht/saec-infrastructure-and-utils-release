package com.bfds.scheduling.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@MockStaticEntityMethods
@Configurable
public class JobConfigTest {
	
    @Test
    public void testMethod() {
    }
}
