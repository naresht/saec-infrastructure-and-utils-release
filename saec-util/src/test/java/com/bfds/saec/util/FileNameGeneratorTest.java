package com.bfds.saec.util;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;

import org.junit.Test;

public class FileNameGeneratorTest {

	final FileNameGenerator generator = new FileNameGenerator();
	
	@Test
	public void testNoSubstitution() {
		assertThat(generator.generateFileName("*.*")).isEqualTo("*.*");
		assertThat(generator.generateFileName("a.txt")).isEqualTo("a.txt");
	}
	
	@Test
	public void testDdaSubstitution() {
		assertThat(generator.generateFileName("DeutscheBankIssuevoid_<dda>_CCYYMMDDHHmm_out", "100001", null)).isEqualTo("DeutscheBankIssuevoid_100001_CCYYMMDDHHmm_out");
		
		assertThat(generator.generateFileName("DeutscheBankIssuevoid_<dda>_<dda>_CCYYMMDDHHmm_out", "100001", null)).isEqualTo("DeutscheBankIssuevoid_100001_100001_CCYYMMDDHHmm_out");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMissingDdaSubstitution() {
		generator.generateFileName("DeutscheBankIssuevoid_<dda>_CCYYMMDDHHmm_out");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidDdaPlaceholder() {
		generator.generateFileName("DeutscheBankIssuevoid_<dda_CCYYMMDDHHmm_out", "101", null);
	}
	
	@Test
	public void testDateSubstitutionyyyyMMddHHmm() {
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 1, 5, 13, 30);

		assertThat(generator.generateFileName("DeutscheBankIssuevoid_<dda>_<yyyyMMddHHmm>_out", "100001", cal.getTime())).isEqualTo("DeutscheBankIssuevoid_100001_201202051330_out");
		
		assertThat(generator.generateFileName("DeutscheBankIssuevoid_<dda>_<dda>_<yyyyMMddHHmm>_<yyyyMMddHHmm>_out", "100001", cal.getTime())).isEqualTo("DeutscheBankIssuevoid_100001_100001_201202051330_201202051330_out");
	}
	
	@Test
	public void testDateSubstitutionddMMyyyyHHmmssSSS() {
		Calendar cal = Calendar.getInstance();
		
		cal.set(2012, 04, 03, 12, 54, 24);
	
		assertThat(generator.generateFileName("SAEC_DBPDCK_<ddMMyyyyHHmmssSSS>", cal.getTime())).contains("SAEC_DBPDCK_03052012125424");
		assertThat(generator.generateFileName("SAEC_DBPDCK_<ddMMyyyyHHmmssSSS>", cal.getTime()).length()).isEqualTo(29);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidDateSubstitutionyyyyMMddHHmm() {
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 1, 5, 13, 30);

		assertThat(generator.generateFileName("DeutscheBankIssuevoid_<dda>_<yyyyssMMddHHmm>_out", "100001", cal.getTime())).isEqualTo("DeutscheBankIssuevoid_100001_201202051330_out");		
	}
	
	@Test
	public void testDateSubstitutionddMMyyyyHH() {
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 1, 5, 13, 30);

		assertThat(generator.generateFileName("DeutscheBankIssuevoid_<dda>_<ddMMyyyyHH>_out", "100001", cal.getTime())).isEqualTo("DeutscheBankIssuevoid_100001_0502201213_out");
		
	}
}
