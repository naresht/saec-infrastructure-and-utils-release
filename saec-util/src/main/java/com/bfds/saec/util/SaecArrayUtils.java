package com.bfds.saec.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.StringUtils;

public class SaecArrayUtils {
	
	/**
	 * @param src - {@link String} array. Can be null. 
	 * @return - A {@link String} array which contains only non-empty values of src. empty String array if src is null or empty.  
	 */
	public static String[] getNonEmptyValues(String[] src) {
		if (src == null || src.length == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		List<String> retList = new ArrayList<String>();
		for (String val : src) {
			if (StringUtils.hasText(val)) {
				retList.add(val);
			}
		}
		return retList.toArray(new String[retList.size()]);
	}

	/**
	 * @param src - {@link String} array. Can be null.
	 * @param src - {@link String} array. Can be null.
	 * @return    - A {@link String} concatenated array which contains only the values available in both the string array used as srcs. null if 
	 * 			    both srcs are null.
	 */
	public static String[] concat(String[] array1, String[] array2) {
		String[] ret = new String[array1.length + array2.length];
		int index = 0;
		for (String val : array1) {
			ret[index++] = val;
		}
		for (String val : array2) {
			ret[index++] = val;
		}
		return ret;
	}
	
	public static boolean atleaseOneEmpty(final Object[] in) {
		for(Object obj : in) {
			if(obj instanceof String || obj == null){
				if(!StringUtils.hasText((String)obj)) {
					return true;
				}
			}else if(obj instanceof Date){
				if(!StringUtils.hasText((String)ConverterUtils.getFormattedDate1((Date)obj))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean atleaseOneFilled(final Object[] in) {
		for(Object obj : in) {
			if(obj instanceof String){
				if(StringUtils.hasText((String)obj)) {
					return true;
				}
			}else if(obj instanceof Date){
				if(StringUtils.hasText((String)ConverterUtils.getFormattedDate1((Date)obj))) {
					return false;
				}
			}
		}
		return false;
	}
	
}
