package com.bbv.prototype1.JUnit;

import android.test.AndroidTestCase;

import com.bbv.prototype1.Kalkulatorer.CEC;

public class CECTest extends AndroidTestCase {
	
	CEC calc;
	float V_mbl = 50;
	float V_boreslam = 40;
	float cec;
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testCalculation() {
		
		//Testing that calc returns blank when trying to calculate wrong editfield
		calc = new CEC(getContext());
		assertEquals("", calc.calculation(0, V_mbl,V_boreslam));
		assertEquals("", calc.calculation(1, V_mbl,V_boreslam));
		
		//Testing that calculator returns correct string
		calc = new CEC(getContext());
		cec = V_mbl/V_boreslam;
		String _CEC = String.format("%.3f", cec);
		assertEquals(_CEC, calc.calculation(2, V_mbl,V_boreslam));
		
		//Testing that calc returns blank when dividing with 0
		//Check logcat for message that toast was displayed saying that V boreslam = 0
		calc = new CEC(getContext());
		assertEquals("", calc.calculation(2, 1,0));
		
	}

	public void testGetCEC() {
		
		//Testing that CEC is correct
		calc = new CEC(getContext());
		calc.calculation(2, 1,1);
		assertEquals(1f, calc.getCEC());
		
		//Testing that CEC is wrong
		calc = new CEC(getContext());
		calc.calculation(2, 1,0);
		assertEquals(0.0f, calc.getCEC());
		
	}

	public void testGetBentPPM() {
		//Testing that BentPPm is correct
		calc = new CEC(getContext());
		calc.calculation(2, 1,1);
		assertEquals(5f, calc.getBentPPM());
		
		//Testing that BentPPm is wrong
		calc = new CEC(getContext());
		calc.calculation(2, 1,0);
		assertEquals(0.0f, calc.getBentPPM());
		
	}

	public void testGetBentKG() {
		//Testing that BentKG is correct
		calc = new CEC(getContext());
		calc.calculation(2, 1,1);
		assertEquals(14.25f, calc.getBentKG());
		
		//Testing that BentKG is wrong
		calc = new CEC(getContext());
		calc.calculation(2, 1,0);
		assertEquals(0.0f, calc.getBentKG());
		
	}

}
