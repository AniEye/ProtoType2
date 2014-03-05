package com.bbv.prototype1.Kalkulatorer;

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

	final int[] IDs = { R.id.etSpessTettPp, R.id.etSpessTettPm,
			R.id.etSpessTettPo, R.id.etSpessTettVv, R.id.etSpessTettVo,
			R.id.etSpessTettFw, R.id.etSpessTettVfs, R.id.etSpessTettCL };

	public final static int Pp_INDEX = 0;

	public Spess_tetthet(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float Pp = fieldStatuses[0];
		float Pm = fieldStatuses[1];
		float Po = fieldStatuses[2];
		float Vv = fieldStatuses[3];
		float Vo = fieldStatuses[4];
		float Fw = fieldStatuses[5];
		float Vfs = fieldStatuses[6];
		float Cl = fieldStatuses[7];

		float Pf = 0, Vs = 0, Vsf = 0;

		float theAnswer = 0;

		if (variableToCalculate == Pp_INDEX) {

			float[] PfAndVsf = calcPfOrVsf(Cl);

			Pf = PfAndVsf[0];
			tvPf.setText(String.format("%.3f", Pf));

			Vsf = PfAndVsf[1];
			tvVsf.setText(String.format("%.3f", Vsf));

			Vs = calcVs(Vsf, Fw);
			tvVs.setText(String.format("%.3f", Vs));

			if (Pf == 0 || Vsf == 0 || Vs == 0) {
				Log.println(Log.INFO, "calc",
						"SpessTetthet got a value of Cl not between 5000 and 86656");
			} else
				theAnswer = ((100 * Pm) - (Pf * (Vv + Vsf) + Po * Vo))
						/ (Vfs - Vs);

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

		float Pf = 0;
		float Vsf = 0;

		if (cl < 5000) {
			try {
				toast.getView().isShown(); // true if visible
				toast.setText("Cl må være mer enn 5000!");
			} catch (Exception e) { // invisible if exception
				toast = Toast.makeText(getContext(),
						"Cl må være mer enn 5000!", Toast.LENGTH_SHORT);
			}
			toast.show();
		} else if (cl < 10000) {
			Pf = (float) (0.3 + (0.6 - 0.3) * ((cl - 5000) / (10000 - 5000)));
			Vsf = (float) (1.004f + (1.010f - 1.004f)
					* ((cl - 5000) / (10000 - 5000)));
		} else if (cl < 20000) {
			Pf = (float) (0.6 + (1.2 - 0.6) * ((cl - 10000) / (20000 - 10000)));
			Vsf = (float) (1.010 + (1.021 - 1.010)
					* ((cl - 10000) / (20000 - 10000)));
		} else if (cl < 30000) {
			Pf = (float) (1.2 + (1.8 - 1.2) * ((cl - 20000) / (30000 - 20000)));
			Vsf = (float) (1.021 + (1.032 - 1.021)
					* ((cl - 20000) / (30000 - 20000)));
		} else if (cl < 40000) {
			Pf = (float) (1.8 + (2.3 - 1.8) * ((cl - 30000) / (40000 - 30000)));
			Vsf = (float) (1.032 + (1.043 - 1.032)
					* ((cl - 30000) / (40000 - 30000)));
		} else if (cl < 60000) {
			Pf = (float) (2.3 + (3.4 - 2.3) * ((cl - 40000) / (60000 - 40000)));
			Vsf = (float) (1.043 + (1.065 - 1.043)
					* ((cl - 40000) / (60000 - 40000)));
		} else if (cl < 80000) {
			Pf = (float) (3.4 + (4.5 - 3.4) * ((cl - 60000) / (80000 - 60000)));
			Vsf = (float) (1.065 + (1.082 - 1.065)
					* ((cl - 60000) / (80000 - 60000)));
		} else if (cl < 100000) {
			Pf = (float) (4.5 + (5.7 - 4.5) * ((cl - 80000) / (100000 - 80000)));
			Vsf = (float) (1.082 + (1.098 - 1.082)
					* ((cl - 80000) / (100000 - 80000)));
		} else if (cl < 120000) {
			Pf = (float) (5.7 + (7.0 - 5.7)
					* ((cl - 100000) / (120000 - 100000)));
			Vsf = (float) (1.098 + (1.129 - 1.098)
					* ((cl - 100000) / (120000 - 100000)));
		} else if (cl < 140000) {
			Pf = (float) (7.0 + (8.2 - 7.0)
					* ((cl - 120000) / (140000 - 120000)));
			Vsf = (float) (1.129 + (1.149 - 1.129)
					* ((cl - 120000) / (140000 - 120000)));
		} else if (cl < 160000) {
			Pf = (float) (8.2 + (9.5 - 8.2)
					* ((cl - 140000) / (160000 - 140000)));
			Vsf = (float) (1.149 + (1.170 - 1.149)
					* ((cl - 140000) / (160000 - 140000)));
		} else if (cl < 180000) {
			Pf = (float) (9.5 + (10.8 - 9.5)
					* ((cl - 160000) / (180000 - 160000)));
			Vsf = (float) (1.170 + (1.194 - 1.170)
					* ((cl - 160000) / (180000 - 160000)));
		} else if (cl <= 186650) {
			Pf = (float) (10.8 + (11.4 - 10.8)
					* ((cl - 180000) / (186650 - 180000)));
			Vsf = (float) (1.194 + (1.197 - 1.194)
					* ((cl - 180000) / (186650 - 180000)));
		} else {
			try {
				toast.getView().isShown(); // true if visible
				toast.setText("Cl må være mellom 5000 og 186650!");
			} catch (Exception e) { // invisible if exception
				toast = Toast
						.makeText(getContext(),
								"Cl må være mellom 5000 og 186650!",
								Toast.LENGTH_SHORT);
			}
			toast.show();
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

}