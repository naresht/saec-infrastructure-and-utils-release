package com.bfds.saec.util;

import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

public class ConverterUtilsTest {

	@Test
	public void verifyFormattedString() {
		String str = ConverterUtils.getFormattedString("Test", 10, "0");
		assertThat(str).isEqualTo("000000Test");
		assertThat(str).isNotEqualTo("000000Test1");

	}
	
	@Test
	public void verifyFormattedAmountString() {
		String str = ConverterUtils.getFormattedAmountString("10025");
		assertThat(str).isEqualTo("100.25");
		
		str = ConverterUtils.getFormattedAmountString("1000");
		assertThat(str).isEqualTo("10.00");
		assertThat(str).isNotEqualTo("1000.00");

	}

	@Test
	public void verifyFormattedAndTruncatedString() {
		String str = ConverterUtils.getFormattedAndTruncatedString("Test", 13,
				13, "0");
		assertThat(str).isEqualTo("000000000Test");
		assertThat(str).isNotEqualTo("000000000Test1");
		String str1 = ConverterUtils.getFormattedAndTruncatedString("Test", 13,
				3, "0");
		assertThat(str1).isEqualTo("0000000000Tes");
		assertThat(str1).isNotEqualTo("000000000Test1");
	}

	@Test
	public void verifyFormattedString1() {
		String str = ConverterUtils.getFormattedString1("Test", 10, "0");
		assertThat(str).isEqualTo("Test000000");
		assertThat(str).isNotEqualTo("Test1000000");
	}

	@Test
	public void verifyFormattedRegistrationString1() {
		String str = ConverterUtils.getFormattedRegistrationString1("Test", 13,
				13, "0");
		assertThat(str).isEqualTo("Test000000000");
		assertThat(str).isNotEqualTo("Test1000000000");
		String str1 = ConverterUtils.getFormattedRegistrationString1("Test",
				13, 3, "0");
		assertThat(str1).isEqualTo("Tes0000000000");
		assertThat(str1).isNotEqualTo("Test0000000000");
	}

	@Test
	public void verifyUnformatterString() {
		String str = ConverterUtils.getUnformatterString("Test", "0");
		assertThat(str).isEqualTo("Test");
		assertThat(str).isNotEqualTo("Test1");
	}

	@Test
	public void verifyFormattedStringForAmt() {
		String str = ConverterUtils.getFormattedString(new BigDecimal(1000.00),12, "0",true);
	    assertThat(str).isEqualTo("000000100000");

	    str = ConverterUtils.getFormattedString(new BigDecimal(1000.25),12, "0",true);
	    assertThat(str).isEqualTo("000000100025");  
	    
	    str = ConverterUtils.getFormattedString(new BigDecimal(1000.26),12, "0",true);
	    assertThat(str).isEqualTo("000000100026");
	    
	    str = ConverterUtils.getFormattedString(new BigDecimal(1000.22),12, "0",true);
	    assertThat(str).isEqualTo("000000100022");
	}
	
	@Test
	public void verifyFormattedStringForSSN() {
		String str = ConverterUtils.getFormattedString2("123-45-6789",9, "0");
	    assertThat(str).isEqualTo("123456789");

	    str = ConverterUtils.getFormattedString2("123-45-64",9, "0");
	    assertThat(str).isEqualTo("001234564");	   
	}

	@Test
	public void verifyDbIssueVoidStringDate() {
		SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("MMddyy");
		String stringDate = new String(dateformatMMDDYYYY.format(new Date()));
		String str = ConverterUtils.getDbIssueVoidStringDate(new Date());
		assertThat(str).isEqualTo(stringDate);

	}

	@Test
	public void verifyIfdsConvertedStatusDate() {
		SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("MMddyyyy");
		String stringDate = new String(dateformatMMDDYYYY.format(new Date()));
		String str = ConverterUtils.getIfdsConvertedStatusDate(new Date());
		assertThat(str).isEqualTo(stringDate);
	}

	@Test
	public void verifyFormattedDate1() {
		SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("yyyyMMdd");
		String stringDate = new String(dateformatMMDDYYYY.format(new Date()));
		String str = ConverterUtils.getFormattedDate1(new Date());
		assertThat(str).isEqualTo(stringDate);
	}

