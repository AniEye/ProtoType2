package com.bbv.prototype1;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public abstract class Basic_Calc extends LinearLayout {

	Context cont;

	LinearLayout _linLay;

	public Basic_Calc(Context context) {
		super(context);
		cont = context;
	}

	protected abstract void Initialize();

	// the user should use this method for all the listeners that are used in
	// the class
	// extending Basic_Calc
	protected abstract void CreateListeners();

	protected LinearLayout setAndGetLinearLayout(int layoutID) {
		_linLay = (LinearLayout) LayoutInflater.from(cont).inflate(layoutID,
				this);
		return _linLay;
	}

	protected EditText FindAndReturnEditText(int id,
			OnFocusChangeListener aListener) {
		/**
		 * This is to set up the reference for a edittext
		 */
		EditText aField = (EditText) _linLay.findViewById(id);
		aField.setOnFocusChangeListener(aListener);

		return aField;
	}

	protected Button FindAndReturnButton(int id, OnClickListener aListener) {
		Button aButton = (Button) _linLay.findViewById(id);
		aButton.setOnClickListener(aListener);
		return aButton;
	}

	protected void Enabeling(EditText... _theEditTextFields) {
		for (int i = 0; i < _theEditTextFields.length; i++) {
			if (!_theEditTextFields[i].isEnabled()) {
				_theEditTextFields[i].setEnabled(true);
				_theEditTextFields[i].setText("");
			}
		}
	}

	protected abstract void updateRelevantResult();

	protected float[] getFloatVariables(EditText... fieldStatuses) {
		float[] returnFloatList = new float[fieldStatuses.length];
		for (int i = 0; i < fieldStatuses.length; i++) {
			try {
				returnFloatList[i] = Float.parseFloat(fieldStatuses[i]
						.getText().toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				returnFloatList[i] = 0;
			}
		}
		return returnFloatList;
	}

	public abstract String calculation(int editTextIndex,
			float... fieldStatuses);

	protected int theSum(int... fieldStatuses) {
		int sumOfEditedFields = 0;
		for (int i = 0; i < fieldStatuses.length; i++) {
			sumOfEditedFields += fieldStatuses[i];
		}
		return sumOfEditedFields;
	}

	protected void ResetFields(EditText... editTexts ){
		for(int i =0;i<editTexts.length;i++){
			editTexts[i].setText("");
			editTexts[i].setEnabled(true);
		}
	}

	{ // unused method at the moment, not working/finished

		/*
		 * protected void FocusChange(EditText[] theEditTextField, int
		 * TheCurrentFieldIndex, int[] _textFieldsStatus, boolean focusStatus,
		 * Basic_Calc trying) { String _fieldsString =
		 * theEditTextField[TheCurrentFieldIndex].getText() .toString();
		 * 
		 * if (theSum(_textFieldsStatus) < theEditTextField.length - 1) {
		 * Log.println(Log.ERROR, "FocusTry", "Entering and less than 2"); if
		 * (focusStatus == false && !_fieldsString.contentEquals("")) { try { if
		 * (Float.parseFloat(_fieldsString) != 0) {
		 * _textFieldsStatus[TheCurrentFieldIndex] = 1; } } catch
		 * (NumberFormatException e) { // TODO: handle exception } } } else {
		 * Log.println(Log.ERROR, "FocusTry", "Entering and more than 2"); if
		 * (_textFieldsStatus[TheCurrentFieldIndex] == 1) {
		 * Log.println(Log.ERROR, "FocusTry", "status = 1"); if (focusStatus ==
		 * false && _fieldsString.contentEquals("")) { try { if
		 * (Float.parseFloat(_fieldsString) == 0) { Log.println(Log.ERROR,
		 * "FocusTry", "if float parse = 0");
		 * _textFieldsStatus[TheCurrentFieldIndex] = 0;
		 * Enabeling(theEditTextField); } } catch (NumberFormatException e) {
		 * Log.println(Log.ERROR, "FocusTry", "if text = nothing");
		 * _textFieldsStatus[TheCurrentFieldIndex] = 0;
		 * Enabeling(theEditTextField); } } else if (focusStatus == false &&
		 * !_fieldsString.contentEquals("")) { Log.println(Log.ERROR,
		 * "FocusTry", "if there is content"); trying.updateRelevantResult(); }
		 * 
		 * } else { Log.println(Log.ERROR, "FocusTry",
		 * "more then 2 and status=0"); trying.updateRelevantResult();
		 * theEditTextField[TheCurrentFieldIndex].setEnabled(false); } }
		 * Log.println(Log.ERROR, "FocusTry", "getting out of here"); }
		 */
	}
}
