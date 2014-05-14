package com.bbv.prototype1.Menus;

import com.bbv.prototype1.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class CalcPrefs extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.calc_prefs);
	}
	

}
