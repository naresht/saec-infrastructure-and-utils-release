package org.springframework.batch.item;

import com.bfds.saec.batch.file.domain.FileRecord;
import com.google.common.base.Preconditions;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

/**
 *
 */
public class FileImportProcessorDecorator implements ItemProcessor, InitializingBean {

    @Value("#{stepExecution}")
    protected StepExecution stepExecution;

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(stepExecution, "stepExecution is null");

    }

    @Override
    public Object process(final Object item) throws Exception {
        if( FileRecord.class.isAssignableFrom(item.getClass()))  {
            FileRecord rec =  (FileRecord) item;
            rec.setJobExecutionId(stepExecution.getJobExecutionId());
            checkDda(rec);
        }
        return item;
    }

	private void checkDda(FileRecord rec) {
		if(!StringUtils.hasText(rec.getDda())) {
			rec.setDda(stepExecution.getJobParameters().getString("dda"));
		}				
	}

}
