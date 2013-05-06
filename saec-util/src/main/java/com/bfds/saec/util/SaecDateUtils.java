package com.bfds.saec.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;

public class SaecDateUtils {

	public static final String DATE_FORMAT = "MM-dd-yyyy";

	public static Date getMax() {
		return new Date(320, 12, 31);
	}

	public static Date getMin() {
		return new Date(1, 1, 1);
	}

	public static Date getMaxIfNull(final Date date) {
		return date == null ? getMax() : date;
	}

	public static Date getMinIfNull(final Date date) {
		return date == null ? getMin() : date;
	}

	public static Date getDaysFromCurrent(int days) {
		return DateUtils.addDays(new Date(), days);
	}

	public static String getFormattedDate(Date date,
			String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	public static String getDateFormatted(Date date) {
		return getFormattedDate(date, DATE_FORMAT);
	}

	/**
	 * Retrieves year in double digits ("yy")
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static String getYear(Calendar date) {
		Integer yy = date.get(Calendar.YEAR);
		return new String(yy.toString()).substring(2);
	}

	public static String getMonth(Calendar date) {
		Integer mm = date.get(Calendar.MONTH);
		if (mm.toString().length() == 1) {
			return "0" + mm.toString();
		}
		return mm.toString();
	}

	public static String getDay(Calendar date) {
		Integer mm = date.get(Calendar.DAY_OF_MONTH);
		if (mm.toString().length() == 1) {
			return "0" + mm.toString();
		}
		return mm.toString();
	}

	public static boolean isBeforeDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		return (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR) || cal1
				.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR));

	}

	public static boolean isAfterDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);		
		if(cal1.after(cal2)){
			return true;
		}
		else
		return false;
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date substractDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}

	/**
	 * Checks if dates are equal
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateEqual(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		if ((cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
				&& (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE))
				&& (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)))
			return true;
		else
			return false;
	}

	/**
	 * Checks if Date falls within range
	 * 
	 * @param dateToSearch
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public static boolean isWithinDateRange(Date dateToSearch, Date startdate,
			Date enddate) {
		Calendar calstart = Calendar.getInstance();
		calstart.setTime(startdate);
		calstart.set(Calendar.HOUR, 0);
		calstart.set(Calendar.MINUTE, 0);
		calstart.set(Calendar.SECOND, 0);
		Calendar calend = Calendar.getInstance();
		calend.setTime(enddate);
		calend.set(Calendar.HOUR, 0);
		calend.set(Calendar.MINUTE, 0);
		calend.set(Calendar.SECOND, 0);
		Calendar calsearch = Calendar.getInstance();
		calsearch.setTime(dateToSearch);
		calsearch.set(Calendar.HOUR, 0);
		calsearch.set(Calendar.MINUTE, 0);
		calsearch.set(Calendar.SECOND, 0);
		if (((isDateEqual(calstart.getTime(), calsearch.getTime())) || (calstart
				.getTime().before(calsearch.getTime())))
				&& ((isDateEqual(calend.getTime(), calsearch.getTime())) || (calend
						.getTime().after(calsearch.getTime()))))
			return true;
		else
			return false;
	}

	public static Date convertDate(XMLGregorianCalendar cal) {
		return cal.toGregorianCalendar().getTime();
	}

	public static String filterDate(Date date) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		if(date != null){
			return formatter.format(date);
		}else{
			return "";
		}
		
	}

	public static int[] toIntArray(final String dateStr) {
		final String[] strArray = dateStr.split("/");
		final int[] ret = new int[3];

		for (int i = 0; i < strArray.length; i++) {
			if (StringUtils.hasText(strArray[i])) {
				ret[i] = Integer.parseInt(strArray[i].trim());
			}
		}
		return ret;
	}

}
