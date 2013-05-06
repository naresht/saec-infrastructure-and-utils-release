package com.bfds.saec.batch.scheduling.web.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bfds.saec.batch.scheduling.SaecJobLauncherFactory;
import com.bfds.scheduling.domain.JobConfig;

@Controller
@RequestMapping("/job")
public class SaecJobController {
	
	@RequestMapping(value = "/{jobConfigId}", method = RequestMethod.POST)
    public String runJob(Model model, @PathVariable("jobConfigId") Long jobConfigId) {
		JobConfig jobConfig = JobConfig.findJobConfig(jobConfigId);
		model.addAttribute("x", "{}");
		SaecJobLauncherFactory.getSaecJobLauncher(jobConfig, true).run(jobConfig);
        return "jobconfigXmlView";
    }

}
