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

public class CaOHInnhold extends Basic_Calc {

	final int layout = R.layout.calc_caoh_innhold;

	// Variables used for calculation
	float Mp;
	float CaOH2;
	
	final static int[] IDs = { R.id.etCaOHMp, // Textfield of R olje
			R.id.etCaOHCaOH // Textfield of CaOH2
	};

	public final static int Mp_INDEX = 0, CaOH_INDEX=1; //Mp_INDEX is used for calculating Mp, and CaOH_INDEX is used to calculate CaOH

	public CaOHInnhold(Context context) {
		super(context, IDs);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		Mp = fieldStatuses[0];
		CaOH2 = fieldStatuses[1];

		switch (variableToCalculate) {

		case Mp_INDEX:
			
			if (checkForNullValues(CaOH2) == false || checkForNegativeValues(CaOH2) == false)
				return "";
			Mp = CaOH2 / 1.3f;
			return String.format(THREE_DECIMALS, Mp);

		case CaOH_INDEX:
			
			if (checkForNullValues(Mp) == false || checkForNegativeValues(Mp) == false)
				return "";

			CaOH2 = Mp * 1.3f;
			return String.format(THREE_DECIMALS, CaOH2);
		}
		Log.println(Log.ERROR, "calc", "Something went very wrong in " + getClass().getName());
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


	public float getMp() {
		return Mp;
	}

	public float getCaOH2() {
		return CaOH2;
	}

}