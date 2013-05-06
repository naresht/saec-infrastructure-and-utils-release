package com.bfds.validation.message;


import com.bfds.validation.execution.ValidationListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.binding.message.Severity;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultValidationMessageContextTest {

    @Test
    public void testInfo() {
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext();
        mc.info("message 1");
        mc.info("message %s", 2);
        assertThat(mc.getAllMessages()).onProperty("text").containsOnly("message 1", "message 2");
    }

    @Test
    public void testWarn() {
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext();
        mc.warn("message 1");
        mc.warn("message %s", 2);
        assertThat(mc.getAllMessages()).onProperty("text").containsOnly("message 1", "message 2");
    }

    @Test
    public void testError() {
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext();
        mc.error("message 1");
        mc.error("message %s", 2);
        assertThat(mc.getAllMessages()).onProperty("text").containsOnly("message 1", "message 2");
    }

    @Test
    public void testFatal() {
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext();
        mc.fatal("message 1");
        mc.fatal("message %s", 2);
        assertThat(mc.getAllMessages()).onProperty("text").containsOnly("message 1", "message 2");
    }

    @Test
    public void clear() {
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext();
        mc.fatal("message 1");
        mc.fatal("message %s", 2);
        mc.clearMessages();
        assertThat(mc.getAllMessages()).isEmpty();
    }

    @Test
    public void testListeners() {
        final Message m = new Message("", "", Severity.ERROR);
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext(){
            @Override
            public void info(String format) {
                add(m);
            }
        };
        ValidationListener listener = mock(ValidationListener.class);
        mc.addMessageListener(listener);
        mc.info("message 1");
        mc.info("message 2");
        verify(listener, times(2)).messageCreated(m);
    }

    @Test
    public void testDefaultSourceForMessages() {
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext();
        mc.setDefaultSource("source-1");
        mc.fatal("message 1");
        mc.fatal("message %s", 2);
        assertThat(mc.getAllMessages()).onProperty("source").containsOnly("source-1");
    }

    @Test
    public void testFindMessagesBySeverity() {
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext();
        mc.info("info 1");
        mc.warn("warn 1");
        mc.warn("warn %s", 2);
        mc.error("error 1");
        mc.error("error %s", 2);
        mc.error("error %s", 3);
        mc.fatal("fatal 1");
        mc.fatal("fatal %s", 2);
        mc.fatal("fatal 3");
        mc.fatal("fatal %s", 4);
        assertThat(mc.getMessagesBySeverity(Severity.INFO)).onProperty("text").containsOnly("info 1");
        assertThat(mc.getMessagesBySeverity(Severity.WARNING)).onProperty("text").containsOnly("warn 1", "warn 2");
        assertThat(mc.getMessagesBySeverity(Severity.ERROR)).onProperty("text").containsOnly("error 1", "error 2", "error 3");
        assertThat(mc.getMessagesBySeverity(Severity.FATAL)).onProperty("text").containsOnly("fatal 1", "fatal 2", "fatal 3", "fatal 4");
    }

    @Test
    public void testMessageCreatedOnDate() {
        DefaultValidationMessageContext mc = new DefaultValidationMessageContext();
        mc.info("info 1");
        mc.warn("warn 1");
        mc.error("error 1");
        mc.fatal("message 1");
        assertThat(mc.getAllMessages()).onProperty("createdOn").isNotNull();
    }
}
