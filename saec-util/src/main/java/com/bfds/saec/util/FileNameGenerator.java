package com.bfds.saec.util;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;



public class FileNameGenerator {
	
	private VariableSubstitutionUtil substitutionUtil;
	
	public FileNameGenerator() {
		substitutionUtil = new VariableSubstitutionUtil();
	}
	
	/**
	 * @param fileNamePattern - The file name with substitution variables that need to replaced with actual values. 
	 * 
	 * Supported substitution variables are 
	 * <dda> - The event dda
	 * <yyyyMMddHmm> 
	 * <ddMMyyyyHH>
	 * <ddMMyyyyHHmmS>
	 * <ddMMyyyyHHmmS>
	 * 
	 * @param ddaValue - Substitution value for dda.
	 * @param date - the date to format and substitute
	 * @return - A string with substitution variable replaced by corresponding values.
	 */
	public String generateFileName(String fileNamePattern, final String ddaValue, final Date date)  {
		Map<String, Object> substitutionValues = Maps.newHashMap();
		substitutionValues.put(VariableSubstitutionUtil.VARIABLE_DDA, ddaValue);
		substitutionValues.put(VariableSubstitutionUtil.VARIABLE_DATE, date);				
		
		return substitutionUtil.setVariables(fileNamePattern, substitutionValues);
	}

	/**
	 * @see FileNameGenerator#generateFileName(String, String, Date)
	 */
	public String generateFileName(final String fileNamePattern, final Date date)  {		
		return generateFileName(fileNamePattern, null, date);
	}
	
	/**
	 * @see FileNameGenerator#generateFileName(String, String, Date)
	 */	
	public String generateFileName(final String fileNamePattern)  {
		return generateFileName(fileNamePattern, new Date());
	}

}
