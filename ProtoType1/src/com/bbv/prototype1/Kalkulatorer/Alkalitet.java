package com.bbv.prototype1.Kalkulatorer;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bbv.prototype1.R;
import com.bbv.prototype1.R.color;
import com.bbv.prototype1.Tables.Table_3_3;

public class Alkalitet extends Basic_Calc {

	final static int layout = R.layout.calc_alkalitet;

	// Variables used for calculations
	float Pf;
	float Mf;
	float OH;
	float CO;
	float HCO;

	final static int[] IDs = { R.id.etAlkalitetPf, // Edittext of Mf
			R.id.etAlkalitetMf, // Edittext of Fw
			R.id.etAlkalitetHideThis // Edittext of nothing - Will be disabled
	};

	public final static int KEY_INDEX = 2; // Only used for error catching when
											// calculating. Only
											// Key_INDEX should ever be used

	public Alkalitet(Context context) {
		super(context, IDs, layout);
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		Pf = fieldStatuses[0];
		Mf = fieldStatuses[1];

		switch (variableToCalculate) {

		default:
			Log.println(Log.ERROR, "calc",
					"Tried to calculate wrong value, and this should not happen!");
			break;
		case KEY_INDEX:

			Table_3_3 table = new Table_3_3(Pf, Mf);

			if (table.getCalculatedRow() == -1) {
				Log.println(Log.ERROR, "calc", "Something went wrong in "
						+ this.getClass().getName());
				showToast("Something went wrong with the calculation!");
				return "";
			}

			if (checkForNegativeValues(Pf, Mf) == false)
				return "";

			OH = table.getOH();
			HCO = table.getHCO();
			CO = table.getCO();

			Log.println(Log.INFO, "calc", "OH = " + table.getOH());
			Log.println(Log.INFO, "calc", "CO = " + table.getCO());
			Log.println(Log.INFO, "calc", "HCO = " + table.getHCO());

			lightUpRow(table.getCalculatedRow());

		}

		return "";

	}

	/**
	 * This method resets the table items to the original string values in the
	 * original table. Also set the text color back to black, and the background
	 * to a difined color in the resources.
	 */
	private void resetTableItems() {

		Table_3_3 table = new Table_3_3(0, 0);

		for (int i = 1; i <= 5; i++) {

			for (int j = 1; j <= 3; j++) {

				String String_ID = "tvAlkalitet" + String.valueOf(i)
						+ String.valueOf(j);
				Log.println(Log.DEBUG, "calc", "ID resetTableItems: "
						+ String_ID);

				int ID = returnIDFromString(String_ID);

				TextView tableItem = (TextView) findViewById(ID);

				// Find better way to deal with this!
				tableItem.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.table_item_with_border));

				tableItem.setText(table.getInitialRows()[i - 1][j - 1]);

				tableItem.setTextColor(Color.BLACK);

			}
		}
	}

	/**
	 * This method will change the selected row to a different background and
	 * text color. Also calls resetTableItems().
	 * 
	 * @param calculatedRow
	 *            - the row to light up
	 */
	private void lightUpRow(int calculatedRow) {
		// TODO Auto-generated method stub

		resetTableItems();

		for (int i = 1; i <= 3; i++) {

			String String_ID = "tvAlkalitet" + String.valueOf(calculatedRow)
					+ String.valueOf(i);
			Log.println(Log.DEBUG, "calc", "ID lightupRow: " + String_ID);

			int ID = returnIDFromString(String_ID);

			TextView tableItem = (TextView) findViewById(ID);

			// Find better way to deal with this!
			tableItem.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.table_item_with_border_selected));

			showResults(tableItem, calculatedRow, i);

			tableItem.setTextColor(Color.WHITE);

		}

	}

	/**
	 * This method is used to show the result for the relevant table item. The
	 * row and column is used to locate the table item, as it is dependent on
	 * the original table if the text should change or stay 0. 
	 * 
	 * @param tableItem - the table item to change
	 * @param row - the row of the table item
	 * @param column - the column of the table item
	 */
	private void showResults(TextView tableItem, int row, int column) {

		switch (row) {
		case 1:
			if (column == 3)
				tableItem.setText(String.format(NO_DECIMALS, HCO));
			break;
		case 2:
			if (column == 2)
				tableItem.setText(String.format(NO_DECIMALS, CO));
			if (column == 3)
				tableItem.setText(String.format(NO_DECIMALS, HCO));
			break;
		case 3:
			if (column == 2)
				tableItem.setText(String.format(NO_DECIMALS, CO));
			break;
		case 4:
			if (column == 1)
				tableItem.setText(String.format(NO_DECIMALS, OH));
			if (column == 2)
				tableItem.setText(String.format(NO_DECIMALS, CO));
			break;
		case 5:
			if (column == 1)
				tableItem.setText(String.format(NO_DECIMALS, OH));
			break;

		default:
			Log.println(Log.ERROR, "calc",
					"A row value not between 1 and 5 was chosen in Alkalitet");
			break;
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
	protected void clearButtonMethod() {
		// TODO Auto-generated method stub
		resetTableItems();
		super.clearButtonMethod();
	}
}