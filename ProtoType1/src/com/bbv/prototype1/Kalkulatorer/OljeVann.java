package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bbv.prototype1.R;

public class OljeVann extends Basic_Calc {

	final static int layout = R.layout.calc_olje_vann;

	// Variables used for calculation
	float Rolje;
	float Rvann;
	float O;
	float W;

	final static int[] textviewIDs = { R.id.tvOVO, R.id.tvOVW, R.id.tvOVOW };

	final static int[] IDs = { R.id.etOVRO, // Textfield of R olje
			R.id.etOVRV, // Textfield of R vann
			R.id.etOVHideThis, // Textfield - Will be disabled
	};

	public final static int KEY_INDEX = 2; // Only used for error catching. Only
											// KEY_INDEX

	// should ever be used

	public OljeVann(Context context) {
		super(context, IDs,textviewIDs, layout);

	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		Rolje = fieldStatuses[0];
		Rvann = fieldStatuses[1];

		switch (variableToCalculate) {

		default:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate wrong edit texts, and this should not happen!");
			break;
		case KEY_INDEX:

			if (checkForNegativeValues(fieldStatuses) == false)
				return "";

			O = (Rolje / (Rolje + Rvann)) * 100;
			W = (Rvann / (Rolje + Rvann)) * 100;

			float[] testFloats = { O, W };
			if (checkForDivisionErrors(testFloats) == false)
				return "";

			String _O = String.format("%.0f", O);
			String _W = String.format("%.0f", W);

			String _OW = _O + "/" + _W;

			textviews[0].setText(_O + "%");
			textviews[1].setText(_W + "%");
			textviews[2].setText(_OW);

			return _OW;

		}

		return "";

	}

	public float getO() {
		return O;
	}

	public float getW() {
		return W;
	}

}