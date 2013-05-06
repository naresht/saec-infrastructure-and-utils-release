package com.bfds.validation.message;

import com.bfds.validation.Validator;
import com.bfds.validation.execution.ValidationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A {@link com.bfds.validation.execution.ValidationListener} that logs the messages to an Slf4j {@link Logger}
 */
public class LoggingValidationListener implements ValidationListener {

    Logger logger = LoggerFactory.getLogger(LoggingValidationListener.class);

    /**
     * @see {@link com.bfds.validation.execution.ValidationListener#messageCreated(Message)}
     */
    @Override
    public void messageCreated(Message message) {
        logger.info(message.toString());
    }

    @Override
    public void preValidation() {
        logger.info("begining application configuration validation process....");
    }

    @Override
    public void preValidation(Validator validator) {
        logger.info("Executing validator " + validator.getClass());
    }

    @Override
    public void postValidation(Validator validator, Message... messages) {
        logger.info("Completed execution of validator " + validator.getClass() + ". Messages created :" + messages.length);
    }

    @Override
    public void postValidation(Message... messages) {
        logger.info("Completed execution of application configuration validation. Messages created :" + messages.length);
    }
}
