package com.bbv.prototype1.TheoryAndLabAssignments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.bbv.prototype1.R;
import com.bbv.prototype1.Kalkulatorer.AlkaPm;
import com.bbv.prototype1.Kalkulatorer.Alkalitet;
import com.bbv.prototype1.Kalkulatorer.Basic_Calc;
import com.bbv.prototype1.Kalkulatorer.CEC;
import com.bbv.prototype1.Kalkulatorer.CaOHInnhold;
import com.bbv.prototype1.Kalkulatorer.Flytegrense;
import com.bbv.prototype1.Kalkulatorer.Herch_Law;
import com.bbv.prototype1.Kalkulatorer.KloridInnhold;
import com.bbv.prototype1.Kalkulatorer.KloridInnholdIVannfasen;
import com.bbv.prototype1.Kalkulatorer.KonverterM3;
import com.bbv.prototype1.Kalkulatorer.MasseOgVolumBalanse;
import com.bbv.prototype1.Kalkulatorer.OljeVann;
import com.bbv.prototype1.Kalkulatorer.Plast_Viskos;
import com.bbv.prototype1.Kalkulatorer.PowerLaw1006;
import com.bbv.prototype1.Kalkulatorer.PowerLaw600300;
import com.bbv.prototype1.Kalkulatorer.Spess_tetthet;
import com.bbv.prototype1.Kalkulatorer.Table_3_1_calc;
import com.bbv.prototype1.Kalkulatorer.Table_9_4_calc;
import com.bbv.prototype1.Kalkulatorer.Til_Viskos;
import com.bbv.prototype1.Kalkulatorer.TotalHardhet;

public class ShowCalculator extends Fragment {
	private View _rootView;
	private LinearLayout _LinearLayout;
	private Basic_Calc _basicCalc;
	public static String[] _calcList = { "Alkalitet", "AlkaPm", "CaOHInnhold",
			"CEC", "Flytegrense", "Herch_Law", "KloridInnhold",
			"KloridInnholdVannfasen", "KonverterM3", "MasseOgVolumBalanse",
			"OljeVann", "Plast_Viskos", "PowerLaw1006", "PowerLaw600300",
			"Spess_tetthet", "Table_3_1_calc", "Table_9_4_calc", "Til_Viskos",
			"TotalHardhet" };

	private String _ChoosenCalc;

	/**
	 * Used for the LogCat in this class, makes it easier to reference the same string-key
	 */
	//private static final String KEY_LOGCAT = "ShowCalculator";for debugging purposes

	/**
	 * The method that will return the view of the calculator
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_rootView = inflater.inflate(R.layout.showcalculator, container, false);
		_LinearLayout = (LinearLayout) _rootView.findViewById(R.id.llShowCalc);
		_ChoosenCalc = getArguments().getString(ShowContentBase.KEY_CHOSENCALC);
		initializeBasicCalc();
		_LinearLayout.addView(_basicCalc);

		return _rootView;
	}

	/**
	 * this is a big if statement used to find out based on an incoming string 
	 * what calculator that the user wants to use
	 */
	private void initializeBasicCalc() {
		if (_ChoosenCalc.contentEquals(_calcList[0])) {// Alkalitet
			_basicCalc = new Alkalitet(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[1])) {// AlkaPm
			_basicCalc = new AlkaPm(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[2])) {
			_basicCalc = new CaOHInnhold(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[3])) {
			_basicCalc = new CEC(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[4])) {
			_basicCalc = new Flytegrense(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[5])) {
			_basicCalc = new Herch_Law(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[6])) {
			_basicCalc = new KloridInnhold(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[7])) {
			_basicCalc = new KloridInnholdIVannfasen(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[8])) {
			_basicCalc = new KonverterM3(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[9])) {
			_basicCalc = new MasseOgVolumBalanse(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[10])) {
			_basicCalc = new OljeVann(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[11])) {
			_basicCalc = new Plast_Viskos(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[12])) {
			_basicCalc = new PowerLaw1006(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[13])) {
			_basicCalc = new PowerLaw600300(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[14])) {
			_basicCalc = new Spess_tetthet(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[15])) {
			_basicCalc = new Table_3_1_calc(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[16])) {
			_basicCalc = new Table_9_4_calc(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[17])) {
			_basicCalc = new Til_Viskos(_LinearLayout.getContext());
		} else if (_ChoosenCalc.contentEquals(_calcList[18])) {
			_basicCalc = new TotalHardhet(_LinearLayout.getContext());
		}
	}
}
