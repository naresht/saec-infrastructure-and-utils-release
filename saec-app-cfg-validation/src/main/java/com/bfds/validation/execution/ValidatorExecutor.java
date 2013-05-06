package com.bfds.validation.execution;

import com.bfds.validation.Validator;
import com.bfds.validation.message.Message;
import com.bfds.validation.message.MessageListener;

import java.util.Collection;
import java.util.List;

public interface ValidatorExecutor {

	public List<Message> execute(final Collection<Validator> validators);

    /**
     * Add a {@link ValidationListener} to the executor.
     * @param validationListener
     *
     * @see {@link ValidationListener}
     */
    public void addValidationListener(ValidationListener validationListener);

    /**
     * Add a {@link MessageListener} to the executor.
     * @param messageListener
     *
     * @see {@link MessageListener}
     */
    public void addMessageListener(MessageListener messageListener);

}
