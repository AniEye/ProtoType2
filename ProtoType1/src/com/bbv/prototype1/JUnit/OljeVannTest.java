package com.bbv.prototype1.JUnit;

import android.test.AndroidTestCase;
import android.util.Log;

import com.bbv.prototype1.Kalkulatorer.OljeVann;

public class OljeVannTest extends AndroidTestCase {

	OljeVann calc;
	float Rolje = 2;
	float Rvann = 3;
	float O;
	float W;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testCalculation() {

		// Testing that calc returns blank when trying to calculate wrong
		// editfield
		calc = new OljeVann(getContext());
		Log.println(Log.INFO, "calc", "JUnit tries to calculate wrong value");
		assertEquals("", calc.calculation(0, Rolje, Rvann));
		Log.println(Log.INFO, "calc", "JUnit tries to calculate wrong value");
		assertEquals("", calc.calculation(1, Rolje, Rvann));

		// Testing that calculator returns correct string
		calc = new OljeVann(getContext());
		O = (Rolje / (Rolje + Rvann)) * 100;
		W = (Rvann / (Rolje + Rvann)) * 100;
		String _O = String.format("%.0f", O);
		String _W = String.format("%.0f", W);
		assertEquals(_O + "/" + _W, calc.calculation(2, Rolje, Rvann));

		// Testing that calc returns blank when dividing with 0
		// Check logcat for message that toast was displayed saying that there
		// was a division with 0
		calc = new OljeVann(getContext());
		Log.println(Log.INFO, "calc", "JUnit causes division by 0 error");
		assertEquals("", calc.calculation(2, 2, -2));

	}

	public void testgetO() { 
		// Testing that getO returns correct value
		calc = new OljeVann(getContext());
		O = (Rolje / (Rolje + Rvann)) * 100;
		calc.calculation(2, Rolje, Rvann);
		assertEquals(O, calc.getO(),0.001);

	}

	public void testgetW() {
		// Testing that getO returns correct value
		calc = new OljeVann(getContext());
		W = (Rvann / (Rolje + Rvann)) * 100;
		calc.calculation(2, Rolje, Rvann);
		assertEquals(W, calc.getW(),0.001);
	}

}
