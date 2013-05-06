package com.bfds.saec.batch.job;


import com.bfds.saec.batch.annotations.Job;
import com.bfds.saec.batch.job.JobBuilder;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.List;


public class JobItemGroup {

    private final Class<?> groupId;
    private final MetadataReader itemWithJobMetadata;
    private List<MetadataReader> items = Lists.newArrayList();

    public JobItemGroup(Class<?> id, MetadataReader itemWithJobMetadata) {
        Preconditions.checkNotNull(id, "groupId cannot be null.");
        Preconditions.checkNotNull(itemWithJobMetadata, "itemWithJobMetadata cannot be null.");
        this.itemWithJobMetadata = itemWithJobMetadata;
        this.groupId = id;
    }

    public void add(MetadataReader item) {
        items.add(item);
    }

    public String getJobId() {
        return groupId.getSimpleName() + "Job";
    }

    public String getStepId() {
        return groupId.getSimpleName() + "Step";
    }

    public boolean isSingleItemGroup() {
        return items.size() > 1;
    }

    public Class<? extends JobBuilder> getTemplateProcessor() {
        return (Class<? extends JobBuilder>) itemWithJobMetadata.getAnnotationMetadata().getAnnotationAttributes(Job.class.getName()).get("processor");
    }

    public Class<?> getGroupId() {
        return groupId;
    }
}
