package org.springframework.batch.item.xml;


import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;


public class StaxEventItemReaderProxy<T> extends SetpScopedDecoratingProxyBean<ItemReader> implements  ItemStream, ResourceAwareItemReaderItemStream<T>, InitializingBean  {

    private Resource resource;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        setResource(resource);
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return (T) target.read();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        ((ItemStream)target).open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        ((ItemStream)target).update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        ((ItemStream)target).close();
    }

    @Override
    protected String getTargetBeanName() {
        return FileJobUtils.getFileRecordClassName(stepExecution, this) + "ItemReader";
    }

    @Override
    protected String getDefaultTargetBeanName() {
        return "xmlFileItemReader";
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        if(target != null) {
            ((ResourceAwareItemReaderItemStream)target).setResource(resource);
        }
    }
}
