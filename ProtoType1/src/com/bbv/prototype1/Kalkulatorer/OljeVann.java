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

	final int layout = R.layout.calc_olje_vann;

	TextView tvO;
	TextView tvW;
	TextView tvOW;

	// Variables used for calculation
	float Rolje;
	float Rvann;
	float O;
	float W;
	
	final static int[] IDs = { R.id.etOVRO, // Textfield of R olje
			R.id.etOVRV, // Textfield of R vann
			R.id.etOVHideThis, // Textfield - Will be disabled
	};

	public final static int KEY_INDEX = 2; // Only used for error catching. Only KEY_INDEX
							// should ever be used

	public OljeVann(Context context) {
		super(context,IDs);
		CreateListeners();
		Initialize();
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

			if(checkForNegativeValues(fieldStatuses) == false)
				return "";
			
			O = (Rolje / (Rolje+Rvann))*100;
			W = (Rvann / (Rolje+Rvann))*100;

			float[] testFloats = { O, W };
			if (checkForDivisionErrors(testFloats) == false)
				return "";
			
			String _O = String.format("%.0f", O);
			String _W = String.format("%.0f", W);

			String _OW = _O+"/"+_W;

			tvO.setText(_O + "%");
			tvW.setText(_W + "%");
			tvOW.setText(_OW);

			return _OW;

		}

		return "";

	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		tvO = (TextView) findViewById(R.id.tvOVO);
		tvW = (TextView) findViewById(R.id.tvOVW);
		tvOW = (TextView) findViewById(R.id.tvOVOW);

		_clear = FindAndReturnButton(clearButtonID, cliLis);
		_update = FindAndReturnButton(updateButtonID, cliLis);
	}

	public float getO() {
		return O;
	}

	public float getW() {
		return W;
	}
	

}