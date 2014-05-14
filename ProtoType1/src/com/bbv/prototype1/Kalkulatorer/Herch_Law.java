package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import com.bbv.prototype1.R;

public class Herch_Law extends Basic_Calc {

	final static int layout = R.layout.calc_modpowerlaw;

	final static int[] textViewIDs = { R.id.tvMPLN, R.id.tvMPLK, R.id.tvMPLT0,
			R.id.tvMPLEksY };
	final static int[] IDs = { R.id.etModPowerLawT600, R.id.etModPowerLawT300,
			R.id.etModPowerLawT6, R.id.etModPowerLawT3, R.id.etMODPowerLawN };

	public final static int T6_INDEX = 0, T3_INDEX = 1, N_INDEX = 4;

	public Herch_Law(Context context) {
		super(context, IDs, textViewIDs, layout);

	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float T600 = fieldStatuses[0];
		float T300 = fieldStatuses[1];
		float T6 = fieldStatuses[2];
		float T3 = fieldStatuses[3];
		float N;
		float T0;
		float ty;
		float K;

		float theAnswer = 0;
		switch (variableToCalculate) {

		default:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate wrong value, and this should not happen!");
			break;

		case N_INDEX:

			if (checkForNullValues(T600, T300, T6, T3) == false
					|| checkForNegativeValues(T600, T300, T6, T3) == false)
				return "";

			T0 = calcT0(T6, T3);
			ty = calcTy(T0);
			N = calcN(T600, T300, T0);
			K = calcK(T600, T0, N);

			Log.i(LogCat_RegularMessage, "Var" + 0 + " = " + T0);
			Log.i(LogCat_RegularMessage, "Var" + 1 + " = " + ty);
			Log.i(LogCat_RegularMessage, "Var" + 2 + " = " + N);
			Log.i(LogCat_RegularMessage, "Var" + 3 + " = " + K);

			float[] testFloats = { T0, ty, N, K };

			if (checkForDivisionErrors(testFloats) == false)
				return "";

			Log.println(Log.INFO, "calc", "Setting textviews in Modpowerlaw!");

			String _N = String.format(THREE_DECIMALS, N);
			String _T0 = String.format(ONE_DECIMAL, T0);
			String _ty = String.format(THREE_DECIMALS, ty);
			String _K = String.format(THREE_DECIMALS, K);

			textviews[0].setText(_N);
			textviews[1].setText(Html.fromHtml(_K
					+ " [Pa * s<sup><small>n</small></sup>]"));
			textviews[2].setText(_T0);
			textviews[3].setText(_ty + " [Pa]");

			return String.format(THREE_DECIMALS, N);

		}

		return "";
	}

	public float calcTy(float t0) {
		return t0 * 0.511f;
	}

	public float calcT0(float t6, float t3) {
		return (2 * t3) - t6;
	}

	public float calcN(float t600, float t300, float t0) {
		
		return (float) (Math.log10((t600 - t0) / (t300 - t0)) / Math
				.log10((600 * 1.7033) / (300 * 1.7033)));
	}

	public float calcK(float t600, float t0, float n) {
		return (float) (0.511f * ((t600 - t0) / Math.pow(600 * 1.7033, n)));
	}

}