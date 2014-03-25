package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.*;

import android.test.AndroidTestCase;

public class Spess_tetthetTest extends AndroidTestCase {
	Spess_tetthet test;
	float epsilon = 0.0001f;
	float[] variablesForPf = { 100, 50 };
	float[] variablesForVs = { 100, 50 };
	float[] variablesForVfs = { 100, 50 };

	float[] variablesForPp = { 0, 5088f, 0.245f, 5f, 2f, 2f, 2f, 2f };

	protected void setUp() throws Exception {
		super.setUp();
		test = new Spess_tetthet(getContext());
	}

	public void testCalculation() {

		// Testing Pp fails and displays LogCat message
		assertEquals("", test.calculation(1, variablesForPp));
		assertEquals("", test.calculation(2, variablesForPp));
		assertEquals("", test.calculation(3, variablesForPp));
		assertEquals("", test.calculation(4, variablesForPp));
		assertEquals("", test.calculation(5, variablesForPp));
		assertEquals("", test.calculation(6, variablesForPp));
		assertEquals("", test.calculation(7, variablesForPp));

		// Testing that Pp catches dividing by 0 error and displays a Toast
		// By putting Cl as 5000, Vs will be 0.3, and when Vsf = Vs (0.3), there
		// will be
		// a dividing with 0 error!
		float[] variablesForPpWithError = { 0, 5000f, 0.245f, 5f, 2f, 2f, 1f,
				0.3f };
		assertEquals("", test.calculation(0, variablesForPpWithError));

		// Testing that Pp succeeds, and returns correct value

		float Cl = 5088f;
		float Pm = 0.245f;
		float Po = 5f;
		float Vv = 2f;
		float Vo = 2f;
		float Fw = 2f;
		float Vfs = 2f;
		float Vsf = (0.3f + (0.6f - 0.3f) * ((Cl - 5000f) / (10000f - 5000f)));
		float Pf = (1.004f + (1.010f - 1.004f)
				* ((Cl - 5000f) / (10000f - 5000f)));
		float Vs = Vsf * Fw;

		float Pp = (100 * Pm - ((Pf * (Vv + Vsf) + (Po * Vo)))) / (Vfs - Vs);
		String Pp_string = String.format("%.3f", Pp);

		assertEquals(Pp_string, test.calculation(0, variablesForPp));

	}

