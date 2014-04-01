package com.bbv.prototype1;

import com.bbv.prototype1.Database.Database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Ovinger extends Activity implements OnClickListener {

	protected Spinner _sOving;
	protected Button _gVidere;
	protected ArrayAdapter<CharSequence> _AAOving;

	protected int SpinnerLayout = R.layout.custom_spinner;
	protected int SpinnerItemLayout = R.layout.custom_spinner_item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ovinger);

		initialize();
		InitializeSpinnerAdapter();
	}

	private void OvingAdapter(String[] array) {
		_AAOving = new ArrayAdapter<CharSequence>(this, SpinnerLayout, array);
		_AAOving.setDropDownViewResource(SpinnerItemLayout);
		_sOving.setAdapter(_AAOving);
	}

	private void InitializeSpinnerAdapter() {
		Database ovingTable = new Database(Ovinger.this);
		ovingTable.open();
		String[] ovinger = ovingTable.getOvingTableRows();
		ovingTable.close();
		OvingAdapter(ovinger);
	}

	private void initialize() {
		_sOving = (Spinner) findViewById(R.id.sOvinger);
		_gVidere = (Button) findViewById(R.id.bVidere_Oving);

		_gVidere.setOnClickListener(this);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ovinger, menu);
		return true;
	}

	private String getStringOfSelected(Spinner s) {
		return ((TextView) s.getSelectedView()).getText().toString();
	}

	@Override
	public void onClick(View v) {
		Bundle newBundle = new Bundle();
		newBundle.putString(ShowContentBase.KEY_OVING,
				getStringOfSelected(_sOving));
		Intent newIntent = new Intent(ShowContentBase.KEY_SHOWCONTENT);
		newIntent.putExtra(ShowContentBase.KEY_OVING, newBundle);
		startActivityForResult(newIntent, 2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 2) {
			finish();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}