package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.*;

import android.test.AndroidTestCase;

public class Spess_tetthetTest extends AndroidTestCase {
	Spess_tetthet test;
	float[] variablesForPf = { 100, 50 };
	float[] variablesForVs = { 100, 50 };
	float[] variablesForVfs = { 100, 50 };

	float[] variablesForPp = { 100, 50, 0.245f, 0, 2, 2, 2, 2 };
	float[] variablesForPpToFail = { 4, 0, 0.245f, 1, 2, 2, 2, 2 };

	protected void setUp() throws Exception {
		super.setUp();
		test = new Spess_tetthet(getContext());
	}

	public void testcalcPfOrVsf() {

		float[] Cl_values = { 5000, 10000, 20000, 30000, 40000, 60000, 80000,
				100000, 120000, 140000, 160000, 180000, 186650 };
		float[] Pf_values = { 0.3f, 0.6f, 1.2f, 1.8f, 2.3f, 3.4f, 4.5f, 5.7f,
				7.0f, 8.2f, 9.5f, 10.8f, 11.4f };
		float[] Vsf_values = { 1.004f, 1.010f, 1.021f, 1.032f, 1.043f, 1.065f,
				1.082f, 1.098f, 1.129f, 1.149f, 1.170f, 1.194f, 1.197f };

		// Testing that values of Cl that match any Cl table value will work
		for (int i = 0; i < Cl_values.length; i++) {
			
			assertEquals(Pf_values[i], test.calcPfOrVsf(Cl_values[i])[0]);
			assertEquals(Vsf_values[i], test.calcPfOrVsf(Cl_values[i])[1]);
		}
		
		//Testing that values of Cl below 5000 and over 186650 will return values of 0
		//using edge testing
		assertEquals(0.0f, test.calcPfOrVsf(4999)[0]);
		assertEquals(0.0f, test.calcPfOrVsf(4999)[1]);
		assertEquals(0.0f, test.calcPfOrVsf(186651)[0]);
		assertEquals(0.0f, test.calcPfOrVsf(186651)[1]);
		
		//Testing that calculated works with floats
		assertEquals(0.3f, test.calcPfOrVsf(5000.0f)[0]);
		assertEquals(1.004f, test.calcPfOrVsf(5000.0f)[1]);
		
		//Testing that calculator works for middle values 
		
		/**
		 * INSERT CODE HERE
		 */
		
	}

	public void testcalcVs() {

		// Testing Vs
		assertEquals(100.0f, test.calcVs(10, 10));
		assertEquals(0.0f, test.calcVs(0, 10));
		assertEquals(0.0f, test.calcVs(10, 0));

		
	}

	public void testCalculation() {

		// Testing Pp fails and displays LogCat message
		assertEquals("", test.calculation(2, variablesForPpToFail));

	}
}
