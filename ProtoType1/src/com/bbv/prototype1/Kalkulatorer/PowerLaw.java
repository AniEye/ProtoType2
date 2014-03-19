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

public class PowerLaw extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bPLClear;
	final int updateButtonID = R.id.bPLUpdate;
	final int layout = R.layout.calc_powerlaw;

	TextView tvN;
	TextView K_notPA;
	TextView K_PA;
	TextView tvskj;
	TextView tvEksponent;

	final int[] IDs = { R.id.etPowerLawT100, R.id.etPowerLaw600,
			R.id.etPowerLawN };

	public final static int T1_INDEX = 0, T6_INDEX = 1, N_INDEX = 2;

	public PowerLaw(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float T1 = fieldStatuses[0];
		float T6 = fieldStatuses[1];
		float N;
		float K;
		float Y;
		float T;

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

			N = calcN(T1, T6);
			K = calcK(T6, N);
			Y = calcY(T6);
			T = calcT(K, Y, N);

			float[] testFloats = {N, K, Y, T };
			if (checkForDivisionErrors(testFloats) == false)
				return "";

			Log.println(Log.INFO, "calc", "Setting textviews in Powerlaw!");

			String _N = String.format("%.3f", N);
			String _K = String.format("%.3f", K);
			String _KPA = String.format("%.3f", K*0.511f);
			String _Y = String.format("%.3f", Y);
			String _T = String.format("%.3f", T);
			
			tvN.setText(_N);
			K_notPA.setText(_K);
			tvskj.setText(_Y);
			K_PA.setText(_KPA);
			tvEksponent.setText(_T);

			return String.format("%.3f", N);

		}

		return "";

	}

	public float calcN(float T100, float T6) {
		return (float) (Math.log10(T100/T6)/Math.log10(170/10));
	}

	public float calcY(float T6) {
		return T6 * 1.7023f;
	}

	public float calcT(float K, float Y, float N) {
		return (float) (K*Math.pow(Y, N));
	}

	public float calcK(float T6, float n) {
		return (float) (T6 / Math.pow(10, n));
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		textFields[2].setVisibility(GONE);

		tvN = (TextView) findViewById(R.id.tvPLN);
 		K_PA = (TextView) findViewById(R.id.tvPLPA);
		K_notPA = (TextView) findViewById(R.id.tvPLNOTPA);
		tvEksponent = (TextView) findViewById(R.id.tvPLEks);
		tvskj = (TextView) findViewById(R.id.tvPLskj);

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
					K_PA.setText("");
					K_notPA.setText("");
					tvEksponent.setText("");
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