package org.springframework.batch.item.xml;


import com.bfds.saec.batch.annotations.XmlSchemaLocation;
import com.bfds.saec.batch.file.util.FileJobUtils;
import com.google.common.base.Preconditions;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.GenericMarshaller;
import org.springframework.oxm.GenericUnmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.oxm.mime.MimeContainer;
import org.springframework.oxm.mime.MimeMarshaller;
import org.springframework.oxm.mime.MimeUnmarshaller;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;
import java.lang.reflect.Type;

public class DefaultJaxb2Marshaller implements MimeMarshaller, MimeUnmarshaller, GenericMarshaller, GenericUnmarshaller, BeanClassLoaderAware,
        ResourceLoaderAware, InitializingBean {

    private Jaxb2Marshaller target;
    private ClassLoader classLoader;
    private ResourceLoader resourceLoader;
    @Value("#{stepExecution}")
    protected StepExecution stepExecution;

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(stepExecution, "stepExecution is null");
        target = new Jaxb2Marshaller();
        target.setResourceLoader(resourceLoader);
        target.setBeanClassLoader(classLoader);
        setXmlSchemaIfPresent(target);
        target.setContextPath(FileJobUtils.getFileRecordClass(stepExecution, this).getPackage().getName());
        target.afterPropertiesSet();
    }

    private void setXmlSchemaIfPresent(Jaxb2Marshaller target) {
        Class<?> clazz = FileJobUtils.getFileRecordClass(stepExecution, this);
        XmlSchemaLocation annotation = clazz.getAnnotation(XmlSchemaLocation.class);
        if(annotation != null) {
            Preconditions.checkNotNull(annotation, "The class %s must have annotation %s", clazz.getName(), XmlSchemaLocation.class.getName());
            target.setSchema(new ClassPathResource(annotation.value()));
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this. classLoader = classLoader;
    }

    @Override
    public boolean supports(Type genericType) {
        return target.supports(genericType);
    }



    @Override
    public void marshal(Object graph, Result result, MimeContainer mimeContainer) throws XmlMappingException, IOException {
        target.marshal(graph, result, mimeContainer);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return target.supports(clazz);
    }

    @Override
    public Object unmarshal(Source source) throws IOException, XmlMappingException {
        return target.unmarshal(source);
    }

    @Override
    public void marshal(Object graph, Result result) throws IOException, XmlMappingException {
        target.marshal(graph, result);
    }

    @Override
    public Object unmarshal(Source source, MimeContainer mimeContainer) throws XmlMappingException, IOException {
        return target.unmarshal(source, mimeContainer);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
