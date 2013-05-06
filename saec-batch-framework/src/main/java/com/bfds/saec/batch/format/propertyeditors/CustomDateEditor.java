package com.bfds.saec.batch.format.propertyeditors;


import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * A {@link java.beans.PropertyEditor} for non standard date formats.
 *
 * Currently the following formats are supported.
 *  1. YYMMDD
 *  2. MMDDYYYY
 *  3. MMDDYY
 *  4. YYYYMMDD
 *
 * Use {@link org.springframework.beans.propertyeditors.CustomDateEditor} for standard dateFormats.
 *
 * @see com.bfds.saec.batch.annotations.DateFormat
 */
public class CustomDateEditor extends org.springframework.beans.propertyeditors.CustomDateEditor {

    private static final Map<String, String> SUPPORTED_FORMATS_TO_STANDARD_FORMATS;

    public static final String YYMMDD = "YYMMDD";

    public static final String MMDDYYYY = "MMDDYYYY";

    public static final String MMDDYY = "MMDDYY";

    public static final String YYYYMMDD = "YYYYMMDD";
    
    public static final String yyyyMMdd = "yyyyMMdd";
    
    public static final String HHmmss = "HHmmss";

    static {
        SUPPORTED_FORMATS_TO_STANDARD_FORMATS = Maps.newHashMap();
        SUPPORTED_FORMATS_TO_STANDARD_FORMATS.put(YYMMDD, "yy/MM/dd");
        SUPPORTED_FORMATS_TO_STANDARD_FORMATS.put(MMDDYYYY, "MM/dd/yyyy");
        SUPPORTED_FORMATS_TO_STANDARD_FORMATS.put(MMDDYY, "MM/dd/yy");
        SUPPORTED_FORMATS_TO_STANDARD_FORMATS.put(YYYYMMDD, "yyyy/MM/dd");
        SUPPORTED_FORMATS_TO_STANDARD_FORMATS.put(yyyyMMdd, "yyyy-MM-dd");
        SUPPORTED_FORMATS_TO_STANDARD_FORMATS.put(HHmmss, "HH:mm:ss");
    }
    /**
     * One of the supported non-standard date formats.
     */
    private final String format;

    private final DateFormat dateFormat;

    public CustomDateEditor(String format) {
        super(getDateFormat(format), true, getExactDateLength(format));

        this.format = format;
        dateFormat = getDateFormat(format);
    }

    /**
     * Converts the non-standard format to a standard data format that is represented by  {@link DateFormat}
     * @param format - One of the supported non-standard date formats.
     * @return A {@link DateFormat}
     */
    private static DateFormat  getDateFormat(String format) {
        assertSupportedFormat(format);
        return  new SimpleDateFormat(SUPPORTED_FORMATS_TO_STANDARD_FORMATS.get(format));
    }

    /**
     *
     * @param format  - One of the non-standard date formats
     * @return the length of the standard data format that is equivalent to the non-standard date format.
     *
     * If YYMMDD is the non-standard data format, its equivalent standard date format is yy/MM/dd which has a length on 8.
     */
    private static int  getExactDateLength(String format) {
        assertSupportedFormat(format);
        if(YYMMDD.equalsIgnoreCase(format) || MMDDYY.equalsIgnoreCase(format)||HHmmss.equalsIgnoreCase(format)) {
            return  8;
        } else if(MMDDYYYY.equalsIgnoreCase(format) || YYYYMMDD.equalsIgnoreCase(format)) {
            return  10;
        }
        throw new IllegalStateException("Unsupported date format " + format);
    }

    private static void assertSupportedFormat(String format) {
        Preconditions.checkArgument(SUPPORTED_FORMATS_TO_STANDARD_FORMATS.keySet().contains(format),
                "format %s not supported. If you have a standard date format use %s", format,
                org.springframework.beans.propertyeditors.CustomDateEditor.class.getName());
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
     *  Converts the date string from a non-standard format to its' equivalent standard format.
     * @param text - the date string in a non-standard format.
     * @return the same date in a standard format.
     */
    private String parseForSetAsText(String text) {
        if(YYMMDD.equalsIgnoreCase(format) || MMDDYYYY.equalsIgnoreCase(format) || MMDDYY.equalsIgnoreCase(format)) {
        return text.substring(0, 2) + "/"
                + text.substring(2, 4) + "/"
                + text.substring(4);
        } else if(YYYYMMDD.equalsIgnoreCase(format)) {
            return text.substring(0, 4) + "/"
                    + text.substring(4, 6) + "/"
                    + text.substring(6);
        }
        throw new IllegalStateException("Unsupported date format " + format);
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
     * Converts the date string from a standard format to its' equivalent non-standard format.
     * @param text - the date string in a standard format.
     * @return the same date in a non-standard format.
     */
    private String parseFromGetAsText(String text) {
        if(YYMMDD.equalsIgnoreCase(format)
                || MMDDYYYY.equalsIgnoreCase(format)
                || YYYYMMDD.equalsIgnoreCase(format)
                || MMDDYY.equalsIgnoreCase(format)
                || HHmmss.equalsIgnoreCase(format)) {
            return text.replaceAll("/", "");
        }
        throw new IllegalStateException("Unsupported date format " + format);
    }
}
