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
import android.widget.Toast;

public class MasseOgVolumBalanse extends Basic_Calc {

	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bMOVBClear;
	final int updateButtonID = R.id.bMOVBUpdate;
	final int layout = R.layout.calc_masseogvolumbalanse;

	public final static int Vv_INDEX = 0, V1_INDEX = 1, p2_INDEX = 2,
			Pv_INDEX = 3, p1_INDEX = 4;

	final int[] IDs = { R.id.etMOVBVv, R.id.etMOVBV1, R.id.etMOVBP2,
			R.id.etMOVBPV, R.id.etMOVBP1 };

	public MasseOgVolumBalanse(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float vv = fieldStatuses[0], v1 = fieldStatuses[1], p2 = fieldStatuses[2], pv = fieldStatuses[3], p1 = fieldStatuses[4];

		float theAnswer = 0;

		switch (variableToCalculate) {

		case Vv_INDEX:

			if (checkForNullValues(v1, p2, pv, p1) == false)
				return "";

			Log.println(Log.DEBUG, "calc", "P1 = " + p1 + "\nP2 = " + p2);
			theAnswer = v1 * ((p2 - p1) / (pv - p2));
			Log.println(Log.DEBUG, "calc", "The answer = " + theAnswer);
			break;
		case V1_INDEX:

			if (checkForNullValues(vv, p2, pv, p1) == false)
				return "";

			theAnswer = vv / ((p2 - p1) / (pv - p2));
			break;
		case p2_INDEX:

			if (checkForNullValues(v1, vv, pv, p1) == false)
				return "";

			theAnswer = (((vv * pv) / v1) + p1) / (1 + (vv / v1));
			break;
		case p1_INDEX:

			if (checkForNullValues(v1, p2, pv, vv) == false)
				return "";
			theAnswer = p2 - (vv * (pv - p2) / v1);

			break;
		case Pv_INDEX:

			if (checkForNullValues(v1, p2, vv, p1) == false)
				return "";
			theAnswer = ((v1 * (p2 - p1) / vv) + p2);

			break;
		}

		if (checkForDivisionErrors(theAnswer) == false)
			return "";

		Log.println(Log.INFO, "calc", "Returning " + theAnswer);
		return String.format("%.3f", theAnswer);

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