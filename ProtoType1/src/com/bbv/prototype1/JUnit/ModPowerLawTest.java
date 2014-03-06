package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.Herch_Law;

import android.test.AndroidTestCase;

public class ModPowerLawTest extends AndroidTestCase {
	Herch_Law test;
	float epsilon = 0.0001f; //Sets the standard for how much the float values can diviate.

	protected void setUp() throws Exception {
		super.setUp();
		test = new Herch_Law(getContext());
	}

	public void testCalculation() {

		//Testing Theta0 to calculate successfully
		assertEquals(2.00f,test.calcT0(4.0f, 3.0f),epsilon);
		//Testing that Ty calculates successfully
		assertEquals(0.511f*200, test.calcTy(200),epsilon);
		// Testing n to successfully calculate
		float N = (float) (3.32f*Math.log10((400-200)/(300-200)));
		assertEquals(N, test.calcN(400, 300, 200),epsilon);
		// Testing K to successfully calculate
		float K = (float) (0.511f * ((400-200)/Math.pow(1022, 2)));
		assertEquals(K, test.calcK(400,200,2),epsilon);
		// Testing Y successfully calculates
		float Y = (float) (600*1.7023);
		assertEquals(Y, test.calcY(600),epsilon);
		// Testing t successfully calculates
		float T = (float) (100 + 200*Math.pow(20, 4));
		assertEquals(T, test.calcT(100,200,20,4),epsilon);

	}

}
