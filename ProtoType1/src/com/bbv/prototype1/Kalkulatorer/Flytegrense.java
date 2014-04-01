package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Flytegrense extends Basic_Calc {

	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bFGClear;
	final int updateButtonID = R.id.bFGUpdate;
	final int layout = R.layout.calc_flytegrense;

	final int[] IDs = { R.id.etFG, R.id.etFGTheta300, R.id.etFGTheta600 };

	public final static int FG_INDEX = 0, T3_INDEX = 1, T6_INDEX = 2;

	public Flytegrense(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float FG = fieldStatuses[0];
		float T3 = fieldStatuses[1];
		float T6 = fieldStatuses[2];

		float theAnswer = 0;
		switch (variableToCalculate) {
		case FG_INDEX:

			if (checkForNullValues(T6, T3) == false)
				return "";


			theAnswer = (2 * T3) - T6;
			break;
		case T3_INDEX:
			if (checkForNullValues(T6, FG) == false)
				return "";
			theAnswer = (FG + T6) / 2;
			break;
		case T6_INDEX:
			if (checkForNullValues(FG, T3) == false)
				return "";
			theAnswer = (2 * T3) - FG;
			break;
		}

		if (checkForDivisionErrors(theAnswer) == false)
			return "";

		if (checkForNullValues(theAnswer) == true)
			return String.format("%.3f", theAnswer);
		else
			return "";
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

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

}