package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.*;

import android.test.AndroidTestCase;

public class MasseOgVolumBalanseTest extends AndroidTestCase {

	Basic_Calc test;
	float[] variables1 = { 0, 10, 20, 10, 40 };
	float[] variables2 = { 5, 0, 20, 10, 40 };
	float[] variables3 = { 5, 10, 0, 10, 40 };
	float[] variables4 = { 5, 10, 20, 0, 40 };
	float[] variables5 = { 5, 10, 20, 10, 0 };

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		test = new MasseOgVolumBalanse(getContext());

	}

	public void testCalculationIntFloatFloat() {

		// Testing Vv
		assertEquals("5.000", test.calculation(0, variables1));

		// Testing V1
		assertEquals("10.000", test.calculation(1, variables2));

		// Testing p2
		assertEquals("20.000", test.calculation(2, variables3));
		
		// Testing pv
		assertEquals("10.000", test.calculation(3, variables4));
		
		// Testing p1
		assertEquals("40.000", test.calculation(4, variables5));

	}
}
