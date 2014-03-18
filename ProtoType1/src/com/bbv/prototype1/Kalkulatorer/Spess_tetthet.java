package com.bbv.prototype1.Kalkulatorer;

import junit.framework.Assert;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bbv.prototype1.R;

public class Spess_tetthet extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bSpessTettClear;
	final int updateButtonID = R.id.bSpessTettUpdate;
	final int layout = R.layout.calc_spesifikktetthet;

	TextView tvPp;
	TextView tvPf;
	TextView tvVs;
	TextView tvVsf;

	final int[] IDs = { R.id.etSpessTettPp, R.id.etSpessTettCL,
			R.id.etSpessTettPm, R.id.etSpessTettPo, R.id.etSpessTettVv,
			R.id.etSpessTettVo, R.id.etSpessTettFw, R.id.etSpessTettVfs };

	public final static int Pp_INDEX = 0;

	public Spess_tetthet(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float Cl = fieldStatuses[1];
		float Pm = fieldStatuses[2];
		float Po = fieldStatuses[3];
		float Vv = fieldStatuses[4];
		float Vo = fieldStatuses[5];
		float Fw = fieldStatuses[6];
		float Vfs = fieldStatuses[7];

		float Pf = 0, Vs = 0, Vsf = 0;

		float theAnswer = 0;

		if (variableToCalculate == Pp_INDEX) {

			float[] PfAndVsf = calcPfOrVsf(Cl);

			Pf = PfAndVsf[1];
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Pf to be "
					+ Pf);
			tvPf.setText(String.format("%.3f", Pf));

			Vsf = PfAndVsf[0];
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Vsf to be "
					+ Vsf);
			tvVsf.setText(String.format("%.3f", Vsf));

			Vs = calcVs(Vsf, Fw);
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Vs to be "
					+ Vs);
			tvVs.setText(String.format("%.3f", Vs));

			if (Pf == 0 || Vsf == 0 || Vs == 0) {
				Log.println(Log.INFO, "calc",
						"SpessTetthet got a value of Cl not between 5000 and 186650");
			} else if (Vfs == Vs) { // Catches dividing with 0 error
				showToast("Vfs is equal to Vs! Can't divide with 0!");
				Log.println(Log.ERROR, "calc",
						"Spess_Tetthet got a dividing with 0 error!"
								+ "\nIgnore if running JUnit tests");
				return "";
			} else {
				theAnswer = ((100f * Pm) - (Pf * (Vv + Vsf) + Po * Vo))
						/ (Vfs - Vs);
				Log.println(Log.INFO, "calc",
						"SpessTetthet calculated an answer! It was "
								+ theAnswer);
			}

		} else {
			Log.println(
					Log.ERROR,
					"calc",
					"Spess_Tetthet tried to calculate anything other than Pp, and this should not happen!"
							+ "\nIgnore if running JUnit tests");
		}

		if (theAnswer != 0){
			tvPp.setText(String.format("%.3f", theAnswer));
			return String.format("%.3f", theAnswer);
		}
		
		else
			return "";
	}

	public float[] calcPfOrVsf(float cl) {
		/**
		 * Calculates the Pf variable based on table 3.1 in
		 * "Øvinger i Bore- og brønnteknikk"
		 */
		Table_3_1 table = new Table_3_1(cl);
		float Pf = table.getPf();
		float Vsf = table.getVsf();

		if (Pf == -1 || Vsf == -1) {
			// Catches out of bounds exception
			showToast("Cl must be over 5000 and less than 186650!");
			return new float[] { 0, 0 };
		} else {
			return new float[] { Vsf, Pf };
		}

	}

	public float calcVs(float vsf, float fw) {
		return vsf * fw;
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		tvPp = (TextView) findViewById(R.id.tvSpessTettPp);
		tvPf = (TextView) findViewById(R.id.tvSpessTettPf);
		tvVs = (TextView) findViewById(R.id.tvSpessTettVs);
		tvVsf = (TextView) findViewById(R.id.tvSpessTettVsf);

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
					tvPp.setText("");
					tvVs.setText("");
					tvVsf.setText("");
					tvPf.setText("");

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