package org.springframework.batch.item;

import com.bfds.saec.batch.file.domain.FileRecord;
import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.hibernate.Session;
import org.springframework.batch.item.ItemProcessor;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
public class FileExportProcessorDecorator extends SetpScopedDecoratingProxyBean<ItemProcessor> implements ItemProcessor {

    @PersistenceContext(unitName = "batchFilesEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Object process(Object item) throws Exception {
        if(target != null) {
            item = target.process(item);
        }

        if( FileRecord.class.isAssignableFrom(item.getClass()))  {
            FileRecord rec = (FileRecord)item;
            rec.setProcessed(Boolean.TRUE);
            rec.setJobExecutionId(stepExecution.getJobExecutionId());
        }
        Session session = (Session) entityManager.getDelegate();
        session.update(item);
        return item;
    }

    @Override
    protected String getTargetBeanName() {
        return FileJobUtils.getFileRecordClassName(stepExecution, this) + "ItemProcessor";
    }

    @Override
    protected boolean allowNullTarget(){
        return true;
    }
}
