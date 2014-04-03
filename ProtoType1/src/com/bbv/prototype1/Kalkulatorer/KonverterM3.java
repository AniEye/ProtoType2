package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bbv.prototype1.R;

public class KonverterM3 extends Basic_Calc {

	final static int layout = R.layout.calc_kvadratmeter;

	TextView tvKonvertert;

	final static int[] IDs = { R.id.etKMgram, R.id.etKMml, R.id.etKMslamvolum };

	/**
	 * ML_INDEX called when ml-field is left blank, which means that gram and
	 * slamvolum is inputed. GRAM_INDEX called when gram-field is left blank,
	 * which means that ml and slamvolum is inputed. SLAMVOLUM_INDEX called when
	 * slamvekt-field is left blank, which means that gram and ml is inputed,
	 * and this should result in error message
	 */
	public final static int GRAM_INDEX = 0, ML_INDEX = 1, SLAMVOLUM_INDEX = 2;

	public KonverterM3(Context context) {
		super(context, IDs, layout);

	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {
		
		float gram = fieldStatuses[0];
		float ml = fieldStatuses[1];
		float slamvolum = fieldStatuses[2];

		Spannable theAnswer = null;
		String calcAnswer;
		
		switch (variableToCalculate) {
		case GRAM_INDEX:
			
			if(checkForNegativeValues(ml, slamvolum) == false || checkForNullValues(ml, slamvolum) == false)
				return "";
			
			calcAnswer = String.format(NO_DECIMALS, (ml/slamvolum)*30*Math.pow(10, 3));
			theAnswer = (Spannable) Html.fromHtml(calcAnswer + " L per 30 m<sup><small>3</small></sup>");
			break;
		case ML_INDEX:
			
			if(checkForNegativeValues(gram, slamvolum) == false || checkForNullValues(gram, slamvolum) == false)
				return "";
			
			calcAnswer = String.format(NO_DECIMALS, (gram/slamvolum)*30*Math.pow(10, 3));
			theAnswer = (Spannable) Html.fromHtml(calcAnswer + " kg per 30 m<sup><small>3</small></sup>");
			break;
		case SLAMVOLUM_INDEX:
			showToast("Du kan bare legge inn enten gram eller ml, ikke begge!", Toast.LENGTH_LONG);
			break;
			
		default:
			Log.e("calc", "A wrong calculation index was used in " + getClass().getName());
			theAnswer = (Spannable) Html.fromHtml("");
			break;
		}
		
		tvKonvertert.setText(theAnswer);
		return "X";

	}

	@Override
	protected void initializeMethod() {
		// TODO Auto-generated method stub
		super.initializeMethod();
		
		tvKonvertert = (TextView) findViewById(R.id.tvKMsvar);
	}

}