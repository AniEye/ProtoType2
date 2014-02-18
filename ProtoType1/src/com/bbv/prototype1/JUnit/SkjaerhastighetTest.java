package com.bbv.prototype1.JUnit;


import android.test.AndroidTestCase;

import com.bbv.prototype1.Kalkulatorer.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.Skjaerhastighet;

public class SkjaerhastighetTest extends AndroidTestCase {

	Basic_Calc test;
	float[] variables1 = {0,10,10,15}; 
	float[] variables2 = {226.195f,0,10,15};
	float[] variables3 = {226.195f,10,0,15}; 
	float[] variables4 = {226.195f,10,10,0}; 
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		test = new Skjaerhastighet(getContext());
		
	}

	public void testCalculationIntFloatFloat() {

		
		assertEquals("226.195", test.calculation(0, variables1));

		assertEquals("10.000", test.calculation(1, variables2));
		
		assertEquals("10.000", test.calculation(2, variables3));
		
		assertEquals("15.000", test.calculation(3, variables4));
		
	}

}
