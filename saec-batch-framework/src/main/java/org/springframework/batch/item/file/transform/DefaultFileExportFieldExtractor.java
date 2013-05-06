package org.springframework.batch.item.file.transform;


import com.bfds.saec.batch.annotations.RangeVo;
import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.FillerParser;
import com.bfds.saec.batch.file.util.FlatFileAnnotationParser;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.beans.PropertyEditor;
import java.util.*;

public class DefaultFileExportFieldExtractor implements FieldExtractor, InitializingBean {

    private String[] names;
    private Map<String, PropertyEditor> customFieldEditors = Maps.newHashMap();
    private Class<?> type;

    @Value("#{stepExecution}")
    protected StepExecution stepExecution;

    @Autowired
    private FlatFileAnnotationParser parser;
    @Autowired
    private FillerParser fillerParser;

    /**
     * @param names field names to be extracted by the {@link #extract(Object)} method.
     */
    public void setNames(String[] names) {
        Preconditions.checkNotNull(names, "Names must be non-null");
        this.names = Arrays.asList(names).toArray(new String[names.length]);
    }

    public void setCustomFieldEditors(Map<String, PropertyEditor> customFieldEditors) {
        this.customFieldEditors = customFieldEditors;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public Object[] extract(Object item) {
        List<Object> values = new ArrayList<Object>();

        BeanWrapper bw = new BeanWrapperImpl(item);
        for (String propertyName : this.names) {
            final String filler = fillerParser.getFiller(propertyName);
            if(filler != null) {
                values.add(filler);
            }else {
                values.add(format(propertyName, bw.getPropertyValue(propertyName)));
            }
        }
        return values.toArray();
    }

    private Object format(String propertyName, Object propertyValue) {
       Object ret = propertyValue;
       if(customFieldEditors != null) {
           PropertyEditor pe =  customFieldEditors.get(propertyName);
           if(pe != null) {
               pe.setValue(propertyValue);
               ret = pe.getAsText();

           }
       }
       return ret;
    }

    @Override
    public void afterPropertiesSet() {
        if(type == null) {
            type = FileJobUtils.getFileRecordClass(stepExecution, this);
        }
        if(type != null) {
            customFieldEditors = buildCustomFiledEditors(type);
        }
        if(names == null) {
            names = buildNames(type);
        }
        Preconditions.checkNotNull(names, "The 'names' property must be set.");
    }

    private String[] buildNames(Class<?> type) {
        List<RangeVo> list = parser.parsePropertyRanges(type);
        Collections.sort(list);
        String[] ret = new String[list.size()];
        for(int i=0; i< list.size(); i++) {
            ret[i] = list.get(i).getPropertyName();
        }
        return ret;
    }


    private Map<String, PropertyEditor> buildCustomFiledEditors(Class<?> type) {
        final Map<String, PropertyEditor> ret = parser.parsePropertyEditors(type);
        for(final String property : customFieldEditors.keySet()) {
            ret.put(property, customFieldEditors.get(property));
        }
        return ret;
    }
}
