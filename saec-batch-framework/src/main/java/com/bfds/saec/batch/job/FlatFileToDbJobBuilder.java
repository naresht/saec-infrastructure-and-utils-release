package com.bfds.saec.batch.job;


import com.google.common.collect.Maps;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Map;

public class FlatFileToDbJobBuilder extends AbstractJobBuilder {

    @Override
    protected String getClasspathTemplateFile() {
        return "vm/batch-file-import-to-db.xml";
    }

    @Override
    public Map<String, String> getJobProperties(JobItemGroup itemGroup, BeanDefinitionRegistry registry) {
        return getDefaultJobProperties(itemGroup,registry);
    }

    private Map<String, String> getDefaultJobProperties(JobItemGroup itemGroup, BeanDefinitionRegistry registry) {
        Map<String, String> ret = Maps.newHashMap();
        ret.put("job_id", itemGroup.getJobId());
        ret.put("validator", "fileImportParametersValidator");
        ret.put("step_id", itemGroup.getStepId());
        ret.put("reader", "fileRecordItemReader");
        ret.put("processor", "fileRecordItemProcessor");
        ret.put("writer", "fileRecordItemWriter");
        ret.put("stepListener", "fileRecordStepListener");
        ret.put("retryListener", "retryListener");
        return ret;
    }
}
