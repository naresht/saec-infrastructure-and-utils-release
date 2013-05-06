package com.bfds.saec.batch.file.util;

import com.bfds.saec.batch.annotations.*;
import com.bfds.saec.batch.format.propertyeditors.AssumedDecimalNumberEditor;
import com.bfds.saec.batch.format.propertyeditors.CompositeEditor;
import com.bfds.saec.batch.format.propertyeditors.CustomDateEditor;
import com.bfds.saec.batch.format.propertyeditors.PaddedStringEditor;
import com.bfds.saec.batch.format.propertyeditors.TruncatingStringEditor;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class FlatFileAnnotationParser {


    public Map<String, PropertyEditor> parsePropertyEditors(final Class<?>  recClass) {
        Map<String, PropertyEditor> ret = Maps.newHashMap();
        Map<Field, List<Annotation>> fieldAnnotations =  getAllFieldAnnotations(recClass);
        for(Map.Entry<Field, List<Annotation>> entry : fieldAnnotations.entrySet()) {
            PropertyEditor pe = buildPropertyEditor(entry.getKey().getName(), entry.getValue());
            if(pe != null) {
                ret.put(entry.getKey().getName(), pe);
            }
        }
       return ret;
    }

    private PropertyEditor buildPropertyEditor(String fieldName, List<Annotation> fieldAnnotations) {
        List<PropertyEditor> peList = Lists.newArrayList();
        for(Annotation annotation : fieldAnnotations) {
            if(isFormatAnnotation(annotation)) {
                peList.add(buildPropertyEditor(annotation));
            }
        }
        if(peList.size() == 1) {
            return peList.get(0);
        } else if(peList.size() > 0) {
          return buildPropertyEditorChain(peList);
        }
        return null;
    }

    private CompositeEditor buildPropertyEditorChain(List<PropertyEditor> peList) {
        CompositeEditor chain = new CompositeEditor(peList);
        return chain;
    }


    private boolean isFormatAnnotation(Annotation annotation) {
        return (annotation instanceof DateFormat)
                || (annotation instanceof PaddedStringFormat)
                || (annotation instanceof AssumedDecimalNumberFormat);
    }

    public PropertyEditor buildPropertyEditor(Annotation annotation) {
        if(annotation instanceof DateFormat) {
            return buildDatePropertyEditor((DateFormat) annotation);
        } else if(annotation instanceof PaddedStringFormat) {
            return buildPaddedStringEditor((PaddedStringFormat) annotation);
        } else if(annotation instanceof AssumedDecimalNumberFormat) {
            return buildAssumedDecimalNumberEditor((AssumedDecimalNumberFormat) annotation);
        }
        throw new IllegalStateException("Unknown Annotation for " + PropertyEditor.class.getName()  + " construction : " + annotation.getClass().getName());
    }

    private PropertyEditor buildAssumedDecimalNumberEditor(AssumedDecimalNumberFormat decimalNumberFormat) {
        return new AssumedDecimalNumberEditor(Double.class, decimalNumberFormat.decimalPlaces());
    }

    private PropertyEditor buildPaddedStringEditor(PaddedStringFormat psf) {
    	PaddedStringEditor pse = new PaddedStringEditor(psf.padding(), psf.paddingChar(), psf.size());
    	if(psf.truncate()) {
    		List<PropertyEditor> peList = Lists.newArrayList();
    		peList.add(pse);
    		peList.add(new TruncatingStringEditor(psf.size()));
    		return buildPropertyEditorChain(peList);
    	}else {
    		return pse ;
    	}
    }


    private PropertyEditor buildDatePropertyEditor(DateFormat df) {
        return new CustomDateEditor(df.value());
    }

    private Map<Field, List<Annotation>> getAllFieldAnnotations(final Class<?>  leafClass) {
        Map<Field, List<Annotation>> ret = Maps.newHashMap();
        Field[] fields = leafClass.getDeclaredFields();
        for(Field field : fields) {
            Annotation[] annotations = ((AnnotatedElement)field).getAnnotations();
            if(annotations != null && annotations.length > 0) {
                ret.put(field, Arrays.asList(annotations));
            }
        }
        return ret;
    }

    public List<RangeVo> parsePropertyRanges(final Class<?>  recClass) {
        List<RangeVo> ret = Lists.newArrayList();
        Annotation[] annotations =  recClass.getAnnotations();
        for(Annotation annotation : annotations) {
            if(annotation instanceof Ranges) {
                Range[] ranges = ((Ranges) annotation).value();
                for(Range r : ranges) {
                    Preconditions.checkNotNull(r.property(), "When set at the Class level a %s must have the field 'property' set", Range.class.getName());
                    ret.add(new RangeVo(r.property(), r.value())) ;
                }
            }
        }

        //Field annotation take precedence over class annotations.
        Map<Field, List<Annotation>> fieldAnnotations =  getAllFieldAnnotations(recClass);
        for(Map.Entry<Field, List<Annotation>> entry : fieldAnnotations.entrySet()) {
            for(Annotation annotation : entry.getValue()) {
                if(annotation instanceof Range) {
                    ret.add(new RangeVo(entry.getKey().getName(), ((Range)annotation).value()));
                }
            }
        }
        return ret;
    }

}
