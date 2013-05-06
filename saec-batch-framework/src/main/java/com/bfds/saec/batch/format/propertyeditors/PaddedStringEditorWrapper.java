package com.bfds.saec.batch.format.propertyeditors;

import com.bfds.saec.batch.annotations.Padding;
import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * A wrapper for {@link PaddedStringEditor}.
 *
 * @see AbstractEditorWrapper
 */
@RooJavaBean
public class PaddedStringEditorWrapper extends AbstractEditorWrapper {
    private Padding padding;
    private char paddingChar;
    private int size;

    @Override
    public void afterPropertiesSet() throws Exception {
        delegate = new  PaddedStringEditor(padding, paddingChar,size);
        super.afterPropertiesSet();
    }


}
