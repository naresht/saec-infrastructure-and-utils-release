package com.bfds.saec.batch.job;


import com.google.common.collect.Maps;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Map;

public class DbToFlatFileJobBuilder extends AbstractJobBuilder {

    @Override
    protected String getClasspathTemplateFile() {
        return "vm/batch-file-export-from-db.xml";
    }

    @Override
    public Map<String, String> getJobProperties(JobItemGroup itemGroup, BeanDefinitionRegistry registry) {
        return getDefaultJobProperties(itemGroup,registry);
    }

    private Map<String, String> getDefaultJobProperties(JobItemGroup itemGroup, BeanDefinitionRegistry registry) {
        Map<String, String> ret = Maps.newHashMap();
        ret.put("job_id", itemGroup.getJobId());
        ret.put("validator", "fileExportParametersValidator");
        ret.put("step_id", itemGroup.getStepId());
        ret.put("reader", "fileExportReader");
        ret.put("processor", "fileExportProcessorDelegate");
        ret.put("writer", "fileExportWriter");
        ret.put("stepListener", "defaultStepExecutionListener");
        ret.put("retryListener", "retryListener");
        return ret;
    }
}
