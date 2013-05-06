package org.springframework.batch.item.file;


import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;

public class FileExportItemWriter extends FlatFileItemWriter {

    @Override
    public void setHeaderCallback(FlatFileHeaderCallback headerCallback) {
        if(headerCallback != null && !isNullHeaderCallback(headerCallback)) {
            super.setHeaderCallback(headerCallback);
        } else {
        super.setHeaderCallback(null);
        }
    }

    private boolean isNullHeaderCallback(FlatFileHeaderCallback headerCallback) {
      StringWriter writer = new StringWriter();
        try {
            headerCallback.writeHeader(writer);
            return !StringUtils.hasLength(writer.getBuffer());
        } catch (IOException e) {
            throw new IllegalStateException("Error in validating " + FlatFileHeaderCallback.class.getName(), e);
        }
    }

    @Override
    public void setFooterCallback(FlatFileFooterCallback footerCallback) {
        if(!isNullFooterCallback(footerCallback)) {
            super.setFooterCallback(footerCallback);
        } else {
            super.setFooterCallback(null);
        }
    }

    private boolean isNullFooterCallback(FlatFileFooterCallback footerCallback) {
        if(footerCallback == null) {
            return true;
        }
        StringWriter writer = new StringWriter();
        try {
            footerCallback.writeFooter(writer);
            return FileExportFooterCallBackDelegate.IS_NULL.equals(writer.getBuffer().toString());
        } catch (IOException e) {
            throw new IllegalStateException("Error in validating " + FlatFileHeaderCallback.class.getName(), e);
        }
    }

}
