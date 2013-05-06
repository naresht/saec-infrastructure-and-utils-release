package org.springframework.batch.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;


public class DefaultStepExecutionListener implements StepExecutionListener {
    final Logger log = LoggerFactory.getLogger(DefaultStepExecutionListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution){

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution){
        log.info("skip count after reading records "+stepExecution.getReadSkipCount());
        log.info("skip count after processing records "+stepExecution.getProcessSkipCount());
        log.info("skip count after writing records "+stepExecution.getWriteSkipCount());
        log.info("step name: "+stepExecution.getStepName());
        log.info("Job Name: "+stepExecution.getJobExecution().getJobInstance().getJobName());
        log.info("Job start time: "+stepExecution.getJobExecution().getStartTime());
        if(!(ExitStatus.FAILED.getExitCode().equals(
                stepExecution.getExitStatus().getExitCode())) &&
                stepExecution.getSkipCount() > 0) {
            stepExecution.setExitStatus(new ExitStatus("COMPLETED WITH SKIPS"));
        }
        log.info("Step Execution Status: "+stepExecution.getExitStatus().getExitCode());
        return null;
    }
}
