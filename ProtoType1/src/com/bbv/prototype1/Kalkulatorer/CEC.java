package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import com.bbv.prototype1.R;

public class CEC extends Basic_Calc {

	final static int layout = R.layout.calc_cec;

	// Variables used for calculation
	float CEC;
	float BentPPM;
	float BentKG;
	float V_mbl;
	float V_boreslam = 1;

	final static int[] textviewIDs = { R.id.tvCECbentKG, R.id.tvCECbentPPM };

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
		super(context, IDs, textviewIDs, layout);
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

			String _BentPPM = String.format(ONE_DECIMAL, BentPPM) + " "
					+ "[lbs/bbl]";
			String _BentKG = String.format(ONE_DECIMAL, BentKG);

			textviews[0].setText(_BentPPM);
			textviews[1].setText(Html.fromHtml(_BentKG
					+ " [kg/m<sup><small>3</small></sup>]"));

			return String.format(ONE_DECIMAL, CEC);

		}

		return "";

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