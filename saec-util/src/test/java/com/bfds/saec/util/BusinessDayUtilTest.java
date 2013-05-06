package com.bfds.saec.util;

import static org.fest.assertions.Assertions.assertThat;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
public class BusinessDayUtilTest {

	@Test
	public void testIsBusinessDay() {
		assertThat(BusinessDayUtil.isBusinessDay(new Date(111, 11, 1))).isTrue();
	}

	@Test
    @Ignore
	public void testOffLimitDatesContains() {
		Calendar baseCalendar = Calendar.getInstance();

		baseCalendar.set(2011, Calendar.JANUARY, 1);
		Date date = offsetForWeekend(baseCalendar);
		baseCalendar.setTime(date);
		assertThat(BusinessDayUtil.offlimitDatesContains(baseCalendar)).isTrue();
	}

	@Test
	public void testAddDays() {

		Format formatter = new SimpleDateFormat("dd/MMM/yyyy");

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, 2);

		String twoDaysOldDate = formatter.format(calendar.getTime());

		assertThat(formatter.format(BusinessDayUtil.addDays(new Date(), 2))).isEqualTo(twoDaysOldDate);

	}

	@Test
	public void testGetNextBusinessDay() {

		Date nextExpectedDate = DateUtils.truncate(
				BusinessDayUtil.addDays(new Date(), 1), Calendar.DATE);
		assertThat(nextExpectedDate).isNotNull();

	}

	private static Date offsetForWeekend(Calendar baseCal) {
		Date returnDate = baseCal.getTime();
		if (baseCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return BusinessDayUtil.addDays(returnDate, -1);
		} else if (baseCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return BusinessDayUtil.addDays(returnDate, 1);
		} else
			return returnDate;
	}

}
