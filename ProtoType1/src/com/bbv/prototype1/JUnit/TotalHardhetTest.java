package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.KloridInnhold;
import com.bbv.prototype1.Kalkulatorer.TotalHardhet;

import android.test.AndroidTestCase;

public class TotalHardhetTest extends AndroidTestCase {

	TotalHardhet calc;
	float epsilon = 0.0001f;
	float CaCo3;
	float Ca2;
	float Mg2;
	float EDTA = 0.01f;
	float M_CaCO3 = 100.0869f;
	float M_Ca2 = 40.78f;
	float M_Mg2 = 24.3050f;
	float V_EDTA = 2;
	float V_Filtrat = 5;

	protected void setUp() throws Exception {
		super.setUp();
		calc = new TotalHardhet(getContext());
		calc.calculation(2, new float[] { V_EDTA, V_Filtrat });
	}

	public void testCalculation() {

		// Testing error messages when calculating other values than CaCo3 -
		// Check LogCat
		assertEquals("", calc.calculation(0, new float[] { V_EDTA, V_Filtrat }));
		assertEquals("", calc.calculation(1, new float[] { V_EDTA, V_Filtrat }));

		// Testing that calculator succeeds
		CaCo3 = (EDTA * V_EDTA * M_CaCO3 * 1000) / V_Filtrat;

		assertEquals(String.format("%.3f", CaCo3),
				calc.calculation(2, new float[] { V_EDTA, V_Filtrat }));

	}

	public void testGetCaCo3() {

		CaCo3 = (EDTA * V_EDTA * M_CaCO3 * 1000) / V_Filtrat;

		assertEquals(CaCo3, calc.getCaCo3(), epsilon);

	}

	public void testGetCa2() {

		Ca2 = (EDTA * V_EDTA * M_Ca2 * 1000) / V_Filtrat;

		assertEquals(Ca2, calc.getCa2(), epsilon);
	}

	public void testGetMg2() {
		
		CaCo3 = (EDTA * V_EDTA * M_CaCO3 * 1000) / V_Filtrat;
		Ca2 = (EDTA * V_EDTA * M_Ca2 * 1000) / V_Filtrat;


		float new_V_EDTA = (V_EDTA * CaCo3) - (V_EDTA * Ca2);

		Mg2 = (EDTA * new_V_EDTA * M_Mg2 * 1000) / V_Filtrat;

		assertEquals(Mg2, calc.getMg2(), epsilon);

		// Testing that Mg2 = 0 if CaCo3 < Ca2
		V_EDTA = 5;
		

	}

}
