package com.bbv.prototype1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Ovinger extends Activity implements OnItemSelectedListener,
		OnClickListener {

	Spinner oving;
	Button gVidere;
	ArrayAdapter<CharSequence> delOving;
	int SpinnerLayout = R.layout.custom_spinner;
	final int OVING1 = 0, OVING2 = 1, OVING3 = 2, OVING4 = 3, OVING5 = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ovinger);
		arrayAdapterSetup();
		initialize();
	}

	private void arrayAdapterSetup() {

		delOving = ArrayAdapter.createFromResource(this, R.array.ovinger,
				SpinnerLayout); // Adds the layout to the second spinner here
		delOving.setDropDownViewResource(SpinnerLayout);

	}

	private void initialize() {
		oving = (Spinner) findViewById(R.id.sOvinger);

		// Adds the custom look for the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.custom_spinner, getResources().getStringArray(
						R.array.ovinger));
		oving.setAdapter(adapter);
		// Adds the custom look for the spinner

		gVidere = (Button) findViewById(R.id.bVidere_Oving);

		gVidere.setOnClickListener(this);
		oving.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ovinger, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int index,
			long arg3) {
		switch (parent.getId()) {
		case R.id.sOvinger:
			switch (index) {
			case OVING1:

				break;
			case OVING2:

				break;
			case OVING3:

				break;
			case OVING4:

				break;
			case OVING5:

				break;
			}
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}