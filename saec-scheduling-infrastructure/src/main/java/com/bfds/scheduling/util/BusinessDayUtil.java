package com.bfds.scheduling.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Useful Business Day calculations
 */
public class BusinessDayUtil {
	private static final Logger logger = LoggerFactory.getLogger(BusinessDayUtil.class);

	private static transient Map<Integer, List<Date>> computedDates = new HashMap<Integer, List<Date>>();

	private static List<Date> offlimitDates;

	/**
	 * Based on a year, this will compute the actual dates
	 * 
	 * Holidays Accounted For: New Year's Day Martin Luther King Jr. Day President's Day Memorial
	 * Day Independence Day Labor Day Columbus Day Veterans Day Thanksgiving Day Christmas Day
	 * 
	 */
	public static boolean isBusinessDay(Date dateToCheck) {
		// Setup the calendar to have the start date truncated
		Calendar baseCal = Calendar.getInstance();
		baseCal.setTime(DateUtils.truncate(dateToCheck, Calendar.DATE));
		// List<Date> offlimitDates;
		// Grab the list of dates for the year. These SHOULD NOT be modified.
		synchronized (computedDates) {
			int year = baseCal.get(Calendar.YEAR);
			// If the map doesn't already have the dates computed, create them.
			if (!computedDates.containsKey(year))
				computedDates.put(year, getOfflimitDates(year));
			offlimitDates = computedDates.get(year);
		}
		// Determine if the date is on a weekend.
		int dayOfWeek = baseCal.get(Calendar.DAY_OF_WEEK);
		boolean onWeekend = dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
		// If it's on a holiday, increment and test again
		// If it's on a weekend, increment necessary amount and test again
		boolean b = offlimitDatesContains(baseCal);
		// boolean b = offlimitDates.contains(baseCal);
		if (b || onWeekend)
			return false;
		else
			return true;
	}

