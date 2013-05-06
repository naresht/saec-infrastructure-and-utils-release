package com.bfds.validation.execution;

import com.bfds.validation.Validator;
import com.bfds.validation.message.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidatorExecutorImpl implements ValidatorExecutor, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorExecutorImpl.class);

    private ApplicationContext applicationContext;

    private List<ValidationListener> validationListeners = Lists.newArrayList();

    private List<MessageListener> messageListeners = Lists.newArrayList();

    @Override
    public List<Message> execute(final Collection<Validator> validators) {
        // ret holds all the messages from all the validators
        final List<Message> ret = new ArrayList<Message>();

        List<? extends ValidationListener> validationListeners = this.getValidationListeners();
        for(ValidationListener validationListener : validationListeners) {
            validationListener.preValidation();
        }

        // iterate over given validators
        for (final Validator validator : validators) {
            ValidationMessageContext messageContext = this.getNewMessageContext();
            if(messageContext instanceof ObservableMessageContext ) {
                  for(ValidationListener validationListener : validationListeners) {
                    ((ObservableMessageContext)messageContext).addMessageListener(validationListener);
                  }
                for(MessageListener messageListener : messageListeners) {
                    ((ObservableMessageContext)messageContext).addMessageListener(messageListener);
                }
            }
            // default source is the class name itself
            messageContext.setDefaultSource(validator.getClass().getName());
            for(ValidationListener validationListener : validationListeners) {
                validationListener.preValidation(validator);
            }
            // run validate
            validator.validate(messageContext);
            for(ValidationListener validationListener : validationListeners) {
                validationListener.postValidation(validator, messageContext.getAllMessages());
            }
            // accumulate all the messages
            ret.addAll(java.util.Arrays.asList(messageContext.getAllMessages()));
        }
        for(ValidationListener validationListener : validationListeners) {
            validationListener.postValidation(ret.toArray(new Message[ret.size()]));
        }
        return ret;
    }

    protected ValidationMessageContext getNewMessageContext() {
        ValidationMessageContext ret = null;
        if (this.applicationContext != null) {
            if (this.applicationContext.containsBean("validationMessageContext")) {
                ret = applicationContext.getBean("validationMessageContext", ValidationMessageContext.class);
            }

        }
        return ret == null ? createDefaultValidationMessageContext() : ret;
    }

    private ValidationMessageContext createDefaultValidationMessageContext() {
        ObservableMessageContext ret = new DefaultValidationMessageContext();
        return ret;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<ValidationListener> getValidationListeners() {
        List<ValidationListener> ret = Lists.newArrayList();
        ret.addAll(this.validationListeners);
        // A ValidationListener is also a messageListener. So if a ValidationListener is added to the messageListeners list then
        // it must receive validation events also.
        for(MessageListener messageListener : messageListeners) {
            if(messageListener instanceof ValidationListener) {
                ret.add((ValidationListener) messageListener);
            }
        }
        return Collections.unmodifiableList(ret);
    }

    public void setValidationListeners(List<ValidationListener> validationListeners) {
        this.validationListeners = validationListeners;
    }

    @Override
    public void addValidationListener(ValidationListener validationListener) {
        this.validationListeners.add(validationListener);
    }

    public void setMessageListeners(List<MessageListener> messageListeners) {
        this.messageListeners = messageListeners;
    }

    @Override
    public void addMessageListener(MessageListener messageListener) {
        messageListeners.add(messageListener);
    }
}
