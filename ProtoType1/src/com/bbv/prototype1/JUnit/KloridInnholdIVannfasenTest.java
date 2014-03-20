package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.KloridInnhold;
import com.bbv.prototype1.Kalkulatorer.KloridInnholdIVannfasen;

import android.test.AndroidTestCase;

public class KloridInnholdIVannfasenTest extends AndroidTestCase {

	KloridInnholdIVannfasen calc;
	float epsilon = 0.0001f;
	float CCl;
	float AgNO3 = 0.282f;
	float Fv;

	protected void setUp() throws Exception {
		super.setUp();
		calc = new KloridInnholdIVannfasen(getContext());
		calc.calculation(3, new float[] {5000, 20, 1});
	}

	public void testCalculation() {

		// Testing error messages when calculating other values than Cl - Check LogCat
		assertEquals("", calc.calculation(0, new float[] {1,1,1}));
		assertEquals("", calc.calculation(1, new float[] {1,1,1}));
		assertEquals("", calc.calculation(2, new float[] {1,1,1}));
		
		// Testing values of Cl below 5000 and over 188650 fail
		assertEquals("", calc.calculation(3, new float[] {4999,5,5}));
		assertEquals("", calc.calculation(3, new float[] {188651,5,5}));

		// Testing that calculator succeeds
		float V_AgNO3 = 20;
		Fv = 1;
		CCl = (AgNO3 * V_AgNO3 * 35.45f * 1000)/Fv;
		assertEquals(String.format("%.3f", CCl), calc.calculation(3, new float[] {5000, V_AgNO3, Fv}));
		
		//Testing that calculator catches dividing with 0 error
		assertEquals("", calc.calculation(KloridInnholdIVannfasen.CCl_index, 5000,20,0));

	}

	public void testGetCl() {
		assertEquals(5000.0f, calc.getCl());
		
	}

	public void testGetV_AgNO3() {
		assertEquals(20.0f, calc.getV_AgNO3());
	}

	public void testGetAgNO3() {
		assertEquals(AgNO3, calc.getAgNO3());
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
		Fv = 1;
		CCl = (AgNO3 * V_AgNO3 * 35.45f * 1000)/Fv;
		assertEquals(CCl, calc.getCCl());
		
	}

	public void testGetClPPM() {
		float V_AgNO3 = 20;
		Fv = 1;
		CCl = (AgNO3 * V_AgNO3 * 35.45f * 1000)/Fv;
		float Pf = 1.004f;
		float testValue = CCl / Pf;
		assertEquals(testValue, calc.getClPPM());
	}

	public void testGetNaClMG() {
		float V_AgNO3 = 20;
		Fv = 1;
		float NaClMG = (AgNO3 * V_AgNO3 * 58.44f * 1000)/Fv;
		assertEquals(NaClMG, calc.getNaClMG());
	}

	public void testGetNaClPPM() {
		float V_AgNO3 = 20;
		Fv = 1;
		float NaClMG = (AgNO3 * V_AgNO3 * 58.44f * 1000)/Fv;
		float Pf = 1.004f;
		float testValue = NaClMG / Pf;
		assertEquals(testValue, calc.getNaClPPM());
	}

}
