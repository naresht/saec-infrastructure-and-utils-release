package com.bfds.saec.util;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;
public class FileUtilsTest {

	@Test
	public void getFileName() {
		String file = "C:/a/b/c.txt";
		assertThat(FileUtils.getFileName(file)).isEqualTo("c.txt");
	}
}
