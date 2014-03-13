package com.bbv.prototype1.Kalkulatorer;

public class Table_3_3 {

	private float OH;
	private float CO;
	private float HCO;

	public Table_3_3(float Pf, float Mf) {

		if (Pf == 0) {
			OH = 0;
			CO = 0;
			HCO = 1220 * (Mf);
		} else if (2 * Pf < Mf) {
			OH = 0;
			CO = 1200 * Pf;
			HCO = 1220 * (Mf - 2 * Pf);
		} else if (2 * Pf == Mf) {
			OH = 0;
			CO = 1200*Pf;
			HCO = 0;
		} else if (2*Pf > Mf) {
			OH = 340*(2*Pf - Mf);
			CO = 1200*(Mf - Pf);
			HCO = 0;
		} else if (Pf == Mf){
			OH = 340*Mf;
			CO = 0;
			HCO = 0;
		} else {
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

}
