package org.springframework.batch.item.xml;


import com.bfds.saec.batch.file.util.FileJobUtils;
import com.google.common.base.Preconditions;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Value;

public class DefaultStaxEventItemReader<T> extends StaxEventItemReader<T> {

    @Value("#{stepExecution}")
    protected StepExecution stepExecution;

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(stepExecution, "stepExecution is null");
        Class<?> fragmentRootElement = FileJobUtils.getFileRecordClass(stepExecution, this) ;
        super.setFragmentRootElementName(fragmentRootElement.getSimpleName());

//        jaxbMarshaller.setContextPath("com.bfds.saec.batch.file.domain.in.infoage_corporate");
//        jaxbMarshaller.setSchema(new ClassPathResource("schema/infoage/corporate-address-research.xsd"));
//        jaxbMarshaller.afterPropertiesSet();
        super.afterPropertiesSet();
    }

}
