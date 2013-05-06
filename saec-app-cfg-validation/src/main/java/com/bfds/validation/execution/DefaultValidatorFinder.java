package com.bfds.validation.execution;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bfds.validation.Validator;

public class DefaultValidatorFinder implements ValidatorFinder, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Collection<Validator> find() {
        final List<Validator> sortedInstances = new LinkedList<Validator>();
        for(ApplicationContext ac =  this.applicationContext; ac != null; ac = ac.getParent()) {
            sortedInstances.addAll(ac.getBeansOfType(Validator.class).values());
        }
        java.util.Collections.sort(sortedInstances, Validator.Comparator.INSTANCE);
        return sortedInstances;
    }

    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
