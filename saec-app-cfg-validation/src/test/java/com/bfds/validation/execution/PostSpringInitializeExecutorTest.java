package com.bfds.validation.execution;


import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bfds.validation.message.Message;
import com.bfds.validation.message.MessageUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/test-post-spring-initialize-validation-executor.xml")
public class PostSpringInitializeExecutorTest  {

    @After
    public void after() {
        (new MessageUtil()).deleteAllMessages();
    }

    @Test
    public void execute() {
        List<Message> messages = Message.findAllMessages();
        assertThat(messages).hasSize(2);
        assertThat(messages).onProperty("text").containsOnly("error in Validator3", "error in Validator4");
    }
}
