package com.bbv.prototype1.Kalkulatorer;

import junit.framework.Assert;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bbv.prototype1.R;
import com.bbv.prototype1.Tables.Table_3_1;

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

	/**
	 * Pp_INDEX is the only variable to be calculated in this calculator
	 */
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

			Vsf = PfAndVsf[0];
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Vsf to be "
					+ Vsf);

			Vs = calcVs(Vsf, Fw);
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Vs to be "
					+ Vs);

			tvPf.setText(String.format(THREE_DECIMALS, Pf));
			tvVsf.setText(String.format(THREE_DECIMALS, Vsf) + " [volum%]");
			tvVs.setText(String.format(THREE_DECIMALS, Vs) + " [volum%]");

			theAnswer = ((100f * Pm) - (Pf * (Vv + Vsf) + Po * Vo))
					/ (Vfs - Vs);

			if (checkForDivisionErrors(theAnswer) == false)
				return "";

			Log.println(Log.INFO, "calc",
					"SpessTetthet calculated an answer! It was " + theAnswer);

		} else {
			Log.println(
					Log.ERROR,
					"calc",
					"Spess_Tetthet tried to calculate anything other than Pp, and this should not happen!"
							+ "\nIgnore if running JUnit tests");
		}

		if (theAnswer != 0) {
			tvPp.setText(String.format(THREE_DECIMALS, theAnswer));
			return String.format(THREE_DECIMALS, theAnswer);
		}

		else
			return "";
	}

	/**
	 * Calculates the Pf and Vsf variables based on table 3.1 in
	 * "�vinger i Bore- og br�nnteknikk"
	 * 
	 * If cl is out of bounds for the table, a toast will be displayed, and Vfs
	 * and Pf will be set to 0
	 * 
	 * @param cl - Value of KloridInnhold
	 * @return float array with Vsf in place 0, and Pf in place 1
	 */
	public float[] calcPfOrVsf(float cl) {

		Table_3_1 table = new Table_3_1(cl);
		float Pf = table.getPf();
		float Vsf = table.getVsf();

		if (Pf == -1 || Vsf == -1) {
			// Catches out of bounds exception
			String message = getResources().getString(R.string.OutOfBounds);
			showToast(message);
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