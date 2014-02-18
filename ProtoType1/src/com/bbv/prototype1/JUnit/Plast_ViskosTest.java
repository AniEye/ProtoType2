package com.bbv.prototype1.JUnit;


import com.bbv.prototype1.Kalkulatorer.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.MasseBalanse;
import com.bbv.prototype1.Kalkulatorer.Plast_Viskos;

import android.test.AndroidTestCase;

public class Plast_ViskosTest extends AndroidTestCase {

	Basic_Calc test;
	float[] variables1 = {0,20,30}; 
	float[] variables2 = {-10,0,30};
	float[] variables3 = {-10,20,0}; 
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		test = new Plast_Viskos(getContext());
		
	}

	public void testCalculationIntFloatFloat() {

		
		assertEquals("-10.000", test.calculation(0, variables1));

		assertEquals("20.000", test.calculation(1, variables2));
		
		assertEquals("30.000", test.calculation(2, variables3));

		
	}

}
