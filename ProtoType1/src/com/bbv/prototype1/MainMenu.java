package com.bbv.prototype1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {

	String[] menuList = { "Pros_og_Teori", "Kalkulator", "Ovinger" ,"SQLPros_Teori","TestNewListAdapter"};
	int[] buttonIDs = {R.id.bTeori,R.id.bKalk,R.id.bOvinger,R.id.bSQLDatabase,R.id.bTNLA};

	Button[] buttonList;
	/**
	 * HEI ANDRE!
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu); 

		buttonList = new Button[buttonIDs.length];

		for(int i =0;i<buttonIDs.length;i++){
			buttonList[i] = (Button) findViewById(buttonIDs[i]);
			buttonList[i].setOnClickListener(this);
		}
	}//KA SKJER?!?!

	@Override
	public void onClick(View v) {
		String selected = "";
		for(int i = 0;i<buttonIDs.length;i++){
			if(v.getId()==buttonIDs[i]){
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
}