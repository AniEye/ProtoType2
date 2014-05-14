package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.TotalHardhet;

import android.test.AndroidTestCase;

public class TotalHardhetTest extends AndroidTestCase {

	TotalHardhet calc;
	float delta = 0.0001f;

	// Variables used for calculation
	float totHardCaCo3;
	float totHardCa2;
	float totHardMg2;
	float V_EDTA_Ca2;
	float V_EDTA_CaCO3;
	float V_EDTA;
	float V_filtrat;
	float EDTA = 0.01f;
	float M_CaCO3 = 100.0869f;
	float M_Ca2 = 40.78f;
	float M_Mg2 = 24.3050f;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		calc = new TotalHardhet(getContext());
	}

	public void testCalculation() {

		// Testing that only KEY_INDEX will be calculated
		// Check LogCat for error messages
		assertEquals("", calc.calculation(0, 1, 1, 1));
		assertEquals("", calc.calculation(1, 1, 1, 1));
		assertEquals("", calc.calculation(2, 1, 1, 1));

		// Testing that calculation returns correct value
		V_EDTA_CaCO3 = 5;
		V_EDTA_Ca2 = 3;
		V_filtrat = 2;
		V_EDTA = V_EDTA_CaCO3 - V_EDTA_Ca2;
		totHardCaCo3 = (EDTA * V_EDTA * M_CaCO3 * 1000) / V_filtrat;
		assertEquals(String.format("%.3f", totHardCaCo3), calc.calculation(
				TotalHardhet.KEY_INDEX, V_EDTA_CaCO3, V_EDTA_Ca2, V_filtrat));

		// Testing that 0 values get rejected
		// Check logcat for error messages and Toast displayings
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 0, 0, 0));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 0, 0, 1));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 0, 1, 0));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 0, 1, 1));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 1, 0, 0));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 1, 0, 1));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 1, 1, 0));

		// Testing that negative values get rejected
		// Check logcat for error messages and Toast displayings
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, -1, -1, -1));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, -1, -1, 0));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, -1, 0, -1));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, -1, 0, 0));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 0, -1, -1));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 0, -1, 0));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 0, 0, -1));
		assertEquals("", calc.calculation(TotalHardhet.KEY_INDEX, 0, 0, 0));

	}

	public void testGetTotHardCaCo3() {
		// Testing that TotHard returns correct value
		V_EDTA_CaCO3 = 5;
		V_EDTA_Ca2 = 3;
		V_EDTA = V_EDTA_CaCO3 - V_EDTA_Ca2;
		V_filtrat = 2;
		totHardCaCo3 = (EDTA * V_EDTA * M_CaCO3 * 1000) / V_filtrat;
		calc.calculation(TotalHardhet.KEY_INDEX, V_EDTA_CaCO3, V_EDTA_Ca2,
				V_filtrat);
		assertEquals(totHardCaCo3, calc.getTotHardCaCo3());
	}

	public void testGetTotHardCa2() {
		// Testing that TotHard returns correct value
		V_EDTA_CaCO3 = 5;
		V_EDTA_Ca2 = 3;
		V_EDTA = V_EDTA_CaCO3 - V_EDTA_Ca2;
		V_filtrat = 2;
		totHardCa2 = (EDTA * V_EDTA * M_Ca2 * 1000) / V_filtrat;
		calc.calculation(TotalHardhet.KEY_INDEX, V_EDTA_CaCO3, V_EDTA_Ca2,
				V_filtrat);
		assertEquals(totHardCa2, calc.getTotHardCa2());
	}

	public void testGetTotHardMg2() {

		// Testing when V_EDTA is negative
		V_EDTA_CaCO3 = 1;
		V_EDTA_Ca2 = 2;
		V_filtrat = 2;
		calc.calculation(TotalHardhet.KEY_INDEX, V_EDTA_CaCO3, V_EDTA_Ca2,
				V_filtrat);
		assertEquals(0.0, calc.getTotHardMg2(), delta);

		// Testing when V_EDTA is positive
		V_EDTA_CaCO3 = 5;
		V_EDTA_Ca2 = 3;
		V_EDTA = V_EDTA_CaCO3 - V_EDTA_Ca2;
		V_filtrat = 2;
		totHardMg2 = (EDTA * V_EDTA * M_Mg2 * 1000) / V_filtrat;
		calc.calculation(TotalHardhet.KEY_INDEX, V_EDTA_CaCO3, V_EDTA_Ca2,
				V_filtrat);
		assertEquals(totHardMg2, calc.getTotHardMg2());

	}

	public void testGetVEDTA() {

		// Testing that getVEDTA returns correct value
		V_EDTA_CaCO3 = 5;
		V_EDTA_Ca2 = 3;
		V_EDTA = V_EDTA_CaCO3 - V_EDTA_Ca2;
		V_filtrat = 2;
		calc.calculation(TotalHardhet.KEY_INDEX, V_EDTA_CaCO3, V_EDTA_Ca2,
				V_filtrat);
		assertEquals(V_EDTA, calc.getVEDTA());

	}

}
