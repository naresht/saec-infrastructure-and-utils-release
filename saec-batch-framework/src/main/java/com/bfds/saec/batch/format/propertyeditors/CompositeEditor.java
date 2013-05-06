package com.bfds.saec.batch.format.propertyeditors;

import com.google.common.base.Preconditions;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A {@link PropertyEditor} that chains two or more {@link PropertyEditor}s. This is useful when a property requires more than one conversion.
 * Except for the last editor in the chain all others must return a string form {@link java.beans.PropertyEditor#getValue()}.
 *
 * Example: -
 *
 * A Double requires a custom format{@link AssumedDecimalNumberEditor} and also padding {@link PaddedStringEditor}.
 *
 * 100.5d must be converted to "00010050" and conversely "00010050" to 100.5d
 *
 * This can be done as follows
 *
 * <code>
 *
 *   List<PropertyEditor> chain = ..
 *   chain.add(new PaddedStringEditor(...));
 *   chain.add(new AssumedDecimalNumberEditor(..));
 *
 *   chain.setAsText("00010050"); // converts to 100.5d
 *   chain.getAsText(100.5d);// returns "00010050"
 *
 * </code>
 */

public class CompositeEditor extends PropertyEditorSupport {
     private final List<PropertyEditor> chain;

    public CompositeEditor(final List<PropertyEditor> chain) {
        Preconditions.checkArgument(chain != null && chain.size() > 1, "propertyEditor list must have a minimum of 2 PropertyEditor(s)");
         this.chain = new ArrayList<PropertyEditor>(chain);
    }

    /**
     * @see java.beans.PropertyEditor#setAsText(String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        for(Iterator<PropertyEditor> itr = chain.iterator(); itr.hasNext();) {
            PropertyEditor pe = itr.next();
            pe.setAsText(text);
            if(itr.hasNext()) {
                text = (String) pe.getValue(); // We expect a string.
            } else {
                setValue(pe.getValue());
            }
        }
    }

    /**
     * @see java.beans.PropertyEditor#getAsText()
     */
    @Override
    public String getAsText() {
        String text = null;
        for(int i= chain.size() -1; i >= 0; i-- ) {
            PropertyEditor pe = chain.get(i);
            if(i == chain.size() -1) {
                pe.setValue(getValue());
            } else {
                pe.setValue(text);
            }
            text = pe.getAsText();
        }
        return text;
    }
}
