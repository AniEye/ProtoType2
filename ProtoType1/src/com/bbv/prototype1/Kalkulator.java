package com.bbv.prototype1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bbv.prototype1.Kalkulatorer.AlkaPm;
import com.bbv.prototype1.Kalkulatorer.Alkalitet;
import com.bbv.prototype1.Kalkulatorer.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.CEC;
import com.bbv.prototype1.Kalkulatorer.CaOHInnhold;
import com.bbv.prototype1.Kalkulatorer.Flytegrense;
import com.bbv.prototype1.Kalkulatorer.Herch_Law;
import com.bbv.prototype1.Kalkulatorer.KloridInnhold;
import com.bbv.prototype1.Kalkulatorer.KloridInnholdIVannfasen;
import com.bbv.prototype1.Kalkulatorer.KonverterM3;
import com.bbv.prototype1.Kalkulatorer.MasseOgVolumBalanse;
import com.bbv.prototype1.Kalkulatorer.OljeVann;
import com.bbv.prototype1.Kalkulatorer.Plast_Viskos;
import com.bbv.prototype1.Kalkulatorer.PowerLaw1006;
import com.bbv.prototype1.Kalkulatorer.PowerLaw600300;
import com.bbv.prototype1.Kalkulatorer.Spess_tetthet;
import com.bbv.prototype1.Kalkulatorer.Table_3_1_calc;
import com.bbv.prototype1.Kalkulatorer.Table_9_4_calc;
import com.bbv.prototype1.Kalkulatorer.Til_Viskos;
import com.bbv.prototype1.Kalkulatorer.TotalHardhet;
import com.bbv.prototype1.Tables.Table_3_1;
import com.bbv.prototype1.Tables.Table_3_3;

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
		Intent i;
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		case R.id.calc_info:
			i = new Intent("com.bbv.prototype1.SHOWCALCINFO");
			startActivity(i);
			break;
		case R.id.calcSettings:
			i = new Intent("com.bbv.prototype1.SETCALCPREFS");
			startActivity(i);
			break;
		case R.id.goBackHome:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;

	}

	private void initialize() {
		/**
		 * Creates a spinner object if the device is in a vertical position, or
		 * a listview if the device is in a horizontal position
		 */

		ArrayAdapter<Spannable> adapter = new ArrayAdapter<Spannable>(this,
				R.layout.custom_spinner);

		for (int i = 0; i < getResources().getStringArray(R.array.calculator).length; i++) {
			String text = getResources().getStringArray(R.array.calculator)[i];
			adapter.add((Spannable) Html.fromHtml(text));
		}

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
			calc = new PowerLaw600300(lLayout.getContext());
			break;
		case 5:
			calc = new PowerLaw1006(lLayout.getContext());
			break;
		case 6:
			calc = new Herch_Law(lLayout.getContext());
			break;
		case 7:
			calc = new Spess_tetthet(lLayout.getContext());
			break;
		case 8:
			calc = new KloridInnhold(lLayout.getContext());
			break;
		case 9:
			calc = new TotalHardhet(lLayout.getContext());
			break;
		case 10:
			calc = new CEC(lLayout.getContext());
			break;
		case 11:
			calc = new AlkaPm(lLayout.getContext());
			break;
		case 12:
			calc = new Alkalitet(lLayout.getContext());
			break;
		case 13:
			calc = new OljeVann(lLayout.getContext());
			break;
		case 14:
			calc = new CaOHInnhold(lLayout.getContext());
			break;
		case 15:
			calc = new KloridInnholdIVannfasen(lLayout.getContext());
			break;
		case 16:
			calc = new Table_9_4_calc(lLayout.getContext());
			break;
		case 17:
			calc = new KonverterM3(lLayout.getContext());
			break;
		case 18:
			calc = new Table_3_1_calc(lLayout.getContext());
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

	/**
	 * Loads previous choice in either the spinner or the listview
	 * 
	 * @return int - previous choice
	 */
	private int loadLC() {

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		int restoredInt = prefs.getInt(lastChoiceMadeKey, 0);

		return restoredInt;

	}

	/**
	 * Saves the current position of the selected item in either the spinner or
	 * the listview
	 * 
	 * @param index
	 *            - int index
	 */

	private void saveLC(int index) {

		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putInt(lastChoiceMadeKey, index);
		editor.commit();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater blowup = getMenuInflater();
		blowup.inflate(R.menu.settings_menu_calc, menu);
		return true;
	}

}