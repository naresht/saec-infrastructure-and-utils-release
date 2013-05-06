package com.bfds.validation.execution;


import com.bfds.validation.Validator;
import com.bfds.validation.message.Message;
import com.bfds.validation.message.MessageListener;

/**
 *  An observer of {@link com.bfds.validation.message.Message}s added to a {@link com.bfds.validation.message.ObservableMessageContext}
 */
public interface ValidationListener extends MessageListener {

    /**
     * A callback method to indicate that the validation process is about to begin.
     */
    void preValidation();

    /**
     * A callback method to indicate that a {@link Validator} is about to be executed.
     *
     * @param validator  The {@link Validator} that is going to be run.
     */
    void preValidation(Validator validator);

    /**
     * A callback method to indicate that a {@link Validator} is about to be executed.
     *
     * @param validator  The {@link Validator} that has just finished execution.
     * @param messages The {@link com.bfds.validation.message.Message}s created by the validator.
     */
    void postValidation(Validator validator, Message... messages);

    /**
     * A callback method to indicate that the validation process has completed.
     *
     * @param messages The {@link com.bfds.validation.message.Message}s created during the validation process.
     */
    void postValidation(Message... messages);

}
