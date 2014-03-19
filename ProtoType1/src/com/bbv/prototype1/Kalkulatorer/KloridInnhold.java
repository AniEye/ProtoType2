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

public class KloridInnhold extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bKIClear;
	final int updateButtonID = R.id.bKIUpdate;
	final int layout = R.layout.calc_klorid_innhold;
	
	TextView tvKloridML;
	TextView tvKloridPPM;
	TextView tvNaClMG;
	TextView tvNaClPPM;
	
	
	//Variables used for calculation
	float Cl;
	float V_AgNO3;
	float V_filtrat;
	float AgNO3 = 0.0282f;
	float MCl = 35.45f;
	float MNaCl = 58.44f;
	float Pf;
	float CCl;
	float ClPPM;
	float NaClMG;
	float NaClPPM;

	final int[] IDs = { 
			R.id.etKICl, //Textfield of Cl
			R.id.etKIVAgNO3, //Textfield of AgNO3
			R.id.etKIVF, //Textfield of filtrat
			R.id.etKICCl //Textfield of CCl - Will be disabled for input
			};

	public final static int Cl_index = 0, AgNO3_index = 1, VFiltrat_index = 2, CCl_index = 3;

	public KloridInnhold(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		Cl = fieldStatuses[0];
		V_AgNO3 = fieldStatuses[1];
		V_filtrat = fieldStatuses[2];

		switch (variableToCalculate) {

		case Cl_index:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate Cl, and this should not happen!");
			break;
		case AgNO3_index:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate volume of AgNO3, and this should not happen!");
			break;
		case VFiltrat_index:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate volume of VFiltrat, and this should not happen!");
			break;
		case CCl_index:

			if(Cl < 5000){
				String message = "Klor innhold kan ikke være mindre enn 5000!";
				showToast(message);
				return "";
			} else if (Cl > 188650){
				String message = "Klor innhold kan ikke være mer enn 188650!";
				showToast(message);
				return "";
			}		
			
			Table_3_1 table = new Table_3_1(Cl);
			
			if (table.getPf() != -1)
				Pf = table.getPf();
			else {
				Log.println(Log.ERROR, "calc", "Somehow there was an error with tables in KlorInnhold");
				return "";
			}
	
			
			CCl = ((AgNO3 * V_AgNO3 * MCl * 1000)/V_filtrat);
			ClPPM = CCl / Pf;
			NaClMG = ((AgNO3 * V_AgNO3 * MNaCl * 1000)/V_filtrat);
			NaClPPM = NaClMG/Pf;

			Log.println(Log.INFO, "calc", "Setting textviews in " + this.getClass().getName() + "!");
			
			float[] testFloats = {CCl, ClPPM, NaClMG, NaClPPM};
			if (checkForDivisionErrors(testFloats) == false)
				return "";

			String _KloridMG = String.format("%.3f", CCl);
			String _KloridPPM = String.format("%.3f", ClPPM);
			String _NaClMG = String.format("%.3f", NaClMG);
			String _NaClPPM = String.format("%.3f", NaClPPM);
			
			tvKloridML.setText(_KloridMG);
			tvKloridPPM.setText(_KloridPPM);
			tvNaClMG.setText(_NaClMG);
			tvNaClPPM.setText(_NaClPPM);

			return String.format("%.3f", CCl);

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

		textFields[3].setVisibility(GONE);

		tvKloridML = (TextView) findViewById(R.id.tvKIKIMG);
		tvKloridPPM = (TextView) findViewById(R.id.tvKIKIPPM);
		tvNaClMG = (TextView) findViewById(R.id.tvKINaCLMG);
		tvNaClPPM = (TextView) findViewById(R.id.tvKINaPPM);

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

					tvKloridML.setText("");
					tvKloridPPM.setText("");
					tvNaClMG.setText("");
					tvNaClPPM.setText("");

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
	
	public float getCl() {
		return Cl;
	}

	public float getV_AgNO3() {
		return V_AgNO3;
	}

	public float getAgNO3() {
		return AgNO3;
	}

	public float getMCl() {
		return MCl;
	}

	public float getMNaCl() {
		return MNaCl;
	}

	public float getPf() {
		return Pf;
	}

	public float getCCl() {
		return CCl;
	}

	public float getClPPM() {
		return ClPPM;
	}

	public float getNaClMG() {
		return NaClMG;
	}

	public float getNaClPPM() {
		return NaClPPM;
	}

}