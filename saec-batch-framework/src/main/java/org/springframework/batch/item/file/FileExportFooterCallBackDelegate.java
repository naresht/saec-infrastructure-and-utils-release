package org.springframework.batch.item.file;

import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.springframework.batch.item.file.FlatFileFooterCallback;

import java.io.IOException;
import java.io.Writer;


public class FileExportFooterCallBackDelegate extends SetpScopedDecoratingProxyBean<FlatFileFooterCallback> implements FlatFileFooterCallback {
    public static final String IS_NULL = "NO FOOTER FOR THIS JOB";
    @Override
    protected String getTargetBeanName() {
        return FileJobUtils.getFileRecordClassName(stepExecution, this) + "FooterCallback";
    }

    @Override
    protected boolean allowNullTarget(){
        return true;
    }

    @Override
    public void writeFooter(Writer writer) throws IOException {
        if(target != null) {
            target.writeFooter(writer);
        } else {
            writer.write(IS_NULL);
        }
    }
}
