package com.bfds.validation.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.binding.message.Severity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/applicationContext-test.xml")
@Transactional
public class JpaPersistenceValidationListenerTest {

    @Test
    public void testPersist() {
        Message m = new Message("a", "b", Severity.ERROR);
        JpaPersistenceMessageListener listener = new JpaPersistenceMessageListener();
        listener.messageCreated(m);
        m.flush();
        m.clear();
        List<Message> messages = Message.findAllMessages();
        assertThat(messages).hasSize(1);
        assertThat(messages.get(0).getSource()).isEqualTo("a");
        assertThat(messages.get(0).getText()).isEqualTo("b");
        assertThat(messages.get(0).getSeverity()).isEqualTo(Severity.ERROR);
    }
}
