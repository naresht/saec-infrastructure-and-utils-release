package com.bfds.saec.util;

import static com.bfds.saec.util.SaecArrayUtils.concat;
import static com.bfds.saec.util.SaecArrayUtils.getNonEmptyValues;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class SaecArrayUtilsTest {

	@Test
	public void testNonEmptyValues() {
		String[] values = { "1", "2", "3", "4", "5", "6" };
		assertThat(getNonEmptyValues(values)).containsOnly("1", "2", "3", "4",
				"5", "6");

		values = new String[] { "1", "", "3", "", "5", null };
		assertThat(getNonEmptyValues(values)).containsOnly("1", "3", "5");
	}

	@Test
	public void testConcat() {
		String[] array1 = { "1", "2", "3", "4", "5", "6" };

		String[] array2 = { "a", "b", "c", "d", "e", "f" };
		assertThat(concat(array1, array2)).containsOnly("1", "2", "3", "4",
				"5", "6", "a", "b", "c", "d", "e", "f");
	}

	@Test
	public void removeEmptyValuesAndConcat() {
		String[] array1 = { "1", "2", "", "4", "5", null };

		String[] array2 = { "a", null, "c", "d", "", null };
		assertThat(concat(getNonEmptyValues(array1), getNonEmptyValues(array2)))
				.containsOnly("1", "2", "4", "5", "a", "c", "d");
	}

}
