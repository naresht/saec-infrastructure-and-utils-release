package com.bfds.saec.batch.format.propertyeditors;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.roo.addon.javabean.RooJavaBean;

import java.beans.PropertyEditor;
import java.util.List;

/**
 * A wrapper for {@link CompositeEditor}. The {@link CompositeEditor} takes a {@link List} of {@link PropertyEditor}s but in almost all cases we will not chain more than
 * two editors. So wrapper allows setting only two {@link PropertyEditor}s.
 *
 * @see AbstractEditorWrapper
 */

@RooJavaBean
public class CompositeEditorWrapper extends AbstractEditorWrapper {
    private PropertyEditor first;
    private PropertyEditor second;


    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkState(first != null, "The first PropertyEditor in the chain cannot be null");
        final List<PropertyEditor> chain = Lists.newArrayList();
        chain.add(first);
        if(second !=null) {
            chain.add(second);
        }
        delegate = new CompositeEditor(chain);
        super.afterPropertiesSet();
    }
}
