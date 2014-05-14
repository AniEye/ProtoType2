package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.Herch_Law;

import android.test.AndroidTestCase;

public class ModPowerLawTest extends AndroidTestCase {
	Herch_Law test;
	float epsilon = 0.0001f; //Sets the standard for how much the float values can diviate.

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		test = new Herch_Law(getContext());
	}

	public void testCalculation() {
		float n;
		float Ty;
		float t0;
		float K;
		
		
		//Testing Theta0 to calculate successfully
		t0 = (2*3 -5 );
		assertEquals(t0,test.calcT0(5, 3),epsilon);
		//Testing that Ty calculates successfully
		Ty = 0.511f * t0;
		assertEquals(Ty, test.calcTy(t0),epsilon);
		// Testing n to successfully calculate
		n= (float) (3.32f * Math.log10((300 - t0)/(200 - t0)));
		assertEquals(n, test.calcN(300, 200, t0), 0.001);
		// Testing K to successfully calculate
		K = (float) (0.511f * ((400-t0)/Math.pow(600*1.7033, n)));
		assertEquals(K, test.calcK(400,t0,n),epsilon);

	}

}
