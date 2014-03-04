package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.*;

import android.test.AndroidTestCase;

public class Spess_tetthetTest extends AndroidTestCase {
	Basic_Calc test;
	float[] variablesForPf = {100, 50, 0 , 0,2,2}; 
	float[] variablesForVs = {100, 50, 0.245f, 0,2,2}; 
	float[] variablesForVfs = {100, 50, 0.245f, 0,2,2}; 

	float[] variablesForPp = {100, 50, 0.245f, 0,2,2,2}; 
	float[] variablesForPpToFail = {4, 0, 0.245f, 1,2,2,2}; 


	protected void setUp() throws Exception {
		super.setUp();
		test = new Spess_tetthet(getContext());
	}

	public void testCalculation() {

		
		//Testing Pp fails and displays LogCat message
		assertEquals("", test.calculation(1, variablesForPpToFail));
		

	}

}
