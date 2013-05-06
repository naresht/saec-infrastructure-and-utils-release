package org.springframework.batch.item.file.transform;

import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.springframework.batch.item.file.transform.FieldExtractor;


public class FileExportFieldExtractorDelegate extends SetpScopedDecoratingProxyBean<FieldExtractor> implements FieldExtractor {

    @Override
    protected String getTargetBeanName() {
        return FileJobUtils.getFileRecordClassName(stepExecution, this) + "FieldExtractor";
    }

    @Override
    protected String getDefaultTargetBeanName() {
        return "defaultFileExportFieldExtractor";
    }

    @Override
    public Object[] extract(Object item) {
        return target.extract(item);
    }
}
