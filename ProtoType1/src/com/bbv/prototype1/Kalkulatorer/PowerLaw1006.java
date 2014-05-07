package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bbv.prototype1.R;

public class PowerLaw1006 extends Basic_Calc {

	final static int layout = R.layout.calc_powerlaw1006;

	final static int[] textViewIDs = { R.id.tvPLN, R.id.tvPLNOTPA, R.id.tvPLPA };

	final static int[] IDs = { R.id.etPowerLawT100, R.id.etPowerLaw6, R.id.etPowerLawN };

	public final static int T100_INDEX = 0, T6_INDEX = 1, N_INDEX = 2;

	public PowerLaw1006(Context context) {
		super(context, IDs, textViewIDs, layout);

	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float T100 = fieldStatuses[T100_INDEX];
		float T6 = fieldStatuses[T6_INDEX];
		float N;
		float K;


		float theAnswer = 0;
		switch (variableToCalculate) {

		case T100_INDEX:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate T1, and this should not happen!");
			break;
		case T6_INDEX:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate T6, and this should not happen!");
			break;
		case N_INDEX:

			if (checkForNullValues(T6, T100) == false
					|| checkForNegativeValues(T6, T100) == false)
				return "";

			N = calcN(T100, T6);
			K = calcK(T6, N);

			float[] testFloats = { N, K };
			if (checkForDivisionErrors(testFloats) == false)
				return "";

			Log.println(Log.INFO, "calc", "Setting textviews in Powerlaw!");

			String _N = String.format(THREE_DECIMALS, N);
			String _K = String.format(THREE_DECIMALS, K);
			String _KPA = String.format(THREE_DECIMALS, K * 0.511f);
			
			textviews[0].setText(_N);
			textviews[1].setText(Html.fromHtml(_K + " [lbs/100 ft<sup><small>2</small></sup>/s]"));
			textviews[2].setText(_KPA + " [Pa]");

			return String.format(THREE_DECIMALS, N);

		}

		return "";

	}

	public float calcN(float T100, float T6) {
		return (float) (Math.log10(T100 / T6) / Math.log10((100*1.7033)/ (6*1.7033)));
	}

	public float calcK(float T6, float n) {
		return (float) (T6 / Math.pow(6*1.7033, n));
	}



}