package com.bfds.saec.util;

public class AddressUtils {

	
	/**
	 * 
	 * @param addressLines {@link Object} array. Can be null.
	 * @param city {@link String},can be null.
	 * @param stateCode {@link String},can be null.
	 * @param zip {@link String}, can be null.
	 * @param zipExt {@link String},can be null.
	 * @param countryCode {@link String}, can be null.
	 * @param lineSeperator {@link String}, can be " ",- or any other String which we want to use while concatenating words.can't be null.
	 * @return A {@link String} which contains all the values passed as input parameter.null if all the parameters are null.
	 */
	public static String getAddressAsString(final Object[] addressLines,
			final String city, final String stateCode, final String zip, final String zipExt,
			final String countryCode,String lineSeperator) {

		int addressLinesLength = addressLines.length;
		final String[] address = new String[addressLinesLength + 2];
		System.arraycopy(addressLines, 0, address, 0, addressLinesLength);
		final String zipCode = SaecStringUtils.getArrayString(
				new String[] { zip, zipExt }, "-");
		address[addressLinesLength++] = SaecStringUtils.getArrayString(
				new String[] { city, stateCode, zipCode }, " "); // Use a space and not lineSeperator
		address[addressLinesLength++] = countryCode;
		return SaecStringUtils.getArrayString(address, lineSeperator);
	}

}
