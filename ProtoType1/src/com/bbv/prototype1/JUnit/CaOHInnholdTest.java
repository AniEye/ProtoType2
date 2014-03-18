package com.bbv.prototype1.JUnit;

import android.test.AndroidTestCase;
import android.util.Log;

import com.bbv.prototype1.Kalkulatorer.CEC;
import com.bbv.prototype1.Kalkulatorer.CaOHInnhold;
import com.bbv.prototype1.Kalkulatorer.OljeVann;

public class CaOHInnholdTest extends AndroidTestCase {

	CaOHInnhold calc;
	float Mp;
	float CaOH;
	float testValue;

	protected void setUp() throws Exception {
		super.setUp();
		calc = new CaOHInnhold(getContext());
	}

	public void testCalculation() {
				
		// Testing that calculator returns correct string
		CaOH = 2;
		testValue = CaOH/1.3f;
		assertEquals(String.format("%.3f", testValue), calc.calculation(CaOHInnhold.Mp_INDEX, Mp, CaOH));
		Mp = 1;
		testValue = Mp * 1.3f;
		assertEquals(String.format("%.3f", testValue), calc.calculation(CaOHInnhold.CaOH_INDEX, Mp, CaOH));
		
		//Testing that calculator returns nothing if negative or zero values are used
		//Check logcat for toast display
		Log.println(Log.INFO, "calc", "JUnit uses negative values in: " + this.getClass().getName());
		assertEquals("", calc.calculation(CaOHInnhold.Mp_INDEX, -1, -1));
		assertEquals("", calc.calculation(CaOHInnhold.CaOH_INDEX, -1, -1));
		Log.println(Log.INFO, "calc", "JUnit uses 0 values in: " + this.getClass().getName());
		assertEquals("", calc.calculation(CaOHInnhold.Mp_INDEX, 0, 0));
		assertEquals("", calc.calculation(CaOHInnhold.CaOH_INDEX, 0, 0));
	}

	public void testgetMp() { 
		// Testing that getMp returns correct value
		CaOH = 1;
		calc.calculation(CaOHInnhold.Mp_INDEX, 1, CaOH);
		testValue = CaOH / 1.3f;
		assertEquals(testValue, calc.getMp());

	}

	public void testgetCaOH() {
		// Testing that getCaOH returns correct value
		Mp = 1;
		calc.calculation(CaOHInnhold.CaOH_INDEX, Mp, 2);
		testValue = Mp * 1.3f;
		assertEquals( testValue, calc.getCaOH2());
	}

}
