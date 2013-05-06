package com.bfds.scheduling.web.mvc.controller;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import com.bfds.scheduling.service.JobConfigService;
import com.bfds.scheduling.web.mvc.controller.JobConfigController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.bfds.scheduling.domain.JobConfig;
import org.springframework.ui.ExtendedModelMap;

@RunWith(MockitoJUnitRunner.class)
public class JobConfigControllerTest {


    @Test
    public void testListJobConfig() {
        JobConfigService service = mock(JobConfigService.class);
        JobConfigController controller = new JobConfigController();
        controller.setJobConfigService(service);
        when(service.getJobConfigList()).thenReturn(new ArrayList<JobConfig>());
        controller.getJobConfigList(new ExtendedModelMap());
        verify(service).getJobConfigList();
    }


}
