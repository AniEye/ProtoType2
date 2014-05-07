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

	final static int layout = R.layout.calc_total_hardhet;

	// Variables used for calculation
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

	final static int[] textViewIDs = { R.id.tvTotHardCaCo, // textview of Total
															// hardhet
			R.id.tvTotHardCa2, // textview of Kalsium hardhet
			R.id.tvTotHardMg2 // textview of Magnesium hardhet
	};

	final static int[] editTextIDs = { R.id.etTotHardVEDTACaCO3, // Textfield of
																	// V EDTA
			R.id.etTotHardVCa2, // Textfield of V filtrat
			R.id.etTotHardVF, // Textfield of Volum filtrat
			R.id.etTotHardHideThis, // Textfield - Will be disabled
	};

	/**
	 * This is the only value that should be calculated in this calculator
	 */
	public final static int KEY_INDEX = 3; // Only index value should be
											// calculated

	public TotalHardhet(Context context) {
		super(context, editTextIDs, textViewIDs, layout);

	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		V_EDTA_CaCO3 = fieldStatuses[0];
		V_EDTA_Ca2 = fieldStatuses[1];
		V_filtrat = fieldStatuses[2];

		switch (variableToCalculate) {

		default:
			Log.println(
					Log.ERROR,
					"calc",
					"Tried to calculate anything other than the key value, and this should not happen! "
							+ this.getClass().getName());
			break;
		case KEY_INDEX:

			Log.println(Log.INFO, "calc", "Setting textviews in "
					+ this.getClass().getName() + "!");

			float[] valuesToCheck = { V_EDTA_CaCO3, V_EDTA_Ca2, V_filtrat };
			if (checkForNullValues(valuesToCheck) == false
					|| checkForNegativeValues(valuesToCheck) == false)
				return "";

			V_EDTA = V_EDTA_CaCO3 - V_EDTA_Ca2;
			totHardCaCo3 = (EDTA * V_EDTA_CaCO3 * M_CaCO3 * 1000) / V_filtrat;
			totHardCa2 = (EDTA * V_EDTA_Ca2 * M_Ca2 * 1000) / V_filtrat;

			if (V_EDTA < 0)
				totHardMg2 = 0;
			else
				totHardMg2 = (EDTA * V_EDTA * M_Mg2 * 1000) / V_filtrat;

			if (checkForDivisionErrors(totHardCa2, totHardCaCo3, totHardMg2) == false)
				return "";

			textviews[0].setText(String.format(THREE_DECIMALS, totHardCaCo3)
					+ " " + "[mg/l]");
			textviews[1].setText(String.format(THREE_DECIMALS, totHardCa2)
					+ " " + "[mg/l]");
			textviews[2].setText(String.format(THREE_DECIMALS, totHardMg2)
					+ " " + "[mg/l]");

			return String.format(THREE_DECIMALS, totHardCaCo3);

		}

		return "";

	}

	/**
	 * 
	 * @return Returns the calculated value of CaCo3
	 */
	public float getTotHardCaCo3() {
		return totHardCaCo3;
	}

	/**
	 * 
	 * @return Returns the calculated value of Ca2
	 */
	public float getTotHardCa2() {
		return totHardCa2;
	}

	/**
	 * 
	 * @return Returns the calculated value of Mg2
	 */
	public float getTotHardMg2() {
		return totHardMg2;
	}

	/**
	 * 
	 * @return Returns the calculated value of VEDTA
	 */
	public float getVEDTA() {
		return V_EDTA;
	}

}