	@Test
	public void verifyFormattedStringForDate() {
		SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss a");
		String stringDate = new String(dateformatMMDDYYYY.format(new Date()));
		String str = ConverterUtils.getFormattedString(new Date(),
				"yyyy-MM-dd hh:mm:ss a");
		assertThat(str).isEqualTo(stringDate);
	}

	@Ignore("Need To Fix")
	@Test
	public void verifyFormattedDate() {
		Date date = ConverterUtils.getStringFormattedDate("12152011",
				"MMddyyyy");
		assertThat(date).isEqualTo("");

	}

	@Test
	public void verifyFormatedYearDate() {
		SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
		StringBuilder formatedDate = new StringBuilder(
				dateformat.format(new Date()));
		String str = ConverterUtils.getFormatedYearDate(new Date(),
				"MM/dd/yyyy");
		assertThat(str).isEqualTo(formatedDate.toString());
		String str1 = ConverterUtils.getFormatedYearDate(null, "MM/dd/yyyy");
		assertThat(str1).isNull();
	}

	@Test
	public void verifyConvertedStringOrZeroSpace() {

		String resultForExtraLengthString = ConverterUtils
				.getConvertedStringOrZeroSpace("testString", 4, 4, " ");
		String resultForLessLengthString = ConverterUtils
				.getConvertedStringOrZeroSpace("te", 4, 4, " ");
		String nullStringResult = ConverterUtils.getConvertedStringOrZeroSpace(
				null, 4, 4, " ");
		assertThat(resultForExtraLengthString).isNotNull();
		assertThat(resultForExtraLengthString.length()).isEqualTo(4);
		assertThat(resultForExtraLengthString.length()).isNotEqualTo(5);
		assertThat(resultForExtraLengthString).isEqualTo("test");
		assertThat(resultForLessLengthString.length()).isEqualTo(4);
		assertThat(resultForLessLengthString).isEqualTo("te  ");
		assertThat(nullStringResult).isEqualTo("");

	}

	@Test
	public void verifyConvertedNumberOrZeroSpace() {

		String resultForExtraNumbersString = ConverterUtils
				.getConvertedStringOrZeroSpace("12345678", 4, 4, "0");
		String resultForLessNumbersString = ConverterUtils
				.getConvertedNumberOrZeroSpace("12", 4, 4, "0");
		String nullStringResult = ConverterUtils.getConvertedNumberOrZeroSpace(
				null, 4, 4, "0");
		assertThat(resultForExtraNumbersString).isNotNull();
		assertThat(resultForExtraNumbersString.length()).isEqualTo(4);
		assertThat(resultForExtraNumbersString.length()).isNotEqualTo(5);
		assertThat(resultForExtraNumbersString).isEqualTo("1234");
		assertThat(resultForLessNumbersString.length()).isEqualTo(4);
		assertThat(resultForLessNumbersString).isEqualTo("0012");
		assertThat(nullStringResult).isEqualTo("");

	}

	@Test
	public void verifyAmountWithDecimalPositions() {

		String resultForExactLengthAmount = ConverterUtils
				.getAmountWithDecimalPositions(0000000400.00, 2, 2, 13, "0");

		String resultForLessLengthAmount = ConverterUtils
				.getAmountWithDecimalPositions(400, 2, 2, 13, "0");

		String resultForZeroAmount = ConverterUtils
				.getAmountWithDecimalPositions(0, 2, 2, 13, "0");

		assertThat(resultForExactLengthAmount).isNotNull();
		assertThat(resultForExactLengthAmount.length()).isEqualTo(13);
		assertThat(resultForLessLengthAmount.length()).isNotEqualTo(6);
		assertThat(resultForExactLengthAmount).isEqualTo("0000000400.00");
		assertThat(resultForLessLengthAmount.length()).isEqualTo(13);
		assertThat(resultForLessLengthAmount).isEqualTo("0000000400.00");
		assertThat(resultForZeroAmount).isEqualTo("0000000000.00");

	}
	
	@Test
	public void verifyGetFormattedAndTruncatedString1()
	{
		String str=ConverterUtils.getFormattedAndTruncatedString1("", 20, 20, "0");
		assertThat(str).isNotEqualTo("000000000000000000000");
		
		 str=ConverterUtils.getFormattedAndTruncatedString1("666", 20, 20, "0");
		assertThat(str).isEqualTo("66600000000000000000");
		
		str=ConverterUtils.getFormattedAndTruncatedString1("666", 20, 20, "0");
		assertThat(str).isNotEqualTo("6660000000000000000");
		 
	}

}
