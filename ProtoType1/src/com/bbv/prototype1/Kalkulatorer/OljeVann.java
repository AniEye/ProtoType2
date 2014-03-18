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

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bOVClear;
	final int updateButtonID = R.id.bOVUpdate;
	final int layout = R.layout.calc_olje_vann;

	TextView tvO;
	TextView tvW;
	TextView tvOW;

	// Variables used for calculation
	float Rolje;
	float Rvann;
	float O;
	float W;
	
	final int[] IDs = { R.id.etOVRO, // Textfield of R olje
			R.id.etOVRV, // Textfield of R vann
			R.id.etOVHideThis, // Textfield - Will be disabled
	};

	public final static int KEY_INDEX = 2; // Only used for error catching. Only KEY_INDEX
							// should ever be used

	public OljeVann(Context context) {
		super(context);
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

			if (Rolje == -Rvann || Rvann == -Rolje) {
				showToast("Du kan ikke dele på null!");
				return "";
			}

			O = (Rolje / (Rolje+Rvann))*100;
			W = (Rvann / (Rolje+Rvann))*100;

			float[] testFloats = { O, W };
			if (testFloat(testFloats) == false)
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

	@Override
	protected void CreateListeners() {

		cliLis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case clearButtonID:
					ResetFields(textFields);

					tvO.setText("");
					tvW.setText("");
					tvOW.setText("");

					_textFieldsStatus = new int[IDs.length];
					break;
				case updateButtonID:
					for (int i = 0; i < textFields.length; i++) {
						FocusChange(i, false);
						try {
							if (Float.parseFloat(textFields[i].getText()
									.toString()) == 0.0)
								textFields[i].setText("");
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
					break;
				}
			}
		};

		focChan = new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				for (int i = 0; i < IDs.length; i++) {
					if (v.getId() == IDs[i])
						FocusChange(i, hasFocus);
				}

			}
		};
	}

	protected void FocusChange(int indexOfCurrentField, boolean focusStatus) {
		String _fieldsString = textFields[indexOfCurrentField].getText()
				.toString();

		if (theSum(_textFieldsStatus) < _textFieldsStatus.length - 1) {
			if (focusStatus == false && !_fieldsString.contentEquals("")) {

				_textFieldsStatus[indexOfCurrentField] = 1;

			}
		} else {
			if (_textFieldsStatus[indexOfCurrentField] == 1) {
				if (focusStatus == false) {
					
					if (_fieldsString.contentEquals("")) {
						_textFieldsStatus[indexOfCurrentField] = 0;
						Enabeling(textFields);
					} else if (!_fieldsString.contentEquals("")) {
						updateRelevantResult();
					}
				}
			} else {
				updateRelevantResult();
				textFields[indexOfCurrentField].setEnabled(false);
			}
		}
	}

	@Override
	protected void updateRelevantResult() {
		for (int i = 0; i < _textFieldsStatus.length; i++) {
			if (_textFieldsStatus[i] == 0) {

				textFields[i].setText(calculation(i,
						getFloatVariables(textFields)));
				textFields[i].setEnabled(false);
				break;
			}
		}
	}

	public float getO() {
		return O;
	}

	public float getW() {
		return W;
	}
	

}