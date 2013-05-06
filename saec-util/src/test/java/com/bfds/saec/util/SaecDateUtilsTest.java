package com.bfds.saec.util;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.springframework.util.StringUtils;

public class SaecDateUtilsTest {

	@Test
	public void tetstGetMax() {
		Date date = SaecDateUtils.getMax();
		Date testDate = new Date(320, 12, 31);
		assertThat(date).isEqualTo(testDate);
	}

	@Test
	public void tetstGetMin() {
		Date date = SaecDateUtils.getMin();
		Date testDate = new Date(1, 1, 1);
		assertThat(date).isEqualTo(testDate);
	}

	@Test
	public void tetstGetMaxIfNull() {
		Date date = SaecDateUtils.getMaxIfNull(null);
		Date testDate = new Date(320, 12, 31);
		assertThat(date).isEqualTo(testDate);

		Date date1 = SaecDateUtils.getMaxIfNull(null);
		Date testDate1 = new Date(320, 12, 31);
		assertThat(date1).isEqualTo(testDate1);

	}

	@Test
	public void tetstGetMinIfNull() {
		Date date = SaecDateUtils.getMinIfNull(new Date(1, 1, 1));
		Date testDate = new Date(1, 1, 1);
		assertThat(date).isEqualTo(testDate);

		Date date1 = SaecDateUtils.getMinIfNull(null);
		Date testDate1 = new Date(1, 1, 1);
		assertThat(date1).isEqualTo(testDate1);

	}

	@Test
	public void testGetDaysFromCurrent() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String date = dateformat.format(SaecDateUtils.getDaysFromCurrent(-2));
		String testDate = dateformat.format(DateUtils.addDays(new Date(), -2));
		assertThat(date).isEqualTo(testDate);
	}

	@Test
	public void testDateTimeFormattedFromPattern() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String date = SaecDateUtils.getFormattedDate(new Date(),
				"yyyyMMdd");
		String testDate = dateformat.format(new Date());
		assertThat(date).isEqualTo(testDate);
	}

	@Test
	public void testDateFormatted() {
		SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy");
		String date = SaecDateUtils.getDateFormatted(new Date());
		String testDate = dateformat.format(new Date());
		assertThat(date).isEqualTo(testDate);
	}

	@Test
	public void testGetYear() {
		String str = SaecDateUtils.getYear(Calendar.getInstance());
		Integer yy = Calendar.getInstance().get(Calendar.YEAR);
		String testStr = new String(yy.toString()).substring(2);
		assertThat(str).isEqualTo(testStr);
	}

	@Test
	public void testGetMonth() {
		String expectedMonth;
		String actualMonth = SaecDateUtils.getMonth(Calendar.getInstance());
		Integer mm = Calendar.getInstance().get(Calendar.MONTH);
		if (mm.toString().length() == 1) {
			expectedMonth = "0" + mm.toString();
		} else {
			expectedMonth = mm.toString();
		}
		assertThat(actualMonth).isEqualTo(expectedMonth);

	}

	@Test
	public void testGetDay() {
		String expectedDay;
		String actualDay = SaecDateUtils.getDay(Calendar.getInstance());
		Integer dd = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		if (dd.toString().length() == 1) {
			expectedDay = "0" + dd.toString();
		} else {
			expectedDay = dd.toString();
		}
		assertThat(actualDay).isEqualTo(expectedDay);
	}

	@Test
	public void testIsBeforeDay() {
		boolean val = SaecDateUtils.isBeforeDay(new Date(), new Date());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		boolean testVal = (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR) || cal1
				.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR));
		assertThat(val).isEqualTo(testVal);
	}

	@Test
	public void testIsAfterDay() {
		boolean val = SaecDateUtils.isAfterDay(new Date(), new Date());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		boolean testVal = (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR) || cal1
				.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR));
		assertThat(val).isEqualTo(testVal);
	}

	@Test
	public void testAddDays() {
		Date date = SaecDateUtils.addDays(new Date(), 3);
		Calendar calForActualDate = Calendar.getInstance();
		calForActualDate.setTime(date);
		getCalendarWithoutTimePart(calForActualDate);
		Date actualDate = calForActualDate.getTime();

		Calendar calForExpectedDate = Calendar.getInstance();

		calForExpectedDate.setTime(new Date());
		getCalendarWithoutTimePart(calForExpectedDate);
		calForExpectedDate.add(Calendar.DATE, 3);

		Date testDate = calForExpectedDate.getTime();
		assertThat(actualDate).isEqualTo(testDate);
	}

	@Test
	public void testSubstractDays() {
		Date date = SaecDateUtils.substractDays(new Date(), 3);
		Calendar calForActualDate = Calendar.getInstance();
		calForActualDate.setTime(date);
		getCalendarWithoutTimePart(calForActualDate);
		Date actualDate = calForActualDate.getTime();

		Calendar calForExpectedDate = Calendar.getInstance();
		calForExpectedDate.setTime(new Date());
		getCalendarWithoutTimePart(calForExpectedDate);
		calForExpectedDate.add(Calendar.DATE, -3);

		Date testDate = calForExpectedDate.getTime();

		assertThat(actualDate).isEqualTo(testDate);
	}

	@Test
	public void testIsDateEqual() {

		boolean isDate1 = SaecDateUtils.isDateEqual(null, null);
		assertThat(isDate1).isFalse();

		boolean isDate = SaecDateUtils.isDateEqual(new Date(), new Date());
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal2.setTime(new Date());
		boolean testDate = ((cal1.get(Calendar.MONTH) == cal2
				.get(Calendar.MONTH))
				&& (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)) && (cal1
				.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)));
		assertThat(isDate).isEqualTo(testDate);

	}

	@Test
	public void testIsWithinDateRange() {

		Calendar calForStartDate = getCalendarWithoutTimePart(Calendar
				.getInstance());
		Date startdate = calForStartDate.getTime();

		Calendar calForSearch = getCalendarWithoutTimePart(Calendar
				.getInstance());
		Date dateToSearch = calForSearch.getTime();

		Calendar calForEndDate = getCalendarWithoutTimePart(Calendar
				.getInstance());

		calForEndDate.add(Calendar.DAY_OF_YEAR, 2);
		Date enddate = calForEndDate.getTime();

		assertEquals(true, SaecDateUtils.isWithinDateRange(dateToSearch,
				startdate, enddate));

	}

	@Test
	public void testConvertDate() {
		XMLGregorianCalendar xmlDate;
		try {
			xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			if (xmlDate != null) {
				assertEquals(true, SaecDateUtils.convertDate(xmlDate)
						.getClass() == Date.class);
			}
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void filterDate() {
		Date inputDate = new Date();
		String actualResult = SaecDateUtils.filterDate(inputDate);
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String expectedResult = formatter.format(inputDate);
		assertThat(actualResult != null);

	}

	@Test
	public void testToIntArray() {
		String inputStringDate = "12/21/2011";
		int[] returnedArray = new int[3];
		assertEquals(returnedArray.length,
				SaecDateUtils.toIntArray(inputStringDate).length);

	}

	private Calendar getCalendarWithoutTimePart(Calendar calForActualDate) {
		calForActualDate.set(Calendar.HOUR_OF_DAY, 0);
		calForActualDate.set(Calendar.MINUTE, 0);
		calForActualDate.set(Calendar.SECOND, 0);
		calForActualDate.set(Calendar.MILLISECOND, 0);
		return calForActualDate;
	}

}
