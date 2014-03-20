package com.bbv.prototype1.Kalkulatorer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class Basic_Calc extends LinearLayout {

	Toast toast;
	Button _clear, _update;
	EditText[] textFields;
	TextView[] textviews;
	Context cont;
	LinearLayout _linLay;

	public Basic_Calc(Context context) {
		super(context);
		cont = context;
		
	}

	protected abstract void Initialize();

	protected abstract void CreateListeners();

	protected LinearLayout setAndGetLinearLayout(int layoutID) {
		_linLay = (LinearLayout) LayoutInflater.from(cont).inflate(layoutID,
				this);
		return _linLay;
	}

	protected EditText FindAndReturnEditText(int id,
			OnFocusChangeListener aListener) {
	
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

	/**
	 * Takes in the index of which variable to calculate, and the variables it needs to do so. 
	 * Must define how variables work together.
	 * @param editTextIndex - Index of textfield that will be calculated
	 * @param fieldStatuses - Float-array of variables 
	 * @return Should return a String which will set the text of the textfield
	 */
	public abstract String calculation(int editTextIndex,
			float... fieldStatuses);

	protected int theSum(int... fieldStatuses) {
		int sumOfEditedFields = 0;
		for (int i = 0; i < fieldStatuses.length; i++) {
			sumOfEditedFields += fieldStatuses[i];
		}
		return sumOfEditedFields;
	}
	
	
	/**
	 * Call this method to set textviews text to nothing
	 * @param textView - TextView array of textviews
	 */
	protected void ResetTextViews(TextView... textView){
		for(int i= 0; i<textView.length;i++)
			textView[i].setText("");
	}

	/**
	 * Call this method to set EditTexts text to nothing and enable them
	 * @param editTexts - EditText array of edittexts
	 */
	protected void ResetFields(EditText... editTexts ){
		for(int i =0;i<editTexts.length;i++){
			editTexts[i].setText("");
			editTexts[i].setEnabled(true);
		}
	} 
	
	/**
	 * Displays a toast saying that there was an error if any value returned NaN or infinity; Division with 0 error.
	 * @param x - Float array of variables to test
	 * @return Returns true if no variables were infinite or NaN. Returns false if there were
	 */
	protected boolean checkForDivisionErrors(float... x) {

		for (int i = 0; i < x.length; i++) {
			
			if (Float.isInfinite(x[i]) || Float.isNaN(x[i])) {
				Log.println(Log.ERROR, "calc", "Dividing with 0 error in "
						+ this.getClass().getName());
				showToast("You can't divide by 0!");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Shows a toast on the device with the message from parameter message.
	 * Also prints a logcat with info.
	 * @param message - The message displayed by the toast
	 */
	protected void showToast(String message) {
		try {
			toast.getView().isShown(); // true if visible
			toast.setText(message);
		} catch (Exception e) { // invisible if exception
			toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
		}
		Log.println(Log.INFO, "calc", "Displayed toast saying: " + message);
		toast.show();
	}
	
	/**
	 * Use this method to check for values of 0 if the calculation does not support it.
	 * Only one variable should be 0, and that is the variable that is being calculated.
	 * Will display a toast and logcat message. 
	 * @param x - Values to check for 0 values
	 * @return FALSE if a value is 0, and TRUE if no values are 0
	 */
	protected boolean checkForNullValues(float... x){
		
		for (int i=0; i<x.length;i++){
			
			if(x[i]==0.0f){
				Log.println(Log.ERROR, "calc", "Division with 0 error! " + getClass().getName());
				showToast("Du kan ikke bruke 0 verdier!");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Use this method to check for negative values if the calculation does not support it.
	 * Will display a toast and logcat message. 
	 * @param x - Values to check for negative values
	 * @return FALSE if a value is negative, and TRUE if no values are negative
	 */
	protected boolean checkForNegativeValues(float... x){
		
		for (int i=0; i<x.length;i++){
			
			if(x[i]<0){
				Log.println(Log.ERROR, "calc", "Found negative value in " + getClass().getName());
				showToast("Du kan ikke bruke negative verdier!");
				return false;
			}
		}
		
		return true;
	}
}
