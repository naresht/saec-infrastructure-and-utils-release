package com.bfds.saec.batch.format.propertyeditors;


import com.google.common.base.Preconditions;
import org.springframework.beans.factory.InitializingBean;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;

/**
 * Abstract base class to wrap a {@link PropertyEditor} for spring xml configuration via spring 'p' namespace - http://www.springframework.org/schema/p.
 * Essentially the wrappers have properties that match the constructor args of the wrapper {@link PropertyEditor}.
 *
 * @see CustomDateEditorWrapper
 * @see AssumedDecimalNumberEditorWrapper
 * @see PaddedStringEditorWrapper
 * @see CompositeEditorWrapper
 */
public abstract class AbstractEditorWrapper implements PropertyEditor, InitializingBean {
    protected PropertyEditor delegate;

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(delegate, "propertyEditor is null");
    }

    @Override
    public void setValue(Object value) {
        delegate.setValue(value);
    }

    @Override
    public Object getValue() {
        return delegate.getValue();
    }

    @Override
    public boolean isPaintable() {
        return delegate.isPaintable();
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box) {
        delegate.paintValue(gfx, box);
    }

    @Override
    public String getJavaInitializationString() {
        return delegate.getJavaInitializationString();
    }

    @Override
    public String getAsText() {
        return delegate.getAsText();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        delegate.setAsText(text);
    }

    @Override
    public String[] getTags() {
        return delegate.getTags();
    }

    @Override
    public Component getCustomEditor() {
        return delegate.getCustomEditor();
    }

    @Override
    public boolean supportsCustomEditor() {
        return delegate.supportsCustomEditor();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        delegate.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        delegate.removePropertyChangeListener(listener);
    }
}
