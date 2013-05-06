package org.springframework.batch.core.listener;


import com.bfds.scheduling.domain.JobConfig;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepListener;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import static org.fest.assertions.Assertions.assertThat;
import java.util.List;

import static org.mockito.Mockito.*;

@MockStaticEntityMethods
@RunWith(MockitoJUnitRunner.class)
public class JobConfigStepExecutionListenerTest {

    private JobConfigStepExecutionListener listener;
    private ApplicationContext applicationContext;
    private ItemWriteListener itemWriteListener;

    @Before
    public void before() {

        listener = new JobConfigStepExecutionListener(mock(StepExecution.class)) {

            protected JobConfig getJobConfigByJobId() {
                JobConfig jobConfig = mock(JobConfig.class);
                when(jobConfig.getLifecycleExtensions()).thenReturn("a,b,c");
                return jobConfig;
            }
        };
        applicationContext = mock(ApplicationContext.class);
        itemWriteListener = mock(ItemWriteListener.class);
        listener.setApplicationContext(applicationContext);
    }


    @Test
    public void allListenersMustBeLookupOnPostCreate() throws Exception {
        when(applicationContext.getBean(anyString(), eq(StepListener.class))).thenReturn(itemWriteListener);

        listener.afterPropertiesSet();

        verify(applicationContext, times(3)).getBean( anyString(), eq(StepListener.class));

        assertThat(listener.getItemWriteListeners()).hasSize(3);
    }

    @Test
    public void beforeWriteMustBeCalledOnAllListeners() throws Exception {
        when(applicationContext.getBean(anyString(), eq(StepListener.class))).thenReturn(itemWriteListener);

        listener.afterPropertiesSet();
        listener.beforeWrite(Lists.newArrayList());

        verify(itemWriteListener, times(3)).beforeWrite(anyList());
    }

    @Test
    public void afterWriteMustBeCalledOnAllListeners() throws Exception {
        when(applicationContext.getBean(anyString(), eq(StepListener.class))).thenReturn(itemWriteListener);

        listener.afterPropertiesSet();
        listener.afterWrite(Lists.newArrayList());

        verify(itemWriteListener, times(3)).afterWrite(anyList());
    }

    @Test
    public void onWriteErrorMustBeCalledOnAllListeners() throws Exception {
        when(applicationContext.getBean(anyString(), eq(StepListener.class))).thenReturn(itemWriteListener);

        listener.afterPropertiesSet();
        listener.onWriteError(new Exception(), Lists.newArrayList());

        verify(itemWriteListener, times(3)).onWriteError((Exception)anyObject(), anyList());
    }

}
