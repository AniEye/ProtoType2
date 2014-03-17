package com.bbv.prototype1.Kalkulatorer;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bbv.prototype1.R;

public class Alkalitet extends Basic_Calc {

	Toast toast;
	int[] _textFieldsStatus;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	final int clearButtonID = R.id.bAlkalitetClear;
	final int updateButtonID = R.id.bAlkalitetUpdate;
	final int layout = R.layout.calc_alkalitet;

	// Variables used for calculations
	float Pf;
	float Mf;

	final int[] IDs = { R.id.etAlkalitetPf, // Edittext of Mf
			R.id.etAlkalitetMf, // Edittext of Fw
			R.id.etAlkalitetHideThis // Edittext of nothing - Will be disabled
	};

	public final static int KEY_INDEX = 2; // Only used for error catching when
											// calculating. Only
											// Key_INDEX should ever be used

	public Alkalitet(Context context) {
		super(context);
		CreateListeners();
		Initialize();
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
				showToast("Something went wrong!");
				return "";
			}

			Log.println(Log.INFO, "calc", "OH = " + table.getOH());
			Log.println(Log.INFO, "calc", "CO = " + table.getCO());
			Log.println(Log.INFO, "calc", "HCO = " + table.getHCO());

			lightUpRow(table.getCalculatedRow());

		}

		return "";

	}

	private void resetTableItems() {

		for (int i = 1; i <= 5; i++) {
			
			for (int j=1; j<=3; j++)
			{

			String String_ID = "tvAlkalitet" + String.valueOf(i)
					+ String.valueOf(j);
			Log.println(Log.DEBUG, "calc", "ID lightupRow: " + String_ID);

			Class clazz = R.id.class;
			Field f = null;
			int id = -1;
			try {
				f = clazz.getField(String_ID);
				id = f.getInt(null); // pass in null, since field is a static
										// field.
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Find better way to deal with this!
			findViewById(id).setBackgroundDrawable(
					getResources().getDrawable(
							R.drawable.table_item_with_border));

			}
		}

	}

	private void lightUpRow(int calculatedRow) {
		// TODO Auto-generated method stub

		resetTableItems();

		for (int i = 1; i <= 3; i++) {

			String String_ID = "tvAlkalitet" + String.valueOf(calculatedRow)
					+ String.valueOf(i);
			Log.println(Log.DEBUG, "calc", "ID lightupRow: " + String_ID);

			Class clazz = R.id.class;
			Field f = null;
			int id = -1;
			try {
				f = clazz.getField(String_ID);
				id = f.getInt(null); // pass in null, since field is a static
										// field.
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Find better way to deal with this!
			findViewById(id).setBackgroundDrawable(
					getResources().getDrawable(
							R.drawable.table_item_with_border_selected));

		}

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
					resetTableItems();
					break;
				case updateButtonID:
					for (int i = 0; i < textFields.length; i++) {
						FocusChange(i, false);
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