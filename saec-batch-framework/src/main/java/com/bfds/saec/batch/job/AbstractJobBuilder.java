package com.bfds.saec.batch.job;


import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public abstract class AbstractJobBuilder implements JobBuilder {

    protected abstract String getClasspathTemplateFile();

    @Override
    public void registerJobDefinition(JobItemGroup itemGroup, BeanDefinitionRegistry registry) {
        String xml =  processTemplate(getJobProperties(itemGroup, registry));
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(registry);
        xmlReader.loadBeanDefinitions(new FileSystemResource(createTempFile(xml)));
    }

    private File createTempFile(String data) {
        try {
            File temp = File.createTempFile("job-support-" + System.currentTimeMillis(), "xml");
            FileUtils.writeStringToFile(temp, data);
            return temp;
        } catch (IOException e) {
            throw new IllegalStateException("Error creating job", e);
        }
    }

    protected abstract Map<String, String> getJobProperties(JobItemGroup itemGroup, BeanDefinitionRegistry registry);

    private String processTemplate(Map<String, String> jobProperties) {
        VelocityBean.initVelocity();
        VelocityContext context = new VelocityContext();
        context.put("p", jobProperties);
        StringWriter w = new StringWriter();
        try {
            Velocity.mergeTemplate(
                    getClasspathTemplateFile(),
                    "ISO-8859-1", context, w);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return w.toString();
    }


    public static class VelocityBean {
        static {
            try {
                Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
                Velocity.setProperty("class.resource.loader.class",
                        ClasspathResourceLoader.class.getName());
                Velocity.init();

            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        public static void initVelocity() {
            // Just to load the class.
        }
    }

}
