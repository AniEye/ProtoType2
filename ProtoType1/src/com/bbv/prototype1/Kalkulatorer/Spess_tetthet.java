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

	Toast toast = new Toast(this.getContext());

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bSpessTettClear;
	final int updateButtonID = R.id.bSpessTettUpdate;
	final int layout = R.layout.calc_spesifikktetthet;

	TextView tvPf;
	TextView tvVs;
	TextView tvVsf;

	final int[] IDs = { R.id.etSpessTettPp, R.id.etSpessTettCL, R.id.etSpessTettPm,
			R.id.etSpessTettPo, R.id.etSpessTettVv, R.id.etSpessTettVo,
			R.id.etSpessTettFw, R.id.etSpessTettVfs};

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

			Pf = PfAndVsf[0];
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Pf to be " + Pf);
			tvPf.setText(String.format("%.3f", Pf));

			Vsf = PfAndVsf[1];
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Vsf to be " + Vsf);
			tvVsf.setText(String.format("%.3f", Vsf));

			Vs = calcVs(Vsf, Fw);
			Log.println(Log.DEBUG, "calc", "Spesstetthet calculated Vs to be " + Vs);
			tvVs.setText(String.format("%.3f", Vs));

			if (Pf == 0 || Vsf == 0 || Vs == 0) {
				Log.println(Log.INFO, "calc",
						"SpessTetthet got a value of Cl not between 5000 and 86656");
			} else if (Vfs == Vs) { //Catches dividing with 0 error
				showToast("Vfs is equal to Vs! Can't divide with 0!");
				Log.println(Log.ERROR,
				"calc",
				"Spess_Tetthet got a dividing with 0 error!"
						+ "\nIgnore if running JUnit tests");
				return "";
			}
			else{
				theAnswer = ((100f * Pm) - (Pf * (Vv + Vsf) + Po * Vo))
						/ (Vfs - Vs);
				Log.println(Log.INFO,"calc" , "SpessTetthet calculated an answer! It was " + theAnswer);
			}
				

		} else {
			Log.println(
					Log.ERROR,
					"calc",
					"Spess_Tetthet tried to calculate anything other than Pp, and this should not happen!"
							+ "\nIgnore if running JUnit tests");
		}

		if (theAnswer != 0)
			return String.format("%.3f", theAnswer);
		else
			return "";
	}

	public float[] calcPfOrVsf(float cl) {
		/**
		 * Calculates the Pf variable based on table 3.1 in
		 * "Øvinger i Bore- og brønnteknikk"
		 */

		float[] Cl_values = { 5000f, 10000f, 20000f, 30000f, 40000f, 60000f, 80000f,
				100000f, 120000f, 140000f, 160000f, 180000f, 186650f };
		float[] Pf_values = { 0.3f, 0.6f, 1.2f, 1.8f, 2.3f, 3.4f, 4.5f, 5.7f,
				7.0f, 8.2f, 9.5f, 10.8f, 11.4f };
		float[] Vsf_values = { 1.004f, 1.010f, 1.021f, 1.032f, 1.043f, 1.065f,
				1.082f, 1.098f, 1.129f, 1.149f, 1.170f, 1.194f, 1.197f };

		float Pf = 0;
		float Vsf = 0;

		if (cl < 5000) { // Cl cannot be less than 5000
			showToast("Cl må være mer enn 5000!");
		} else if (cl > 186650) { // Cl cannot be more than 186650
			showToast("Cl må være mellom 5000 og 186650!");
		} else {

			for (int i = 0; i < Cl_values.length; i++) {

				if (cl == Cl_values[i]) {
					Pf = Pf_values[i];
					Vsf = Vsf_values[i];
					break; // Found values, no need to continue
				} else if (cl < Cl_values[i]) {
					
					Pf = Pf_values[i - 1]
							+ ((Pf_values[i] - Pf_values[i - 1])
							* ((cl - Cl_values[i - 1]) / (Cl_values[i] - Cl_values[i - 1])));
					
					Vsf = Vsf_values[i - 1]
							+ ((Vsf_values[i] - Vsf_values[i - 1])
							* ((cl - Cl_values[i - 1]) / (Cl_values[i] - Cl_values[i - 1])));
					
					break; // Found values, no need to continue

				}

			}

			if (Pf == 0 || Vsf == 0)
				throw new Error("Something went severly wrong in SpessTetthet");

		}

		float[] returnValue = { Pf, Vsf };
		return returnValue;

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

		textFields[0].setEnabled(false);

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

					textFields[0].setEnabled(false); // Sets the field for Pp to
														// not be enabled for
														// input
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
						textFields[0].setEnabled(false); // Sets the field for
															// Pp to not be
															// enabled for input
					} else if (number == 0.0) {
						_textFieldsStatus[indexOfCurrentField] = 0;
						textFields[indexOfCurrentField].setText("");
						Enabeling(textFields);
						textFields[0].setEnabled(false); // Sets the field for
															// Pp to not be
															// enabled for input
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

	private void showToast(String message){
		try {
			toast.getView().isShown(); // true if visible
			toast.setText(message);
		} catch (Exception e) { // invisible if exception
			toast = Toast.makeText(getContext(),
					message, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

}