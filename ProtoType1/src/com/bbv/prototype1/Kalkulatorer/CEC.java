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

public class CEC extends Basic_Calc {

	final static int layout = R.layout.calc_cec;

	TextView tvbentPPM;
	TextView tvbentKG;

	// Variables used for calculation
	float CEC;
	float BentPPM;
	float BentKG;
	float V_mbl;
	float V_boreslam = 1;

	final static int[] IDs = { R.id.etCECVmbl, // Textfield of V
												// metylenblått-løsning
			R.id.etCECCEC, // Textfield of CEC - Will be disabled
	};

	/**
	 * Only CEC_INDEX will get calculated
	 */
	public final static int V_mbl_INDEX = 0, CEC_INDEX = 1; // Only used for
															// error catching.
															// Only CEC_INDEX
															// should ever be
															// used

	public CEC(Context context) {
		super(context, IDs, layout);
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		V_mbl = fieldStatuses[0];

		switch (variableToCalculate) {

		default:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate anything other than CEC, and this should not happen!");
			break;
		case CEC_INDEX:

			if (checkForNullValues(V_mbl, V_boreslam) == false
					|| checkForNegativeValues(V_mbl) == false)
				return "";

			CEC = V_mbl / V_boreslam;
			BentPPM = 5 * CEC;
			BentKG = 14.25f * CEC;

			float[] testFloats = { CEC, BentKG, BentPPM };
			if (checkForDivisionErrors(testFloats) == false)
				return "";

			String _BentPPM = String.format(THREE_DECIMALS, BentPPM) + " "
					+ "[ppm]";
			String _BentKG = String.format(THREE_DECIMALS, BentKG);

			tvbentPPM.setText(_BentPPM);
			tvbentKG.setText(Html.fromHtml(_BentKG
					+ " [kg/m<sup><small>3</small></sup>]"));

			return String.format(THREE_DECIMALS, CEC);

		}

		return "";

	}


	@Override
	protected void initializeMethod() {
		// TODO Auto-generated method stub
		super.initializeMethod();

		tvbentKG = (TextView) findViewById(R.id.tvCECbentKG);
		tvbentPPM = (TextView) findViewById(R.id.tvCECbentPPM);

	}

	public float getCEC() {
		return CEC;
	}

	public float getBentPPM() {
		return BentPPM;
	}

	public float getBentKG() {
		return BentKG;
	}

}