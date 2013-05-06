package org.springframework.batch.item.database.orm;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;

import javax.persistence.Query;


public class DefaultFileRecordQueryProvider extends AbstractJpaQueryProvider {

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    private Class<?> entityClass;

    @Override
    public Query createQuery() {
        Preconditions.checkNotNull(stepExecution, "stepExecution is null");
        String dda = stepExecution.getJobParameters().getString("dda");
        Preconditions.checkNotNull(dda, "dda is null");
        
        final Class<?> entityClass = this.entityClass != null ? this.entityClass : inferEntityClass();

        final StringBuilder sb = new StringBuilder(
                "from "+ entityClass.getName() + " i"
                        + " where (i.processed = :processed or i.processed is null) ");
        if(!"all".equals(dda)) {
        	sb.append(" and i.dda = :dda");
        }
        
        final Map<String, String> filters = getFilters(stepExecution.getJobParameters());
        addAdditionalFilterParameters(filters, sb);
        
        final Query query = this.getEntityManager().createQuery(sb.toString());
        query.setParameter("processed", Boolean.FALSE);
        if(!"all".equals(dda)) {
        	 query.setParameter("dda", dda);
        }
        addAdditionalFilterParameterValues(filters, query);
        return query;
    }
    
    private void addAdditionalFilterParameterValues(Map<String, String> filters, Query query) {
    	for(Map.Entry<String, String> entry : filters.entrySet()) { 
    		query.setParameter(entry.getKey(), entry.getValue());
    	}
		
	}

	/**
     * Adds additional query filters specified in filters to the jpaql query. 
     * @param filters - contains additional filters
     * @param sb - The partially built JPAQL query. 
     */
    private void addAdditionalFilterParameters(Map<String, String> filters, StringBuilder sb) {    	
    	for(Map.Entry<String, String> entry : filters.entrySet()) { 
    		sb.append(" and i.").append(entry.getKey()).append(" =:").append(entry.getKey());
    	}		
	}

	/**
     * Additional filters on the query can be applied by setting the filters as job parameters in 
     * {@link JobParameters}. Each additional filter must be a job Parameter having the key in the form queryProviderFilter.<fieldName>.
     * The value must be of type String.
     * 
     * eg:-
     * JobParameters jobParameters = new JobParametersBuilder()
     *            .addString("queryProviderFilter.dstoFileType", 'CHECKFILE')
     *            .toJobParameters();
     * 
     * This will result in an additiona where clause condition "and dstoFileType = 'CHECKFILE'"            
     * 
     * @return additions query filters. 
     */
    private Map<String, String> getFilters(final JobParameters jobParameters) {
    	final Map<String, String> ret = Maps.newHashMap();
    	for(Map.Entry<String, JobParameter> entry : jobParameters.getParameters().entrySet()) {
    		String key = entry.getKey();
    		if(key.startsWith("queryProviderFilter")) {
    			key = key.substring(20);
    			ret.put(key, (String)entry.getValue().getValue());
    		}
    	}
    	return ret;
    }

    private Class<?> inferEntityClass() {
        return ClassUtils.resolveClassName(stepExecution.getJobParameters().getString("fileRecordClass"), this.getClass().getClassLoader());
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }
}
