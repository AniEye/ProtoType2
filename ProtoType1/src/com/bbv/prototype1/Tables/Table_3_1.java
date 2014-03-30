package com.bbv.prototype1.Tables;

import com.bbv.prototype1.R.string;

import android.util.Log;

public class Table_3_1 {

	private float[] Cl_values = { 0f, 5000f, 10000f, 20000f, 30000f, 40000f,
			60000f, 80000f, 100000f, 120000f, 140000f, 160000f, 180000f,
			186650f };
	private float[] Vsf_values = { 0f, 0.3f, 0.6f, 1.2f, 1.8f, 2.3f, 3.4f,
			4.5f, 5.7f, 7.0f, 8.2f, 9.5f, 10.8f, 11.4f };
	private float[] Pf_values = { 1f, 1.004f, 1.010f, 1.021f, 1.032f, 1.043f,
			1.065f, 1.082f, 1.098f, 1.129f, 1.149f, 1.170f, 1.194f, 1.197f };

	private String[] column1, column2, column3;
	private String[][] rows;

	private int calculatedRow;

	private float[] calculatedPfAndVsf;

	public Table_3_1(float Cl) {

		calculatedPfAndVsf = calcCl(Cl);

		column1 = new String[Cl_values.length];
		column2 = new String[Vsf_values.length];
		column3 = new String[Pf_values.length];
		rows = new String[Cl_values.length][3];

		for (int i = 0; i < Cl_values.length; i++) {

			column1[i] = String.format("%.0f", Cl_values[i]);
			column2[i] = String.valueOf(Vsf_values[i]);
			column3[i] = String.format("%.3f", Pf_values[i]);
			
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

		float Pf = 0;
		float Vsf = 0;

		if (cl < Cl_values[0] || cl > Cl_values[Cl_values.length - 1]) {
			// Returns -1 and -1 if out of bounds
			Log.println(Log.ERROR, "calc", "Cl was out of bounds! It was " + cl);
			return new float[] { -1, -1 };
		}

		for (int i = 0; i < Cl_values.length; i++) {

			if (cl == Cl_values[i]) {
				calculatedRow = i;
				Pf = Pf_values[i];
				Vsf = Vsf_values[i];
				break; // Found values, no need to continue
			} else if (cl < Cl_values[i]) {

				calculatedRow = i;

				Pf = Pf_values[i - 1]
						+ ((Pf_values[i] - Pf_values[i - 1]) * ((cl - Cl_values[i - 1]) / (Cl_values[i] - Cl_values[i - 1])));

				Vsf = Vsf_values[i - 1]
						+ ((Vsf_values[i] - Vsf_values[i - 1]) * ((cl - Cl_values[i - 1]) / (Cl_values[i] - Cl_values[i - 1])));

				break; // Found values, no need to continue

			}

		}

		return new float[] { Vsf, Pf };
	}

	/**
	 * Returns calculated Vsf
	 * 
	 * If something went wrong, then Vsf is -1.
	 * 
	 * 
	 * @return Vsf
	 */
	public float getVsf() {
		return calculatedPfAndVsf[0];
	}

	/**
	 * Returns calculated Pf. If something went wrong, then Pf is -1.
	 * 
	 * @return Pf
	 */
	public float getPf() {
		return calculatedPfAndVsf[1];
	}

	/**
	 * Returns which row was used to calculate results.
	 * If not exact Cl value, then the calculated row is the row above
	 * @return Value between 0 and 14
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
