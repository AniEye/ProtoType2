package com.bbv.prototype1.Kalkulatorer;

import com.bbv.prototype1.R;
import android.content.Context;

public class Flytegrense extends Basic_Calc {

	final static int layout = R.layout.calc_flytegrense;

	final static int[] IDs = { R.id.etFG, R.id.etFGTheta300, R.id.etFGTheta600 };

	public final static int FG_INDEX = 0, T3_INDEX = 1, T6_INDEX = 2;

	public Flytegrense(Context context) {
		super(context, IDs, layout);
	}

	@Override
	public String calculation(int variableToCalculate, float... fieldStatuses) {

		float FG = fieldStatuses[0];
		float T3 = fieldStatuses[1];
		float T6 = fieldStatuses[2];

		float theAnswer = 0;
		boolean wasThatFG = false;
		switch (variableToCalculate) {
		case FG_INDEX:

			if (checkForNullValues(T6, T3) == false)
				return "";

			wasThatFG = true;
			theAnswer = (2 * T3) - T6;
			break;
		case T3_INDEX:
			if (checkForNullValues(T6, FG) == false)
				return "";
			theAnswer = (FG + T6) / 2;
			break;
		case T6_INDEX:
			if (checkForNullValues(FG, T3) == false)
				return "";
			theAnswer = (2 * T3) - FG;
			break;
		}

		if (checkForDivisionErrors(theAnswer) == false)
			return "";

		if (checkForNullValues(theAnswer) == false)
			return "";
		else{
			if (wasThatFG)
			return (String.format(ONE_DECIMAL, theAnswer) + " [lbs/100 ft^2]");
			else
				return String.format(ONE_DECIMAL, theAnswer);
				
		}
	}

}