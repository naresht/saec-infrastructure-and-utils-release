package com.bfds.scheduling.web.mvc.controller;

import com.bfds.scheduling.domain.JobConfig;
import com.bfds.scheduling.service.JobConfigService;
import com.bfds.scheduling.service.SchedulingService;
import com.google.common.collect.Maps;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jobconfig")
public class JobConfigController {

    @Autowired
    @Qualifier("jobConfigServiceImpl")
    private JobConfigService jobConfigService;


    private ModelAndView success() {
        Map<String, String> model = Maps.newHashMap();
        model.put("result", "success");
        return new ModelAndView("jobconfigXmlView", model);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getJobConfigList(Model model) {
        model.addAttribute(jobConfigService.getJobConfigList());
        return "jobconfigXmlView";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findJobConfig(Model model, @PathVariable("id") Long id) {
        model.addAttribute(JobConfig.findJobConfig(id));
        return "jobconfigXmlView";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String saveJobConfig(Model model, @PathVariable("id") Long id, @RequestBody String json) {
    	JobConfig jobConfig = jsonToObject(json, new TypeReference<JobConfig>() { });
        jobConfigService.saveJobConfig(jobConfig);
        model.addAttribute("x", "{}");
        return "jobconfigXmlView";
    }
    
    private <T> T jsonToObject(String json, TypeReference<T> typeRef) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(json, typeRef);
        } catch (IOException ex) {
            throw new ResourceAccessException("I/O error: " + ex.getMessage(), ex);
        }
    }

    public void setJobConfigService(JobConfigService jobConfigService) {
        this.jobConfigService = jobConfigService;
    }
}
