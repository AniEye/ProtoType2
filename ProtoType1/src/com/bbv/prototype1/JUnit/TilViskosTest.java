package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.Til_Viskos;

import android.test.AndroidTestCase;

public class TilViskosTest extends AndroidTestCase {

	Til_Viskos test;
	float[] variables1 = {0,600,60}; //Theta = 120.0
	float[] variables2 = {45.5f,0,100}; //RPM = 136.5
	float[] variables3 = {40,300,0}; //TV= 40.0

	float THETA1=0, RPM1=600, TV1=60; //Theta = 120.0
	float THETA2=45.5f, RPM2=0, TV2=100; //RPM = 136.5
	float THETA3=40, RPM3=300, TV3=0; //TV= 40.0
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		test = new Til_Viskos(getContext());
		
	}

	public void testCalculationIntFloatFloat() {

		
		//Testing Theta
		assertEquals("120.000", test.calculation(0, variables1));
		assertEquals("120.000", test.calculation(0, THETA1, RPM1, TV1));
		
		//Testing RPM
		assertEquals("136.500", test.calculation(1, variables2));
		assertEquals("136.500", test.calculation(1, THETA2, RPM2, TV2));
		
		//Testing TV
		assertEquals("40.000", test.calculation(2, variables3));
		assertEquals("40.000", test.calculation(2, THETA3, RPM3, TV3));

		
	}

}
