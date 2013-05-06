package com.bfds.saec.batch.format.propertyeditors;


import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * A wrapper for {@link CustomDateEditor}.
 *
 * @see AbstractEditorWrapper
 */
@RooJavaBean
public class CustomDateEditorWrapper extends AbstractEditorWrapper {
    private String format;

    @Override
    public void afterPropertiesSet() throws Exception {
        delegate = new  CustomDateEditor(format);
        super.afterPropertiesSet();
    }

}
