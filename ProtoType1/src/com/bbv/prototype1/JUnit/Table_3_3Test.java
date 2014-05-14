package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Tables.Table_3_3;

import android.test.AndroidTestCase;

public class Table_3_3Test extends AndroidTestCase {

	Table_3_3 calc;
	float Pf;
	float Mf;
	float delta = 0.0001f;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

	}

	public void testGetOH() {
		// Testing Pf = 0
		Pf = 0;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(0f, calc.getOH(), delta);

		// Testing 2Pf < Mf
		Pf = 2;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(0f, calc.getOH(), delta);
		// Testing 2Pf = Mf
		Pf = 3;
		Mf = 6;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(0f, calc.getOH(), delta);
		// Testing 2Pf > Mf
		Pf = 3;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(340 * (2 * Pf - Mf), calc.getOH(), delta);
		// Testing Pf = Mf
		Pf = 5;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(340 * Mf, calc.getOH(), delta);
	}

	public void testGetCO() {
		// Testing Pf = 0
		Pf = 0;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(0f, calc.getCO(), delta);

		// Testing 2Pf < Mf
		Pf = 2;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(1200*Pf, calc.getCO(), delta);
		// Testing 2Pf = Mf
		Pf = 3;
		Mf = 6;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(1200*Pf, calc.getCO(), delta);
		// Testing 2Pf > Mf
		Pf = 3;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(1200*(Mf-Pf), calc.getCO(), delta);
		// Testing Pf = Mf
		Pf = 5;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(0f, calc.getCO(), delta);
	}

	public void testGetHCO() {
		// Testing Pf = 0
		Pf = 0;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(1220*Mf, calc.getHCO(), delta);

		// Testing 2Pf < Mf
		Pf = 2;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(1220*(Mf-2*Pf), calc.getHCO(), delta);
		// Testing 2Pf = Mf
		Pf = 3;
		Mf = 6;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(0f, calc.getHCO(), delta);
		// Testing 2Pf > Mf
		Pf = 3;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(0f, calc.getHCO(), delta);
		// Testing Pf = Mf
		Pf = 5;
		Mf = 5;
		calc = new Table_3_3(Pf, Mf);
		assertEquals(0f, calc.getHCO(), delta);
	}

}
