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

		// Testing that values of Cl below 5000 and over 186650 will return
		// values of 0
		// using edge testing
		assertEquals(0.0f, test.calcPfOrVsf(4999)[0]);
		assertEquals(0.0f, test.calcPfOrVsf(4999)[1]);
		assertEquals(0.0f, test.calcPfOrVsf(186651)[0]);
		assertEquals(0.0f, test.calcPfOrVsf(186651)[1]);

		// Testing that calculated works with floats
		assertEquals(0.3f, test.calcPfOrVsf(5000.0f)[0]);
		assertEquals(1.004f, test.calcPfOrVsf(5000.0f)[1]);

		// Testing that calculator works for middle values by calculating Vsf
		// and Pf using the table
		float Pf;
		float Vsf;
		float Cl;

		// Testing Cl between 5000 and 10000 using edge
		Cl = 5001;
		Pf = (float) (0.3 + (0.6 - 0.3) * ((Cl - 5000) / (10000 - 5000)));
		Vsf = (float) (1.004 + (1.010 - 1.004) * ((Cl - 5000) / (10000 - 5000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 9999;
		Pf = (float) (0.3 + (0.6 - 0.3) * ((Cl - 5000) / (10000 - 5000)));
		Vsf = (float) (1.004 + (1.010 - 1.004) * ((Cl - 5000) / (10000 - 5000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 10000 and 20000 using edge
		Cl = 10001;
		Pf = (float) (0.6 + (1.2 - 0.6) * ((Cl - 10000) / (20000 - 10000)));
		Vsf = (float) (1.010 + (1.021 - 1.010)
				* ((Cl - 10000) / (20000 - 10000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 19999;
		Pf = (float) (0.6 + (1.2 - 0.6) * ((Cl - 10000) / (20000 - 10000)));
		Vsf = (float) (1.010 + (1.021 - 1.010)
				* ((Cl - 10000) / (20000 - 10000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 20000 and 30000 using edge
		Cl = 20001;
		Pf = (float) (1.2 + (1.8 - 1.2) * ((Cl - 20000) / (30000 - 20000)));
		Vsf = (float) (1.021 + (1.032 - 1.021)
				* ((Cl - 20000) / (30000 - 20000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 29999;
		Pf = (float) (1.2 + (1.8 - 1.2) * ((Cl - 20000) / (30000 - 20000)));
		Vsf = (float) (1.021 + (1.032 - 1.021)
				* ((Cl - 20000) / (30000 - 20000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 30000 and 40000 using edge
		Cl = 30001;
		Pf = (float) (1.8 + (2.3 - 1.8) * ((Cl - 30000) / (40000 - 30000)));
		Vsf = (float) (1.032 + (1.043 - 1.032)
				* ((Cl - 30000) / (40000 - 30000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 39999;
		Pf = (float) (1.8 + (2.3 - 1.8) * ((Cl - 30000) / (40000 - 30000)));
		Vsf = (float) (1.032 + (1.043 - 1.032)
				* ((Cl - 30000) / (40000 - 30000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 40000 and 60000 using edge
		Cl = 40001;
		Pf = (float) (2.3 + (3.4 - 2.3) * ((Cl - 40000) / (60000 - 40000)));
		Vsf = (float) (1.043 + (1.065 - 1.043)
				* ((Cl - 40000) / (60000 - 40000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 59999;
		Pf = (float) (2.3 + (3.4 - 2.3) * ((Cl - 40000) / (60000 - 40000)));
		Vsf = (float) (1.043 + (1.065 - 1.043)
				* ((Cl - 40000) / (60000 - 40000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 60000 and 80000 using edge
		Cl = 60001;
		Pf = (float) (3.4 + (4.5 - 3.4) * ((Cl - 60000) / (80000 - 60000)));
		Vsf = (float) (1.065 + (1.082 - 1.065)
				* ((Cl - 60000) / (80000 - 60000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 79999;
		Pf = (float) (3.4 + (4.5 - 3.4) * ((Cl - 60000) / (80000 - 60000)));
		Vsf = (float) (1.065 + (1.082 - 1.065)
				* ((Cl - 60000) / (80000 - 60000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 80000 and 100000 using edge
		Cl = 80001;
		Pf = (float) (4.5 + (5.7 - 4.5) * ((Cl - 80000) / (100000 - 80000)));
		Vsf = (float) (1.082 + (1.098 - 1.082)
				* ((Cl - 80000) / (100000 - 80000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 99999;
		Pf = (float) (4.5 + (5.7 - 4.5) * ((Cl - 80000) / (100000 - 80000)));
		Vsf = (float) (1.082 + (1.098 - 1.082)
				* ((Cl - 80000) / (100000 - 80000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 100000 and 120000 using edge
		Cl = 100001;
		Pf = (float) (5.7 + (7.0 - 5.7) * ((Cl - 100000) / (120000 - 100000)));
		Vsf = (float) (1.098 + (1.129 - 1.098)
				* ((Cl - 100000) / (120000 - 100000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 119999;
		Pf = (float) (5.7 + (7.0 - 5.7) * ((Cl - 100000) / (120000 - 100000)));
		Vsf = (float) (1.098 + (1.129 - 1.098)
				* ((Cl - 100000) / (120000 - 100000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 120000 and 140000 using edge
		Cl = 120001;
		Pf = (float) (7.0 + (8.2 - 7.0) * ((Cl - 120000) / (140000 - 120000)));
		Vsf = (float) (1.129 + (1.149 - 1.129)
				* ((Cl - 120000) / (140000 - 120000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);
		
		Cl = 139999;
		Pf = (float) (7.0 + (8.2 - 7.0) * ((Cl - 120000) / (140000 - 120000)));
		Vsf = (float) (1.129 + (1.149 - 1.129)
				* ((Cl - 120000) / (140000 - 120000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 140000 and 160000 using edge
		Cl = 140001;
		Pf = (float) (8.2 + (9.5 - 8.2) * ((Cl - 140000) / (160000 - 140000)));
		Vsf = (float) (1.149 + (1.170 - 1.149)
				* ((Cl - 140000) / (160000 - 140000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 159999;
		Pf = (float) (8.2 + (9.5 - 8.2) * ((Cl - 140000) / (160000 - 140000)));
		Vsf = (float) (1.149 + (1.170 - 1.149)
				* ((Cl - 140000) / (160000 - 140000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 160000 and 180000 using edge
		Cl = 160001;
		Pf = (float) (9.5 + (10.8 - 9.5) * ((Cl - 160000) / (180000 - 160000)));
		Vsf = (float) (1.170 + (1.194 - 1.170)
				* ((Cl - 160000) / (180000 - 160000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 179999;
		Pf = (float) (9.5 + (10.8 - 9.5) * ((Cl - 160000) / (180000 - 160000)));
		Vsf = (float) (1.170 + (1.194 - 1.170)
				* ((Cl - 160000) / (180000 - 160000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 180000 and 186650 using edge
		Cl = 180001;
		Pf = (float) (10.8 + (11.4 - 10.8) * ((Cl - 180000) / (186650 - 180000)));
		Vsf = (float) (1.194 + (1.197 - 1.194)
				* ((Cl - 180000) / (186650 - 180000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

		Cl = 186649;
		Pf = (float) (10.8 + (11.4 - 10.8) * ((Cl - 180000) / (186650 - 180000)));
		Vsf = (float) (1.194 + (1.197 - 1.194)
				* ((Cl - 180000) / (186650 - 180000)));
		assertEquals(Pf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[1]);

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
