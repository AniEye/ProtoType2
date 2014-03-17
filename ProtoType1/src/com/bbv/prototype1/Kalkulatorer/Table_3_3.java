package com.bbv.prototype1.Kalkulatorer;

public class Table_3_3 {

	private float OH;
	private float CO;
	private float HCO;
	private int calculatedRow;

	public Table_3_3(float Pf, float Mf) {

		if (Pf == 0) {
			calculatedRow = 1;
			OH = 0;
			CO = 0;
			HCO = 1220 * (Mf);
		} else if (2 * Pf < Mf) {
			calculatedRow = 2;
			OH = 0;
			CO = 1200 * Pf;
			HCO = 1220 * (Mf - 2 * Pf);
		} else if (2 * Pf == Mf) {
			calculatedRow = 3;
			OH = 0;
			CO = 1200 * Pf;
			HCO = 0;
		} else if (Pf == Mf) {
			calculatedRow = 5;
			OH = 340 * Mf;
			CO = 0;
			HCO = 0;
		} else if (2 * Pf > Mf) {
			calculatedRow = 4;
			OH = 340 * (2 * Pf - Mf);
			CO = 1200 * (Mf - Pf);
			HCO = 0;

		} else {
			calculatedRow = -1;
			OH = -1;
			CO = -1;
			HCO = -1;
		}

	}

	public float getOH() {
		return OH;
	}

	public float getCO() {
		return CO;
	}

	public float getHCO() {
		return HCO;
	}

	public int getCalculatedRow() {
		return calculatedRow;
	}

}
