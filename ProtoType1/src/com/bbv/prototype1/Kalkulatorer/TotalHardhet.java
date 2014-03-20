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
	
	//Variables used for calculation
	float totHardCaCo3;
	float totHardCa2;
	float totHardMg2;
	float V_EDTA_Ca2;
	float V_EDTA_CaCO3;
	float V_EDTA;
	float V_filtrat;
	float EDTA = 0.01f;
	float M_CaCO3 = 100.0869f;
	float M_Ca2 = 40.78f;
	float M_Mg2 = 24.3050f;
	
	final int[] textViewIDs = {
			R.id.tvTotHardCaCo, //textview of Total hardhet
			R.id.tvTotHardCa2, //textview of Kalsium hardhet
			R.id.tvTotHardMg2 //textview of Magnesium hardhet
	};

	final int[] editTextIDs = { 
			R.id.etTotHardVEDTACaCO3, //Textfield of V EDTA
			R.id.etTotHardVCa2, //Textfield of V filtrat
			R.id.etTotHardVF, //Textfield of Volum filtrat
			R.id.etTotHardHideThis, //Textfield - Will be disabled
			};

	/**
	 * This is the only value that should be calculated in this calculator
	 */
	public final static int KEY_INDEX = 3; //Only index value should be calculated

	public TotalHardhet(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		V_EDTA_CaCO3 = fieldStatuses[0];
		V_EDTA_Ca2 = fieldStatuses[1];
		V_filtrat = fieldStatuses[2];

		switch (variableToCalculate) {

		default: 
			Log.println(Log.ERROR, "calc",
					"Tried to calculate anything other than the key value, and this should not happen! " + this.getClass().getName());
			break;
		case KEY_INDEX:

			Log.println(Log.INFO, "calc", "Setting textviews in " + this.getClass().getName() + "!");
			
			float[] valuesToCheck = {V_EDTA_CaCO3, V_EDTA_Ca2,V_filtrat};
			if(checkForNullValues(valuesToCheck) == false || checkForNegativeValues(valuesToCheck) == false)
					return "";			

			V_EDTA = V_EDTA_CaCO3 - V_EDTA_Ca2;
			totHardCaCo3 = (EDTA*V_EDTA*M_CaCO3*1000)/V_filtrat;
			totHardCa2 = (EDTA*V_EDTA*M_Ca2*1000)/V_filtrat;
			
			if(V_EDTA < 0)
				totHardMg2 = 0;
			else
				totHardMg2 = (EDTA*V_EDTA*M_Mg2*1000)/V_filtrat;

			if(checkForDivisionErrors(totHardCa2,totHardCaCo3,totHardMg2) == false)
				return "";
			
			textviews[0].setText(String.format("%.3f", totHardCaCo3));
			textviews[1].setText(String.format("%.3f", totHardCa2));
			textviews[2].setText(String.format("%.3f", totHardMg2));
			
			return String.format("%.3f",totHardCaCo3);
			
		}

		return "";

	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[editTextIDs.length];
		textviews = new TextView[textViewIDs.length];
		_textFieldsStatus = new int[editTextIDs.length];

		for (int i = 0; i < editTextIDs.length; i++)
			textFields[i] = FindAndReturnEditText(editTextIDs[i], focChan);
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
					_textFieldsStatus = new int[editTextIDs.length];
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

				for (int i = 0; i < editTextIDs.length; i++) {
					if (v.getId() == editTextIDs[i])
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

	public float getTotHardCaCo3() {
		return totHardCaCo3;
	}

	public float getTotHardCa2() {
		return totHardCa2;
	}

	public float getTotHardMg2() {
		return totHardMg2;
	}
	public float getVEDTA(){
		return V_EDTA;
	}
	
}