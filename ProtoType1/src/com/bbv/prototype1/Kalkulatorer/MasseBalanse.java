package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.Basic_Calc;
import com.bbv.prototype1.R;
import android.content.Context;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MasseBalanse extends Basic_Calc {
	LinearLayout _linLay;
	Button _clear, _update;
	int[] _textFieldsStatus = { 0, 0, 0 };
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	// indexes: 0=theta, 1=RPM, 2=tilvisk
	public final static int m1_INDEX=0, mv_INDEX=1, m2_INDEX=2;
	EditText[] textFields = new EditText[3];

	public MasseBalanse(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(R.layout.activity_massebalanse);
		textFields[0] = FindAndReturnEditText(R.id.etMBm1, focChan);
		textFields[1] = FindAndReturnEditText(R.id.etMBmv, focChan);
		textFields[2] = FindAndReturnEditText(R.id.etMBm2, focChan);
		_clear = FindAndReturnButton(R.id.bMBClear, cliLis);
		_update = FindAndReturnButton(R.id.bMBUpdate, cliLis);
	}

	@Override
	protected void CreateListeners() {

		cliLis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.bMBClear:
					ResetFields(textFields);
					_textFieldsStatus = new int[] { 0, 0, 0 };
					break;
				case R.id.bMBUpdate:
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
				case R.id.etTheta:
					FocusChange(0, hasFocus);
					break;
				case R.id.etRPM:
					FocusChange(1, hasFocus);
					break;
				case R.id.etViskosTil:
					FocusChange(2, hasFocus);
					break;
				}
			}
		};
	}

	protected void FocusChange(int indexOfCurrentField, boolean focusStatus) {
		String _fieldsString = textFields[indexOfCurrentField].getText()
				.toString();

		if (theSum(_textFieldsStatus) < 2) {
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
		
		float theAnswer = 0;
		switch (variableToCalculate) {
		case m1_INDEX:// theta
			theAnswer = fieldStatuses[2] - fieldStatuses[1];
			break;
		case mv_INDEX:// rpm
			theAnswer = fieldStatuses[2] - fieldStatuses[0];
			break;
		case m2_INDEX:// tilvisk
			theAnswer = fieldStatuses[0] + fieldStatuses[1];
			break;
		}
		if (theAnswer != 0)
			return theAnswer + "";
		else
			return "";
	}
}