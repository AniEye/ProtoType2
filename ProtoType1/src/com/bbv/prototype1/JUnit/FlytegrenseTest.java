package com.bbv.prototype1.JUnit;


import com.bbv.prototype1.Kalkulatorer.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.Flytegrense;

import android.test.AndroidTestCase;

public class FlytegrenseTest extends AndroidTestCase {

	Basic_Calc test;
	float[] variables1 = {0,20,20}; 
	float[] variables2 = {20,0,20};
	float[] variables3 = {20,20,0}; 
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		test = new Flytegrense(getContext());
		
	}

	public void testCalculationIntFloatFloat() {

		
		assertEquals("20.000", test.calculation(0, variables1));

		assertEquals("20.000", test.calculation(1, variables2));
		
		assertEquals("20.000", test.calculation(2, variables3));

		
	}

}
