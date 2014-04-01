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

public class PowerLaw600300 extends Basic_Calc {

	final int layout = R.layout.calc_powerlaw600300;

	final static int[] TextViewIDs = { R.id.tvPL600300N, R.id.tvPL600300NOTPA, R.id.tvPL600300PA };

	final static int[] IDs = { R.id.etPL600300T600, R.id.etPL600300T300, R.id.etPL600300N };

	public final static int T600_INDEX = 0, T300_INDEX = 1, N_INDEX = 2;

	public PowerLaw600300(Context context) {
		super(context, IDs, TextViewIDs);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float T600 = fieldStatuses[0];
		float T300 = fieldStatuses[1];
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
			
			textviews[0].setText(_N);
			textviews[1].setText(Html.fromHtml(_K + " [lbs/100 ft<sup><small>2</small></sup>/s]"));
			textviews[2].setText(_KPA + " [Pa]");

			return String.format(THREE_DECIMALS, N);

		}

		return "";

	}

	public float calcN(float T600, float T300) {
		return (float) (Math.log10(T600 / T300) / Math.log10(600*1.7033/ 300*1.7033));
	}

	public float calcK(float T600, float n) {
		return (float) (T600 / Math.pow(600*1.7033, n));
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];
		textviews = new TextView[TextViewIDs.length];

		for (int i = 0; i < IDs.length; i++){
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);
			textviews[i] = (TextView) findViewById(TextViewIDs[i]);
		}

		_clear = FindAndReturnButton(clearButtonID, cliLis);
		_update = FindAndReturnButton(updateButtonID, cliLis);
	}

}