package com.bfds.scheduling.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import com.bfds.saec.util.SaecStringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.quartz.Job;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;


import com.google.common.collect.Maps;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit="entityManagerFactory")
 @NamedQueries({
	@NamedQuery(name = "findJobConfigByJobId", query = "from JobConfig o where o.jobId = :jobId")})
public class JobConfig implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * unique Batch Id
	 */
    @NotNull
    @Column(nullable=false)
	private String jobId;
    
	/**
	 * Batch Name
	 */
    @Column(nullable=false, unique=true)
	private String jobName; 
    
	/**
	 * Vendor Name
	 */
	private String vendorName; 
    
	/**
	 * Is this an in job.
	 */
    @Column(nullable=false)
	private Boolean incoming;  

	/**
	 * The class to invoke. Must implement {@link Job}
	 */
    @Column(nullable=false)
	private String jobClass;
	
	/**
	 * contact Person Email
	 */		
	private String  contactName;   
	
	/**
	 * contact Person Email
	 */		
	private String  contactEmail;
	
	/**
	 * contact Person Email
	 */		
	private String  contactPhone;

    /**
     * A comma(,) separated list of spring bean ids that implementent one or more of the following listener interfaces
     * @see org.springframework.batch.core.ChunkListener
     * @see org.springframework.batch.core.ItemReadListener
     * @see org.springframework.batch.core.ItemProcessListener
     * @see org.springframework.batch.core.ItemWriteListener
     * @see org.springframework.batch.core.SkipListener
     */
    private String  lifecycleExtensions;
	
	@Embedded
	private ScheduleConfig scheduleConfig;
	
	@Embedded
	private FileConfig fileConfig;
	/**
	 * Loading mapped collection (jobParameters) Eagerly to avoid the LazyInitalizationException, As this object is used only in case of the
	 * batch-jobs(i.e.Only once OR twice in a day) will not cause any performance problem.
	 **/
	 @ElementCollection(fetch=FetchType.EAGER)
	    @MapKeyColumn(name="name")
	    @Column(name="value", nullable=false)
	    @CollectionTable(name="saec_job_parameter", joinColumns=@JoinColumn(name="job_config_id"))
	    Map<String, String> jobParameters = new HashMap<String, String>();
	
	public JobConfig() {
		scheduleConfig = new ScheduleConfig();
		fileConfig = new FileConfig();
	}
	
	public Map<String, String> validate() {
		Map<String, String> ret = Maps.newHashMap();
//		if(this.scheduleConfig != null) {
//			ret.putAll(this.scheduleConfig.validate());
//		}
//		if(this.fileConfig != null) {
//			ret.putAll(this.fileConfig.validate());
//		}
		return ret;
	}

    @JsonIgnore
	public String getContactInfoAsString(String lineSeperator) {
		final String[] lines = {this.contactName, this.contactPhone, this.contactEmail};
		return SaecStringUtils.getArrayString(lines, lineSeperator);
	}
	
	public static JobConfig findJobConfigByJobId(final String jobId) {
		return entityManager()
				.createNamedQuery(
						"findJobConfigByJobId",
						JobConfig.class).setParameter("jobId", jobId).getSingleResult();
	}

    @JsonIgnore
	public boolean isFielJob() {
		return this.getFileConfig() != null && StringUtils.hasText(this.getFileConfig().getFilePath());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobConfig other = (JobConfig) obj;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		return true;
	}
	
	
}
