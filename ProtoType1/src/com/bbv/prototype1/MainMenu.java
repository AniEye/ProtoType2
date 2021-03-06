package com.bbv.prototype1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {

	String[] menuList = { "Pros_og_Teori", "Kalkulator", "Ovinger",
			"SQLPros_Teori" };
	int[] buttonIDs = { R.id.bTeori, R.id.bKalk, R.id.bOvinger,
			R.id.bSQLDatabase };

	Button[] buttonList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);

		buttonList = new Button[buttonIDs.length];

		for (int i = 0; i < buttonIDs.length; i++) {
			buttonList[i] = (Button) findViewById(buttonIDs[i]);
			buttonList[i].setOnClickListener(this);
		}
	}// KA SKJER?!?!

	@Override
	public void onClick(View v) {
		String selected = "";
		for (int i = 0; i < buttonIDs.length; i++) {
			if (v.getId() == buttonIDs[i]) {
				selected = menuList[i];
				break;
			}
		}
		try {
			Class<?> selectedClass = Class.forName("com.bbv.prototype1."
					+ selected);
			Intent createIntent = new Intent(MainMenu.this, selectedClass);
			startActivity(createIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowup = getMenuInflater();
		blowup.inflate(R.menu.settings_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.aboutUs:
			Intent i = new Intent("com.bbv.prototype1.ABOUT");
			startActivity(i);
			break;
		case R.id.calcSettings:
			Intent p = new Intent("com.bbv.prototype1.SETCALCPREFS");
			startActivity(p);
			break;
		case R.id.exit:
			finish();
			break;

		default:
			Log.e("Main", "Error when selecting options from menu!");
			break;
		}
		return false;

	}
}