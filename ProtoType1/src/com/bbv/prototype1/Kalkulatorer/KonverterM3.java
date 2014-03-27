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

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bKMClear;
	final int updateButtonID = R.id.bKMUpdate;
	final int layout = R.layout.calc_kvadratmeter;

	TextView tvKonvertert;

	final int[] IDs = { R.id.etKMgram, R.id.etKMml, R.id.etKMslamvolum };

	/**
	 * ML_INDEX called when ml-field is left blank, which means that gram and
	 * slamvolum is inputed. GRAM_INDEX called when gram-field is left blank,
	 * which means that ml and slamvolum is inputed. SLAMVOLUM_INDEX called when
	 * slamvekt-field is left blank, which means that gram and ml is inputed,
	 * and this should result in error message
	 */
	public final static int GRAM_INDEX = 0, ML_INDEX = 1, SLAMVOLUM_INDEX = 2;

	public KonverterM3(Context context) {
		super(context);
		CreateListeners();
		Initialize();
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
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		tvKonvertert = (TextView) findViewById(R.id.tvKMsvar);
		
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

					tvKonvertert.setText("");

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

}