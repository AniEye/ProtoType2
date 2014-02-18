package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.Slamtetthet;

import android.test.AndroidTestCase;

public class SlamtetthetTest extends AndroidTestCase {

	Basic_Calc test;
	float[] variables1 = {0,20,30,20,20,20}; 
	float[] variables2 = {-10,0,30,20,20,20}; 
	float[] variables3 = {-10,20,0,20,20,20}; 
	float[] variables4 = {-10,20,30,0,20,20}; 
	float[] variables5 = {-10,20,30,20,0,20}; 
	float[] variables6 = {-10,20,30,20,20,0}; 

	
	
	protected void setUp() throws Exception {
		super.setUp();
		
		test = new Slamtetthet(getContext());
	}

	public void testCalculation() {
		assertEquals("-10.0", test.calculation(0, variables1));
		assertEquals("20.0", test.calculation(1, variables2));
		assertEquals("30.0", test.calculation(2, variables3));
		assertEquals("20.0", test.calculation(3, variables4));
		assertEquals("20.0", test.calculation(4, variables5));
		assertEquals("20.0", test.calculation(5, variables6));

	}

}
