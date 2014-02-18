package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Slamtetthet extends Basic_Calc {
	LinearLayout _linLay;
	Button _clear, _update;
	int[] _textFieldsStatus = { 0, 0, 0, 0, 0, 0 };
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	public final static int p1_INDEX = 0, V1_INDEX = 1, pv_INDEX = 2,
			Vv_INDEX = 3, p2_INDEX = 4, V2_INDEX = 5;

	EditText[] textFields = new EditText[6];

	public Slamtetthet(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(R.layout.calc_slamtetthet);
		textFields[0] = FindAndReturnEditText(R.id.etSlamTettp1, focChan);
		textFields[1] = FindAndReturnEditText(R.id.etSlamTettv1, focChan);
		textFields[2] = FindAndReturnEditText(R.id.etSlamTettpv, focChan);
		textFields[3] = FindAndReturnEditText(R.id.etSlamTettVV, focChan);
		textFields[4] = FindAndReturnEditText(R.id.etSlamTettp2, focChan);
		textFields[5] = FindAndReturnEditText(R.id.etSlamTettV2, focChan);
		_clear = FindAndReturnButton(R.id.bSlamTettClear, cliLis);
		_update = FindAndReturnButton(R.id.bSlamTettUpdate, cliLis);
	}

	@Override
	protected void CreateListeners() {

		cliLis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.bMBClear:
					ResetFields(textFields);
					_textFieldsStatus = new int[] { 0, 0, 0, 0, 0, 0 };
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
				case R.id.etSlamTettp1:
					FocusChange(0, hasFocus);
					break;
				case R.id.etSlamTettv1:
					FocusChange(1, hasFocus);
					break;
				case R.id.etSlamTettpv:
					FocusChange(2, hasFocus);
					break;
				case R.id.etSlamTettVV:
					FocusChange(3, hasFocus);
					break;
				case R.id.etSlamTettp2:
					FocusChange(4, hasFocus);
					break;
				case R.id.etSlamTettV2:
					FocusChange(5, hasFocus);
					break;
				}
			}
		};
	}

	protected void FocusChange(int indexOfCurrentField, boolean focusStatus) {
		String _fieldsString = textFields[indexOfCurrentField].getText()
				.toString();

		if (theSum(_textFieldsStatus) < 5) {
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

		float 	p1 = fieldStatuses[0], 
				v1 = fieldStatuses[1], 
				pv = fieldStatuses[2], 
				vv = fieldStatuses[3], 
				p2 = fieldStatuses[4], 
				v2 = fieldStatuses[5];

		float theAnswer = 0;
		switch (variableToCalculate) {
		case p1_INDEX:
			theAnswer = ((p2*v2) - (pv*vv))/v1;
			break;
		case V1_INDEX:
			theAnswer = ((p2*v2) - (pv*vv))/p1;
			break;
		case pv_INDEX:
			theAnswer = ((p2*v2) - (p1*v1))/vv;
			break;
		case Vv_INDEX:
			theAnswer = ((p2*v2) - (p1*v1))/pv;
			break;
		case p2_INDEX:
			theAnswer = ((p1*v1) + (pv*vv))/v2;
			break;
		case V2_INDEX:
			theAnswer = ((p1*v1) + (pv*vv))/p2;
			break;

		}
		if (theAnswer != 0)
			return theAnswer + "";
		else
			return "";
	}
}