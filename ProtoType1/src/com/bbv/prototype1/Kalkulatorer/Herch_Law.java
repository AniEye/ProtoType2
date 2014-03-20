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

	TextView tvN;
	TextView tvK;
	TextView tvEksY;
	TextView tvskj;
	TextView tvEks;
	TextView tvT0;

	final int[] IDs = { R.id.etModPowerLawT600, R.id.etModPowerLawT300,
			R.id.etMODPowerLawN };

	public final static int T6_INDEX = 0, T3_INDEX = 1, N_INDEX = 2;

	public Herch_Law(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float T6 = fieldStatuses[0];
		float T3 = fieldStatuses[1];
		float N;
		float T0;
		float ty;
		float K;
		float Y;
		float T;

		float theAnswer = 0;
		switch (variableToCalculate) {

		case T6_INDEX:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate T6, and this should not happen!");
			break;
		case T3_INDEX:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate T3, and this should not happen!");
			break;
		case N_INDEX:
			
			if (checkForNullValues(T6,T3) == false || checkForNegativeValues(T6,T3))
				return "";

			T0 = calcT0(T6, T3);
			ty = calcTy(T0);
			N = calcN(T6, T3, T0);
			K = calcK(T6, T0, N);
			Y = calcY(T6);
			T = calcT(ty, K, Y, N);

			float[] testFloats = { T0, ty, N, K, Y, T };

			if (checkForDivisionErrors(testFloats) == false)
				return "";

			Log.println(Log.INFO, "calc", "Setting textviews in Modpowerlaw!");

			String _N = String.format("%.3f", N);
			String _T0 = String.format("%.3f", T0);
			String _ty = String.format("%.3f", ty);
			String _K = String.format("%.3f", K);
			String _Y = String.format("%.3f", Y);
			String _T = String.format("%.3f", T);

			tvN.setText(_N);
			tvK.setText(_K);
			tvEksY.setText(_ty);
			tvskj.setText(_Y);
			tvEks.setText(_T);
			tvT0.setText(_T0);

			return String.format("%.3f", N);

		}

		return "";
	}

	public float calcTy(float t0) {
		return t0 * 0.511f;
	}

	public float calcT0(float t6, float t3) {
		return (2 * t3) - t6;
	}

	public float calcN(float t6, float t3, float t0) {
		return (float) (3.32 * Math.log10((t6 - t0) / (t3 - t0)));
	}

	public float calcK(float t6, float t0, float n) {
		return (float) (0.511 * ((t6 - t0) / Math.pow(1022, n)));
	}

	public float calcY(float t6) {
		return (float) (1.7023 * t6);
	}

	public float calcT(float ty, float K, float Y, float N) {
		return (float) (ty + (K * Math.pow(Y, N)));
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		textFields[2].setVisibility(GONE);

		tvN = (TextView) findViewById(R.id.tvMPLN);
		tvK = (TextView) findViewById(R.id.tvMPLK);
		tvEksY = (TextView) findViewById(R.id.tvMPLEksY);
		tvEks = (TextView) findViewById(R.id.tvMPLEks);
		tvT0 = (TextView) findViewById(R.id.tvMPLT0);
		tvskj = (TextView) findViewById(R.id.tvMPLskj);

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

					tvN.setText("");
					tvK.setText("");
					tvEksY.setText("");
					tvEks.setText("");
					tvT0.setText("");
					tvskj.setText("");

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