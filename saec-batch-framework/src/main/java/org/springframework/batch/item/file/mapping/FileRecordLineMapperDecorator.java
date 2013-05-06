package org.springframework.batch.item.file.mapping;


import com.bfds.saec.batch.file.domain.BatchPhase;
import com.bfds.saec.batch.file.domain.FileItemError;
import com.bfds.saec.batch.file.domain.FileRecord;
import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.springframework.batch.item.file.LineMapper;

import java.io.PrintWriter;
import java.io.StringWriter;

public class FileRecordLineMapperDecorator extends SetpScopedDecoratingProxyBean<LineMapper> implements LineMapper {

    @Override
    public Object mapLine(final String line, final int lineNumber) throws Exception {

       try {
           Object obj =   target.mapLine(line, lineNumber );
           if( FileRecord.class.isAssignableFrom(obj.getClass()))  {
              FileRecord rec =  (FileRecord) obj;
               rec.setJobExecutionId(stepExecution.getJobExecutionId());
               rec.setLineNumber(lineNumber);
               rec.setRawData(line);
           }

           return obj;
       } catch(final Exception e) {
           saveError(line, lineNumber, e);
           throw e;
       }
    }

    private void saveError(String line, int lineNumber, final Exception e) {
        FileItemError recError = new  FileItemError();
        recError.setRawData(line);
        recError.setJobExecutionId(stepExecution.getJobExecutionId());
        recError.setLineNumber(lineNumber);
        recError.setPhase(BatchPhase.READ);
        recError.setErrorMessage(getErrorMessage(e));
        recError.persist();
    }

    private String getErrorMessage(final Exception e) {
        final StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String ret =  sw.getBuffer().toString();
        if(ret.length() > 5000) {
            ret = ret.substring(0, 4999);
        }
        return ret;
    }

    @Override
    protected String getTargetBeanName() {
        return FileJobUtils.getFileRecordClassName(stepExecution, this) + "LineMapper";
    }

    @Override
    protected String getDefaultTargetBeanName() {
        return "defaultFileRecordLineMapper";
    }
}
