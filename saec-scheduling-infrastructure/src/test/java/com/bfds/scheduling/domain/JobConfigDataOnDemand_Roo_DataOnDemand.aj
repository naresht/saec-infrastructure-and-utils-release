// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bfds.scheduling.domain;

import com.bfds.scheduling.domain.FileConfig;
import com.bfds.scheduling.domain.JobConfig;
import com.bfds.scheduling.domain.JobConfigDataOnDemand;
import com.bfds.scheduling.domain.ScheduleConfig;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect JobConfigDataOnDemand_Roo_DataOnDemand {
    
    declare @type: JobConfigDataOnDemand: @Component;
    
    private Random JobConfigDataOnDemand.rnd = new SecureRandom();
    
    private List<JobConfig> JobConfigDataOnDemand.data;
    
    public JobConfig JobConfigDataOnDemand.getNewTransientJobConfig(int index) {
        JobConfig obj = new JobConfig();
        setScheduleConfig(obj, index);
        setFileConfig(obj, index);
        setContactEmail(obj, index);
        setContactName(obj, index);
        setContactPhone(obj, index);
        setIncoming(obj, index);
        setJobClass(obj, index);
        setJobId(obj, index);
        setJobName(obj, index);
        setLifecycleExtensions(obj, index);
        setVendorName(obj, index);
        return obj;
    }
    
    public void JobConfigDataOnDemand.setScheduleConfig(JobConfig obj, int index) {
        ScheduleConfig embeddedClass = new ScheduleConfig();
        setScheduleConfigStartLookupTime(embeddedClass, index);
        setScheduleConfigEndLookupTime(embeddedClass, index);
        setScheduleConfigErrorTriggerTime(embeddedClass, index);
        setScheduleConfigCyclicFlag(embeddedClass, index);
        setScheduleConfigScheduledDays(embeddedClass, index);
        setScheduleConfigCronExpression(embeddedClass, index);
        setScheduleConfigEnabled(embeddedClass, index);
        obj.setScheduleConfig(embeddedClass);
    }
    
    public void JobConfigDataOnDemand.setScheduleConfigStartLookupTime(ScheduleConfig obj, int index) {
        String startLookupTime = "sta_" + index;
        if (startLookupTime.length() > 5) {
            startLookupTime = startLookupTime.substring(0, 5);
        }
        obj.setStartLookupTime(startLookupTime);
    }
    
    public void JobConfigDataOnDemand.setScheduleConfigEndLookupTime(ScheduleConfig obj, int index) {
        String endLookupTime = "end_" + index;
        if (endLookupTime.length() > 5) {
            endLookupTime = endLookupTime.substring(0, 5);
        }
        obj.setEndLookupTime(endLookupTime);
    }
    
    public void JobConfigDataOnDemand.setScheduleConfigErrorTriggerTime(ScheduleConfig obj, int index) {
        String errorTriggerTime = "errorTriggerTime_" + index;
        obj.setErrorTriggerTime(errorTriggerTime);
    }
    
    public void JobConfigDataOnDemand.setScheduleConfigCyclicFlag(ScheduleConfig obj, int index) {
        Boolean cyclicFlag = Boolean.TRUE;
        obj.setCyclicFlag(cyclicFlag);
    }
    
    public void JobConfigDataOnDemand.setScheduleConfigScheduledDays(ScheduleConfig obj, int index) {
        String scheduledDays = "scheduledDays_" + index;
        obj.setScheduledDays(scheduledDays);
    }
    
    public void JobConfigDataOnDemand.setScheduleConfigCronExpression(ScheduleConfig obj, int index) {
        String cronExpression = "cronExpression_" + index;
        obj.setCronExpression(cronExpression);
    }
    
    public void JobConfigDataOnDemand.setScheduleConfigEnabled(ScheduleConfig obj, int index) {
        Boolean enabled = Boolean.TRUE;
        obj.setEnabled(enabled);
    }
    
    public void JobConfigDataOnDemand.setFileConfig(JobConfig obj, int index) {
        FileConfig embeddedClass = new FileConfig();
        setFileConfigFilePath(embeddedClass, index);
        setFileConfigArchiveFilePath(embeddedClass, index);
        setFileConfigCreateCnfFile(embeddedClass, index);
        obj.setFileConfig(embeddedClass);
    }
    
    public void JobConfigDataOnDemand.setFileConfigFilePath(FileConfig obj, int index) {
        String filePath = "filePath_" + index;
        obj.setFilePath(filePath);
    }
    
    public void JobConfigDataOnDemand.setFileConfigArchiveFilePath(FileConfig obj, int index) {
        String archiveFilePath = "archiveFilePath_" + index;
        obj.setArchiveFilePath(archiveFilePath);
    }
    
    public void JobConfigDataOnDemand.setFileConfigCreateCnfFile(FileConfig obj, int index) {
        Boolean createCnfFile = Boolean.TRUE;
        obj.setCreateCnfFile(createCnfFile);
    }
    
    public void JobConfigDataOnDemand.setContactEmail(JobConfig obj, int index) {
        String contactEmail = "foo" + index + "@bar.com";
        obj.setContactEmail(contactEmail);
    }
    
    public void JobConfigDataOnDemand.setContactName(JobConfig obj, int index) {
        String contactName = "contactName_" + index;
        obj.setContactName(contactName);
    }
    
    public void JobConfigDataOnDemand.setContactPhone(JobConfig obj, int index) {
        String contactPhone = "contactPhone_" + index;
        obj.setContactPhone(contactPhone);
    }
    
    public void JobConfigDataOnDemand.setIncoming(JobConfig obj, int index) {
        Boolean incoming = Boolean.TRUE;
        obj.setIncoming(incoming);
    }
    
    public void JobConfigDataOnDemand.setJobClass(JobConfig obj, int index) {
        String jobClass = "jobClass_" + index;
        obj.setJobClass(jobClass);
    }
    
    public void JobConfigDataOnDemand.setJobId(JobConfig obj, int index) {
        String jobId = "jobId_" + index;
        obj.setJobId(jobId);
    }
    
    public void JobConfigDataOnDemand.setJobName(JobConfig obj, int index) {
        String jobName = "jobName_" + index;
        obj.setJobName(jobName);
    }
    
    public void JobConfigDataOnDemand.setLifecycleExtensions(JobConfig obj, int index) {
        String lifecycleExtensions = "lifecycleExtensions_" + index;
        obj.setLifecycleExtensions(lifecycleExtensions);
    }
    
    public void JobConfigDataOnDemand.setVendorName(JobConfig obj, int index) {
        String vendorName = "vendorName_" + index;
        obj.setVendorName(vendorName);
    }
    
    public JobConfig JobConfigDataOnDemand.getSpecificJobConfig(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        JobConfig obj = data.get(index);
        Long id = obj.getId();
        return JobConfig.findJobConfig(id);
    }
    
    public JobConfig JobConfigDataOnDemand.getRandomJobConfig() {
        init();
        JobConfig obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return JobConfig.findJobConfig(id);
    }
    
    public boolean JobConfigDataOnDemand.modifyJobConfig(JobConfig obj) {
        return false;
    }
    
    public void JobConfigDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = JobConfig.findJobConfigEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'JobConfig' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<JobConfig>();
        for (int i = 0; i < 10; i++) {
            JobConfig obj = getNewTransientJobConfig(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
