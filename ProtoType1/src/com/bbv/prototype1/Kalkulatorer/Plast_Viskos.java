package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;

public class Plast_Viskos extends Basic_Calc {

	final static int layout = R.layout.calc_viskositet_plastisk;

	final static int[] IDs = { R.id.etViskosPlas, R.id.etPlasViskTheta600,
			R.id.etPlasviskTheta300 };

	public final static int PV_INDEX = 0, T6_INDEX = 1, T3_INDEX = 2;

	public Plast_Viskos(Context context) {
		super(context, IDs, layout);

	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float PV = fieldStatuses[PV_INDEX];
		float T6 = fieldStatuses[T6_INDEX];
		float T3 = fieldStatuses[T3_INDEX];

		float theAnswer = 0;
		boolean wasThatPV = false;
		
		switch (variableToCalculate) {
		case PV_INDEX:
			if (checkForNullValues(T6, T3) == false)
				return "";
			theAnswer = T6 - T3;
			wasThatPV = true;
			break;
		case T6_INDEX:
			if (checkForNullValues(PV, T3) == false)
				return "";
			theAnswer = PV + T3;
			break;
		case T3_INDEX:
			if (checkForNullValues(T6, PV) == false)
				return "";
			theAnswer = T6 - PV;
			break;
		}
		if(checkForDivisionErrors(theAnswer) == false)
			return "";
		else {
			if(wasThatPV)
				return String.format(ONE_DECIMAL, theAnswer) + " [cP]";				
			else 
				return String.format(ONE_DECIMAL, theAnswer);
		}
	}
	
}