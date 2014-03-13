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

public class AlkaPm extends Basic_Calc {

	Toast toast;
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

			Ca = 0.742f * (Pm - Fw * Pf);

			float[] testFloats = { Ca, Pm, Fw, Pf };
			for (int i = 0; i < testFloats.length; i++) {
				// Displays a toast saying that there was an error if any value
				// returned NaN or infinity
				if (testFloat(testFloats[i]) == false) {
					Log.println(Log.DEBUG, "calc", "Dividing with 0 error in "
							+ this.getClass().getName());
					return "";
				}
			}

			String _Ca = String.format("%.3f", Ca);

			tvCa.setText(_Ca);

			return _Ca;

		}

		return "";

	}

	private boolean testFloat(float x) {
		if (Float.isInfinite(x) || Float.isNaN(x)) {
			Log.println(Log.ERROR, "calc", this.getClass().getName()
					+ " tried to divide by 0!");
			showToast("You can't divide by 0!");
			return false;
		}
		return true;
	}

	private void showToast(String message) {
		try {
			toast.getView().isShown(); // true if visible
			toast.setText(message);
		} catch (Exception e) { // invisible if exception
			toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
		}
		Log.println(Log.INFO, "calc", "Displayed toast saying: " + message);
		toast.show();
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
				try {
					if (Float.parseFloat(_fieldsString) != 0.0) {
						_textFieldsStatus[indexOfCurrentField] = 1;
					}
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
			}
		} else {
			if (_textFieldsStatus[indexOfCurrentField] == 1) {
				if (focusStatus == false) {
					float number = 0;
					try {
						number = Float.parseFloat(_fieldsString);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}

					if (_fieldsString.contentEquals("")) {
						_textFieldsStatus[indexOfCurrentField] = 0;
						Enabeling(textFields);
					} else if (number == 0.0) {
						_textFieldsStatus[indexOfCurrentField] = 0;
						textFields[indexOfCurrentField].setText("");
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
	
	public float getCa(){
		return Ca;
	}

}