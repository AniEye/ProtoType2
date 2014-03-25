package com.bbv.prototype1.JUnit;

import android.test.AndroidTestCase;

import com.bbv.prototype1.Kalkulatorer.PowerLaw1006;

public class PowerLaw1006Test extends AndroidTestCase {
	PowerLaw1006 test;
	float epsilon = 0.0001f; // Sets the standard for how much the float values
								// can diviate.

	float[] variables1 = { 100, 50, 0, 0, 0 };
	float[] variables2 = { 100, 50, 0.245f, 0 };

	protected void setUp() throws Exception {
		super.setUp();
		test = new PowerLaw1006(getContext());
	}
	
	public void testCalculation() {
		/**
		 * Returns false if float is NaN or infinite! Returns true if okay
		 */
		// Testing n to successfully calculate
		float N = (float) ((Math.log10(80/40) / (Math.log10(100*1.7033 / 6*1.7033))));
		assertEquals(N, test.calcN(80, 40), epsilon);
		// Testing K to successfully calculate
		float K = (float) (200 / Math.pow(6*1.7033, 4));
		assertEquals(K, test.calcK(200, 4), epsilon);

	}

}
