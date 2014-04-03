package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class Basic_Calc extends LinearLayout {

	final int clearButtonID = R.id.bClear;
	final int updateButtonID = R.id.bUpdate;
	int[] IDs;
	int[] textViewIDs;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;
	int[] _textFieldsStatus;
	Toast toast;
	Button _clear, _update;
	EditText[] textFields;
	TextView[] textviews;

	Context cont;
	LinearLayout _linLay;
	final String THREE_DECIMALS = "%.3f";
	final String NO_DECIMALS = "%.0f";
	final String LogCat_TryCatch = "TryCatch";
	final String LogCat_RegularMessage = "calc";

	final int WITH_TEXTVIEW_FIELDS = 0, WITHOUT_TEXTVIEW_FIELDS = 1;

	
	public Basic_Calc(Context context, int[] IDs, int layoutID) {
		super(context);
		cont = context;
		this.IDs = IDs;
		CreateListeners();
		Initialize(layoutID, WITHOUT_TEXTVIEW_FIELDS);


	}

	public Basic_Calc(Context context, int[] editTextIDs, int[] textViewIDs,
			int layoutID) {
		super(context);
		cont = context;
		this.IDs = editTextIDs;
		this.textViewIDs = textViewIDs;
		CreateListeners();
		Initialize(layoutID, WITH_TEXTVIEW_FIELDS);

	}

	/**
	 * This method gets called whenever a calculator is instantiated.
	 * 
	 * @param layout
	 * @param textFieldFlag
	 *            - Use WITH_TEXTVIEW_FIELDS or WITHOUT_TEXTVIEW_FIELDS to
	 *            specify if the calculator has textviews or not that need to be
	 *            instantiated.
	 */
	protected void Initialize(int layout, int textFieldFlag) {
		
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		if (textFieldFlag == WITH_TEXTVIEW_FIELDS) {
			textviews = new TextView[textViewIDs.length];
			for (int i = 0; i < textViewIDs.length; i++)
				textviews[i] = (TextView) _linLay.findViewById(textViewIDs[i]);
		}

		_clear = FindAndReturnButton(clearButtonID, cliLis);
		_update = FindAndReturnButton(updateButtonID, cliLis);
		
		initializeMethod();
	}

	/**
	 * This is a method that is used only for the purpose of keeping the
	 * sub-classes organized. This class should contain all the listeners that
	 * are used in the sub-classes.
	 */
	protected void CreateListeners() {

		cliLis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case clearButtonID:
					clearButtonMethod();
					break;
				case updateButtonID:
					updateButtonMethod();
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

	/**
	 * This method is called whenever the update Button is pressed. 
	 * 
	 * Override and pass super() method to add features to the button.
	 */
	protected void updateButtonMethod() {
		Log.i(LogCat_RegularMessage, "Update-button pressed in "
				+ getClass().getSimpleName());
		for (int i = 0; i < textFields.length; i++) {
			FocusChange(i, false);
		}
		hideSoftKeyboard();
	}

	/**
	 * This method is called whenever the clear Button is pressed. First calls
	 * ResetFields, and then resets _textFieldStatus
	 * 
	 * Override and pass super() method to add features to the button.
	 */
	protected void clearButtonMethod() {
		Log.i(LogCat_RegularMessage, "Clear-button pressed in "
				+ getClass().getSimpleName());
		ResetFields(textFields);
		_textFieldsStatus = new int[IDs.length];
	}

	/**
	 * Override this method to pass extra features to a calculators initialize method.
	 */
	protected void initializeMethod(){
		
	}
	/**
	 * This method is used to inflate linearlayout that is used. This method
	 * takes as a parameter the id of the linearlayout that the user want's to
	 * inflate.
	 * 
	 * @param layoutID
	 *            - The ID of the layout to inflate
	 * @return the inflated linearLayout
	 */
	protected LinearLayout setAndGetLinearLayout(int layoutID) {
		_linLay = (LinearLayout) LayoutInflater.from(cont).inflate(layoutID,
				this);
		return _linLay;
	}

	/**
	 * It is used to initialize an EditText in the linear layout of the
	 * sub-classes. The purpose of this method is to cut down code in the
	 * sub-classes.
	 * 
	 * @param id
	 *            - the ID of the edittext
	 * @param aListener
	 *            (OnFocusChangeListener) - The listener associated with this
	 *            editext.
	 * @return the initialized EditText
	 */
	protected EditText FindAndReturnEditText(int id,
			OnFocusChangeListener aListener) {

		EditText aField = (EditText) _linLay.findViewById(id);
		aField.setOnFocusChangeListener(aListener);

		return aField;
	}

	/**
	 * It is used to initialize an Button in the linear layout of the
	 * sub-classes. The purpose of this method is to cut down code in the
	 * sub-classes.
	 * 
	 * @param id
	 *            - The id of the button
	 * @param aListener
	 *            (OnClickListener) - the listener to be associated with the
	 *            button
	 * @return the initialized Button
	 */
	protected Button FindAndReturnButton(int id, OnClickListener aListener) {
		Button aButton = (Button) _linLay.findViewById(id);
		aButton.setOnClickListener(aListener);
		return aButton;
	}

	/**
	 * This method will reset all the EditTexts in the array by making them
	 * enabled and emptying the fields.
	 * 
	 * @param _theEditTextFields
	 *            (EditText-Array)
	 */
	protected void Enabeling(EditText... _theEditTextFields) {
		for (int i = 0; i < _theEditTextFields.length; i++) {
			if (!_theEditTextFields[i].isEnabled()) {
				_theEditTextFields[i].setEnabled(true);
				_theEditTextFields[i].setText("");
			}
		}
	}

	/**
	 * A method that the sub-classes should have to find the field that has not
	 * been filled and do the calculation relevant to that field. This is to be
	 * done only when all but one field has been entered. This can be modified
	 * to be done differently.
	 */
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
	 * Takes in the index of which variable to calculate, and the variables it
	 * needs to do so. Must define how variables work together.
	 * 
	 * @param editTextIndex
	 *            - Index of textfield that will be calculated
	 * @param fieldStatuses
	 *            - Float-array of variables
	 * @return Should return a String which will set the text of the textfield
	 */
	public abstract String calculation(int editTextIndex,
			float... fieldStatuses);

	/**
	 * This method sums every int in an integer array, and returns this sum.
	 * 
	 * @param fieldStatuses
	 *            - Integer array
	 * @return the sum of all integers in an integer-array
	 */
	protected int theSum(int... fieldStatuses) {
		int sumOfEditedFields = 0;
		for (int i = 0; i < fieldStatuses.length; i++) {
			sumOfEditedFields += fieldStatuses[i];
		}
		return sumOfEditedFields;
	}

	/**
	 * Call this method to set textviews text to nothing
	 * 
	 * @param textView
	 *            - TextView array of textviews
	 */
	protected void ResetTextViews() {
		for (int i = 0; i < textviews.length; i++)
			textviews[i].setText("");
	}

	/**
	 * Call this method to set EditTexts text to nothing and enable them
	 * 
	 * @param editTexts
	 *            - EditText array of edittexts
	 */
	protected void ResetFields(EditText... editTexts) {
		for (int i = 0; i < editTexts.length; i++) {
			editTexts[i].setText("");
			editTexts[i].setEnabled(true);
		}
	}

	/**
	 * Displays a toast saying that there was an error if any value returned NaN
	 * or infinity; Division with 0 error.
	 * 
	 * @param x
	 *            - Float array of variables to test
	 * @return Returns true if no variables were infinite or NaN. Returns false
	 *         if there were
	 */
	protected boolean checkForDivisionErrors(float... x) {

		for (int i = 0; i < x.length; i++) {

			if (Float.isInfinite(x[i])) {
				Log.println(Log.ERROR, "calc", "Var. " + i
						+ " caused dividing with 0 error in "
						+ this.getClass().getSimpleName());
				showToast("Verdier ble delt på null!");
				return false;
			} else if (Float.isNaN(x[i])) {
				Log.println(Log.ERROR, "calc", "Var. " + i
						+ " caused Log(-x) error in "
						+ this.getClass().getSimpleName());
				showToast("En Log utregnelse ble tatt av en negativ verdi!");
				return false;
			}
		}
		Log.println(Log.INFO, "calc", "Found no infinite values in "
				+ getClass().getName());
		return true;
	}

	/**
	 * Shows a toast on the device with the message from parameter message. Also
	 * prints a logcat with info.
	 * 
	 * @param message
	 *            - The message displayed by the toast
	 */
	protected void showToast(String message) {
		try {
			toast.getView().isShown(); // true if visible
			toast.setText(message);
		} catch (Exception e) { // invisible if exception
			toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
		}
		Log.println(Log.DEBUG, "calc", "Displayed toast saying: " + message);
		toast.show();
	}

	/**
	 * 
	 * @param indexOfCurrentField
	 * @param focusStatus
	 */
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

	/**
	 * Shows a toast on the device with the message from parameter message and
	 * length of toast Also prints a logcat with info.
	 * 
	 * @param message
	 *            - The message displayed by the toast
	 * @param lengthOfToast
	 *            - Length of toast, based on the Toast-class
	 */
	protected void showToast(String message, int lengthOfToast) {
		try {
			toast.getView().isShown(); // true if visible
			toast.setText(message);
		} catch (Exception e) { // invisible if exception
			toast = Toast.makeText(getContext(), message, lengthOfToast);
		}
		Log.println(Log.DEBUG, "calc", "Displayed toast saying: " + message);
		toast.show();
	}

	/**
	 * Use this method to check for values of 0 if the calculation does not
	 * support it. Only one variable should be 0, and that is the variable that
	 * is being calculated. Will display a toast and logcat message.
	 * 
	 * @param x
	 *            - Values to check for 0 values
	 * @return FALSE if a value is 0, and TRUE if no values are 0
	 */
	protected boolean checkForNullValues(float... x) {

		for (int i = 0; i < x.length; i++) {

			if (x[i] == 0.0f) {
				Log.println(Log.ERROR, "calc", "Division with 0 error! Var.: "
						+ i + getClass().getSimpleName());
				showToast("Du kan ikke bruke 0 verdier!");
				return false;
			}
		}
		Log.println(Log.DEBUG, "calc", "Found no null values in "
				+ getClass().getName());
		return true;
	}

	/**
	 * Use this method to check for negative values if the calculation does not
	 * support it. Will display a toast and logcat message.
	 * 
	 * @param x
	 *            - Values to check for negative values
	 * @return FALSE if a value is negative, and TRUE if no values are negative
	 */
	protected boolean checkForNegativeValues(float... x) {

		for (int i = 0; i < x.length; i++) {

			if (x[i] < 0) {
				Log.println(Log.ERROR, "calc", "Found negative value in "
						+ getClass().getSimpleName());
				showToast("Du kan ikke bruke negative verdier!");
				return false;
			}
		}
		Log.println(Log.DEBUG, "calc", "Found no negative values in "
				+ getClass().getName());
		return true;
	}

	/**
	 * Use this method to hide the soft keyboard
	 */
	protected void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindowToken(), 0);
	}
}
