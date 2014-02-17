package com.bbv.prototype1;

import com.bbv.prototype1.Kalkulatorer.MasseBalanse;
import com.bbv.prototype1.Kalkulatorer.Til_Viskos;
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
	String[] kalks = {"Til_Viskos"};
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
		
		//Adds the custom look for the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, 
				getResources().getStringArray(R.array.calculator));
		adapter.setDropDownViewResource(SpinnerItemLayout);
		valg.setAdapter(adapter);
		//Adds the custom look for the spinner
				
		lLayout = (LinearLayout) findViewById(R.id.lInSVKalk);
		valg.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int index,
			long arg3) {
		switch (index) {
		case 0:
			if (lLayout.getChildCount() > 0)
				lLayout.removeAllViews();
			Log.println(Log.DEBUG, "calc", "Adding Calc1!");
			calc = new MasseBalanse(lLayout.getContext());
			lLayout.addView(calc);
			Log.println(Log.DEBUG, "calc", "Added Calc1!");
			break;
		case 1:
			if (lLayout.getChildCount() > 0)
				lLayout.removeAllViews();
			Log.println(Log.DEBUG, "calc", "Adding Calc2!");
			calc = new Til_Viskos(lLayout.getContext());
			lLayout.addView(calc);
			Log.println(Log.DEBUG, "calc", "Added Calc2!");
			break;
		case 2:
			if (lLayout.getChildCount() > 0)
				lLayout.removeAllViews();
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	
}