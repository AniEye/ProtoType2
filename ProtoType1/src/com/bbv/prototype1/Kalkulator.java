package com.bbv.prototype1;

import com.bbv.prototype1.Kalkulatorer.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Kalkulator extends Activity implements OnItemSelectedListener {

	Spinner valg;
	LinearLayout lLayout;
	String[] kalks = { "Til_Viskos" };
	int SpinnerItemLayout = R.layout.custom_spinner_item;

	Basic_Calc calc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initialize();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initialize() {
		valg = (Spinner) findViewById(R.id.sKalk);

		// Adds the custom look for the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.custom_spinner, getResources().getStringArray(
						R.array.calculator));
		adapter.setDropDownViewResource(SpinnerItemLayout);
		valg.setAdapter(adapter);
		// Adds the custom look for the spinner

		lLayout = (LinearLayout) findViewById(R.id.lInSVKalk);
		valg.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int index,
			long arg3) {

		if (lLayout.getChildCount() > 0)
			lLayout.removeAllViews();

		switch (index) {
		case 0:
			calc = new MasseBalanse(lLayout.getContext());
			break;
		case 1:
			calc = new VolumBalanse(lLayout.getContext());
			break;
		case 2:
			calc = new Slamtetthet(lLayout.getContext());
			break;
		case 3:
			calc = new Skjaerhastighet(lLayout.getContext());
			break;
		case 4:
			calc = new Til_Viskos(lLayout.getContext());
			break;
		case 5:
			calc = new Plast_Viskos(lLayout.getContext());
			break;
		case 6:
			calc = new Flytegrense(lLayout.getContext());
			break;
		case 7:
//			calc = new Ekspo_model(lLayout.getContext());
			break;
		case 8:
//			calc = new Hersch_model(lLayout.getContext());
			break;
		case 9:
//			calc = new Darcy_filtertap(lLayout.getContext());
			break;
		case 10:
//			calc = new Stat_filtrering(lLayout.getContext());
			break;
		case 11:
//			calc = new Filtertap_Tid(lLayout.getContext());
			break;
		case 12:
//			calc = new Filtertap_Trykk(lLayout.getContext());
			break;
		case 13:
//			calc = new Filtertap_Temp(lLayout.getContext());
			break;
		case 14:
//			calc = new Spess_tetthet(lLayout.getContext());
			break;
		case 15:
//			calc = new Klorinnhold(lLayout.getContext());
			break;
		}

		try {
			lLayout.addView(calc);
		} catch (NullPointerException e) {
			// TODO: handle exception
			Log.println(Log.ERROR, "TryCatch",
					"Somehow no calculator was selected!");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}