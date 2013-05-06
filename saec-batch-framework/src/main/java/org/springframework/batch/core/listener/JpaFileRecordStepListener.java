/**
 * 
 */
package org.springframework.batch.core.listener;

import org.springframework.batch.core.StepExecution;

public class JpaFileRecordStepListener extends FlatFileRecordStepListener  {


    public JpaFileRecordStepListener(final StepExecution stepExecution) {
        super(stepExecution);
    }
}
