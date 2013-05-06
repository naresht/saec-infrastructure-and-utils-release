package com.bfds.saec.util;


import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;

import com.bfds.saec.util.CsvReader;
import com.bfds.saec.util.CsvReader.GarbageLineException;
import com.bfds.saec.util.CsvReader.NoSuchColumnException;
import com.bfds.saec.util.CsvReader.UnsupportedTypeException;

public class CsvReaderTest {

	@Test
	@Ignore
	public void readWithHeaders() throws IOException, UnsupportedTypeException,
			GarbageLineException, NoSuchColumnException {
		CsvReader csvr = new CsvReader(
				"CsvReaderTest.csv",
				false, true, true);
		csvr.setColumn("Number", String.class);
		csvr.setColumn("GivenName", String.class);
		Map<String, Object> line = csvr.readLine();
		assertThat(line.size(), CoreMatchers.is(2));
		
	}
		
}
