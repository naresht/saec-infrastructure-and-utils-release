/**
 * 
 */
package org.springframework.batch.core.listener;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;

import java.util.List;

public class FlatFileRecordStepListener implements ChunkListener, ItemReadListener,
        ItemProcessListener, ItemWriteListener, SkipListener {

	final Logger log = LoggerFactory.getLogger(FlatFileRecordStepListener.class);

    protected final StepExecution stepExecution;

    public FlatFileRecordStepListener(final StepExecution stepExecution) {
        this.stepExecution = Preconditions.checkNotNull(stepExecution, "stepExecution is null.");
    }

    @Override
    public void beforeChunk() {
        log.debug("Before Chunk  - "+ getJobStr());
    }


    @Override
    public void afterChunk() {
        log.debug("After Chunk  - "+ getJobStr());
    }


    @Override
    public void beforeProcess(Object item) {
        log.debug("Before Process  - "+ getJobStr());
    }


    @Override
    public void afterProcess(Object item, Object result) {
        log.debug("After Process  - "+ getJobStr());
    }


    @Override
    public void onProcessError(Object item, Exception e) {
        log.debug("On Process Error - "+ getJobStr());
    }


    @Override
    public void beforeRead() {
        log.debug("Before Read  - "+ getJobStr());
    }


    @Override
    public void afterRead(Object item) {
        log.debug("After Read  - "+ getJobStr());
    }


    @Override
    public void onReadError(Exception ex) {
        log.error("On Read Error  - "+ getJobStr());
    }


    @Override
    public void beforeWrite(List items) {
        log.error("Before Write  - "+ getJobStr());
    }


    @Override
    public void afterWrite(List items) {
        log.debug("After Write  - "+ getJobStr());
    }


    @Override
    public void onWriteError(Exception exception, List items) {
        log.error("On Write Error  - "+ getJobStr());
    }

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("On Skip In Read  - "+ getJobStr() + t);
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
        log.error("On Skip In Write  - "+ getJobStr() + item + t);
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        log.error("On Skip In Process  - "+ getJobStr() + item + t);
    }

    private transient String jobStr = null;
    private String getJobStr() {
        if(jobStr == null) {
            jobStr =  stepExecution.getJobExecution().getJobInstance().toString();
        }
        return jobStr;
    }
}
