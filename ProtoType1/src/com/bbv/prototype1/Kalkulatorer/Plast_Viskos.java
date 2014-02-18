package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Plast_Viskos extends Basic_Calc {
	LinearLayout _linLay;
	Button _clear, _update;
	int[] _textFieldsStatus = { 0, 0, 0 };
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButton = R.id.bPlasViskosClear;
	final int updateButton = R.id.bPlasViskosUpdate;

	final int textField1 = R.id.etViskosPlas;
	final int textField2 = R.id.etPlasViskTheta600;
	final int textField3 = R.id.etPlasviskTheta300;
	
	public final static int PV_INDEX = 0, T6_INDEX = 1, T3_INDEX = 2;
	EditText[] textFields = new EditText[_textFieldsStatus.length];

	public Plast_Viskos(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(R.layout.calc_viskositet_plastisk);
		textFields[0] = FindAndReturnEditText(textField1, focChan);
		textFields[1] = FindAndReturnEditText(textField2, focChan);
		textFields[2] = FindAndReturnEditText(textField3, focChan);
		_clear = FindAndReturnButton(clearButton, cliLis);
		_update = FindAndReturnButton(updateButton, cliLis);
	}

	@Override
	protected void CreateListeners() {

		cliLis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case clearButton:
					ResetFields(textFields);
					_textFieldsStatus = new int[] { 0, 0, 0 };
					break;
				case updateButton:
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
				switch (v.getId()) {
				case textField1:
					FocusChange(0, hasFocus);
					break;
				case textField2:
					FocusChange(1, hasFocus);
					break;
				case textField3:
					FocusChange(2, hasFocus);
					break;
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

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float PV = fieldStatuses[0];
		float T6 = fieldStatuses[1];
		float T3 = fieldStatuses[2];

		float theAnswer = 0;
		switch (variableToCalculate) {
		case PV_INDEX:
			theAnswer = T6 - T3;
			break;
		case T6_INDEX:
			theAnswer = PV + T3;
			break;
		case T3_INDEX:
			theAnswer = T6 - PV;
			break;
		}
		if (theAnswer != 0)
			return String.format("%.3f", theAnswer);
		else
			return "";
	}
}