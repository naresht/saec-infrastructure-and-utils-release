package com.bfds.saec.util;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class AddressUtilsTest {

	private String[] addressLines = new String[2];

	@Test
	public void getAddressAsString() {

		addressLines[0] = "2397";
		addressLines[1] = "Bee Street";

		String address = AddressUtils.getAddressAsString(addressLines,
				"Muskegon", "MI", "49470", "21", "1", " ");
		assertThat(address).isEqualTo("2397 Bee Street Muskegon MI 49470-21 1");
	}

}
