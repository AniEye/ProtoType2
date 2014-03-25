package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.KloridInnhold;
import com.bbv.prototype1.Kalkulatorer.KloridInnholdIVannfasen;
import com.bbv.prototype1.Kalkulatorer.Table_3_1;

import android.test.AndroidTestCase;

public class KloridInnholdTest extends AndroidTestCase {

	KloridInnhold calc;
	float epsilon = 0.0001f;
	float CCl;
	float AgNO3 = 0.0282f;
	float MCl = 35.45f;
	float MNaCl = 58.44f;

	protected void setUp() throws Exception {
		super.setUp();
		calc = new KloridInnhold(getContext());
		calc.calculation(KloridInnhold.CCl_index, new float[] {20, 1});
	}

	public void testCalculation() {

		// Testing error messages when calculating other values than Cl - Check LogCat
		assertEquals("", calc.calculation(0, new float[] {1,1}));
		assertEquals("", calc.calculation(1, new float[] {1,1}));

		// Testing that calculator succeeds
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		CCl = (AgNO3 * V_AgNO3 * MCl * 1000)/V_Filtrat;
		assertEquals(String.format("%.0f", CCl), calc.calculation(KloridInnhold.CCl_index, 20, 1));

	}

	public void testGetV_AgNO3() {
		assertEquals(20.0f, calc.getV_AgNO3());
	}

	public void testGetAgNO3() {
		assertEquals(AgNO3, calc.getAgNO3());
	}

	public void testGetMCl() {
		assertEquals(MCl, calc.getMCl());
	}

	public void testGetMNaCl() {
		assertEquals(MNaCl, calc.getMNaCl());
	}

	public void testGetPf() {
		
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		CCl = (AgNO3 * V_AgNO3 * MCl * 1000)/V_Filtrat;
		
		Table_3_1 table = new Table_3_1(CCl);
		
		assertEquals(table.getPf(), calc.getPf());

	}

	public void testGetCCl() {
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		CCl = (AgNO3* V_AgNO3 * MCl * 1000)/V_Filtrat;
		assertEquals(CCl, calc.getCCl());
		
	}

	public void testGetClPPM() {
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		CCl = (AgNO3 * V_AgNO3 * MCl * 1000)/V_Filtrat;
		
		Table_3_1 table = new Table_3_1(CCl);

		float testValue = CCl / table.getPf();
		assertEquals(testValue, calc.getClPPM());
	}

	public void testGetNaClMG() {
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		float NaClMG = (AgNO3 * V_AgNO3 * MNaCl* 1000)/V_Filtrat;
		assertEquals(NaClMG, calc.getNaClMG());
	}

	public void testGetNaClPPM() {
		float V_AgNO3 = 20;
		float V_Filtrat = 1;
		CCl = (AgNO3 * V_AgNO3 * MCl * 1000)/V_Filtrat;
		float NaClPPM = (AgNO3 * V_AgNO3 * MNaCl * 1000)/V_Filtrat;

		Table_3_1 table = new Table_3_1(CCl);
		float testValue = NaClPPM / table.getPf();
		assertEquals(testValue, calc.getNaClPPM());
	}

}
