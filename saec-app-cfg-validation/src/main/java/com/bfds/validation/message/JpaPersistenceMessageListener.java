package com.bfds.validation.message;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

/**
 * A {@link com.bfds.validation.execution.ValidationListener} that persists @{link Message}s via JPA.
 */
@Configurable
public class JpaPersistenceMessageListener implements MessageListener {

    @Override
    @Transactional
    public void messageCreated(Message message) {
        message.persist();
    }
}
