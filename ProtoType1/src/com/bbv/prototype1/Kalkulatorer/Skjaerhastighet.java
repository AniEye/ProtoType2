package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Skjaerhastighet extends Basic_Calc {
	LinearLayout _linLay;
	Button _clear, _update;
	int[] _textFieldsStatus = { 0, 0, 0, 0 };
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	public final static int tegn_INDEX = 0, N_INDEX = 1, r1_INDEX = 2,
			r2_INDEX = 3;
	EditText[] textFields = new EditText[_textFieldsStatus.length];

	public Skjaerhastighet(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(R.layout.calc_skjaerhastighet);
		textFields[0] = FindAndReturnEditText(R.id.etSkjaerHastighetTegn,
				focChan);
		textFields[1] = FindAndReturnEditText(R.id.etSkjaerHastighetN, focChan);
		textFields[2] = FindAndReturnEditText(R.id.etSkjaerHastighetR1, focChan);
		textFields[3] = FindAndReturnEditText(R.id.etSkjaerHastighetR2, focChan);
		_clear = FindAndReturnButton(R.id.bSkjaerHastighetClear, cliLis);
		_update = FindAndReturnButton(R.id.bSkjaerHastighetUpdate, cliLis);
	}

	@Override
	protected void CreateListeners() {

		cliLis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.bSkjaerHastighetClear:
					ResetFields(textFields);
					_textFieldsStatus = new int[] { 0, 0, 0, 0 };
					break;
				case R.id.bSkjaerHastighetUpdate:
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
				case R.id.etSkjaerHastighetTegn:
					FocusChange(0, hasFocus);
					break;
				case R.id.etSkjaerHastighetN:
					FocusChange(1, hasFocus);
					break;
				case R.id.etSkjaerHastighetR1:
					FocusChange(2, hasFocus);
					break;
				case R.id.etSkjaerHastighetR2:
					FocusChange(3, hasFocus);
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
		
		float Y = fieldStatuses[0];
		float N = fieldStatuses[1];
		float R1 = fieldStatuses[2];
		float R2 = fieldStatuses[3];

		
		float theAnswer = 0;
		switch (variableToCalculate) {
		case tegn_INDEX:
			theAnswer = (float) ((4*Math.PI*N)/(1-(Math.pow(R1/R2, 2))));
			break;
		case N_INDEX:
			theAnswer = (float) (Y*(1-(Math.pow(R1/R2, 2)))/(4*Math.PI));
			break;
		case r1_INDEX:
			theAnswer = (float) (Math.sqrt(1-((4*Math.PI*N)/Y)))*R2;
			break;
		case r2_INDEX:
			theAnswer = (float) ((float) R1/(Math.sqrt(1-((4*Math.PI*N)/Y))));
			break;
		}
		if (theAnswer != 0)
			return String.format("%.3f", theAnswer);
		else
			return "";
	}
}