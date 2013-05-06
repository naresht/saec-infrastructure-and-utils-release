package com.bfds.saec.batch.format.propertyeditors;


import com.google.common.base.Preconditions;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * A {@link java.beans.PropertyEditor} for the non-standard decimal number format which assumes the position of the decimal point.
 * The only supported number is a java.lang.Double.
 *
 * Example : -
 *
 * if "100050" is a string and {@link AssumedDecimalNumberEditor#decimalPlaces} = 2 then
 * the converted java.lang.Double is 1000.50d. Inversely 1000.50d becomes "100050".
 *
 * @see com.bfds.saec.batch.annotations.AssumedDecimalNumberFormat
 */
public class AssumedDecimalNumberEditor extends org.springframework.beans.propertyeditors.CustomNumberEditor {
    private final int decimalPlaces;

    /**
     * @param numberClass - A subtype of {@link Number}. Current implementation supports only {@link Double}
     * @param decimalPlaces - The assumed position of the decimal point for the right.
     */
    public AssumedDecimalNumberEditor(Class<? extends Number> numberClass, final int decimalPlaces) {
        super(numberClass, true);
        Preconditions.checkArgument(Double.class.isAssignableFrom(numberClass)
                                    || BigDecimal.class.isAssignableFrom(numberClass),
                                    "numberClass must be of type %s or %s ", Double.class.getName(), BigDecimal.class.getName());

        Preconditions.checkArgument(decimalPlaces > 0 , "decimalPaces must be greater than 0: %s", decimalPlaces);
        this.decimalPlaces = decimalPlaces;
    }

    /**
     * @see java.beans.PropertyEditor#setAsText(String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(StringUtils.hasText(text)) {
            text = parseForSetAsText(text);
        }
        super.setAsText(text);
    }

    /**
     *  Converts the number string from a non-standard format to its' equivalent standard format.
     *
     * @param text - The number in a non-standard format.
     * @return the same number in a standard format.
     */
    private String parseForSetAsText(String text) {
        assertCanAccomidateDecimalPlaces(text);
        text = text.substring(0, text.length() - decimalPlaces) + "."
                + text.substring(text.length() - decimalPlaces);
        return text;
    }

    private void assertCanAccomidateDecimalPlaces(String text) {
        Preconditions.checkArgument(text.length() > decimalPlaces, "the text length %s cannot be less than %s", text.length(), decimalPlaces);
    }

    /**
     * @see java.beans.PropertyEditor#getAsText()
     */
    @Override
    public String getAsText() {
        String str = super.getAsText();
        if(StringUtils.hasText(str)) {
            str = parseFromGetAsText(str);
        }
        return str;
    }


    /**
     * Converts the number string from a standard format to its' equivalent non-standard format.
     * @param text - the number string in a standard format.
     * @return the same number in a non-standard format.
     */
    private String parseFromGetAsText(String text) {
        assertCanAccomidateDecimalPlaces(text);
        if(text.contains(".")) {
          int decimalSize =  decimalPlaces - ((text.length() - text.indexOf(".")) - 1);
          Preconditions.checkState(decimalSize >=0 , "the fraction digit count %s must not be greater than decimalPlaces %s", decimalSize, decimalPlaces);
          if(decimalSize > 0) {
              text =  appendZeros(text, decimalSize);
          }
        }else {
            text =  appendZeros(text, decimalPlaces);
        }

        return text.replaceAll("\\.", "");
    }

    private String appendZeros(String text, int count) {
        StringBuilder sb = new StringBuilder(text);
        while(count-- > 0) {
            sb.append("0");
        }
        return sb.toString();
    }
}
