package com.bfds.saec.batch.file.domain;

import org.springframework.roo.addon.configurable.RooConfigurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Base class for all entities that represent a record(header. footer or data) in the flat file.
 *
 */

@MappedSuperclass
@RooJavaBean
@RooToString
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class FileRecord implements Entity {
    public static final String UNWANTED_TRAILER_HOLDER = "unwantedTrailer";
    /**
     * The lineNumber on the file.
     */
    //@Column(nullable = true)
    //TODO: Make nullable for incoming files
    @XmlTransient
    private int lineNumber;

    /**
     * The raw data as on file.
     */
   // @Column(nullable = false, length = 10000)
    //TODO: Make nullable for incoming files
    @Column(length = 5000)
    @XmlTransient
    private String rawData;

    /**
     * An FK on {@link org.springframework.batch.core.JobExecution} that processed the file.
     */
    //@Column(nullable = false, unique = false)
    //TODO: Make nullable for incoming files
    @XmlTransient
    private Long jobExecutionId;

    /**
     * Is this record picked-up for processing by the event system.
     */
    @XmlTransient
    private Boolean processed;

    /**
     * When using a LineTokenizer with strict=true we must have a consume the last token.
     * We do not want to set strict=fals as this can cause problems.
     * Instead we have this variable as a place to consume the last token.
     * TODO : Get rid of the variable and define a custom LineTokenizer to address this problem.
     */
    @Transient
    @XmlTransient
    private String unwantedTrailer;

    public abstract void setDda(String dda);
    /**
     * 
     * @return The event dda.
     */
    public abstract String getDda();
    
    public void setUnwantedTrailer(String unwantedTrailer) {
      // We don't need it so we wont hold it!!
    }
}
