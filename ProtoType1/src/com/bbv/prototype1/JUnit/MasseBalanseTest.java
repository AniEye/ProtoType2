package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.MasseBalanse;
import com.bbv.prototype1.Kalkulatorer.Til_Viskos;

import android.test.AndroidTestCase;

public class MasseBalanseTest extends AndroidTestCase {

	Basic_Calc test;
	float[] variables1 = {0,20,30}; 
	float[] variables2 = {10,0,30};
	float[] variables3 = {10,20,0}; 
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		test = new MasseBalanse(getContext());
		
	}

	public void testCalculationIntFloatFloat() {

		
		//Testing M1
		assertEquals("10.0", test.calculation(0, variables1));

		//Testing MV
		assertEquals("20.0", test.calculation(1, variables2));
		
		//Testing M2
		assertEquals("30.0", test.calculation(2, variables3));

		
	}

}