	public void testcalcPfOrVsf() {

		float[] Cl_values = { 5000, 10000, 20000, 30000, 40000, 60000, 80000,
				100000, 120000, 140000, 160000, 180000, 186650 };
		float[] Vsf_values = { 0.3f, 0.6f, 1.2f, 1.8f, 2.3f, 3.4f, 4.5f, 5.7f,
				7.0f, 8.2f, 9.5f, 10.8f, 11.4f };
		float[] Pf_values = { 1.004f, 1.010f, 1.021f, 1.032f, 1.043f, 1.065f,
				1.082f, 1.098f, 1.129f, 1.149f, 1.170f, 1.194f, 1.197f };

		// Testing that values of Cl that match any Cl table value will work
		for (int i = 0; i < Cl_values.length; i++) {
			assertEquals(Vsf_values[i], test.calcPfOrVsf(Cl_values[i])[0]);
			assertEquals(Pf_values[i], test.calcPfOrVsf(Cl_values[i])[1]);
		}

		// Testing that values of Cl below 0 and over 186650 will return
		// values of 0 using edge testing
		assertEquals(0.0f, test.calcPfOrVsf(-1)[0]);
		assertEquals(0.0f, test.calcPfOrVsf(-1)[1]);
		assertEquals(0.0f, test.calcPfOrVsf(186651)[0]);
		assertEquals(0.0f, test.calcPfOrVsf(186651)[1]);

		// Testing that calculated works with floats
		assertEquals(0.3f, test.calcPfOrVsf(5000.0f)[0]);
		assertEquals(1.004f, test.calcPfOrVsf(5000.0f)[1]);

		// Testing that calculator works for middle values by calculating Vsf
		// and Pf using the table
		float Vsf;
		float Pf;
		float Cl;

		// Testing Cl between 0 and 5000 using edge
		Cl = 1;
		Vsf = (0f + (0.3f - 0f) * ((Cl - 0) / (5000 - 0)));
		Pf = (1 + (1.004f - 1) * ((Cl - 0) / (5000 - 0)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 4999;
		Vsf = (0f + (0.3f - 0f) * ((Cl - 0) / (5000 - 0)));
		Pf = (1 + (1.004f - 1) * ((Cl - 0) / (5000 - 0)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 5000 and 10000 using edge
		Cl = 5001;
		Vsf = (0.3f + (0.6f - 0.3f) * ((Cl - 5000f) / (10000f - 5000f)));
		Pf = (1.004f + (1.010f - 1.004f) * ((Cl - 5000f) / (10000f - 5000f)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 9999;
		Vsf = (0.3f + (0.6f - 0.3f) * ((Cl - 5000.0f) / (10000 - 5000)));
		Pf = (1.004f + (1.010f - 1.004f) * ((Cl - 5000.0f) / (10000 - 5000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 10000 and 20000 using edge
		Cl = 10001;
		Vsf = (0.6f + (1.2f - 0.6f) * ((Cl - 10000f) / (20000f - 10000f)));
		Pf = (1.010f + (1.021f - 1.010f) * ((Cl - 10000f) / (20000f - 10000f)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 19999;
		Vsf = (0.6f + (1.2f - 0.6f) * ((Cl - 10000f) / (20000 - 10000)));
		Pf = (1.010f + (1.021f - 1.010f) * ((Cl - 10000f) / (20000 - 10000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 20000 and 30000 using edge
		Cl = 20001;
		Vsf = (1.2f + (1.8f - 1.2f) * ((Cl - 20000f) / (30000 - 20000)));
		Pf = (1.021f + (1.032f - 1.021f) * ((Cl - 20000f) / (30000 - 20000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 29999;
		Vsf = (1.2f + (1.8f - 1.2f) * ((Cl - 20000f) / (30000 - 20000)));
		Pf = (1.021f + (1.032f - 1.021f) * ((Cl - 20000f) / (30000 - 20000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 30000 and 40000 using edge
		Cl = 30001;
		Vsf = (1.8f + (2.3f - 1.8f) * ((Cl - 30000f) / (40000 - 30000)));
		Pf = (1.032f + (1.043f - 1.032f) * ((Cl - 30000f) / (40000f - 30000f)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 39999;
		Vsf = (1.8f + (2.3f - 1.8f) * ((Cl - 30000f) / (40000 - 30000)));
		Pf = (1.032f + (1.043f - 1.032f) * ((Cl - 30000f) / (40000 - 30000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 40000 and 60000 using edge
		Cl = 40001;
		Vsf = (2.3f + (3.4f - 2.3f) * ((Cl - 40000f) / (60000 - 40000)));
		Pf = (1.043f + (1.065f - 1.043f) * ((Cl - 40000f) / (60000 - 40000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 59999;
		Vsf = (2.3f + (3.4f - 2.3f) * ((Cl - 40000f) / (60000 - 40000)));
		Pf = (1.043f + (1.065f - 1.043f) * ((Cl - 40000f) / (60000 - 40000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 60000 and 80000 using edge
		Cl = 60001;
		Vsf = (3.4f + (4.5f - 3.4f) * ((Cl - 60000f) / (80000 - 60000)));
		Pf = (1.065f + (1.082f - 1.065f) * ((Cl - 60000f) / (80000 - 60000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 79999;
		Vsf = (3.4f + (4.5f - 3.4f) * ((Cl - 60000f) / (80000 - 60000)));
		Pf = (1.065f + (1.082f - 1.065f) * ((Cl - 60000f) / (80000 - 60000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 80000 and 100000 using edge
		Cl = 80001;
		Vsf = (4.5f + (5.7f - 4.5f) * ((Cl - 80000f) / (100000 - 80000)));
		Pf = (1.082f + (1.098f - 1.082f) * ((Cl - 80000f) / (100000 - 80000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 99999;
		Vsf = (4.5f + (5.7f - 4.5f) * ((Cl - 80000f) / (100000 - 80000)));
		Pf = (1.082f + (1.098f - 1.082f) * ((Cl - 80000f) / (100000 - 80000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 100000 and 120000 using edge
		Cl = 100001;
		Vsf = (5.7f + (7.0f - 5.7f) * ((Cl - 100000f) / (120000 - 100000)));
		Pf = (1.098f + (1.129f - 1.098f) * ((Cl - 100000f) / (120000 - 100000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 119999;
		Vsf = (5.7f + (7.0f - 5.7f) * ((Cl - 100000f) / (120000 - 100000)));
		Pf = (1.098f + (1.129f - 1.098f) * ((Cl - 100000f) / (120000 - 100000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 120000 and 140000 using edge
		Cl = 120001;
		Vsf = (7.0f + (8.2f - 7.0f) * ((Cl - 120000f) / (140000 - 120000)));
		Pf = (1.129f + (1.149f - 1.129f) * ((Cl - 120000f) / (140000 - 120000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 139999;
		Vsf = (7.0f + (8.2f - 7.0f) * ((Cl - 120000f) / (140000 - 120000)));
		Pf = (1.129f + (1.149f - 1.129f) * ((Cl - 120000f) / (140000 - 120000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 140000 and 160000 using edge
		Cl = 140001;
		Vsf = (8.2f + (9.5f - 8.2f) * ((Cl - 140000f) / (160000 - 140000)));
		Pf = (1.149f + (1.170f - 1.149f) * ((Cl - 140000f) / (160000 - 140000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 159999;
		Vsf = (8.2f + (9.5f - 8.2f) * ((Cl - 140000f) / (160000 - 140000)));
		Pf = (1.149f + (1.170f - 1.149f) * ((Cl - 140000f) / (160000 - 140000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 160000 and 180000 using edge
		Cl = 160001;
		Vsf = (9.5f + (10.8f - 9.5f) * ((Cl - 160000f) / (180000 - 160000)));
		Pf = (1.170f + (1.194f - 1.170f) * ((Cl - 160000f) / (180000 - 160000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 179999;
		Vsf = (9.5f + (10.8f - 9.5f) * ((Cl - 160000f) / (180000 - 160000)));
		Pf = (1.170f + (1.194f - 1.170f) * ((Cl - 160000f) / (180000 - 160000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		// Testing Cl between 180000 and 186650 using edge
		Cl = 180001;
		Vsf = (10.8f + (11.4f - 10.8f) * ((Cl - 180000f) / (186650 - 180000)));
		Pf = (1.194f + (1.197f - 1.194f) * ((Cl - 180000f) / (186650 - 180000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

		Cl = 186649;
		Vsf = (10.8f + (11.4f - 10.8f) * ((Cl - 180000f) / (186650 - 180000)));
		Pf = (1.194f + (1.197f - 1.194f) * ((Cl - 180000f) / (186650 - 180000)));
		assertEquals(Vsf, test.calcPfOrVsf(Cl)[0]);
		assertEquals(Pf, test.calcPfOrVsf(Cl)[1]);

	}

	public void testcalcVs() {

		// Testing Vs
		assertEquals(100.0f, test.calcVs(10, 10));
		assertEquals(0.0f, test.calcVs(0, 10));
		assertEquals(0.0f, test.calcVs(10, 0));
		assertEquals(1.0f, test.calcVs(0.5f, 2));

	}

}
