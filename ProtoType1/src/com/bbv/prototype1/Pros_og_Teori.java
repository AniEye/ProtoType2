package com.bbv.prototype1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Pros_og_Teori extends Activity implements OnItemSelectedListener {

	Spinner kapittel, delKapittel, delDKapittel;
	Button gVidere;
	ArrayAdapter<CharSequence> AAdelKapittel1, AAdelKapittel2, AAdelKapittel3,
			AAdelKapittel4;
	int spinnerLayout = R.layout.custom_spinner;
	int SpinnerItemLayout = R.layout.custom_spinner_item;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pros_og__teori);
		arrayAdapters();
		initialize();
	}

	private void arrayAdapters() {
		AAdelKapittel1 = ArrayAdapter.createFromResource(this,
				R.array.del_kapittel1, spinnerLayout);
		AAdelKapittel1.setDropDownViewResource(SpinnerItemLayout);

		AAdelKapittel2 = ArrayAdapter.createFromResource(this,
				R.array.del_kapittel2, spinnerLayout);
		AAdelKapittel2.setDropDownViewResource(SpinnerItemLayout);

		AAdelKapittel3 = ArrayAdapter.createFromResource(this,
				R.array.del_kapittel3, spinnerLayout);
		AAdelKapittel3.setDropDownViewResource(SpinnerItemLayout);

		AAdelKapittel4 = ArrayAdapter.createFromResource(this,
				R.array.del_kapittel4, spinnerLayout);
		AAdelKapittel4.setDropDownViewResource(SpinnerItemLayout);
	}

	private void initialize() {
		kapittel = (Spinner) findViewById(R.id.sKapittel);

		// Adds the custom look for the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.custom_spinner, getResources().getStringArray(
						R.array.kapittler));
		adapter.setDropDownViewResource(SpinnerItemLayout);
		kapittel.setAdapter(adapter);
		// Adds the custom look for the spinner

		delKapittel = (Spinner) findViewById(R.id.sDelKapittel);
		delDKapittel = (Spinner) findViewById(R.id.sDDKapittel);
		gVidere = (Button) findViewById(R.id.bVidere_ProsTeori);

		kapittel.setOnItemSelectedListener(this);
		delKapittel.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pros_og__teori, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int index,
			long arg3) {
		switch (parent.getId()) {
		case R.id.sKapittel:
			switch (index) {
			case 0:
				delKapittel.setVisibility(android.view.View.GONE);
				break;
			case 1:
				delKapittel.setAdapter(AAdelKapittel1);
				delKapittel.setVisibility(android.view.View.VISIBLE);
				break;
			case 2:
				delKapittel.setAdapter(AAdelKapittel2);
				delKapittel.setVisibility(android.view.View.VISIBLE);
				break;
			case 3:// kapitel 3
				delKapittel.setAdapter(AAdelKapittel3);
				delKapittel.setVisibility(android.view.View.VISIBLE);
				break;
			case 4:
				delKapittel.setAdapter(AAdelKapittel4);
				delKapittel.setVisibility(android.view.View.VISIBLE);
				break;
			}
			break;
		case R.id.sDelKapittel:
			switch (index) {
			case 0:
				delDKapittel.setVisibility(android.view.View.GONE);
				break;
			case 1:
				delDKapittel.setVisibility(android.view.View.VISIBLE);
				break;
			case 2:
				delDKapittel.setVisibility(android.view.View.GONE);
				break;
			case 3:
				delDKapittel.setVisibility(android.view.View.GONE);
				break;
			case 4:
				delDKapittel.setVisibility(android.view.View.GONE);
				break;
			}
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}