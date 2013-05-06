package com.bfds.saec.batch.annotations;

import com.google.common.base.Preconditions;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * See {@link com.bfds.saec.batch.annotations.Range}.
 *
 * A Value object to hold the values of the {@link com.bfds.saec.batch.annotations.Range} annotation.
 * The {@link com.bfds.saec.batch.annotations.Range#property()} is redundant when added to a member variable.
 * The sole purpose of this class is to identify the member variable to which  {@link com.bfds.saec.batch.annotations.Range}
 * is added.
 *
 */
@RooJavaBean
@RooToString
public class RangeVo implements Comparable<RangeVo> {
    private final String value;
    private final String propertyName;

    public RangeVo(final String propertyName, final String value) {
        Preconditions.checkNotNull(value, "Range value canot be null.");
         this.propertyName = propertyName;
        this.value = value;
    }

    /**
     * Two RangeVos are equal if their corresponding min values are equal.
     * @param o
     * @return
     */
    @Override
    public int compareTo(RangeVo o) {
        int thisMin = this.getMin();
        int oMin = o.getMin();
        return thisMin - oMin;
    }

    private int getMin() {
        if(value.indexOf('-') > 0) {
            return Integer.parseInt(value.substring(0, value.indexOf('-')).trim());
        }
        return Integer.parseInt(value.trim()) ;
    }
}
