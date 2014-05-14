package com.bbv.prototype1.Kalkulatorer;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import com.bbv.prototype1.R;
import com.bbv.prototype1.Tables.Table_3_1;

public class Table_3_1_calc extends Basic_Calc {

	final static int layout = R.layout.calc_table3_1;

	float Cl;
	float Vsf;
	float Pf;

	final static int[] IDs = { R.id.etT31Cl, R.id.etTable3_1_HideThis, // Textfield
																		// -
	// Will be
	// disabled
	};

	public final static int KEY_INDEX = 1; // Only used for error catching. Only
											// KEY_INDEX

	// should ever be used

	public Table_3_1_calc(Context context) {
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

			Table_3_1 table = new Table_3_1(Cl);

			if (checkForNegativeValues(table.getPf(), table.getVsf()) == false) {
				showToast("Verdien for Cl var utenfor tabellen!");
				return "";
			}

			Vsf = table.getVsf();
			Pf = table.getPf();

			Log.i(LogCat_RegularMessage, "Cl = " + Cl);
			Log.i(LogCat_RegularMessage, "Vsf = " + Vsf);
			Log.i(LogCat_RegularMessage, "Pf = " + Pf);

			lightUpRow(table.getCalculatedRow());

		}

		return "";

	}

	@SuppressWarnings("deprecation")
	private void resetTableItems() {

		Table_3_1 table = new Table_3_1(0);

		for (int i = 1; i <= 15; i++) {

			for (int j = 1; j <= 3; j++) {

				String String_ID = "tvTabell" + String.valueOf(i)
						+ String.valueOf(j);
				Log.println(Log.DEBUG, "calc", "ID resetTableItems: "
						+ String_ID);

				int ID = returnIDFromString(String_ID);

				TextView tableItem = (TextView) findViewById(ID);

				// Find better way to deal with this!
				tableItem.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.table_item_with_border));

				if (i == 15) {
					tableItem.setVisibility(GONE);
				} else {
					tableItem.setText(table.getInitialRows()[i - 1][j - 1]);

					tableItem.setTextColor(Color.BLACK);
				}

			}
		}

	}

	@SuppressWarnings("deprecation")
	private void lightUpRow(int calculatedRow) {
		// TODO Auto-generated method stub

		resetTableItems();
		insertNewTableItem(calculatedRow);

		for (int i = 1; i <= 3; i++) {

			String String_ID = "tvTabell" + String.valueOf(calculatedRow + 1)
					+ String.valueOf(i);
			Log.println(Log.DEBUG, "calc", "ID lightupRow: " + String_ID);

			int ID = returnIDFromString(String_ID);

			TextView tableItem = (TextView) findViewById(ID);

			// Find better way to deal with this!
			tableItem.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.table_item_with_border_selected));

			tableItem.setTextColor(Color.WHITE);

		}

	}

	private void insertNewTableItem(int calculatedRow) {
		Table_3_1 table = new Table_3_1(Cl);

		for (int i = 1; i <= 3; i++) {
			String String_ID = "tvTabell" + String.valueOf(calculatedRow + 1)
					+ String.valueOf(i);
			Log.println(Log.DEBUG, "calc", "ID inserting: " + String_ID);

			int ID = returnIDFromString(String_ID);

			TextView tableItem = (TextView) findViewById(ID);

			switch (i) {
			case 1:
				tableItem.setText(String.format("%.0f", Cl));
				break;
			case 2:
				tableItem.setText(String.format("%.3f", Vsf));
				break;
			case 3:
				tableItem.setText(String.format("%.3f", Pf));
				break;
			default:
				Log.e(LogCat_RegularMessage,
						"Error when inserting new tableitem!");
				break;
			}

		}

		for (int i = calculatedRow + 2; i <= 15; i++) {

			for (int j = 1; j <= 3; j++) {

				String String_ID = "tvTabell" + String.valueOf(i)
						+ String.valueOf(j);
				Log.println(Log.DEBUG, "calc", "ID setting rest of tables: "
						+ String_ID);

				int ID = returnIDFromString(String_ID);

				TextView tableItem = (TextView) findViewById(ID);

				// Find better way to deal with this!
				tableItem.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.table_item_with_border));

				tableItem.setText(table.getInitialRows()[i - 2][j - 1]);

				tableItem.setTextColor(Color.BLACK);

				if (i == 15)
					tableItem.setVisibility(VISIBLE);

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
	protected void initializeMethod() {
		// TODO Auto-generated method stub
		super.initializeMethod();
		resetTableItems();
	}

	@Override
	protected void clearButtonMethod() {
		// TODO Auto-generated method stub
		resetTableItems();
		super.clearButtonMethod();

	}
}