package com.bbv.prototype1.JUnit;

import com.bbv.prototype1.Kalkulatorer.Table_3_1;

import android.test.AndroidTestCase;

public class Table_3_1Test extends AndroidTestCase {
	
	Table_3_1 table;

	protected void setUp() throws Exception {
		super.setUp();
		table = new Table_3_1(5000);
	}

	public void testGetPf() {
		assertEquals(1.004f, table.getPf());
	}

	public void testGetVsf() {
		assertEquals(0.3f, table.getVsf());
	}

}
