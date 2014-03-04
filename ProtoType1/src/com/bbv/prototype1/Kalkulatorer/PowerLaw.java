package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bbv.prototype1.R;

public class PowerLaw extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bPLClear;
	final int updateButtonID = R.id.bPLUpdate;
	final int layout = R.layout.calc_powerlaw;

	TextView K_notPA;
	TextView K_PA;
	TextView tvEksponent;

	final int[] IDs = { R.id.etPowerLawT100, R.id.etPowerLaw600,
			R.id.etPowerLawN };

	public final static int T1_INDEX = 0, T6_INDEX = 1, N_INDEX = 2,
			K_INDEX = 3;

	public PowerLaw(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float T1 = fieldStatuses[0];
		float T6 = fieldStatuses[1];
		float N = fieldStatuses[2];

		float theAnswer = 0;
		switch (variableToCalculate) {

		case T1_INDEX:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate T1, and this should not happen!");
			break;
		case T6_INDEX:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate T6, and this should not happen!");
			break;
		case N_INDEX:
			theAnswer = (float) ((Math.log10(T1 / T6)) / Math.log10(170 / 10));
			N = theAnswer;
		case K_INDEX:

			float K;
			float eksponent;
			
			if (N == 0) {
				Log.println(Log.ERROR, "calc",
						"PowerLawCalc tried to calculate K without n!" +
						"\nIgnore if running JUnit tests");
				break;
			}

			if (variableToCalculate == K_INDEX) {
				// This is used only by JUNIT for testing purposes
				K = calcK(T6, N);

				return String.format("%.3f", K);
			} else {

				Log.println(Log.INFO, "calc", "Setting textviews in powerlaw!");
				K = calcK(T6, theAnswer);
				
				eksponent = (float) (K*Math.pow(T6*1.7023, N));
				
				String _k = String.format("%.3f", K);
				K_notPA.setText(_k);
				
				String _k_pa = String.format("%.3f", K * 0.511f);
				K_PA.setText(_k_pa);
				
				String _eksponent = String.format("%.3f", eksponent);
				tvEksponent.setText(_eksponent);

			}

			break;

		}
		if (theAnswer != 0)
			return String.format("%.3f", theAnswer);
		else
			return "";
	}

	protected float calcK(float T6, float n) {
		return (float) (T6 / Math.pow(10, n));
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		textFields[2].setEnabled(false);

		K_PA = (TextView) findViewById(R.id.tvPLPA);
		K_notPA = (TextView) findViewById(R.id.tvPLNOTPA);
		tvEksponent = (TextView) findViewById(R.id.tvPLEks);

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

					textFields[2].setEnabled(false);
					K_PA.setText("");
					K_notPA.setText("");
					tvEksponent.setText("");

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
						textFields[2].setEnabled(false);
					} else if (number == 0.0) {
						_textFieldsStatus[indexOfCurrentField] = 0;
						textFields[indexOfCurrentField].setText("");
						Enabeling(textFields);
						textFields[2].setEnabled(false);
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

}