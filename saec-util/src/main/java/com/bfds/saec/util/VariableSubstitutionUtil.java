package com.bfds.saec.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

public class VariableSubstitutionUtil {
	
	public static final String DDA_PLACEHOLDER = "<dda>";
	private static final char PLACEHOLDER_START = '<';
	private static final char PLACEHOLDER_END = '>';

	public static final String VARIABLE_DATE = "date";
	public static final String VARIABLE_DDA= "dda";
	public static final String VARIABLE_DEFAULT= "default";
	
	private static final Set<String> SUPPORTED_DATE_PLACEHOLDERS = Sets.newHashSet();
	
	private static final Set<String> SUPPORTED_PLACEHOLDERS = Sets.newHashSet();
	
	static {
		SUPPORTED_DATE_PLACEHOLDERS.add("<yyyyMMddHHmm>");
		SUPPORTED_DATE_PLACEHOLDERS.add("<ddMMyyyyHH>");
		SUPPORTED_DATE_PLACEHOLDERS.add("<ddMMyyyyHHmmS>");
		SUPPORTED_DATE_PLACEHOLDERS.add("<ddMMyyyyHHmmS>");
		SUPPORTED_DATE_PLACEHOLDERS.add("<ddMMyyyyHHmmssSSS>");
		SUPPORTED_DATE_PLACEHOLDERS.add("<ddMMyyyyHHmm>");
		SUPPORTED_DATE_PLACEHOLDERS.add("<yyyy-MM-dd>");
		SUPPORTED_DATE_PLACEHOLDERS.add("<MMddyyyy>");
		
		SUPPORTED_PLACEHOLDERS.add(DDA_PLACEHOLDER);
		SUPPORTED_PLACEHOLDERS.addAll(SUPPORTED_DATE_PLACEHOLDERS);
	}

	public String getDdaPlaceholder() {
		return DDA_PLACEHOLDER;
	}

	public char getPlaceholderStart() {
		return PLACEHOLDER_START;
	}

	public char getPlaceholderEnd() {
		return PLACEHOLDER_END;
	}

	public Set<String> getSupportedDatePlaceholders() {
		return SUPPORTED_DATE_PLACEHOLDERS;
	}

	public Set<String> getSupportedPlaceholders() {
		return SUPPORTED_PLACEHOLDERS;
	}
	
	public String setVariables(String str, final Map<String, Object> substitutionValues)  {
				
		final Set<String> placeHolders = this.parsePlaceHolders(str);
		this.validatePlaceholders(placeHolders);
		for(String placeHolder : placeHolders) {
			String value = getPlaceholderValue(placeHolder, substitutionValues);
			Preconditions.checkArgument(value != null, "%s value required for Subtitution in %s", placeHolder, str);
			str = str.replace(placeHolder, value);
		}
		return str;
	}
	
	private String getPlaceholderValue(String placeHolder,
			Map<String, Object> substitutionValues) {
		String ret = null;
		if(DDA_PLACEHOLDER.equals(placeHolder)) {
			Object val = substitutionValues.get(VARIABLE_DDA);
			ret =  val != null ? val.toString() : null;
		} else { // For now we return the only other placeholder.
			Date date = (Date) substitutionValues.get(VARIABLE_DATE);
			if(date != null) {
				ret = SaecDateUtils.getFormattedDate(date, extractVariableName(placeHolder));
			}
		}
		return ret == null ? (String) substitutionValues.get(VARIABLE_DEFAULT) : ret;
	}

	public void validatePlaceholders(Set<String> placeHolders) {
		Set<String> unsupportedPlaceholders = Sets.newHashSet();
		unsupportedPlaceholders.addAll(placeHolders);
		unsupportedPlaceholders.removeAll(this.getSupportedPlaceholders());
		if(unsupportedPlaceholders.size() > 0) {
			throw new IllegalArgumentException("Unsupported placeholders : " + unsupportedPlaceholders);
		}
	}
	
	public boolean validateDdaSubstitution(final String ddaValue,
			final Set<String> placeHolders) {
		return (placeHolders.contains(this.getDdaPlaceholder()) && StringUtils.hasText(ddaValue)
				|| !placeHolders.contains(this.getDdaPlaceholder()));
	}
	
	public String extractVariableName(final String str) {
		return str.substring(1, str.length() -1);
	}
	
	public Set<String> parsePlaceHolders(String str) {
		final Set<String> placeHolders = Sets.newHashSet();
		final char[] chars = str.toCharArray();
		int startPos = -1;
		
		for(int i=0; i<chars.length; i++) {
			if(chars[i] == this.getPlaceholderStart()) {
				startPos = i;
			} else if(chars[i] == this.getPlaceholderEnd()) {
				placeHolders.add(extractPlaceholder(chars, startPos, i));
				startPos = -1;
			}
		}
		Preconditions.checkArgument(startPos == -1, "%s does not have matching %s in %s", this.getPlaceholderStart(), this.getPlaceholderEnd(), str);
		return placeHolders;
	}
	
	
	public boolean validateDateSubsitution(Set<String> placeHolders, Date date) {
		for(String datePlaceholder : this.getSupportedDatePlaceholders()) { 
			if(placeHolders.contains(datePlaceholder)) {
				return date != null;
			}
		}
		return true;
	}
	
	private String extractPlaceholder(char[] chars, int startPos, int endPos) {
		Preconditions.checkArgument(startPos >= 0 , "< not found in %s", chars);
		Preconditions.checkArgument(endPos <= chars.length &&  endPos > 0, "> not found in %s", chars);
		return new String(Arrays.copyOfRange(chars, startPos, endPos + 1));
	}

	public Set<String> getSupportedDatePlaceholder() {

		return Collections.unmodifiableSet(SUPPORTED_DATE_PLACEHOLDERS);

	}
}
