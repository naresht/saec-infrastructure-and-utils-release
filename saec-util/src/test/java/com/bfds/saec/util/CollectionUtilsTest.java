package com.bfds.saec.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class CollectionUtilsTest {
	
	@Test
	public void testFirstN() {

		List<String> stringElements = new ArrayList<String>();
		stringElements.add("element1");
		stringElements.add("element2");
		stringElements.add("element3");

		List<String> firstNElements = CollectionUtils.firstN(stringElements, 2);

		assertEquals(2, firstNElements.size());

	}
	
	@Test
	public void testMax(){
		List<Number> numbers = new ArrayList<Number>();
		numbers.add(10);
		numbers.add(20);
		numbers.add(30);
		numbers.add(40);//40 is the max value added in the list.
		assertEquals(40, CollectionUtils.max(numbers));
	}

}
