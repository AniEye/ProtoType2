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

public class AlkaPm extends Basic_Calc {

	final static int layout = R.layout.calc_alkalitet_pm;

	TextView tvCa;

	// Variables used for calculation
	float Ca;
	float Pm;
	float Fw;
	float Pf;

	final static int[] IDs = { R.id.etAlkaPm, // Textfield of Pm
			R.id.etAlkaFw, // Textfield of Fw
			R.id.etAlkaPf, // Textfield of Pf
			R.id.etAlkaCa // Textfield of Ca (OH)2 - Will be disabled
	};

	public final static int Ca_INDEX = 3; // Only used for error catching. Only
											// Ca_INDEX

	// should ever be used

	public AlkaPm(Context context) {
		super(context, IDs, layout);
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		Pm = fieldStatuses[0];
		Fw = fieldStatuses[1];
		Pf = fieldStatuses[2];

		switch (variableToCalculate) {

		default:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate anything other than CEC, and this should not happen!");
			break;
		case Ca_INDEX:

			if (Fw == 0) {
				showToast("Fw kan ikke være null!");
				return "";
			}

			Ca = 0.742f * (Pm - Fw * Pf);

			float[] testFloats = { Ca, Pm, Fw, Pf };
			if (checkForDivisionErrors(testFloats) == false)
				return "";

			if (Ca < 0)
				Ca = 0;

			String _Ca = String.format(THREE_DECIMALS, Ca);

			tvCa.setText(Html.fromHtml(_Ca + " [kg/m<sup><small>3</small></sup>]"));

			return _Ca;

		}

		return "";

	}
	
	@Override
	protected void initializeMethod() {
		// TODO Auto-generated method stub
		super.initializeMethod();
		
		tvCa = (TextView) findViewById(R.id.tvAlkaCa);
		
	}


	public float getCa() {
		return Ca;
	}

}