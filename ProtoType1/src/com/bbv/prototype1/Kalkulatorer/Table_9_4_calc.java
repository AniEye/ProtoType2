package com.bbv.prototype1.Kalkulatorer;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bbv.prototype1.R;
import com.bbv.prototype1.Tables.Table_3_1;
import com.bbv.prototype1.Tables.Table_9_4;

public class Table_9_4_calc extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bCaCl2Clear;
	final int updateButtonID = R.id.bCaCl2Update;
	final int layout = R.layout.calc_table9_4;

	/**
	 * Sets the number of vertical table items to show for the table
	 */
	private int numberOfTableItemsToShow = 15;

	float Cl;
	float CaCl2;
	float Corf;
	/**
	 * The middle of numberOfTableItemsToShow
	 */
	int half;

	final int[] IDs = { R.id.etCaCl2Cl, R.id.etCaCl2_HideThis, // Textfield -
																// Will be
																// disabled
	};

	public final static int KEY_INDEX = 1; // Only used for error catching. Only
											// KEY_INDEX

	// should ever be used

	public Table_9_4_calc(Context context) {
		super(context);
		CreateListeners();
		Initialize();
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		Cl = fieldStatuses[0];

		switch (variableToCalculate) {

		default:
			Log.e(LogCat_RegularMessage,
					"Tried to calculate wrong value, and this should not happen!");
			break;
		case KEY_INDEX:

			Table_9_4 table = new Table_9_4(Cl);

			Log.i("calc", "Correct calculation");

			if (checkForNegativeValues(table.getCaCl(), table.getCaCl()) == false) {
				showToast("Verdien for Cl var utenfor tabellen!");
				return "";
			}

			CaCl2 = table.getCaCl();
			Corf = table.getCorf();
			half = numberOfTableItemsToShow/2 + 1 ;

			Log.i(LogCat_RegularMessage, "Corf = " + Corf);
			Log.i(LogCat_RegularMessage, "CaCl2 = " + CaCl2);

			insertNewTableItem(table.getCalculatedRow());

		}

		return "";

	}

	private void resetTableItems() {

		for (int i = 1; i <= numberOfTableItemsToShow; i++) {

			for (int j = 1; j <= 3; j++) {

				String String_ID = "tvCaCl2Tabell" + String.valueOf(i)
						+ String.valueOf(j);
				Log.println(Log.DEBUG, "calc", "ID resetTableItems: "
						+ String_ID);

				int ID = returnIDFromString(String_ID);

				TextView tableItem = (TextView) findViewById(ID);

				if (i == half) {
					// Find better way to deal with this!
					tableItem.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.table_item_with_border_selected));

					tableItem.setTextColor(Color.WHITE);
				} else {
					// Find better way to deal with this!
					tableItem.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.table_item_with_border));

					tableItem.setTextColor(Color.BLACK);
				}

				tableItem.setVisibility(GONE);

			}
		}

	}

	private void insertNewTableItem(int calculatedRow) {
		resetTableItems();
		Table_9_4 table = new Table_9_4(Cl);

		for (int k = 1; k <= numberOfTableItemsToShow; k++) {

			for (int i = 1; i <= 3; i++) {

				String String_ID = "tvCaCl2Tabell" + String.valueOf(k)
						+ String.valueOf(i);
				Log.println(Log.DEBUG, "calc", "ID inserting: " + String_ID);

				int ID = returnIDFromString(String_ID);

				TextView tableItem = (TextView) findViewById(ID);

				if (k < half
						&& (calculatedRow - half+k) >= 0) {
					Log.i("calc", "Calculated row = " + calculatedRow);
					Log.i("calc", "Calculated row - half = "
							+ (calculatedRow - half));
					tableItem.setText(table.getInitialRows()[ (calculatedRow
							- half +k)][i - 1]);
					tableItem.setVisibility(VISIBLE);

				} else if (k == half) {
					Log.i(LogCat_RegularMessage, "Inserting calculated values");
					switch (i) {
					case 1:
						tableItem.setText(String.format("%.0f", Cl));
						break;
					case 2:
						tableItem.setText(String.format("%.3f", Corf));
						break;
					case 3:
						tableItem.setText(String.format("%.2f", CaCl2));
						break;
					default:
						Log.e(LogCat_RegularMessage,
								"Error when inserting new tableitem!");
						break;
					}
					tableItem.setVisibility(VISIBLE);

				} else if (k > half && calculatedRow
						+ (k-half-1) < 41) {
					tableItem.setText(table.getInitialRows()[calculatedRow
							+ (k-half-1)][i - 1]);
					tableItem.setVisibility(VISIBLE);

				}

			}

		}

	}

	private int returnIDFromString(String String_ID) {
		Class clazz = R.id.class;
		Field f = null;
		int id = -1;
		try {
			f = clazz.getField(String_ID);
			id = f.getInt(null); // pass in null, since field is a static
									// field.
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			Log.println(Log.ERROR, "TryCatch",
					"Error when returning textview ID");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			Log.println(Log.ERROR, "TryCatch",
					"Error when returning textview ID");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			Log.println(Log.ERROR, "TryCatch",
					"Error when returning textview ID");
			e.printStackTrace();
		}
		return id;
	}

	@Override
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		resetTableItems();
		
		loadNumberOfTableItemsToShow();

		_clear = FindAndReturnButton(clearButtonID, cliLis);
		_update = FindAndReturnButton(updateButtonID, cliLis);
	}

	private void loadNumberOfTableItemsToShow() {
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		String values = getPrefs.getString("table9_4_list", "3");
		Log.i("calc", "String = " + values);
		numberOfTableItemsToShow = Integer.parseInt(values);
	}

	@Override
	protected void CreateListeners() {

		cliLis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case clearButtonID:
					ResetFields(textFields);
					resetTableItems();
					loadNumberOfTableItemsToShow();
					_textFieldsStatus = new int[IDs.length];
					break;
				case updateButtonID:
					loadNumberOfTableItemsToShow();
					Log.i("calc", "Updatebutton pressed!");
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