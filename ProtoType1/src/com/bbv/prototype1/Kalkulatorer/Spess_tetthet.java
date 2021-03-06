package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.util.Log;
import com.bbv.prototype1.R;
import com.bbv.prototype1.Tables.Table_3_1;

public class Spess_tetthet extends Basic_Calc {

	final static int layout = R.layout.calc_spesifikktetthet;

	static int[] textViewIDs = { R.id.tvSpessTettPp, R.id.tvSpessTettPf,
			R.id.tvSpessTettVs, R.id.tvSpessTettVsf };

	final static int[] IDs = { R.id.etSpessTettPp, R.id.etSpessTettCL,
			R.id.etSpessTettPm, R.id.etSpessTettVv};

	/**
	 * Pp_INDEX is the only variable to be calculated in this calculator
	 */
	public final static int Pp_INDEX = 0;

	public Spess_tetthet(Context context) {
		super(context, IDs, textViewIDs, layout);

	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float Cl = fieldStatuses[1];
		float Pm = fieldStatuses[2];
		float Vv = fieldStatuses[3];


		float Pf = 0, Vs = 0, Vsf = 0, Fw = 0, Vfs = 0;

		float theAnswer = 0;

		if (variableToCalculate == Pp_INDEX) {

			float[] PfAndVsf = calcPfOrVsf(Cl);

			if (checkForNegativeValues(Cl, Pm, Vv) == false
					|| checkForNullValues(Cl, Pm, Vv) == false)
				return "";

			Fw = Vv/100;
			Vfs = 100-Vv;
			
			Pf = PfAndVsf[1];
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Pf to be "
					+ Pf);

			Vsf = PfAndVsf[0];
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Vsf to be "
					+ Vsf);

			Vs = calcVs(Vsf, Fw);
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Vs to be "
					+ Vs);

			textviews[1].setText(String.format(FOUR_DECIMALS, Pf));
			textviews[3].setText(String.format(THREE_DECIMALS, Vsf)
					+ " [volum%]");
			textviews[2].setText(String.format(THREE_DECIMALS, Vs)
					+ " [volum%]");

			theAnswer = ((100f * Pm) - (Pf * (Vv + Vsf))) / (Vfs - Vs);

			if (checkForDivisionErrors(theAnswer) == false)
				return "";

			Log.println(Log.INFO, "calc",
					"SpessTetthet calculated an answer! It was " + theAnswer);

		} else {
			Log.println(
					Log.ERROR,
					"calc",
					"Spess_Tetthet tried to calculate anything other than Pp, and this should not happen!"
							+ "\nIgnore if running JUnit tests");
		}

		if (theAnswer != 0) {
			textviews[0].setText(String.format(TWO_DECIMALS, theAnswer));
			return String.format(THREE_DECIMALS, theAnswer);
		}

		else
			return "";
	}

	/**
	 * Calculates the Pf and Vsf variables based on table 3.1 in
	 * "�vinger i Bore- og br�nnteknikk"
	 * 
	 * If cl is out of bounds for the table, a toast will be displayed, and Vfs
	 * and Pf will be set to 0
	 * 
	 * @param cl
	 *            - Value of KloridInnhold
	 * @return float array with Vsf in place 0, and Pf in place 1
	 */
	public float[] calcPfOrVsf(float cl) {

		Table_3_1 table = new Table_3_1(cl);
		float Pf = table.getPf();
		float Vsf = table.getVsf();

		if (Pf == -1 || Vsf == -1) {
			// Catches out of bounds exception
			String message = getResources().getString(R.string.OutOfBounds);
			showToast(message);
			return new float[] { 0, 0 };
		} else {
			return new float[] { Vsf, Pf };
		}

	}

	public float calcVs(float vsf, float fw) {
		return vsf * fw;
	}

}