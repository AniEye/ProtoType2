package com.bbv.prototype1.Kalkulatorer;

public class Table_3_1 {

	private float[] Cl_values = { 5000f, 10000f, 20000f, 30000f, 40000f,
			60000f, 80000f, 100000f, 120000f, 140000f, 160000f, 180000f,
			186650f };
	private float[] Vsf_values = { 0.3f, 0.6f, 1.2f, 1.8f, 2.3f, 3.4f, 4.5f,
			5.7f, 7.0f, 8.2f, 9.5f, 10.8f, 11.4f };
	private float[] Pf_values = { 1.004f, 1.010f, 1.021f, 1.032f, 1.043f,
			1.065f, 1.082f, 1.098f, 1.129f, 1.149f, 1.170f, 1.194f, 1.197f };

	private float[] calculatedPfAndVsf;

	public Table_3_1(float Cl) {

		calculatedPfAndVsf = calcCl(Cl);

	}

	private float[] calcCl(float cl) {
		/**
		 * Calculates Pf and Vsf in table 3.1 using Cl-value
		 * Returns Pf and Vsf values as float[]
		 * Returns -1,-1 if out of bounds
		 */
		float Pf = 0;
		float Vsf = 0;
		
		if(cl < Cl_values[0] || cl > Cl_values[Cl_values.length-1]){
			//Returns -1 and -1 if out of bounds
			return new float[] {-1,-1};
		}

		for (int i = 0; i < Cl_values.length; i++) {

			if (cl == Cl_values[i]) {
				Pf = Pf_values[i];
				Vsf = Vsf_values[i];
				break; // Found values, no need to continue
			} else if (cl < Cl_values[i]) {

				Pf = Pf_values[i - 1]
						+ ((Pf_values[i] - Pf_values[i - 1]) * ((cl - Cl_values[i - 1]) / (Cl_values[i] - Cl_values[i - 1])));

				Vsf = Vsf_values[i - 1]
						+ ((Vsf_values[i] - Vsf_values[i - 1]) * ((cl - Cl_values[i - 1]) / (Cl_values[i] - Cl_values[i - 1])));

				break; // Found values, no need to continue

			}

		}

		if (Pf == 0 || Vsf == 0)
			throw new Error("Something went severly wrong in Table3.1");

		return new float[] {Vsf, Pf};
	}
	
	public float getVsf() {
		return calculatedPfAndVsf[0];
	}

	public float getPf() {
		return calculatedPfAndVsf[1];
	}


}
