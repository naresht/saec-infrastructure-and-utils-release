package org.springframework.batch.item.file;


import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.springframework.batch.item.*;

public class DelegatingFlatFileRecordItemReader<T> extends SetpScopedDecoratingProxyBean<ItemReader> implements ItemReader<T>, ItemStream {

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
        return "flatFileRecordItemReader";
    }
}
