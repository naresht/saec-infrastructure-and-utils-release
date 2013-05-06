package com.bfds.validation.message;

import org.springframework.binding.message.Message;

/**
 * A {@link ValidationMessageContext} that can notify {@link com.bfds.validation.execution.ValidationListener}s when a {@link Message}
 * is added.
 */
public interface ObservableMessageContext extends ValidationMessageContext {
    void addMessageListener(MessageListener listener);
}
