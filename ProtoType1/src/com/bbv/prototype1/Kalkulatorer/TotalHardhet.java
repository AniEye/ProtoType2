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

public class TotalHardhet extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bTotHardClear;
	final int updateButtonID = R.id.bTotHardUpdate;
	final int layout = R.layout.calc_total_hardhet;
	
	TextView tvCaCo3;
	TextView tvCa2;
	TextView tvMg2;	
	
	//Variables used for calculation
	float CaCo3;
	float Ca2;
	float Mg2;
	float V_EDTA;
	float V_filtrat;
	float EDTA = 0.01f;
	float M_CaCO3 = 100.0869f;
	float M_Ca2 = 40.78f;
	float M_Mg2 = 24.3050f;

	final int[] IDs = { 
			R.id.etTotHardVEDTA, //Textfield of V EDTA
			R.id.etTotHardVF, //Textfield of V filtrat
			R.id.etTotHardHideThis, //Textfield of CaCO3 - Will be disabled
			};

	public final static int VEDTA_index = 0, VFiltrat_index = 1, CaCO3_index = 2;

	public TotalHardhet(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		V_EDTA = fieldStatuses[0];
		V_filtrat = fieldStatuses[1];

		switch (variableToCalculate) {

		case VFiltrat_index:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate Cl, and this should not happen!");
			break;
		case VEDTA_index:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate volume of AgNO3, and this should not happen!");
			break;
		case CaCO3_index:

			Log.println(Log.INFO, "calc", "Setting textviews in " + this.getClass().getName() + "!");
			
//			float[] testFloats = {CCl, ClPPM, NaClMG, NaClPPM};
//			boolean floatIsFine = true;
//			for (int i = 0; i < testFloats.length; i++) {
//				//Displays a toast saying that there was an error if any value returned NaN or infinity
//				if (testFloat(testFloats[i]) == false)
//				{
//					Log.println(Log.DEBUG, "calc", "Dividing with 0 error in " + this.getClass().getName());
//					return "";
//				}
//			}
//
//			String _KloridMG = String.format("%.3f", CCl);
//			String _KloridPPM = String.format("%.3f", ClPPM);
//			String _NaClMG = String.format("%.3f", NaClMG);
//			String _NaClPPM = String.format("%.3f", NaClPPM);
//			
//			tvKloridML.setText(_KloridMG);
//			tvKloridPPM.setText(_KloridPPM);
//			tvNaClMG.setText(_NaClMG);
//			tvNaClPPM.setText(_NaClPPM);
//
//			return String.format("%.3f", CCl);

		}

		return "";

	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		textFields[2].setVisibility(GONE);

		tvCa2 = (TextView) findViewById(R.id.tvKIKIMG);
		tvCaCo3 = (TextView) findViewById(R.id.tvKIKIPPM);
		tvMg2 = (TextView) findViewById(R.id.tvKINaCLMG);

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

					tvCa2.setText("");
					tvCaCo3.setText("");
					tvMg2.setText("");

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

	public float getCaCo3() {
		return CaCo3;
	}

	public float getCa2() {
		return Ca2;
	}

	public float getMg2() {
		return Mg2;
	}

	public float getV_EDTA() {
		return V_EDTA;
	}

	public float getV_filtrat() {
		return V_filtrat;
	}
	
}