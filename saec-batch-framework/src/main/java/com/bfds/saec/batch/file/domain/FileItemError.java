package com.bfds.saec.batch.file.domain;

import org.springframework.roo.addon.configurable.RooConfigurable;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
  Persistence for all FileItemError errors.
 */
@RooJpaActiveRecord(table="saec_batch_file_record_error", persistenceUnit = "batchFilesEntityManagerFactory")
@RooJavaBean
@RooToString(excludeFields = {"id", "version"})
@RooConfigurable
public class FileItemError implements Serializable {
    /**
     * The lineNumber on the file.
     */
    @Column(nullable = false)
    private int lineNumber;
    /**
     * An FK on {@link org.springframework.batch.core.JobExecution} that processed the file.
     */
    @Column(nullable = false, unique = false)
    private Long jobExecutionId;
    /**
     * The raw data as on file.
     */
    @Column(nullable = false, length = 5000)
    private String rawData;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BatchPhase phase;

    @Column(nullable = false, length = 5000)
    private String errorMessage;

}
