package org.springframework.batch.item.file;

import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;


public class FileExportHeaderCallBackDelegate extends SetpScopedDecoratingProxyBean<FlatFileHeaderCallback> implements FlatFileHeaderCallback {

    @Override
    public void writeHeader(Writer writer) throws IOException {
        if(target != null) {
            target.writeHeader(writer);
        }
    }

    @Override
    protected String getTargetBeanName() {
        return FileJobUtils.getFileRecordClassName(stepExecution, this) + "HeaderCallback";
    }

    @Override
    protected boolean allowNullTarget(){
        return true;
    }

}
