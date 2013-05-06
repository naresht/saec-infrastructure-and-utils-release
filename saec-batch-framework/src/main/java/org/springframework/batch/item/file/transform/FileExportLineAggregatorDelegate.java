package org.springframework.batch.item.file.transform;

import com.bfds.saec.batch.file.domain.FileRecord;
import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.springframework.batch.item.file.transform.LineAggregator;


public class FileExportLineAggregatorDelegate extends SetpScopedDecoratingProxyBean<LineAggregator> implements LineAggregator {

    @Override
    protected String getTargetBeanName() {
        return FileJobUtils.getFileRecordClassName(stepExecution, this) + "LineAggregator";
    }

    @Override
    public String aggregate(Object item) {
        String ret =  target.aggregate(item);
        if( FileRecord.class.isAssignableFrom(item.getClass()))  {
            ((FileRecord)item).setRawData(ret);
        }
        return ret;
    }

    @Override
    protected String getDefaultTargetBeanName() {
        return "defaultFileExportLineAggregator";
    }
}
