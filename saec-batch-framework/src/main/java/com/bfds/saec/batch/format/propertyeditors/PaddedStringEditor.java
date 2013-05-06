package com.bfds.saec.batch.format.propertyeditors;

import com.bfds.saec.batch.annotations.Padding;
import com.google.common.base.Preconditions;

import java.beans.PropertyEditorSupport;

/**
 * A {@link java.beans.PropertyEditor} for padded strings. {@link java.beans.PropertyEditor#getValue()} gives the string in non padded form.
 * And {@link java.beans.PropertyEditor#getAsText()} gives the string in padded form.
 *
 * When {@link PaddedStringEditor#strict} is true the string in it's padded form must have its length equal to {@link PaddedStringEditor#size}
 *
 * @see com.bfds.saec.batch.annotations.PaddedStringFormat
 */
public class PaddedStringEditor extends PropertyEditorSupport {
    private final Padding padding;
    private final char paddingChar;
    private final int size;
    private boolean strict;

    public PaddedStringEditor(final Padding padding, final char paddingChar, final int size) {
        Preconditions.checkNotNull(padding, "padding is null");
        Preconditions.checkNotNull(paddingChar, "paddingChar is null");
        Preconditions.checkArgument(size > 0, "size must be greater than zero");
        this.padding = padding;
        this.paddingChar = paddingChar;
        this.size = size;
        this.strict = true;
    }

    /**
     * @see java.beans.PropertyEditor#setAsText(String)
     */
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        if(Padding.LPAD == padding) {
            setValue(removeLpadding(text));
        } else {
            setValue(removeRpadding(text));
        }
    }

    /**
     * @see java.beans.PropertyEditor#getAsText()
     */
    @Override
    public String getAsText() {
        String str = (String) getValue();
        if(str != null && str.length() == size) {
            return str;
        }
        if(Padding.LPAD == padding) {
            return addLpadding(str);
        } else {
            return addRpadding(str);
        }

    }

    private void assertSize(String str) {
    	if(str!= null && str.length() > size){
        throw new IllegalArgumentException("the value must have a size <= "+size+", size is "+ str.length());
    	}
    }

    public String removeLpadding(String text) {
        if(text == null) {
            return null;
        }
        final char[] charArray = text.toCharArray();
        final StringBuilder sb = new StringBuilder();
        boolean stripped = false;
        for (final char c : charArray) {
            if (stripped || paddingChar != c) {
                stripped = true;
                sb.append(c);
            }
        }
        final String ret =  sb.toString();
        if(strict) {
            assertSize(ret);
        }
        return ret;
    }

    public String removeRpadding(String text) {
        if(text == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(text);
        sb = new StringBuilder(removeLpadding(sb.reverse().toString()));
        return sb.reverse().toString();
    }

    public String addLpadding(String str) {
        if(strict) {
            assertSize(str);
        }
        StringBuilder sb = getPadding(str);

        return str == null ? sb.toString() : sb.append(str).toString();
    }

    public String addRpadding(String str) {
        if(strict) {
            assertSize(str);
        }
        StringBuilder sb = getPadding(str);

        return str == null ? sb.toString() : str + sb.toString();
    }

    /**
     *
     * @param str - The string that has to be padded.
     * @return The padding that has to be added to the string.
     */
    private StringBuilder getPadding(final String str) {
        StringBuilder sb = new StringBuilder();
        int padCount = str == null ? size : (size - str.length());
        for(int i=0; i< padCount; i++) {
            sb.append(paddingChar);
        }
        return sb;
    }

    public boolean isStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }
}
