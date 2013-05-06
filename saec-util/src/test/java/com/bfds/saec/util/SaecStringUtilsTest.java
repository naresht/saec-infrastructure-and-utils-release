package com.bfds.saec.util;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SaecStringUtilsTest {
	
	
	@Test
	public void testGetArrayString(){
		SaecStringUtils saecStringUtils = new SaecStringUtils();
		String str = saecStringUtils.getArrayString(new String[] { "string1", "string2" });
		assertThat(str).isEqualTo("string1, string2");
		assertThat(str).isNotEqualTo("string1-string2");
	}
	
	@Test
	public void verifyGetArrayString(){
		SaecStringUtils saecStringUtils = new SaecStringUtils();
		String str = saecStringUtils.getArrayString(new String[] { "string1", "string2" }, "-");
		assertThat(str).isEqualTo("string1-string2");
		assertThat(str).isNotEqualTo("string1,string2");
		String[] list1 = null;
		String s = saecStringUtils.getArrayString(list1, "-");
		assertThat(s).isEqualTo(null);
	}
	
	@Test
	public void verifyGetAsString(){
		SaecStringUtils saecStringUtils = new SaecStringUtils();
		List<String> list = new ArrayList<String>();
		list.add("One");
		list.add("Two");
		list.add("Three");
		String str = saecStringUtils.getAsString(list, "-");
		assertThat(str).isEqualTo("One-Two-Three");
		assertThat(str).isNotEqualTo("One Two Three");
		List<String> list1 = null;
		String s = saecStringUtils.getAsString(list1, "-");
		assertThat(s).isEqualTo("");
		
	}
	
	@Test
	public void verifyDefaultString(){
		SaecStringUtils saecStringUtils = new SaecStringUtils();
		String defaultText = saecStringUtils.defaultString("Test");
		assertThat(defaultText).isEqualTo("Test");
		assertThat(defaultText).isNotEqualTo("Test ");
	}
	
	@Test
	public void verifyGetAsDouble(){
		SaecStringUtils saecStringUtils = new SaecStringUtils();
		double val = saecStringUtils.getAsDouble("1000");
		assertThat(val).isEqualTo(1000.0);
		double val1 = saecStringUtils.getAsDouble("test");
		assertThat(val).isEqualTo(1000.0);
		
		
	}
	
	

}
