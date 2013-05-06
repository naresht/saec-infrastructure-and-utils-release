package com.bfds.saec.util;

import java.util.Collection;
import java.util.List;

import org.springframework.util.StringUtils;

public class SaecStringUtils {
	private static final String DEFAULT_LINE_SEPERATOR = ", ";

	/**
	 * @param lines {@link Object} array.can be null.
	 * @return {@link String} A String which contains all the values passed in the lines separated with string ",". null if lines is null.
	 */
	public static String getArrayString(Object[] lines) {
		return getArrayString(lines, DEFAULT_LINE_SEPERATOR);
	}
	
	/**
	 * @param lines {@link Object} array.can be null.
	 * @param lineSeperator {@link String}, can be " ",- or any other String which we want to use while concatenating words.can't be null.
	 * @return {@link String} which contains all the values passed as input parameter concatenated with lineSeperator.null if lines is null.
	 */
	public static String getArrayString(Object[] lines, String lineSeperator) {
		if (lines == null) {
			return null;
		}
		lineSeperator = lineSeperator != null ? lineSeperator
				: DEFAULT_LINE_SEPERATOR;
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< lines.length; i++) {
			String line = lines[i] == null ? null : String.valueOf(lines[i]);
			if (StringUtils.hasText(line)) {
				if (sb.length() > 0) {
					sb.append(lineSeperator);
				}
				if(i < lines.length) {
					sb.append(line);
				}
			}
		}
		return sb.toString().trim();
	}
	
	/**
	 * @param lines {@link Collection} any collection object.can be null.
	 * @param lineSeperator {@link String} can be " ",- or any other String which we want to use while concatenating words.can't be null.
	 * @return {@link String} which contains all the values passed as input parameter concatenated with lineSeperator.null if lines is null.
	 */
	public static String getAsString(Collection<?> lines, String lineSeperator) {
		if(lines == null) {
			return "";
		}
		return getArrayString(lines.toArray(), lineSeperator);
	}
    public static String defaultString(String str) {
        return org.apache.commons.lang.StringUtils.defaultString(str);
    }	
    
	public static double getAsDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
		}
		return 0;
	}
}
