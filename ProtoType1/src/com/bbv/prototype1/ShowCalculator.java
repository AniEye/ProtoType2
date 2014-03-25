package com.bbv.prototype1;

import com.bbv.prototype1.Kalkulatorer.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.MasseOgVolumBalanse;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ShowCalculator extends Fragment {
	private View _rootView;
	private LinearLayout _LinearLayout;
	private Basic_Calc[] _basicCalcList;
	private String[] _classList = { "Alkalitet", "AlkaPm", "CaOHInnhold" };
	private String _ChoosenCalc;
	
	public static final String KEY_CHOSENCALC = "TheChoosenCalc";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_rootView = inflater.inflate(R.layout.showcalculator, container, false);
		_LinearLayout = (LinearLayout) _rootView.findViewById(R.id.llShowCalc);
		_ChoosenCalc = getArguments().getString(KEY_CHOSENCALC);
		createBasicCalcList();

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void createBasicCalcList() {
		_basicCalcList = new Basic_Calc[_classList.length]; 
		if(_ChoosenCalc.contentEquals(_classList[0])){
			
		}
	}

}
