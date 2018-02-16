package com.insight.codingchallenge;

import java.util.Collections;
import java.util.List;

public class UtilClass {

	private static final String DATE_REGEX = "^(1[0-2]|0[1-9])(3[01]|[12][0-9]|0[1-9])[0-9]{4}$";

	//private static final String[] ZIP_REGEX = { "^\\d{9}$", "^\\d{5}(-\\d{4})?$" };

	// validation method for transaction amount. 
	public static boolean isValidAmount(String transaction_amount) {

		try {
			Float.parseFloat(transaction_amount.trim());
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	// validation method for transaction date
	public static boolean isValidDate(String transaction_date) {
		if (isEmpty(transaction_date) || !transaction_date.matches(DATE_REGEX))
			return false;
		else
			return true;

	}

	// more validation for malformed string.name can contain any kind of character.fix
	public static boolean isValidName(String name) {

		if (isEmpty(name))
			return false;
		return true;
	}

	// validation method for zip code
	public static boolean isValidZip(String zipcode) {

		if (isEmpty(zipcode)||zipcode.length()<5||zipcode.length()>9)
			return false;
		for(int i=0;i<5;i++){
			if(!Character.isLetterOrDigit(zipcode.charAt(i)))return false;
		}
		
		return true;
	}

	//validation to check for empty or null string
	public static boolean isEmpty(String str) {

		if (str == null || str.trim().equals(""))
			return true;
		return false;
	}
	
	// method to calculate the percentile index.
		public static int getPercentileValue(List<Float> amounts,int percentile) {
			Collections.sort(amounts);
			int num_donations = amounts.size();
			int index =  (int) Math.ceil(percentile / 100.0 * num_donations);
			return Math.round(amounts.get(index-1));
			
			
		}

	

}
