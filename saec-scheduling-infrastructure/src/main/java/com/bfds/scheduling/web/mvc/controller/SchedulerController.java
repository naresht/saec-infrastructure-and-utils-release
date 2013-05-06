package com.bfds.scheduling.web.mvc.controller;

import com.bfds.scheduling.domain.JobConfig;
import com.bfds.scheduling.service.SchedulingService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    @Qualifier("schedulingServiceImpl")
    private SchedulingService schedulingService;

    @RequestMapping(method = RequestMethod.PUT, params = "refresh")
    public ModelAndView loadJobsAndTriggersConfiguratoin() {
        schedulingService.loadJobsAndTriggersConfiguratoin();
        return success();
    }

    @RequestMapping(value = "/holidays", method = RequestMethod.PUT)
    public ModelAndView registerHolidays() {
        schedulingService.registerHolidays();
        return success();
    }

    @RequestMapping(value = "/{jobConfigId}", method = RequestMethod.POST)
    public ModelAndView rescheduleJob(@PathVariable Long jobConfigId) {
        JobConfig config = JobConfig.findJobConfig(jobConfigId);
        schedulingService.rescheduleJob(config);
        return success();
    }


    private ModelAndView success() {
        Map<String, String> model = Maps.newHashMap();
        model.put("result", "success");
        return new ModelAndView("jobconfigXmlView", model);
    }

    public void scheduleJob(JobConfig config) {
        schedulingService.scheduleJob(config);
    }

    public void enableJobSchedule(Long id, Boolean enabled) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
}
