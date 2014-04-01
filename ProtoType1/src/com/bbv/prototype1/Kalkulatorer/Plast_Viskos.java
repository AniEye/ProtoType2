package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Plast_Viskos extends Basic_Calc {

	final int layout = R.layout.calc_viskositet_plastisk;

	final static int[] IDs = { R.id.etViskosPlas, R.id.etPlasViskTheta600,
			R.id.etPlasviskTheta300 };

	public final static int PV_INDEX = 0, T6_INDEX = 1, T3_INDEX = 2;

	public Plast_Viskos(Context context) {
		super(context, IDs);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float PV = fieldStatuses[0];
		float T6 = fieldStatuses[1];
		float T3 = fieldStatuses[2];

		float theAnswer = 0;
		switch (variableToCalculate) {
		case PV_INDEX:
			if (checkForNullValues(T6, T3) == false)
				return "";
			theAnswer = T6 - T3;
			break;
		case T6_INDEX:
			if (checkForNullValues(PV, T3) == false)
				return "";
			theAnswer = PV + T3;
			break;
		case T3_INDEX:
			if (checkForNullValues(T6, PV) == false)
				return "";
			theAnswer = T6 - PV;
			break;
		}
		if(checkForDivisionErrors(theAnswer) == true)
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

	
}