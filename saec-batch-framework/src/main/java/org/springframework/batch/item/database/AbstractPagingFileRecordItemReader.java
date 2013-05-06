package org.springframework.batch.item.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * A {@link AbstractPagingItemReader} that does not extend {@link org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader}
 */
public abstract class AbstractPagingFileRecordItemReader<T> implements ItemReader<T>, ItemStream, InitializingBean {

    protected Log logger = LogFactory.getLog(getClass());

    private volatile boolean initialized = false;

    private int pageSize = 10;

    private volatile int current = 0;

    private volatile int page = 0;

    protected volatile List<T> results;

    private Object lock = new Object();

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            doOpen();
        }
        catch (Exception e) {
            throw new ItemStreamException("Failed to initialize the reader", e);
        }
    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // no op
    }

    public void close() throws ItemStreamException {
        try {
            doClose();
        }
        catch (Exception e) {
            throw new ItemStreamException("Error while closing item reader", e);
        }
    }

    public final T read() throws Exception, UnexpectedInputException, ParseException {
        return doRead();
    }

    /**
     * The current page number.
     * @return the current page
     */
    public int getPage() {
        return page;
    }

    /**
     * The page size configured for this reader.
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * The number of rows to retrieve at a time.
     *
     * @param pageSize the number of rows to fetch per page
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Check mandatory properties.
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(pageSize > 0, "pageSize must be greater than zero");
    }


    protected T doRead() throws Exception {

        synchronized (lock) {

            if (results == null || current >= pageSize) {

                if (logger.isDebugEnabled()) {
                    logger.debug("Reading page " + getPage());
                }

                doReadPage();
                page++;
                if (current >= pageSize) {
                    current = 0;
                }

            }

            int next = current++;
            if (next < results.size()) {
                return results.get(next);
            }
            else {
                return null;
            }

        }

    }

    abstract protected void doReadPage();


    protected void doOpen() throws Exception {

        Assert.state(!initialized, "Cannot open an already opened ItemReader, call close first");
        initialized = true;

    }


    protected void doClose() throws Exception {

        synchronized (lock) {
            initialized = false;
            current = 0;
            page = 0;
            results = null;
        }

    }
}