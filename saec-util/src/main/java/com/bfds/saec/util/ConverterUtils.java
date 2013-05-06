package com.bfds.saec.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConverterUtils {

	public static String getFormattedString(String str, int length,
			String paddingChar) {
		while (str.length() < length) {
			str = paddingChar + str;
		}
		return str;
	}

	public static String getFormattedAmountString(String str) {
		return str.substring(0, str.length() - 2) + "."
				+ str.substring(str.length() - 2);
	}

	public static String getUnformattedSsn(String str) {
		char[] charArray = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : charArray) {
			if ('-' != c) {
				sb.append(c);
			}
		}
		return sb.toString();		
	}
	
	// Note:- This method is same as above with one Extra parameter, which we
	// are using to truncate the extra characters.
	public static String getFormattedAndTruncatedString(String str, int length,
			int maxLength, String paddingChar) {
		if (str == null)
			return str;
		if (str.length() > maxLength) {
			str = str.substring(0, maxLength);
			while (str.length() < length) {
				str = paddingChar + str;
			}
		} else {
			while (str.length() < length) {
				str = paddingChar + str;
			}
		}
		return str;
	}

	public static String getFormattedAndTruncatedString1(String str,
			int length, int maxLength, String paddingChar) {
		if (str != null) {
			// str = str.substring(0, maxLength);
			while (str.length() < length) {
				str = str + paddingChar;
			}
		} else {
			int i = 1;
			while (i < length) {
				str = paddingChar + str;
				str = str.replace("null", "");
				i++;
			}
		}
		return str;
	}

	public static String getFormattedString1(String str, int length,
			String paddingChar) {
		str = str == null ? "" : str;
		if (str.length() > length) {
			// Truncate
			str = str.substring(0, length) ;
			return str ;
		}
		while (str.length() < length) {
			// Pad it
			str = str + paddingChar;
		}
		return str;
	}

	public static String getFormattedStringPrePopulate(String str, int length,
			String paddingChar) {
		str = str == null ? "" : str;
		if (str.length() > length) {
			// Truncate
			return str.substring(0, length) ;
		}
		while (str.length() < length) {
			str = paddingChar + str;
		}
		return str;
	}

	public static String getFormattedRegistrationString1(String str,
			int length, int maxLength, String paddingChar) {

		if (str != null && str.length() > maxLength) {
			str = str.substring(0, maxLength);
			while (str.length() < length) {
				str = str + paddingChar;
			}
		} else {
			str = str == null ? "" : str;
			while (str.length() < length) {
				str = str + paddingChar;
			}
		}
		return str;
	}

	public static String getUnformatterString(String str, String paddingChar) {
		char[] charArray = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		boolean stripped = false;
		for (char c : charArray) {
			if (stripped || !paddingChar.equals(String.valueOf(c))) {
				stripped = true;
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String getFormattedString(BigDecimal val, int length,
			String paddingChar) {
		return getFormattedString(val, length, paddingChar, false);
	}

	public static String getFormattedString(BigDecimal val, int length,
			String paddingChar, boolean removeDecimalPoints) {
		String stringAmount;
		String amountToFormat = val.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
		if (removeDecimalPoints&&amountToFormat.contains(".")) {
			 amountToFormat = amountToFormat.replaceAll("[.]", "");      
		}else{
			 amountToFormat = amountToFormat.replaceAll("[.]", "");
			 amountToFormat=amountToFormat+"00";
		}
		stringAmount = ConverterUtils.getFormattedString(amountToFormat,
				length, paddingChar);
		return stringAmount;
	}
	
	public static String getFormattedString2(String val, int length,
			String paddingChar ) {
		
		String valToFormat = val;
		if (valToFormat.contains("-")) {
			valToFormat = valToFormat.replaceAll("[-]", "");      
		}
		valToFormat = ConverterUtils.getFormattedString(valToFormat,
				length, paddingChar);
		return valToFormat;
	}

	public static String getDbIssueVoidStringDate(Date date) {
		SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("MMddyy");
		String stringDate = new String(dateformatMMDDYYYY.format(date));
		return stringDate;
	}

	public static String getIfdsConvertedStatusDate(Date date) {
		SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("MMddyyyy");
		String stringDate = new String(dateformatMMDDYYYY.format(date));
		return stringDate;
	}

	public static String getFormattedDate1(Date date) {
		SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("yyyyMMdd");
		String stringDate = new String(dateformatMMDDYYYY.format(date));
		return stringDate;
	}

	public static String getFormattedDateTime(Date date) {
		SimpleDateFormat dateformatyyyyMMddHmm = new SimpleDateFormat(
				"yyyyMMddHmm");
		String stringDate = new String(dateformatyyyyMMddHmm.format(date));
		return stringDate;
	}

	public static String getFormattedString(Date date, String pattern) {
		SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat(pattern);
		String stringDate = new String(dateformatMMDDYYYY.format(date));
		return stringDate;
	}

	public static Date getStringFormattedDate(String dateString, String pattern) {
		DateFormat dateformat = new SimpleDateFormat(pattern);
		Date stringDate = null;
		try {
			stringDate = dateformat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return stringDate;
	}

	public static String getFormatedYearDate(Date inputDate, String pattern) {
		if (inputDate != null) {
			SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
			StringBuilder formatedDate = new StringBuilder(
					dateformat.format(inputDate));
			return formatedDate.toString().trim();
		} else {
			return null;
		}
	}

	/**
	 * Following three methods has been added the DSTO OutBound File Generation
	 * Job and in that changes needs to be done as per the change requirements.
	 * 
	 **/
	public static String getConvertedStringOrZeroSpace(String str,
			int minLength, int maxLength, String paddingChar) {
		if (str != null && str.length() > maxLength) {
			str = str.substring(0, maxLength);
			while (str.length() < minLength) {
				str = str + paddingChar;
			}
		} else if (str == null) {
			return "";
		} else {
			while (str.length() < minLength) {
				str = str + paddingChar;
			}
		}
		return str;
	}

	public static String getConvertedNumberOrZeroSpace(String str,
			int minLength, int maxLength, String paddingChar) {
		if (str != null && str.length() > maxLength) {
			str = str.substring(0, maxLength);
			while (str.length() < minLength) {
				str = paddingChar + str;
			}
		} else if (str == null) {
			return "";
		} else {
			while (str.length() < minLength) {
				str = paddingChar + str;
			}
		}
		return str;
	}

	/**
	 * Method to get the value with the given number of Decimal positions.If the
	 * total length of the resultant String is less then the required length
	 * then pre-fill that with paddingChar.
	 * 
	 */

	public static String getAmountWithDecimalPositions(double amount,
			int minimumFractionDigits, int maximumFractionDigits,
			int minLength, String paddingChar) {
		String formatedAmount = null;
		double passedAmount = amount;
		int min = minimumFractionDigits;
		int max = maximumFractionDigits;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(min);
		nf.setMaximumFractionDigits(max);
		nf.setGroupingUsed(false);

		try {
			formatedAmount = nf.format(passedAmount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (formatedAmount.length() < minLength) {
			formatedAmount = paddingChar + formatedAmount;
		}

		return formatedAmount;
	}

	/**
	 * Method to compare the two dates without considering the their time part.
	 **/
	public static boolean compareDatesEquality(Date firstDate, Date secondDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		if (firstDate != null && secondDate != null) {

			try {

				Date aDate = sdf.parse(new String(sdf.format(firstDate)));
				Date cDate = sdf.parse(new String(sdf.format(secondDate)));

				if (aDate.compareTo(cDate) == 0) {

					return true;
				} else {
					return false;
				}

			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
}
