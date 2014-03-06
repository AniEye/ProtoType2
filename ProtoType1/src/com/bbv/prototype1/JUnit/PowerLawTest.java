package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.PowerLaw;

import android.test.AndroidTestCase;
import junit.framework.TestCase;

public class PowerLawTest extends AndroidTestCase {
	Basic_Calc test;
	float[] variables1 = { 100, 50, 0, 0 };
	float[] variables2 = { 100, 50, 0.245f, 0 };

	protected void setUp() throws Exception {
		super.setUp();
		test = new PowerLaw(getContext());
	}

	public void testCalculation() {

		// Testing n
		assertEquals("0.245", test.calculation(2, variables1));

		// Testing k fails and displays LogCat message
		assertEquals("", test.calculation(3, variables1));

		// Testing k doesn't fail
		assertEquals("28.443", test.calculation(3, variables2));

		// Testing that the program catches dividing with 0 errors
		float[] variablesDivideBy0 = { 0, 0, 0, 0 };
		assertEquals("", test.calculation(0, variablesDivideBy0));
		assertEquals("", test.calculation(1, variablesDivideBy0));
		assertEquals("", test.calculation(2, variablesDivideBy0));
		assertEquals("", test.calculation(3, variablesDivideBy0));
	}

}
