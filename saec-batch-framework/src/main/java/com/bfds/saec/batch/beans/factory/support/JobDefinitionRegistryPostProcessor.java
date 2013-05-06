package com.bfds.saec.batch.beans.factory.support;

import com.bfds.saec.batch.annotations.Group;
import com.bfds.saec.batch.annotations.Job;
import com.bfds.saec.batch.job.JobBuilder;
import com.bfds.saec.batch.job.JobItemGroup;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class JobDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
    private static final String BATCH_CLASS_RESOURCE_PATTERN = "/**/*.class";
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private String packageToScan = "";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        final List<MetadataReader> jobClassMetadataList = getClassesWithAnnotation(Job.class, packageToScan );
        Collection<JobItemGroup> batchGroups = Lists.newArrayList();
        for(MetadataReader m : jobClassMetadataList) {
            batchGroups.add(buildJobItemGroup(m));
        }
        for(JobItemGroup jobItemGroup : batchGroups) {
            createBatchJobDefinition(jobItemGroup, registry);
        }
    }

    private JobItemGroup buildJobItemGroup(MetadataReader metaDataReader) {
        Class<?> groupId =  getGroupValue(metaDataReader);
        if(groupId == null) {
            groupId = ClassUtils.resolveClassName(metaDataReader.getClassMetadata().getClassName(), this.getClass().getClassLoader());
        }
        JobItemGroup ret = new JobItemGroup(groupId, metaDataReader);
        final List<MetadataReader> jobGroupClassMetadataList = getClassesWithAnnotation(Group.class, packageToScan );
        for(MetadataReader mr : jobGroupClassMetadataList) {
            if(getGroupValue(mr) == ret.getGroupId()) {
                ret.add(mr);
            }
        }
        return ret;
    }

    private Class<?> getGroupValue(MetadataReader metaDataReader) {
        Map<String, Object> attributes = metaDataReader.getAnnotationMetadata().getAnnotationAttributes(Group.class.getName());
        if(attributes != null) {
            return (Class<?>) attributes.get("value");
        }
        return null;
    }

    private void createBatchJobDefinition(JobItemGroup jobItemGroup, BeanDefinitionRegistry registry) {

        JobBuilder processor;
        try {
            processor = jobItemGroup.getTemplateProcessor().newInstance();
        } catch (Exception e) {
           throw new IllegalStateException("error creating object of class " + jobItemGroup.getTemplateProcessor(), e);
        }
        processor.registerJobDefinition(jobItemGroup, registry);
    }

    private List<MetadataReader> getClassesWithAnnotation(final Class<? extends Annotation> annotation, String packageToScan) {
        Preconditions.checkNotNull(annotation, "annotation is null");
        Preconditions.checkArgument(StringUtils.hasText(packageToScan), "packageToScan is null or empty");

        List<MetadataReader> scannedBatchClasses = Lists.newArrayList();
        TypeFilter batchClassFilter = new AnnotationTypeFilter(annotation, false);
        try{
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
            ClassUtils.convertClassNameToResourcePath(packageToScan) + BATCH_CLASS_RESOURCE_PATTERN;
            Resource[] resources = this.resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    if (batchClassFilter.match(reader, readerFactory)) {
                        scannedBatchClasses.add(reader);
                    }
                }
            }
            return scannedBatchClasses;
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to scan classpath for batch classes", ex);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //noop
    }

    public void setPackageToScan(String packageToScan) {
        this.packageToScan = packageToScan;
    }
}
