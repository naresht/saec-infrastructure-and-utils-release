package com.bfds.saec.batch.file.util;


import com.google.common.base.Preconditions;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Value;

public abstract class SetpScopedDecoratingProxyBean<T> extends DecoratingProxyBean<T>  {

    @Value("#{stepExecution}")
    protected StepExecution stepExecution;

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(stepExecution, "stepExecution is null");
        super.afterPropertiesSet();
    }
}
