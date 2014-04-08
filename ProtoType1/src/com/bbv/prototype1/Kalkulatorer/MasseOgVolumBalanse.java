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

public class MasseOgVolumBalanse extends Basic_Calc {

	static int layout = R.layout.calc_masseogvolumbalanse;

	final int Vv_INDEX = 0, V1_INDEX = 1, p2_INDEX = 2,
			Pv_INDEX = 3, p1_INDEX = 4;

	static int[] IDs = { R.id.etMOVBVv, R.id.etMOVBV1, R.id.etMOVBP2,
			R.id.etMOVBPV, R.id.etMOVBP1 };

	public MasseOgVolumBalanse(Context context) {
		super(context, IDs, layout);
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float vv = fieldStatuses[Vv_INDEX], 
				v1 = fieldStatuses[V1_INDEX], 
				p2 = fieldStatuses[p2_INDEX], 
				pv = fieldStatuses[Pv_INDEX], 
				p1 = fieldStatuses[p1_INDEX];

		float theAnswer = 0;

		switch (variableToCalculate) {
 
		case Vv_INDEX:

			if (checkForNullValues(v1, p2, pv, p1) == false)
				return "";

			Log.i("calc", "P1 = " + p1 + "\nP2 = " + p2);
			theAnswer = v1 * ((p2 - p1) / (pv - p2));
			Log.i("calc", "The answer = " + theAnswer);
			break;
		case V1_INDEX:

			if (checkForNullValues(vv, p2, pv, p1) == false)
				return "";

			theAnswer = vv / ((p2 - p1) / (pv - p2));
			break;
		case p2_INDEX:

			if (checkForNullValues(v1, vv, pv, p1) == false)
				return "";

			theAnswer = (((vv * pv) / v1) + p1) / (1 + (vv / v1));
			break;
		case p1_INDEX:

			if (checkForNullValues(v1, p2, pv, vv) == false)
				return "";
			theAnswer = p2 - (vv * (pv - p2) / v1);

			break;
		case Pv_INDEX:

			if (checkForNullValues(v1, p2, vv, p1) == false)
				return "";
			theAnswer = ((v1 * (p2 - p1) / vv) + p2);

			break;
		}

		if (checkForDivisionErrors(theAnswer) == false)
			return "";

		Log.println(Log.INFO, "calc", "Returning " + theAnswer);
		return String.format("%.3f", theAnswer);

	}

}