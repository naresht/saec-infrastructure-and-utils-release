package org.springframework.batch.item.xml;



import com.bfds.saec.batch.annotations.Job;
import com.bfds.saec.batch.file.util.FileJobUtils;
import com.google.common.collect.Maps;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import java.util.Map;

public class DefaultStaxEventItemWriter extends StaxEventItemWriter {

    @Value("#{stepExecution}")
    protected StepExecution stepExecution;

    @Override
    public void afterPropertiesSet() throws Exception {
        Class<?> clazz = FileJobUtils.getFileRecordClass(stepExecution, this) ;
        this.setRootTagName(getRootTagName(clazz));
        this.setRootElementAttributes(getRootElementAttributes(clazz));
        super.afterPropertiesSet();
    }

    private String  getRootTagName(Class<?> clazz) {
        String ret = null;
        Job annotation = clazz.getAnnotation(Job.class);
        return getDefaultRootTagNamespacePrefix() + ":" + annotation.xmlRootTagName();
    }

    private Map<String, String> getRootElementAttributes(Class<?> clazz) {
        Map<String, String> ret = Maps.newHashMap();
        Job annotation = clazz.getAnnotation(Job.class);
        ret.put("xmlns:" + getDefaultRootTagNamespacePrefix(),  annotation.xmlRootTagNamespace());
        return ret;

    }

    protected String getDefaultRootTagNamespacePrefix() {
        return "ns2";
    }
}
