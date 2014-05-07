package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Til_Viskos extends Basic_Calc {

	static int layout = R.layout.calc_viskositet_tilsynelatende;

	static int[] IDs = { R.id.etViskosTil, R.id.etTheta, R.id.etRPM };

	public final static int  TIL_VISK_INDEX = 0, THETA_INDEX = 1, RPM_INDEX = 2 ;

	public Til_Viskos(Context context) {
		super(context, IDs, layout);
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {
		float theta = fieldStatuses[THETA_INDEX];
		float rpm = fieldStatuses[RPM_INDEX];
		float TV = fieldStatuses[TIL_VISK_INDEX];

		float theAnswer = 0;
		switch (variableToCalculate) {
		case THETA_INDEX:// theta

			if (checkForNullValues(TV, rpm) == false
					|| checkForNegativeValues(TV, rpm) == false)
				return "";

			theAnswer = (TV * rpm) / 300;
			break;
		case RPM_INDEX:// rpm
			if (checkForNullValues(TV, theta) == false
					|| checkForNegativeValues(TV, theta) == false)
				return "";

			theAnswer = (float) ((300.0 * theta) / TV);
			break;
		case TIL_VISK_INDEX:// tilvisk

			if (checkForNullValues(theta, rpm) == false
					|| checkForNegativeValues(theta, rpm) == false)
				return "";

			theAnswer = (float) ((300.0 * theta) / rpm);
			break;
		}

		Log.println(Log.DEBUG, "calc", "Printing the answer");
		if (checkForDivisionErrors(theAnswer) == false
				|| checkForNullValues(theAnswer) == false)
			return "";
		else
			return String.format("%.3f", theAnswer);

	}

}