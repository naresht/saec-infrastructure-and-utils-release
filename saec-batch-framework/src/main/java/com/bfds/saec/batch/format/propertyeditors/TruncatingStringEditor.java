package com.bfds.saec.batch.format.propertyeditors;

import java.beans.PropertyEditorSupport;

import com.google.common.base.Preconditions;

/**
 * A {@link java.beans.PropertyEditor} for truncating strings. {@link java.beans.PropertyEditor#getValue()} gives the string in non truncated form.
 * And {@link java.beans.PropertyEditor#getAsText()} gives the string in truncated form.
 *
 * When {@link PaddedStringEditor#strict} is true the string in it's padded form must have its length equal to {@link PaddedStringEditor#size}
 *
 * @see com.bfds.saec.batch.annotations.PaddedStringFormat
 */
public class TruncatingStringEditor extends PropertyEditorSupport {
    private final int size;

    public TruncatingStringEditor(final int size) {
        Preconditions.checkArgument(size > 0, "size must be greater than zero");
        this.size = size;
    
    }

    /**
     * @see java.beans.PropertyEditor#setAsText(String)
     */
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
    	setValue(text);
    }

    /**
     * @see java.beans.PropertyEditor#getAsText()
     */
    @Override
    public String getAsText() {
        String str = (String) getValue();
        if(str != null && str.length() > size) {
            return str.substring(0, size);
        }
        return str;
    }
}
