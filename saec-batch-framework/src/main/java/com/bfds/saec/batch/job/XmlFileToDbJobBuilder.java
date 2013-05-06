package com.bfds.saec.batch.job;


import com.bfds.saec.batch.job.FlatFileToDbJobBuilder;
import com.bfds.saec.batch.job.JobItemGroup;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Map;

public class XmlFileToDbJobBuilder extends FlatFileToDbJobBuilder {

    @Override
    public Map<String, String> getJobProperties(JobItemGroup itemGroup, BeanDefinitionRegistry registry) {
        Map<String, String> params = super.getJobProperties(itemGroup, registry);
        params.put("reader", "staxEventItemReaderProxy");
        return params;
    }


}
