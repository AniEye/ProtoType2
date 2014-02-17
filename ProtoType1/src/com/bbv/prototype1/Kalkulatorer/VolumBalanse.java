package com.bbv.prototype1.Kalkulatorer;

<<<<<<< HEAD
import com.bbv.prototype1.Basic_Calc;
=======
>>>>>>> Made major changes to Calculators
import com.bbv.prototype1.R;
import android.content.Context;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class VolumBalanse extends Basic_Calc {
	LinearLayout _linLay;
	Button _clear, _update;
	int[] _textFieldsStatus = { 0, 0, 0 };
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	// indexes: 0=theta, 1=RPM, 2=tilvisk
	public final static int v1_INDEX=0, vv_INDEX=1, v2_INDEX=2;
	EditText[] textFields = new EditText[3];

	public VolumBalanse(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(R.layout.activity_volumbalanse);
		textFields[0] = FindAndReturnEditText(R.id.etVBm1, focChan);
		textFields[1] = FindAndReturnEditText(R.id.etVBmv, focChan);
		textFields[2] = FindAndReturnEditText(R.id.etVBm2, focChan);
		_clear = FindAndReturnButton(R.id.bVBClear, cliLis);
		_update = FindAndReturnButton(R.id.bVBUpdate, cliLis);
	}

	@Override
	protected void CreateListeners() {

		cliLis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.bVBClear:
					ResetFields(textFields);
					_textFieldsStatus = new int[] { 0, 0, 0 };
					break;
				case R.id.bVBUpdate:
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
				case R.id.etVBm1:
					FocusChange(0, hasFocus);
					break;
				case R.id.etVBmv:
					FocusChange(1, hasFocus);
					break;
				case R.id.etVBm2:
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
		case v1_INDEX:// theta
			theAnswer = fieldStatuses[2] - fieldStatuses[1];
			break;
		case vv_INDEX:// rpm
			theAnswer = fieldStatuses[2] - fieldStatuses[0];
			break;
		case v2_INDEX:// tilvisk
			theAnswer = fieldStatuses[0] + fieldStatuses[1];
			break;
		}
		if (theAnswer != 0)
			return theAnswer + "";
		else
			return "";
	}
}