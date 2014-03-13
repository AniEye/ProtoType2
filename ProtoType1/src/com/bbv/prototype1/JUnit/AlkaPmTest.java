package com.bbv.prototype1.JUnit;

import android.test.AndroidTestCase;

import com.bbv.prototype1.Kalkulatorer.AlkaPm;
import com.bbv.prototype1.Kalkulatorer.CEC;

public class AlkaPmTest extends AndroidTestCase {
	
	AlkaPm calc;
	float ca;
		
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testCalculation() {
		
		//Testing that calc returns blank when trying to calculate wrong editfield
		calc = new AlkaPm(getContext());
		assertEquals("", calc.calculation(0, 1,1,1));
		assertEquals("", calc.calculation(1, 1,1,1));
		assertEquals("", calc.calculation(2, 1,1,1));
		
		//Testing that calculator returns correct string
		calc = new AlkaPm(getContext());
		ca = (float) 0.742*(5 - 4*2);
		String _Ca = String.format("%.3f", ca);
		assertEquals(_Ca, calc.calculation(3, 5,4,2));
		
	}

	public void testGetCa() {
		
		//Testing that CEC is correct
		calc = new AlkaPm(getContext());
		calc.calculation(3, 5,4,2);
		ca = (float) 0.742*(5 - 4*2);
		assertEquals(ca, calc.getCa());
		
	}

}
