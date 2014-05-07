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
import com.bbv.prototype1.Tables.Table_3_1;

public class KloridInnholdIVannfasen extends Basic_Calc {

	final static int layout = R.layout.calc_klorid_innhold_i_vannfasen;

	// Variables used for calculation
	float V_AgNO3;
	float Fv;
	float AgNO3 = 0.282f;
	float MCl = 35.45f;
	float MNaCl = 58.44f;
	float Pf;
	float CCl;
	float ClPPM;
	float NaClMG;
	float NaClPPM;

	final static int[] textviewIDs = { R.id.tvKIVFKIMG, R.id.tvKIVFKIPPM,
			R.id.tvKIVFNaCLMG, R.id.tvKIVFNaPPM };

	final static int[] IDs = {
			R.id.etKIVFVAgNO3, // Textfield of AgNO3
			R.id.etKIVFFv, // Textfield of filtrat
			R.id.etKIVFCCl // Textfield of CCl - Will be disabled for input
	};

	/**
	 * Calculator will only calculate CCl_index
	 */
	public final static int AgNO3_index = 0, VFiltrat_index = 1,
			CCl_index = 2;

	public KloridInnholdIVannfasen(Context context) {
		super(context, IDs, textviewIDs, layout);
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		V_AgNO3 = fieldStatuses[0];
		Fv = fieldStatuses[1];

		switch (variableToCalculate) {

		default:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate anything other than Key value, and this should not happen!");
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

			if (checkForNegativeValues(V_AgNO3, Fv) == false || checkForNullValues(V_AgNO3, Fv) == false)
				return "";
			
			if (Fv > 1 || Fv < 0)
			{
				showToast("Fw kan bare v�re mellom 0 og 1!");
				return "";
			}
			
			CCl = ((AgNO3 * V_AgNO3 * MCl * 1000) / Fv);

			Log.println(Log.DEBUG, "calc", "Value of CCl: " + CCl);
			Table_3_1 table = new Table_3_1(CCl);

			if (table.getPf() != -1)
				Pf = table.getPf();
			else {
				showToast("Volum AgNO3 gir en klorid innhold som ligger utfor tabell 3.1, vennligst bruk en lavere verdi");
				return "";
			}

			ClPPM = CCl / Pf;
			NaClMG = ((AgNO3 * V_AgNO3 * MNaCl * 1000) / Fv);
			NaClPPM = NaClMG / Pf;

			Log.println(Log.INFO, "calc", "Setting textviews in "
					+ this.getClass().getName() + "!");

			float[] testFloats = { CCl, ClPPM, NaClMG, NaClPPM };
			if (checkForDivisionErrors(testFloats) == false)
				return "";

			String _KloridMG = String.format(NO_DECIMALS, CCl)
					+ " " + getResources().getString(R.string.ClmgL);
			String _KloridPPM = String.format(NO_DECIMALS, ClPPM)
					+ " " + getResources().getString(R.string.Clppm);
			String _NaClMG = String.format(NO_DECIMALS, NaClMG)
					+ " " + getResources().getString(R.string.NaClmgL);
			String _NaClPPM = String.format(NO_DECIMALS, NaClPPM)
					+ " " + getResources().getString(R.string.NaClppm);

			textviews[0].setText(_KloridMG);
			textviews[1].setText(_KloridPPM);
			textviews[2].setText(_NaClMG);
			textviews[3].setText(_NaClPPM);

			return String.format(NO_DECIMALS, CCl);

		}

		return "";

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