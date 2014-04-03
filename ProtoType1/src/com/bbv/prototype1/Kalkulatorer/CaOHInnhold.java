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

	final static int layout = R.layout.calc_caoh_innhold;

	// Variables used for calculation
	float Mp;
	float CaOH2;
	
	final static int[] IDs = { R.id.etCaOHMp, // Textfield of R olje
			R.id.etCaOHCaOH // Textfield of CaOH2
	};

	public final static int Mp_INDEX = 0, CaOH_INDEX=1; //Mp_INDEX is used for calculating Mp, and CaOH_INDEX is used to calculate CaOH

	public CaOHInnhold(Context context) {
		super(context, IDs, layout);
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


	public float getMp() {
		return Mp;
	}

	public float getCaOH2() {
		return CaOH2;
	}

}