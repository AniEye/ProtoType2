package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Tables.Table_3_1;

import android.test.AndroidTestCase;

public class Table_3_1Test extends AndroidTestCase {

	Table_3_1 table;
	float Pf;
	float Vsf;
	float Cl;

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetPf() {

		float[] Cl_values = { 0, 5000, 10000, 20000, 30000, 40000, 60000,
				80000, 100000, 120000, 140000, 160000, 180000, 186650 };
		float[] Pf_values = { 1, 1.004f, 1.010f, 1.021f, 1.032f, 1.043f,
				1.065f, 1.082f, 1.098f, 1.129f, 1.149f, 1.170f, 1.194f, 1.197f };

		// Testing that values of Cl that match any Cl table value will work
		for (int i = 0; i < Cl_values.length; i++) {
			table = new Table_3_1(Cl_values[i]);
			assertEquals(Pf_values[i], table.getPf());
		}

		// Testing that values of Cl below 0 and over 186650 will return
		// values of 0 using edge testing
		table = new Table_3_1(-1);
		assertEquals(-1f, table.getPf());
		table = new Table_3_1(186651);
		assertEquals(-1f, table.getPf());

		// Testing Cl between 0 and 5000 using edge
		Cl = 1;
		Pf = (1 + (1.004f - 1) * ((Cl - 0) / (5000 - 0)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 4999;
		Pf = (1 + (1.004f - 1) * ((Cl - 0) / (5000 - 0)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 5000 and 10000 using edge
		Cl = 5001;
		Pf = (1.004f + (1.010f - 1.004f) * ((Cl - 5000f) / (10000f - 5000f)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 9999;
		Pf = (1.004f + (1.010f - 1.004f) * ((Cl - 5000.0f) / (10000 - 5000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 10000 and 20000 using edge
		Cl = 10001;
		Pf = (1.010f + (1.021f - 1.010f) * ((Cl - 10000f) / (20000f - 10000f)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 19999;
		Pf = (1.010f + (1.021f - 1.010f) * ((Cl - 10000f) / (20000 - 10000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 20000 and 30000 using edge
		Cl = 20001;
		Pf = (1.021f + (1.032f - 1.021f) * ((Cl - 20000f) / (30000 - 20000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 29999;
		Pf = (1.021f + (1.032f - 1.021f) * ((Cl - 20000f) / (30000 - 20000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 30000 and 40000 using edge
		Cl = 30001;
		Pf = (1.032f + (1.043f - 1.032f) * ((Cl - 30000f) / (40000f - 30000f)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 39999;
		Pf = (1.032f + (1.043f - 1.032f) * ((Cl - 30000f) / (40000 - 30000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 40000 and 60000 using edge
		Cl = 40001;
		Pf = (1.043f + (1.065f - 1.043f) * ((Cl - 40000f) / (60000 - 40000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 59999;
		Pf = (1.043f + (1.065f - 1.043f) * ((Cl - 40000f) / (60000 - 40000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 60000 and 80000 using edge
		Cl = 60001;
		Pf = (1.065f + (1.082f - 1.065f) * ((Cl - 60000f) / (80000 - 60000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 79999;
		Pf = (1.065f + (1.082f - 1.065f) * ((Cl - 60000f) / (80000 - 60000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 80000 and 100000 using edge
		Cl = 80001;
		Pf = (1.082f + (1.098f - 1.082f) * ((Cl - 80000f) / (100000 - 80000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 99999;
		Pf = (1.082f + (1.098f - 1.082f) * ((Cl - 80000f) / (100000 - 80000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 100000 and 120000 using edge
		Cl = 100001;
		Pf = (1.098f + (1.129f - 1.098f) * ((Cl - 100000f) / (120000 - 100000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 119999;
		Pf = (1.098f + (1.129f - 1.098f) * ((Cl - 100000f) / (120000 - 100000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 120000 and 140000 using edge
		Cl = 120001;
		Pf = (1.129f + (1.149f - 1.129f) * ((Cl - 120000f) / (140000 - 120000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 139999;
		Pf = (1.129f + (1.149f - 1.129f) * ((Cl - 120000f) / (140000 - 120000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 140000 and 160000 using edge
		Cl = 140001;
		Pf = (1.149f + (1.170f - 1.149f) * ((Cl - 140000f) / (160000 - 140000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 159999;
		Pf = (1.149f + (1.170f - 1.149f) * ((Cl - 140000f) / (160000 - 140000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 160000 and 180000 using edge
		Cl = 160001;
		Pf = (1.170f + (1.194f - 1.170f) * ((Cl - 160000f) / (180000 - 160000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 179999;
		Pf = (1.170f + (1.194f - 1.170f) * ((Cl - 160000f) / (180000 - 160000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		// Testing Cl between 180000 and 186650 using edge
		Cl = 180001;
		Pf = (1.194f + (1.197f - 1.194f) * ((Cl - 180000f) / (186650 - 180000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());

		Cl = 186649;
		Pf = (1.194f + (1.197f - 1.194f) * ((Cl - 180000f) / (186650 - 180000)));
		table = new Table_3_1(Cl);
		assertEquals(Pf, table.getPf());
	}

	public void testGetVsf() {

		float[] Cl_values = { 0, 5000, 10000, 20000, 30000, 40000, 60000,
				80000, 100000, 120000, 140000, 160000, 180000, 186650 };
		float[] Vsf_values = { 0, 0.3f, 0.6f, 1.2f, 1.8f, 2.3f, 3.4f, 4.5f,
				5.7f, 7.0f, 8.2f, 9.5f, 10.8f, 11.4f };

		// Testing that values of Cl that match any Cl table value will work
		for (int i = 0; i < Cl_values.length; i++) {
			table = new Table_3_1(Cl_values[i]);
			assertEquals(Vsf_values[i], table.getVsf());
		}

		// Testing that values of Cl below 5000 and over 186650 will return
		// values of 0 using edge testing
		table = new Table_3_1(-1);
		assertEquals(-1f, table.getVsf());
		table = new Table_3_1(186651);
		assertEquals(-1f, table.getVsf());

		// Testing Cl between 0 and 5000 using edge
		Cl = 1;
		Vsf = (0f + (0.3f - 0f) * ((Cl - 0) / (5000 - 0)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 4999;
		Vsf = (0f + (0.3f - 0f) * ((Cl - 0) / (5000 - 0)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 5000 and 10000 using edge
		Cl = 5001;
		Vsf = (0.3f + (0.6f - 0.3f) * ((Cl - 5000f) / (10000f - 5000f)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 9999;
		Vsf = (0.3f + (0.6f - 0.3f) * ((Cl - 5000.0f) / (10000 - 5000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 10000 and 20000 using edge
		Cl = 10001;
		Vsf = (0.6f + (1.2f - 0.6f) * ((Cl - 10000f) / (20000f - 10000f)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 19999;
		Vsf = (0.6f + (1.2f - 0.6f) * ((Cl - 10000f) / (20000 - 10000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 20000 and 30000 using edge
		Cl = 20001;
		Vsf = (1.2f + (1.8f - 1.2f) * ((Cl - 20000f) / (30000 - 20000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 29999;
		Vsf = (1.2f + (1.8f - 1.2f) * ((Cl - 20000f) / (30000 - 20000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 30000 and 40000 using edge
		Cl = 30001;
		Vsf = (1.8f + (2.3f - 1.8f) * ((Cl - 30000f) / (40000 - 30000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 39999;
		Vsf = (1.8f + (2.3f - 1.8f) * ((Cl - 30000f) / (40000 - 30000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 40000 and 60000 using edge
		Cl = 40001;
		Vsf = (2.3f + (3.4f - 2.3f) * ((Cl - 40000f) / (60000 - 40000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 59999;
		Vsf = (2.3f + (3.4f - 2.3f) * ((Cl - 40000f) / (60000 - 40000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 60000 and 80000 using edge
		Cl = 60001;
		Vsf = (3.4f + (4.5f - 3.4f) * ((Cl - 60000f) / (80000 - 60000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 79999;
		Vsf = (3.4f + (4.5f - 3.4f) * ((Cl - 60000f) / (80000 - 60000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 80000 and 100000 using edge
		Cl = 80001;
		Vsf = (4.5f + (5.7f - 4.5f) * ((Cl - 80000f) / (100000 - 80000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 99999;
		Vsf = (4.5f + (5.7f - 4.5f) * ((Cl - 80000f) / (100000 - 80000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 100000 and 120000 using edge
		Cl = 100001;
		Vsf = (5.7f + (7.0f - 5.7f) * ((Cl - 100000f) / (120000 - 100000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 119999;
		Vsf = (5.7f + (7.0f - 5.7f) * ((Cl - 100000f) / (120000 - 100000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 120000 and 140000 using edge
		Cl = 120001;
		Vsf = (7.0f + (8.2f - 7.0f) * ((Cl - 120000f) / (140000 - 120000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 139999;
		Vsf = (7.0f + (8.2f - 7.0f) * ((Cl - 120000f) / (140000 - 120000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 140000 and 160000 using edge
		Cl = 140001;
		Vsf = (8.2f + (9.5f - 8.2f) * ((Cl - 140000f) / (160000 - 140000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 159999;
		Vsf = (8.2f + (9.5f - 8.2f) * ((Cl - 140000f) / (160000 - 140000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 160000 and 180000 using edge
		Cl = 160001;
		Vsf = (9.5f + (10.8f - 9.5f) * ((Cl - 160000f) / (180000 - 160000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 179999;
		Vsf = (9.5f + (10.8f - 9.5f) * ((Cl - 160000f) / (180000 - 160000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		// Testing Cl between 180000 and 186650 using edge
		Cl = 180001;
		Vsf = (10.8f + (11.4f - 10.8f) * ((Cl - 180000f) / (186650 - 180000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());

		Cl = 186649;
		Vsf = (10.8f + (11.4f - 10.8f) * ((Cl - 180000f) / (186650 - 180000)));
		table = new Table_3_1(Cl);
		assertEquals(Vsf, table.getVsf());
	}

}
