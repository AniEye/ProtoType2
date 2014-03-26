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

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bAlkaClear;
	final int updateButtonID = R.id.bAlkaUpdate;
	final int layout = R.layout.calc_alkalitet_pm;

	TextView tvCa;

	// Variables used for calculation
	float Ca;
	float Pm;
	float Fw;
	float Pf;

	final int[] IDs = { R.id.etAlkaPm, // Textfield of Pm
			R.id.etAlkaFw, // Textfield of Fw
			R.id.etAlkaPf, // Textfield of Pf
			R.id.etAlkaCa // Textfield of Ca (OH)2 - Will be disabled
	};

	public final static int Ca_INDEX = 3; // Only used for error catching. Only
											// Ca_INDEX

	// should ever be used

	public AlkaPm(Context context) {
		super(context);
		CreateListeners();
		Initialize();
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
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		textFields[3].setVisibility(GONE);

		tvCa = (TextView) findViewById(R.id.tvAlkaCa);

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

					tvCa.setText("");

					_textFieldsStatus = new int[IDs.length];
					break;
				case updateButtonID:
					for (int i = 0; i < textFields.length; i++) {
						FocusChange(i, false);
					}
					hideSoftKeyboard();
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

	public float getCa() {
		return Ca;
	}

}