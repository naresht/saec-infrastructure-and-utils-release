package com.bfds.saec.batch.job;


import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Map;

public class DbToXmlFileJobBuilder extends DbToFlatFileJobBuilder {


    @Override
    public Map<String, String> getJobProperties(JobItemGroup itemGroup, BeanDefinitionRegistry registry) {
        Map<String, String> params = super.getJobProperties(itemGroup, registry);
        params.put("writer", "staxEventItemWriterProxy");
        return params;
    }


}
