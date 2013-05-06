package com.bfds.saec.batch.format.propertyeditors;


import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * A wrapper for {@link AssumedDecimalNumberEditor}.
 *
 * @see AbstractEditorWrapper
 */
@RooJavaBean
public class AssumedDecimalNumberEditorWrapper extends AbstractEditorWrapper {
    private int decimalPlaces;
    private Class<? extends Number> numberClass;

    @Override
    public void afterPropertiesSet() throws Exception {
        delegate = new  AssumedDecimalNumberEditor(numberClass, decimalPlaces);
        super.afterPropertiesSet();
    }
}
