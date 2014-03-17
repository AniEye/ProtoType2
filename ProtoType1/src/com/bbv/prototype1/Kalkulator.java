package com.bbv.prototype1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import com.bbv.prototype1.Kalkulatorer.*;

public class Kalkulator extends Activity implements OnItemSelectedListener,
		OnItemClickListener {

	String lastChoiceMadeKey = "lastChoice";
	int lastChoice;

	ListView valg_horizontal;
	Spinner valg_vertical;
	LinearLayout lLayout;
	int SpinnerItemLayout = R.layout.custom_spinner_item;

	Basic_Calc calc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {
			lastChoice = loadLC(); // Loads the last chosen calculator
		} catch (NullPointerException e) {
			Log.println(Log.ERROR, "TryCatch",
					"Error with SharedPreferences in kalkulator!");
		}

		setContentView(R.layout.activity_calculator);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initialize();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initialize() {
		/**
		 * Creates a spinner object if the device is in a vertical position, or
		 * a listview if the device is in a horizontal position
		 */

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.custom_spinner, getResources().getStringArray(
						R.array.calculator));
		adapter.setDropDownViewResource(SpinnerItemLayout);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			valg_vertical = (Spinner) findViewById(R.id.sKalkVertical);

			// Adds the custom look for the spinner
			valg_vertical.setAdapter(adapter);
			// Adds the custom look for the spinner

			lLayout = (LinearLayout) findViewById(R.id.lInSVKalkVertical);
			valg_vertical.setOnItemSelectedListener(this);
			valg_vertical.setSelection(lastChoice); // Chooses which item the
													// spinner starts at
		} else {
			valg_horizontal = (ListView) findViewById(R.id.listKalkHorizontal);

			// Adds the custom look for the spinner
			valg_horizontal.setAdapter(adapter);
			// Adds the custom look for the spinner

			lLayout = (LinearLayout) findViewById(R.id.lInSVKalkHorizontal);
			valg_horizontal.setOnItemClickListener(this);
			valg_horizontal.performItemClick(
					findViewById(R.id.lInSVKalkHorizontal), lastChoice,
					lastChoice); // Chooses which item the list starts at
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int index,
			long arg3) {

		showCalc(index);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub
		showCalc(index);
	}

	public void showCalc(int index) {
		/**
		 * Shows the calculator selected in either the spinner or the listview
		 */

		if (lLayout.getChildCount() > 0)
			lLayout.removeAllViews();

		switch (index) {
		case 0:
			calc = new MasseOgVolumBalanse(lLayout.getContext());
			break;
		case 1:
			calc = new Til_Viskos(lLayout.getContext());
			break;
		case 2:
			calc = new Plast_Viskos(lLayout.getContext());
			break;
		case 3:
			calc = new Flytegrense(lLayout.getContext());
			break;
		case 4:
			calc = new PowerLaw(lLayout.getContext());
			break;
		case 5:
			calc = new Herch_Law(lLayout.getContext());
			break;
		case 6:
			calc = new Spess_tetthet(lLayout.getContext());
			break;
		case 7:
			calc = new KloridInnhold(lLayout.getContext());
			break;
		case 8:
			calc = new TotalHardhet(lLayout.getContext());
			break;
		case 9:
			calc = new CEC(lLayout.getContext());
			break;
		case 10:
			calc = new AlkaPm(lLayout.getContext());
			break;
		case 11: 
			calc = new Alkalitet(lLayout.getContext());
			break;
		}

		try {
			lLayout.addView(calc);
		} catch (NullPointerException e) {
			// TODO: handle exception
			Log.println(Log.ERROR, "TryCatch",
					"Somehow no calculator was selected!");
		}

		saveLC(index); // Saves the current index here
	}

	private int loadLC() {
		/**
		 * Loads previous choice in either the spinner or the listview
		 */
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		int restoredInt = prefs.getInt(lastChoiceMadeKey, 0);

		return restoredInt;

	}

	private void saveLC(int index) {
		/**
		 * Saves the current position of the selected item in either the spinner
		 * or the listview
		 */
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putInt(lastChoiceMadeKey, index);
		editor.commit();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

}