package com.bfds.saec.batch.file.util;



import org.springframework.batch.core.StepExecution;
import org.springframework.util.ClassUtils;

public final class FileJobUtils {

    private FileJobUtils() {}

    public static String getFileRecordClassName(final StepExecution stepExecution, Object obj) {
        final String classStr =  stepExecution.getJobExecution().getJobInstance().getJobParameters().getString("fileRecordClass");
        return getFileRecordClass(stepExecution, obj).getSimpleName();
    }

    public static Class<?> getFileRecordClass(final StepExecution stepExecution, Object obj) {
        final String classStr =  stepExecution.getJobExecution().getJobInstance().getJobParameters().getString("fileRecordClass");
        return ClassUtils.resolveClassName(classStr, obj.getClass().getClassLoader());
    }
}
