package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import com.bbv.prototype1.R;

public class PowerLaw600300 extends Basic_Calc {

	final static int layout = R.layout.calc_powerlaw600300;

	final static int[] TextViewIDs = { R.id.tvPL600300N, R.id.tvPL600300NOTPA, R.id.tvPL600300PA };

	final static int[] TextFieldIDs = { R.id.etPL600300T600, R.id.etPL600300T300, R.id.etPL600300N };

	public final static int T600_INDEX = 0, T300_INDEX = 1, N_INDEX = 2;
	public final static int _N_INDEX = 0, _K_INDEX = 1, _K_PA_INDEX = 2;


	public PowerLaw600300(Context context) {
		super(context, TextFieldIDs, TextViewIDs, layout);

	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float T600 = fieldStatuses[T600_INDEX];
		float T300 = fieldStatuses[T300_INDEX];
		float N;
		float K;


		float theAnswer = 0;
		switch (variableToCalculate) {

		case T600_INDEX:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate T600, and this should not happen!");
			break;
		case T300_INDEX:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate T300, and this should not happen!");
			break;
		case N_INDEX:

			if (checkForNullValues(T300, T600) == false
					|| checkForNegativeValues(T300, T600) == false)
				return "";

			N = calcN(T600, T300);
			K = calcK(T600, N);

			float[] testFloats = { N, K };
			if (checkForDivisionErrors(testFloats) == false)
				return "";

			Log.println(Log.INFO, "calc", "Setting textviews in Powerlaw!");

			String _N = String.format(THREE_DECIMALS, N);
			String _K = String.format(THREE_DECIMALS, K);
			String _KPA = String.format(THREE_DECIMALS, K * 0.511f);
			
			textviews[_N_INDEX].setText(_N);
			textviews[_K_INDEX].setText(Html.fromHtml(_K + " [lbs/100 ft<sup><small>2</small></sup>/s]"));
			textviews[_K_PA_INDEX].setText(_KPA + " [Pa]");

			return String.format(THREE_DECIMALS, N);

		}

		return "";

	}

	public float calcN(float T600, float T300) {

		
		return (float) ((Math.log10(T600 / T300)) / (Math.log10((600*1.7033)/ (300*1.7033))));
	}

	public float calcK(float T600, float n) {
		return (float) (T600 / Math.pow(600*1.7033, n));
	}

}