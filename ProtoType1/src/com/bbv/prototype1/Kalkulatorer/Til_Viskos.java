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

public class Til_Viskos extends Basic_Calc {
	
	Toast toast;
	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bViskosClear;
	final int updateButtonID = R.id.bViskosUpdate;
	final int layout = R.layout.calc_viskositet_tilsynelatende;
	
	final int[] IDs = {R.id.etViskosTil,  R.id.etRPM, R.id.etRPM};
	
	public final static int THETA_INDEX = 0, RPM_INDEX = 1, TIL_VISK_INDEX = 2;
	
	public Til_Viskos(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	
	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {
		/**
		 * This method calculates the expression according to which field is
		 * left blank.
		 * 
		 * @param variableToCalculate
		 *            The index of the variable to be calculated { @value
		 *            #THETA_INDEX } { @value #RPM_INDEX } { @value
		 *            #TIL_VISK_INDEX }
		 */

		float theAnswer = 0;
		switch (variableToCalculate) {
		case THETA_INDEX:// theta
			theAnswer = (fieldStatuses[2] * fieldStatuses[1]) / 300;
			break;
		case RPM_INDEX:// rpm
			theAnswer = (float) ((300.0 * fieldStatuses[0]) / fieldStatuses[2]);
			break;
		case TIL_VISK_INDEX:// tilvisk
			theAnswer = (float) ((300.0 * fieldStatuses[0]) / fieldStatuses[1]);
			break;
		}
		if (Float.isInfinite(theAnswer) || Float.isNaN(theAnswer)) {
			Log.println(Log.ERROR, "calc", "Til_Viskos tried to divide by 0!");
			try {
				toast.getView().isShown(); // true if visible
				toast.setText("You can't divide by 0!");
			} catch (Exception e) { // invisible if exception
				toast = Toast.makeText(getContext(), "You can't divide by 0!",
						Toast.LENGTH_SHORT);
			}
			toast.show();
			return "";
		}
		if (theAnswer != 0)
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
						try {
							if (Float.parseFloat(textFields[i].getText()
									.toString()) == 0.0)
								textFields[i].setText("");
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
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
				try {
					if (Float.parseFloat(_fieldsString) != 0.0) {
						_textFieldsStatus[indexOfCurrentField] = 1;
					}
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
			}
		} else {
			if (_textFieldsStatus[indexOfCurrentField] == 1) {
				if (focusStatus == false) {
					float number = 0;
					try {
						number = Float.parseFloat(_fieldsString);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}

					if (_fieldsString.contentEquals("")) {
						_textFieldsStatus[indexOfCurrentField] = 0;
						Enabeling(textFields);
					} else if (number == 0.0) {
						_textFieldsStatus[indexOfCurrentField] = 0;
						textFields[indexOfCurrentField].setText("");
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