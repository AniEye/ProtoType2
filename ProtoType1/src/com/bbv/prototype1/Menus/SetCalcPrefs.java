package com.bbv.prototype1.Menus;

import android.app.Activity;
import android.os.Bundle;

public class SetCalcPrefs extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new CalcPrefs()).commit();
	}
}
