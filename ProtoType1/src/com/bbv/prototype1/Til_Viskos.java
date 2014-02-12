package com.bbv.prototype1;

import android.content.Context;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Til_Viskos extends LinearLayout {
	LinearLayout _linLay;
	EditText _theta, _RPM, _tilVisk;
	Button _clear;
	int[] _textFieldsStatus = { 0, 0, 0 };
	Context cont;
	OnFocusChangeListener focChan;
	OnClickListener cliLis;

	public Til_Viskos(Context context) {
		super(context);
		cont= context;
		// TODO Auto-generated constructor stub
		CreateListeners();
		Initialize();
		//lol
	}

	private void Initialize() {
		// TODO Auto-generated method stub
		_linLay = (LinearLayout) LayoutInflater.from(cont).inflate(
				R.layout.activity_viskositet__tilsynelatende, this);
		_theta = (EditText) _linLay.findViewById(R.id.etTheta);
		_RPM = (EditText) _linLay.findViewById(R.id.etRPM);
		_tilVisk = (EditText) _linLay.findViewById(R.id.etViskosTil);
		
		_theta.setOnFocusChangeListener(focChan);
		_RPM.setOnFocusChangeListener(focChan);
		_tilVisk.setOnFocusChangeListener(focChan);		
	}
	
	private void CreateListeners(){
		
		cliLis = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				_theta.setText("");
				_RPM.setText("");
				_tilVisk.setText("");
				_textFieldsStatus = new int[] { 0, 0, 0 };
				_theta.setEnabled(true);
				_tilVisk.setEnabled(true);
				_RPM.setEnabled(true);
			}
		};
		
		focChan = new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				switch (v.getId()) {
				case R.id.etViskosTil:
					FocusChange(_tilVisk, 0, hasFocus);
					break;
				case R.id.etTheta:
					FocusChange(_theta, 1, hasFocus);
					break;
				case R.id.etRPM:
					FocusChange(_RPM, 2, hasFocus);
					break;
				}
			}
		};
	}

	private void FocusChange(EditText theCurrentField, int index,
			boolean focusStatus) {
		String _fieldsString = theCurrentField.getText().toString();
		String _thetaString = _theta.getText().toString();
		String _tilSynViskosString = _tilVisk.getText().toString();
		String _RPMString = _RPM.getText().toString();

		if (theSum() < 2) {
			if (focusStatus == false && !_fieldsString.contentEquals("")) {
				try {
					if (Float.parseFloat(_fieldsString) != 0) {
						_textFieldsStatus[index] = 1;
					}
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
			}
		} else {
			if (_textFieldsStatus[index] == 1) {
				if (focusStatus == false && _fieldsString.contentEquals("")) {
					try {
						if (Float.parseFloat(_fieldsString) == 0) {
							_textFieldsStatus[index] = 0;
							Enabeling();
						}
					} catch (NumberFormatException e) {
						_textFieldsStatus[index] = 0;
						Enabeling();
					}

				} else if (focusStatus == false
						&& !_fieldsString.contentEquals("")) {
					updateRelevantResult(_thetaString, _RPMString,
							_tilSynViskosString);
				}

			} else {
				updateRelevantResult(_thetaString, _RPMString,
						_tilSynViskosString);
				theCurrentField.setEnabled(false);
			}
		}
	}

	private void Enabeling() {
		if (!_theta.isEnabled()) {
			_theta.setEnabled(true);
			_theta.setText("");
		} else if (!_tilVisk.isEnabled()) {
			_tilVisk.setEnabled(true);
			_tilVisk.setText("");
		} else if (!_RPM.isEnabled()) {
			_RPM.setEnabled(true);
			_RPM.setText("");
		}
	}

	private String calculation(int type, float number1, float number2) {
		float theAnswer = 0;

		switch (type) {
		case 0:
			theAnswer = (float) ((300.0 * number1) / number2);
			break;
		case 1:
			theAnswer = (float) ((number1 * number2) / 300);
			break;
		}
		return theAnswer + "";
	}

	private void updateRelevantResult(String _thetaString, String _RPMString,
			String _tilSynViskosString) {
		if (_textFieldsStatus[0] == 0) {
			_tilVisk.setText(calculation(0, Float.parseFloat(_thetaString),
					Float.parseFloat(_RPMString)));
			_tilVisk.setEnabled(false);
		} else if (_textFieldsStatus[1] == 0) {
			_theta.setText(calculation(1,
					Float.parseFloat(_tilSynViskosString),
					Float.parseFloat(_RPMString)));
			_theta.setEnabled(false);
		} else if (_textFieldsStatus[2] == 0) {
			_RPM.setText(calculation(0, Float.parseFloat(_thetaString),
					Float.parseFloat(_tilSynViskosString)));
			_RPM.setEnabled(false);
		}
	}

	private int theSum() {
		return _textFieldsStatus[0] + _textFieldsStatus[1]
				+ _textFieldsStatus[2];
	}
}
