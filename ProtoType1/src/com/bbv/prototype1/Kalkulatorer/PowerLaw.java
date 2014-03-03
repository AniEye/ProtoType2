package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PowerLaw extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bPLClear;
	final int updateButtonID = R.id.bPLUpdate;
	final int layout = R.layout.calc_powerlaw;

	final int[] IDs = { R.id.etPowerLawT100, R.id.etPowerLaw600,
			R.id.etPowerLawN, R.id.etPowerLawTegn };

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
		float K = fieldStatuses[3];

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

		case K_INDEX:

			if (N == 0) {
				Log.println(Log.ERROR, "calc",
						"Somehow the program tried to calculate K without n!");
				break;
			}
			
			if (variableToCalculate == K_INDEX){
				
				K = calcK(T6, N);
				
				return String.format("%.3f", K);
			} else {
				
				K = calcK(T6, theAnswer);

				textFields[3].setText(String.format("%.3f", K));
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
		textFields[3].setEnabled(false);

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
					textFields[3].setEnabled(false);

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

		if (theSum(_textFieldsStatus) < _textFieldsStatus.length - 2) {
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
						textFields[3].setEnabled(false);
					} else if (number == 0.0) {
						_textFieldsStatus[indexOfCurrentField] = 0;
						textFields[indexOfCurrentField].setText("");
						Enabeling(textFields);
						textFields[2].setEnabled(false);
						textFields[3].setEnabled(false);
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