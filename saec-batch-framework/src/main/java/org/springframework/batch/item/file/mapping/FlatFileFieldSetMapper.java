package org.springframework.batch.item.file.mapping;

import com.bfds.saec.batch.file.util.FlatFileAnnotationParser;
import com.google.common.collect.Maps;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.support.DefaultPropertyEditorRegistrar;
import org.springframework.beans.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class FlatFileFieldSetMapper extends DefaultPropertyEditorRegistrar implements FieldSetMapper, InitializingBean {

    private Class type;

    private Map<String, PropertyEditor> customFieldEditors = Maps.newHashMap();

    @Autowired
    private FlatFileAnnotationParser parser;

   @Autowired
   @Qualifier("batchFilesConversionService")
   private ConversionService conversionService;

   BeanWrapper beanWrapper;

    protected BeanWrapper createBeanWrapper(Object target) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);
        Map<String, PropertyDescriptor> annotationFieldPropertyDescriptors = loadAnnotationFieldPropertyDescriptors(target, buildCustomFiledEditors(target));
        for(Map.Entry<String, PropertyEditor> entry : buildCustomFiledEditors(target).entrySet()) {
            beanWrapper.registerCustomEditor(annotationFieldPropertyDescriptors.get(entry.getKey()).getPropertyType(), entry.getKey(), entry.getValue());
        }
        return beanWrapper;
    }

    private Map<String, PropertyDescriptor> loadAnnotationFieldPropertyDescriptors(Object target, Map<String, PropertyEditor> annotationFieldEditors) {
        final BeanWrapper bw = new BeanWrapperImpl(target);
        Map<String, PropertyDescriptor> annotationFieldPropertyDescriptors = Maps.newHashMap();
        for(Map.Entry<String, PropertyEditor> entry : annotationFieldEditors.entrySet()) {
            annotationFieldPropertyDescriptors.put(entry.getKey(), bw.getPropertyDescriptor(entry.getKey()));
        }
        return annotationFieldPropertyDescriptors;
    }

    private Map<String, PropertyEditor> buildCustomFiledEditors(Object target) {
        customFieldEditors = parser.parsePropertyEditors(target.getClass());
        return customFieldEditors;
    }


    /**
     * Public setter for the type of bean to create instead of using a prototype
     * bean. An object of this type will be created from its default constructor
     * for every call to {@link #mapFieldSet(FieldSet)}.<br/>
     *
     * Either this property or the prototype bean name must be specified, but
     * not both.
     *
     * @param type the type to set
     */
    public void setTargetType(Class type) {
        this.type = type;
    }

    /**
     * Check that precisely one of type or prototype bean name is specified.
     *
     * @throws IllegalStateException if neither is set or both properties are
     * set.
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        Assert.state(type != null, "type must be provided.");
    }

    /**
     * Map the {@link FieldSet} to an object retrieved from the enclosing Spring
     * context, or to a new instance of the required type if no prototype is
     * available.
     *
     * @throws NotWritablePropertyException if the {@link FieldSet} contains a
     * field that cannot be mapped to a bean property.
     * @see org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet(FieldSet)
     */
    public Object mapFieldSet(final FieldSet fs) throws BindException {
        final Object copy = getBean();
        if(beanWrapper == null) {
            beanWrapper = createBeanWrapper(copy);
        }
        final MutablePropertyValues mpvs = new MutablePropertyValues(getBeanProperties(copy, fs.getProperties()));
        for(PropertyValue pv : mpvs.getPropertyValues()) {
            if(pv.getValue() == null) {
                mpvs.removePropertyValue(pv);
                continue;
            }
            Object value = pv.getValue();
            final PropertyDescriptor pd =  beanWrapper.getPropertyDescriptor(pv.getName());
            final PropertyEditor pe = customFieldEditors.get(pv.getName());
            if(pe != null) {
                pe.setAsText((String)pv.getValue());
                value = pe.getValue();
            }
            setProperty(copy, pd.getWriteMethod(), convertIfRequired(pd.getPropertyType(), value));
            mpvs.removePropertyValue(pv);
        }
        return copy;
    }

    private Object convertIfRequired(Class<?> propertyType, Object value) {
        if(propertyType.isAssignableFrom(value.getClass())) {
            // No need to convert
            return value;
        }
        return conversionService.convert(value, propertyType);
    }

    private void setProperty(Object copy, Method writeMethod, Object value)  {
        try {
            writeMethod.invoke(copy, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Error calling method "+writeMethod.getName()+" of class "+copy.getClass()+" with value "+value, e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Error calling method "+writeMethod.getName()+" of class "+copy.getClass()+" with value "+value, e);
        } catch (Exception e) {
            throw new IllegalStateException("Error calling method "+writeMethod.getName()+" of class "+copy.getClass()+" with value "+value, e);
        }
    }

    @SuppressWarnings("unchecked")
    private Object getBean() {
        try {
            return type.newInstance();
        }
        catch (InstantiationException e) {
            ReflectionUtils.handleReflectionException(e);
        }
        catch (IllegalAccessException e) {
            ReflectionUtils.handleReflectionException(e);
        }
        // should not happen
        throw new IllegalStateException("Internal error: could not create bean instance for mapping.");
    }

    /**
     * @param bean
     * @param properties
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Properties getBeanProperties(Object bean, Properties properties) {

        Class<?> cls = bean.getClass();

        Set<String> keys = new HashSet(properties.keySet());
        for (String key : keys) {
            String name = key;
            if (name != null) {
                switchPropertyNames(properties, key, name);
            }
        }
        return properties;
    }

    private void switchPropertyNames(Properties properties, String oldName, String newName) {
        String value = properties.getProperty(oldName);
        properties.remove(oldName);
        properties.setProperty(newName, value);
    }

}
