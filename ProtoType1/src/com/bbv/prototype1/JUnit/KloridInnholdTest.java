package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.KloridInnhold;

import android.test.AndroidTestCase;

public class KloridInnholdTest extends AndroidTestCase {

	KloridInnhold calc;
	float epsilon = 0.0001f;
	float CCl;

	protected void setUp() throws Exception {
		super.setUp();
		calc = new KloridInnhold(getContext());
		calc.calculation(3, new float[] {5000, 20, 1});
	}

	public void testCalculation() {

		// Testing error messages when calculating other values than Cl - Check LogCat
		assertEquals("", calc.calculation(0, new float[] {1,1,1}));
		assertEquals("", calc.calculation(1, new float[] {1,1,1}));
		assertEquals("", calc.calculation(2, new float[] {1,1,1}));
		
		// Testing values of Cl below 5000 and over 188665 fail
		assertEquals("", calc.calculation(3, new float[] {4999,5,5}));
		assertEquals("", calc.calculation(3, new float[] {188665,5,5}));

		// Testing that calculator succeeds
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		CCl = (0.0282f * V_AgNO3 * 35.45f * 1000)/V_Filtrat;
		assertEquals(String.format("%.3f", CCl), calc.calculation(3, new float[] {5000, 20, 1}));

	}

	public void testGetCl() {
		assertEquals(5000.0f, calc.getCl());
		
	}

	public void testGetV_AgNO3() {
		assertEquals(20.0f, calc.getV_AgNO3());
	}

	public void testGetAgNO3() {
		assertEquals(0.0282f, calc.getAgNO3());
	}

	public void testGetMCl() {
		assertEquals(35.45f, calc.getMCl());
	}

	public void testGetMNaCl() {
		assertEquals(58.44f, calc.getMNaCl());
	}

	public void testGetPf() {
		assertEquals(1.004f, calc.getPf());

	}

	public void testGetCCl() {
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		CCl = (0.0282f * V_AgNO3 * 35.45f * 1000)/V_Filtrat;
		assertEquals(CCl, calc.getCCl());
		
	}

	public void testGetClPPM() {
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		CCl = (0.0282f * V_AgNO3 * 35.45f * 1000)/V_Filtrat;
		float Pf = 1.004f;
		float testValue = CCl / Pf;
		assertEquals(testValue, calc.getClPPM());
	}

	public void testGetNaClMG() {
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		float NaClMG = (0.0282f * V_AgNO3 * 58.44f * 1000)/V_Filtrat;
		assertEquals(NaClMG, calc.getNaClMG());
	}

	public void testGetNaClPPM() {
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		float NaClMG = (0.0282f * V_AgNO3 * 58.44f * 1000)/V_Filtrat;
		float Pf = 1.004f;
		float testValue = NaClMG / Pf;
		assertEquals(testValue, calc.getNaClPPM());
	}

}
