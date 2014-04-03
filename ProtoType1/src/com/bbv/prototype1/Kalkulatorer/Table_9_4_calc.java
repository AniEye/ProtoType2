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

	final static int layout = R.layout.calc_table9_4;

	/**
	 * Sets the number of vertical table items to show for the table
	 */
	private int numberOfTableItemsToShow = 15;

	/**
	 * Sets the number of vertical table items to show for the table
	 */
	private final int MAX_numberOfTableItemsToShow = 15;

	float Cl;
	float CaCl2;
	float Corf;
	/**
	 * The middle of numberOfTableItemsToShow
	 */
	int half;

	/**
	 * Values for the textfields. Use KEY_INDEX to choose which one to
	 * calculate.
	 */
	final static int[] IDs = { R.id.etCaCl2Cl, R.id.etCaCl2_HideThis, };

	/**
	 * As there is only one field to be calculated in this calculator, only this
	 * KEY_INDEX should be used.
	 */
	public final static int KEY_INDEX = 1;

	public Table_9_4_calc(Context context) {
		super(context, IDs, layout);
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
			half = numberOfTableItemsToShow / 2 + 1;

			Log.i(LogCat_RegularMessage, "Corf = " + Corf);
			Log.i(LogCat_RegularMessage, "CaCl2 = " + CaCl2);

			insertNewTableItem(table.getCalculatedRow());

		}

		return "";

	}

	/**
	 * This method resets the table items using the variable
	 * MAX_numberOfTableItemsToShow. This variable is set after how many
	 * textfields have been defined in the XML-layout of this calculator. This
	 * method will therefore always reset every single table item, despite how
	 * many are currently showing.
	 */
	private void resetTableItems() {

		for (int i = 1; i <= MAX_numberOfTableItemsToShow; i++) {

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

	/**
	 * This method inserts new table items, based on the variable
	 * numberOfTableItemsToShow. It will start to display items based on the
	 * parameter calculatedRow, which is where in the table the calculated
	 * variable is located in the initial tables. If the variable
	 * numberOfTableItemsToShow is 5, the variable half is set to be 3, which
	 * means that this method will insert 2 table items first, then the
	 * calculated variable, and then 2 more table items. Also calls the
	 * resetTableItems() method.
	 * 
	 * @param calculatedRow
	 *            - where the calculated variable is inserted, and then
	 *            surrounded with values from the initial tables.
	 */
	private void insertNewTableItem(int calculatedRow) {
		resetTableItems();
		Table_9_4 table = new Table_9_4(Cl);
		
		Log.i("calc", "Calculated row = " + calculatedRow);
		Log.i("calc", "Calculated row - half = "
				+ (calculatedRow - half));

		for (int k = 1; k <= numberOfTableItemsToShow; k++) {

			for (int i = 1; i <= 3; i++) {

				String String_ID = "tvCaCl2Tabell" + String.valueOf(k)
						+ String.valueOf(i);
				Log.println(Log.DEBUG, "calc", "ID inserting: " + String_ID);

				int ID = returnIDFromString(String_ID);

				TextView tableItem = (TextView) findViewById(ID);

				if (k < half && (calculatedRow - half + k) >= 0) {

					Log.i(LogCat_RegularMessage, "Inserting tableitem before calculated value at row: " + k );
					tableItem.setText(table.getInitialRows()[(calculatedRow
							- half + k)][i - 1]);
					tableItem.setVisibility(VISIBLE);

				} else if (k == half) {
					Log.i(LogCat_RegularMessage, "Inserting calculated values at row: " + k);
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

				} else if (k > half && calculatedRow + (k - half - 1) < 41) { //41 is the number of total rows in the table
					Log.i(LogCat_RegularMessage, "Inserting tableitem after calculated value at row: " + k );
					tableItem.setText(table.getInitialRows()[calculatedRow
							+ (k - half - 1)][i - 1]);
					tableItem.setVisibility(VISIBLE);

				}

			}

		}

	}

	/**
	 * This method uses a string ID to return an integer ID from R.id using
	 * classes and fields. Will also catch any error messages and display a
	 * logCat message.
	 * 
	 * @param String_ID
	 *            - The string ID to find
	 * @return integer ID
	 */
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
	protected void initializeMethod() {
		// TODO Auto-generated method stub
		super.initializeMethod();
		
		resetTableItems();

		loadNumberOfTableItemsToShow();

	}

	/**
	 * This method used SharedPreferences to load the selected number of table
	 * items to show from the settings for calculators.
	 */
	private void loadNumberOfTableItemsToShow() {
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		String values = getPrefs.getString("table9_4_list", "3");
		Log.i(LogCat_RegularMessage, "Returned value for amount of tableitems shown = " + values);
		numberOfTableItemsToShow = Integer.parseInt(values);
	}

	@Override
	protected void updateButtonMethod() {
		// TODO Auto-generated method stub
		//resetTableItems();
		loadNumberOfTableItemsToShow();
		super.updateButtonMethod();


	}
	
	@Override
	protected void clearButtonMethod() {
		// TODO Auto-generated method stub
		super.clearButtonMethod();
		resetTableItems();
	}
}