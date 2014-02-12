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

public class Pros_og_Teori extends Activity implements OnItemSelectedListener{

	Spinner kapittel,delKapittel,delDKapittel;
	Button gVidere;
	ArrayAdapter<CharSequence> AAdelKapittel1,AAdelKapittel2,AAdelKapittel3,AAdelKapittel4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pros_og__teori);
		arrayAdapters();
		initialize();		
	}



	private void arrayAdapters() {
		 AAdelKapittel1 = ArrayAdapter.createFromResource(this, R.array.del_kapittel1, android.R.layout.simple_spinner_item);
		 AAdelKapittel1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		 AAdelKapittel2 = ArrayAdapter.createFromResource(this, R.array.del_kapittel2, android.R.layout.simple_spinner_item);
		 AAdelKapittel2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		 AAdelKapittel3 = ArrayAdapter.createFromResource(this, R.array.del_kapittel3, android.R.layout.simple_spinner_item);
		 AAdelKapittel3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		 AAdelKapittel4 = ArrayAdapter.createFromResource(this, R.array.del_kapittel4, android.R.layout.simple_spinner_item);
		 AAdelKapittel4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}


	private void initialize() {
		kapittel = (Spinner) findViewById(R.id.sKapittel);
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
		switch(parent.getId()){
		case R.id.sKapittel:
			switch(index){
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
			case 3://kapitel 3
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
			switch(index){
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