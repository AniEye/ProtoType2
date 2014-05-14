package com.bbv.prototype1.Tables;

import android.util.Log;

public class Table_9_4 {

	private float[] Cl_values = { 0f, 6452f, 13012f, 19682f, 26464, 33360,
			40372, 47501, 54750, 62120, 68413, 77231, 84975, 92848, 100581,
			108986, 117256, 125660, 134203, 142885, 151708, 160674, 169786,
			179043, 188450, 198007, 207715, 217578, 227597, 237773, 248109,
			258606, 269267, 280092, 291084, 302244, 313575, 325078, 336756,
			348609, 360640 };
	private float[] Corf_values = { 1, 1.002f, 1.003f, 1.006f, 1.007f, 1.009f,
			1.011f, 1.013f, 1.016f, 1.018f, 1.021f, 1.023f, 1.027f, 1.029f,
			1.033f, 1.036f, 1.039f, 1.042f, 1.046f, 1.050f, 1.054f, 1.058f,
			1.062f, 1.067f, 1.071f, 1.077f, 1.082f, 1.087f, 1.093f, 1.099f,
			1.105f, 1.111f, 1.117f, 1.125f, 1.132f, 1.140f, 1.147f, 1.155f,
			1.164f, 1.173f, 1.182f };
	private float[] CaCl_values = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
			30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40 };

	private String[] column1, column2, column3;
	private String[][] rows;

	private int calculatedRow;

	private float[] calculatedCorfAndCaCl;

	public Table_9_4(float Cl) {

		calculatedCorfAndCaCl = calcCl(Cl);

		column1 = new String[Cl_values.length];
		column2 = new String[Corf_values.length];
		column3 = new String[CaCl_values.length];
		rows = new String[Cl_values.length][3];

		for (int i = 0; i < Cl_values.length; i++) {

			column1[i] = String.format("%.0f", Cl_values[i]);
			column2[i] = String.format("%.3f", Corf_values[i]);
			column3[i] = String.format("%.1f", CaCl_values[i]);

			rows[i][0] = column1[i];
			rows[i][1] = column2[i];
			rows[i][2] = column3[i];

		}

	}

	/**
	 * Calculates Pf and Vsf in table 3.1 using Cl-value Returns Pf and Vsf
	 * values as float[] Returns -1,-1 if out of bounds
	 * 
	 * @param cl
	 *            - Value of KloridInnhold
	 * @return Float array with Vsf in place 0 and Pf in place 1
	 */
	private float[] calcCl(float cl) {

		float CaCl2 = 0;
		float Corf = 0;

		if (cl < Cl_values[0] || cl > Cl_values[Cl_values.length - 1]) {
			// Returns -1 and -1 if out of bounds
			Log.println(Log.ERROR, "calc", "Cl was out of bounds! It was " + cl);
			return new float[] { -1, -1 };
		}

		for (int i = 0; i < Cl_values.length; i++) {

			if (cl == Cl_values[i]) {
				calculatedRow = i;
				CaCl2 = CaCl_values[i];
				Corf = Corf_values[i];
				break; // Found values, no need to continue
			} else if (cl < Cl_values[i]) {

				calculatedRow = i;

				CaCl2 = CaCl_values[i - 1]
						+ ((CaCl_values[i] - CaCl_values[i - 1]) * ((cl - Cl_values[i - 1]) / (Cl_values[i] - Cl_values[i - 1])));

				Corf = Corf_values[i - 1]
						+ ((Corf_values[i] - Corf_values[i - 1]) * ((cl - Cl_values[i - 1]) / (Cl_values[i] - Cl_values[i - 1])));

				break; // Found values, no need to continue

			}

		}

		return new float[] { Corf, CaCl2 };
	}

	/**
	 * Returns calculated Corf
	 * 
	 * If something went wrong, then Corf is -1.
	 * 
	 * 
	 * @return Corf
	 */
	public float getCorf() {
		return calculatedCorfAndCaCl[0];
	}

	/**
	 * Returns calculated CaCl2. If something went wrong, then CaCl2 is -1.
	 * 
	 * @return CaCl2
	 */
	public float getCaCl() {
		return calculatedCorfAndCaCl[1];
	}

	/**
	 * Returns which row was used to calculate results. If not exact Cl value,
	 * then the calculated row is the row above
	 * 
	 * @return Value between 0 and 40
	 */
	public int getCalculatedRow() {
		return calculatedRow;
	}

	/**
	 * Returns initial rows
	 * 
	 * @return rows
	 */
	public String[][] getInitialRows() {

		return rows;
	}

}