	public static boolean offlimitDatesContains(Calendar calendarDateToCheck) {
		if (calendarDateToCheck != null) {
			for (Date date : offlimitDates) {
				Calendar c2 = Calendar.getInstance();
				c2.setTime(date);
				if (calendarDateToCheck.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
						&& calendarDateToCheck.get(Calendar.DAY_OF_YEAR) == c2
								.get(Calendar.DAY_OF_YEAR))
					return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * This method will calculate the next business day after the one input. This leverages the
	 * isBusinessDay heavily, so look at that documentation for further information.
	 * 
	 * @param startDate the Date of which you need the next business day.
	 * @return The next business day. I.E. it doesn't fall on a weekend, a holiday or the official
	 * observance of that holiday if it fell on a weekend.
	 * 
	 */
	public static Date getNextBusinessDay(Date startDate) {
		// Increment the Date object by a Day and clear out hour/min/sec
		// information
		Date nextDay = DateUtils.truncate(addDays(startDate, 1), Calendar.DATE);
		// If tomorrow is a valid business day, return it
		if (isBusinessDay(nextDay))
			return nextDay;
		// Else we recursively call our function until we find one.
		else
			return getNextBusinessDay(nextDay);
	}

	/**
	 * Based on a year, this will compute the actual dates of
	 * 
	 * Holidays Accounted For: New Year's Day Martin Luther King Jr. Day President's Day Memorial
	 * Day Independence Day Labor Day Columbus Day Veterans Day Thanksgiving Day Christmas Day
	 */
	public static List<Date> getOfflimitDates(int year) {
		List<Date> offlimitDates = new ArrayList<Date>();

		Calendar baseCalendar = GregorianCalendar.getInstance();
		baseCalendar.clear();

		// Add in the static dates for the year.
		// New years day
		baseCalendar.set(year, Calendar.JANUARY, 1);
		offlimitDates.add(offsetForWeekend(baseCalendar));

		// Independence Day
		baseCalendar.set(year, Calendar.JULY, 4);
		offlimitDates.add(offsetForWeekend(baseCalendar));

		// Veterans Day
		baseCalendar.set(year, Calendar.NOVEMBER, 11);
		offlimitDates.add(offsetForWeekend(baseCalendar));

		// Christmas
		baseCalendar.set(year, Calendar.DECEMBER, 25);
		offlimitDates.add(offsetForWeekend(baseCalendar));

		// Now deal with floating holidays.
		// Martin Luther King Day
		offlimitDates.add(calculateFloatingHoliday(3, Calendar.MONDAY, year, Calendar.JANUARY));

		// Presidents Day
		offlimitDates.add(calculateFloatingHoliday(3, Calendar.MONDAY, year, Calendar.FEBRUARY));

		// Memorial Day
		offlimitDates.add(calculateFloatingHoliday(0, Calendar.MONDAY, year, Calendar.MAY));

		// Labor Day
		offlimitDates.add(calculateFloatingHoliday(1, Calendar.MONDAY, year, Calendar.SEPTEMBER));

		// Columbus Day
		offlimitDates.add(calculateFloatingHoliday(2, Calendar.MONDAY, year, Calendar.OCTOBER));

		// Thanksgiving Day and Thanksgiving Friday
		Date thanksgiving = calculateFloatingHoliday(4, Calendar.THURSDAY, year, Calendar.NOVEMBER);
		offlimitDates.add(thanksgiving);

		// This is for Thanksgiving Friday..It is not a bank holiday
		// offlimitDates.add(addDays(thanksgiving, 1));

		return offlimitDates;
	}

	/**
	 * This method will take in the various parameters and return a Date objet that represents that
	 * value.
	 * 
	 * Ex. To get Martin Luther Kings BDay, which is the 3rd Monday of January, the method call
	 * woudl be:
	 * 
	 * calculateFloatingHoliday(3, Calendar.MONDAY, year, Calendar.JANUARY);
	 * 
	 * Reference material can be found at:
	 * http://michaelthompson.org/technikos/holidays.php#MemorialDay
	 * 
	 * @param nth 0 for Last, 1 for 1st, 2 for 2nd, etc.
	 * @param dayOfWeek Use Calendar.MODAY, Calendar.TUESDAY, etc.
	 * @param year
	 * @param month Use Calendar.JANUARY, etc.
	 * @return
	 */
	private static Date calculateFloatingHoliday(int nth, int dayOfWeek, int year, int month) {
		Calendar baseCal = Calendar.getInstance();
		baseCal.clear();

		// Determine what the very earliest day this could occur.
		// If the value was 0 for the nth parameter, increment to the following
		// month so that it can be subtracted alter.
		baseCal.set(year, month + ((nth <= 0) ? 1 : 0), 1);
		Date baseDate = baseCal.getTime();

		// Figure out which day of the week that this "earliest" could occur on
		// and then determine what the offset is for our day that we actually
		// need.
		int baseDayOfWeek = baseCal.get(Calendar.DAY_OF_WEEK);
		int fwd = dayOfWeek - baseDayOfWeek;

		// Based on the offset and the nth parameter, we are able to determine
		// the offset of days and then
		// adjust our base date.
		return addDays(baseDate, (fwd + (nth - (fwd >= 0 ? 1 : 0)) * 7));
	}

	/**
	 * If the given date falls on a weekend, the method will adjust to the closest weekday. I.E. If
	 * the date is on a Saturday, then the Friday will be returned, if it's a Sunday, then Monday is
	 * returned.
	 */
	private static Date offsetForWeekend(Calendar baseCal) {
		Date returnDate = baseCal.getTime();
		if (baseCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			logger.debug("Offsetting the Saturday by -1: " + returnDate);
			return addDays(returnDate, -1);
		}
		else if (baseCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			logger.debug("Offsetting the Sunday by +1: " + returnDate);
			return addDays(returnDate, 1);
		}
		else
			return returnDate;
	}

	/**
	 * Add a dat to a date
	 * 
	 * @param dateToAdd
	 * @param numberOfDay
	 * @return
	 */
	public static Date addDays(Date dateToAdd, int numberOfDay) {
		if (dateToAdd == null)
			throw new IllegalArgumentException("Date can't be null!");
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(dateToAdd);
		tempCal.add(Calendar.DATE, numberOfDay);
		return tempCal.getTime();
	}

}