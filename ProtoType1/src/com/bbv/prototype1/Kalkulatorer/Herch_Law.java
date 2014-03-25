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

public class Herch_Law extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bMPLClear;
	final int updateButtonID = R.id.bMPLUpdate;
	final int layout = R.layout.calc_modpowerlaw;

	final int[] textViewIDs = { R.id.tvMPLN, R.id.tvMPLK, R.id.tvMPLT0,
			R.id.tvMPLEksY };
	final int[] IDs = { R.id.etModPowerLawT600, R.id.etModPowerLawT300,
			R.id.etModPowerLawT6, R.id.etModPowerLawT3, R.id.etMODPowerLawN };

	public final static int T6_INDEX = 0, T3_INDEX = 1, N_INDEX = 4;

	public Herch_Law(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float T600 = fieldStatuses[0];
		float T300 = fieldStatuses[1];
		float T6 = fieldStatuses[2];
		float T3 = fieldStatuses[3];
		float N;
		float T0;
		float ty;
		float K;

		float theAnswer = 0;
		switch (variableToCalculate) {

		default:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate wrong value, and this should not happen!");
			break;
		
		case N_INDEX:

			if (checkForNullValues(T600, T300,T6,T3) == false
					|| checkForNegativeValues(T600, T300,T6,T3) == false)
				return "";

			T0 = calcT0(T6, T3);
			ty = calcTy(T0);
			N = calcN(T600, T300, T0);
			K = calcK(T600, T0, N);

			float[] testFloats = { T0, ty, N, K};

			if (checkForDivisionErrors(testFloats) == false)
				return "";

			Log.println(Log.INFO, "calc", "Setting textviews in Modpowerlaw!");

			String _N = String.format(THREE_DECIMALS, N);
			String _T0 = String.format(THREE_DECIMALS, T0);
			String _ty = String.format(THREE_DECIMALS, ty);
			String _K = String.format(THREE_DECIMALS, K);

			textviews[0].setText(_N);
			textviews[1].setText(_T0);
			textviews[2].setText(_ty);
			textviews[3].setText(_K);
			
			return String.format(THREE_DECIMALS, N);

		}

		return "";
	}

	public float calcTy(float t0) {
		return t0 * 0.511f;
	}

	public float calcT0(float t6, float t3) {
		return (2 * t3) - t6;
	}

	public float calcN(float t600, float t300, float t0) {
		return (float) (Math.log10((t600-t0)/(t300-t0)) / Math.log10((600*1.7033)/(300*1.7033)));
	}

	public float calcK(float t600, float t0, float n) {
		return (float) (0.511f*((t600-t0)/Math.pow(600*1.7033, n)));
	}
	
	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		textviews = new TextView[textViewIDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);
		for (int i = 0; i < textViewIDs.length; i++)
			textviews[i] = (TextView) findViewById(textViewIDs[i]);
		

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
					ResetTextViews();
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

}