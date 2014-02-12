package com.bbv.prototype1;

import com.bbv.prototype1.Kalkulatorer.Til_Viskos;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Kalkulator extends Activity implements OnItemSelectedListener {

	Spinner valg;
	LinearLayout lLayout;
	String[] kalks = { "Til_Viskos"};

	// test
	Til_Viskos visk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		initialize();
	}

	private void initialize() {

		valg = (Spinner) findViewById(R.id.sKalk);
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

			break;
		case 1:
			if (lLayout.getChildCount() > 0)
				lLayout.removeAllViews();
			visk = new Til_Viskos(lLayout.getContext());
			lLayout.addView(visk);

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