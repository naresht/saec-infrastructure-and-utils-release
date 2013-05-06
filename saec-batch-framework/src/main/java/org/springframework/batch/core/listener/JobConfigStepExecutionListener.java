package org.springframework.batch.core.listener;

import com.bfds.scheduling.domain.JobConfig;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Listerner interface for the step life cycle of a {@link Job} that has a corresponding {@link com.bfds.scheduling.domain.JobConfig}.
 *
 * If there exists a {@link com.bfds.scheduling.domain.JobConfig} where  {@link org.springframework.batch.core.Job#getName()} = {@link com.bfds.scheduling.domain.JobConfig#getJobId()}
 * then {@link Job} has a corresponding {@link com.bfds.scheduling.domain.JobConfig}.
 *
 * This listener must be configured as {@link org.springframework.batch.core.scope.StepScope}d to work correctly.
 *
 * This listener must be configured only for {@link Job}s that have a corresponding {@link com.bfds.scheduling.domain.JobConfig}
 *
 * Sample configuration
 *
 *     <bean id="fileRecordStepListener" class="org.springframework.batch.core.listener.JobConfigStepExecutionListener" scope="step" >
 *          <constructor-arg name="stepExecution" value="#{stepExecution}"></constructor-arg>
 *      </bean>
 *
 */
public class JobConfigStepExecutionListener implements ItemWriteListener, InitializingBean, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(JobConfigStepExecutionListener.class);

    protected final StepExecution stepExecution;

    private List<ItemWriteListener> itemWriteListeners = Lists.newArrayList();

    private ApplicationContext applicationContext;

    public JobConfigStepExecutionListener(final StepExecution stepExecution) {
        this.stepExecution = Preconditions.checkNotNull(stepExecution, "stepExecution is null.");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(applicationContext, "applicationContext is null");
        setupListeners();
    }

    /**
     * Looks-up all {@link StepListener}s configured for the {@link JobConfig}.
     *
     */
    private void setupListeners() {
        final JobConfig jobConfig = getJobConfigByJobId();
        if(StringUtils.hasText(jobConfig.getLifecycleExtensions())) {
            for(String beanId : jobConfig.getLifecycleExtensions().split(",")) {
                StepListener listener = applicationContext.getBean(beanId.trim(), StepListener.class);
                if(listener instanceof ItemWriteListener) {
                    itemWriteListeners.add((ItemWriteListener)listener);
                } else {
                    log.info("Only listeners of type " + ItemWriteListener.class + " are supported.");
                }
            }
        }
    }

    //Increased visibility for testing only.
    protected JobConfig getJobConfigByJobId() {
        return JobConfig.findJobConfigByJobId(stepExecution.getJobExecution().getJobInstance().getJobName());
    }

    @Override
    public void beforeWrite(List items) {
        for(ItemWriteListener listener : itemWriteListeners) {
            listener.beforeWrite(items);
        }
    }

    @Override
    public void afterWrite(List items) {
        for(ItemWriteListener listener : itemWriteListeners) {
            listener.afterWrite(items);
        }
    }

    @Override
    public void onWriteError(Exception exception, List items) {
        for(ItemWriteListener listener : itemWriteListeners) {
            listener.onWriteError(exception, items);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<ItemWriteListener> getItemWriteListeners() {
        return Collections.unmodifiableList(itemWriteListeners);
    }
}
