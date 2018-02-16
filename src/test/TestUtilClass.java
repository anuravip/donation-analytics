package test;

import java.util.*;

import org.junit.Test;

import com.insight.codingchallenge.UtilClass;
import junit.framework.Assert;

public class TestUtilClass {

	@Test
	public void testValidAmount() {

		String[] amounts = { "128938.9990", "12000" };
		for (String amount : amounts)
			Assert.assertTrue(UtilClass.isValidAmount(amount));

	}

	@Test
	public void testInvalidAmount() {

		String[] amounts = { "128938.9990o", "", null };
		for (String amount : amounts)
			Assert.assertFalse(UtilClass.isValidAmount(amount));
	}

	@Test
	public void testValidDateFormat() {

		String date = "01122016";
		boolean flag = UtilClass.isValidDate(date);
		Assert.assertTrue(flag);

	}

	@Test
	public void testInvalidDateFormat() {

		String[] dates = { "20160112", "24112016", "jan24201", "", null };
		for (String date : dates)
			Assert.assertFalse(UtilClass.isValidDate(date));

	}

	@Test
	public void testValidName() {

		String name = "CHRULSKI, TIM MR.";
		Assert.assertTrue(UtilClass.isValidName(name));

	}

	@Test
	public void testInValidName() {

		String[] names = { " ", null };
		for (String name : names)
			Assert.assertFalse(UtilClass.isValidName(name));

	}

	@Test
	public void testValidZip() {

		String[] zipcodes = { "12345", "123456789", "123456" };
		for (String zipcode : zipcodes)
			Assert.assertTrue(UtilClass.isValidZip(zipcode));
	}

	@Test
	public void testInValidZip() {

		String[] zipcodes = { "1234", "123-456", "1234567891", "", null };
		for (String zipcode : zipcodes)
			Assert.assertFalse(UtilClass.isValidZip(zipcode));
	}

	@Test
	public void testGetPercentileValue() {
		List<Float> values = new ArrayList<Float>();// (Arrays.asList((Float[])arr));

		values.add(50.0f);
		values.add(35.0f);
		values.add(40.0f);
		values.add(20.0f);
		values.add(15.0f);
		float percentileVal = UtilClass.getPercentileValue(values, 100);
		// System.out.println(percentileVal);
		Assert.assertEquals(percentileVal, 50.0f);

		percentileVal = UtilClass.getPercentileValue(values, 5);
		Assert.assertEquals(percentileVal, 15.0f);

	}

	@Test
	public void testIsEmpty() {

		String s = "";
		Assert.assertTrue(UtilClass.isEmpty(s));

		s = null;
		Assert.assertTrue(UtilClass.isEmpty(s));

	}

	@Test
	public void testIsEmptyWithData() {

		String s = "test";
		Assert.assertFalse(UtilClass.isEmpty(s));

	}
}
