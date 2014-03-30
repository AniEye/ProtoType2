package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.KloridInnhold;
import com.bbv.prototype1.Kalkulatorer.KloridInnholdIVannfasen;
import com.bbv.prototype1.Tables.Table_3_1;

import android.test.AndroidTestCase;

public class KloridInnholdIVannfasenTest extends AndroidTestCase {

	KloridInnholdIVannfasen calc;
	float epsilon = 0.0001f;
	float CCl;
	float AgNO3 = 0.282f;
	float Fv;
	float MCl = 35.45f;
	float MNaCl = 58.44f;

	protected void setUp() throws Exception {
		super.setUp();
		calc = new KloridInnholdIVannfasen(getContext());
		calc.calculation(KloridInnholdIVannfasen.CCl_index, 20, 10);
	}

	public void testCalculation() {

		// Testing error messages when calculating other values than Cl - Check LogCat
		assertEquals("", calc.calculation(0, 1,10));
		assertEquals("", calc.calculation(1, 1,10));

		// Testing that calculator succeeds
		float V_AgNO3 = 20;
		Fv = 10;
		CCl = (AgNO3 * V_AgNO3 * 35.45f * 1000)/Fv;
		assertEquals(String.format("%.0f", CCl), calc.calculation(KloridInnholdIVannfasen.CCl_index, V_AgNO3, Fv));
		
		//Testing that calculator catches dividing with 0 error
		assertEquals("", calc.calculation(KloridInnholdIVannfasen.CCl_index, 20,0));

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
		float V_AgNO3 = 20;
		Fv = 10;
		CCl = (AgNO3 * V_AgNO3 * 35.45f * 1000)/Fv;
		Table_3_1 table = new Table_3_1(CCl);
		assertEquals(table.getPf(), calc.getPf());

	}

	public void testGetCCl() {
		float V_AgNO3 = 20;
		Fv = 10;
		CCl = (AgNO3 * V_AgNO3 * 35.45f * 1000)/Fv;
		assertEquals(CCl, calc.getCCl());
		
	}

	public void testGetClPPM() {
		float V_AgNO3 = 20;
		Fv = 10;
		CCl = (AgNO3 * V_AgNO3 * 35.45f * 1000)/Fv;
		Table_3_1 table = new Table_3_1(CCl);
		float testValue = CCl / table.getPf();
		assertEquals(testValue, calc.getClPPM());
	}

	public void testGetNaClMG() {
		float V_AgNO3 = 20;
		Fv = 10;
		float NaClMG = (AgNO3 * V_AgNO3 * 58.44f * 1000)/Fv;
		assertEquals(NaClMG, calc.getNaClMG());
	}

	public void testGetNaClPPM() {
		float V_AgNO3 = 20;
		Fv = 10;
		float NaClMG = (AgNO3 * V_AgNO3 * 58.44f * 1000)/Fv;
		CCl = (AgNO3 * V_AgNO3 * MCl * 1000)/Fv;
		Table_3_1 table = new Table_3_1(CCl);
		float testValue = NaClMG / table.getPf();
		assertEquals(testValue, calc.getNaClPPM());
	}

}
