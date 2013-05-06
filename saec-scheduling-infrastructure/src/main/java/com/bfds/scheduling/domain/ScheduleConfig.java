package com.bfds.scheduling.domain;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.quartz.CronExpression;
import org.springframework.core.io.UrlResource;
import org.springframework.roo.addon.configurable.RooConfigurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

@RooJavaBean
@RooConfigurable
@RooToString
@Embeddable
public class ScheduleConfig implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
    
    /**
	 * Time to start looking for file for that day
	 */
    @NotNull
    @Size(max=5, min=5)
    //@Column(nullable=false)
	private String startLookupTime;
    
	/**
	 * Time to end looking for a file for that day
	 */	
    @NotNull
    @Size(max=5, min=5)
    //@Column(nullable=false)
	private String endLookupTime;
    
	/**
	 * Time to trigger an error report if no files were processed in the day.
	 */	
	private String errorTriggerTime;
    
	/**
	 * Whether to stop looking once files has been received/send. 
	 * If it's cyclic it should start looking again for same files after processing.
	 */	
    //@Column(nullable=false)
	private Boolean cyclicFlag;
    
	/**
	 *  What days of week to look. Eg. M-F
	 */	
	private String scheduledDays;
	
	/**
	 * The cron expression. This overrides all other scheduling parameters of this class.
	 */		
	private String  cronExpression;
	
	/**
	 * Is this schedule enabled. The Job will at the scheduled time only if it's schedule is enabled.
	 */
	private Boolean enabled;
	
	public String getCronExpression() {
		return StringUtils.hasText(cronExpression) ? cronExpression : generateCronExpression();
	}

	private String generateCronExpression() {
		//TODO:
		return "* * * * * ? ";
	}
	
	public Map<String, String> validate() {
		 Map<String, String> ret = Maps.newHashMap();

			try {
				CronExpression.validateExpression(this.getCronExpression());
			} catch (ParseException e) {
				ret.put("cronExpression", "Invalid Cron Expression. "+e.getMessage());
			}
		return ret;
	}

}
