package com.bbv.prototype1.Kalkulatorer;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bbv.prototype1.R;

public class Table_3_1_calc extends Basic_Calc {

	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bKloridInnholdClear;
	final int updateButtonID = R.id.bKloridInnholdUpdate;
	final int layout = R.layout.calc_table3_1;

	float Cl;
	float Vsf;
	float Pf;

	final int[] IDs = { R.id.etT31Cl, R.id.etTable3_1_HideThis, // Textfield -
																// Will be
																// disabled
	};

	public final static int KEY_INDEX = 1; // Only used for error catching. Only
											// KEY_INDEX

	// should ever be used

	public Table_3_1_calc(Context context) {
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

			Table_3_1 table = new Table_3_1(Cl);

			Vsf = table.getVsf();
			Pf = table.getPf();

			if (checkForNegativeValues(Pf, Vsf, table.getCalculatedRow()) == false)
				return "";

			Log.i(LogCat_RegularMessage, "Cl = " + Cl);
			Log.i(LogCat_RegularMessage, "Vsf = " + Vsf);
			Log.i(LogCat_RegularMessage, "Pf = " + Pf);

			lightUpRow(table.getCalculatedRow());

		}

		return "";

	}

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
			String String_ID = "tvTabell" + String.valueOf(calculatedRow+1)
					+ String.valueOf(i);
			Log.println(Log.DEBUG, "calc", "ID inserting: " + String_ID);

			int ID = returnIDFromString(String_ID);

			TextView tableItem = (TextView) findViewById(ID);

			switch (i) {
			case 1:
				tableItem.setText(String.format("%.0f", Cl));
				break;
			case 2:
				tableItem.setText(String.format("%.1f", Vsf));
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

				tableItem.setText(table.getInitialRows()[i-2][j - 1]);

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
	protected void Initialize() {
		_linLay = setAndGetLinearLayout(layout);

		textFields = new EditText[IDs.length];
		_textFieldsStatus = new int[IDs.length];

		for (int i = 0; i < IDs.length; i++)
			textFields[i] = FindAndReturnEditText(IDs[i], focChan);

		resetTableItems();

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
					resetTableItems();
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