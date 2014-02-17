package com.bbv.prototype1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.view.ActionProvider.VisibilityListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SQLPros_Teori extends Activity implements OnClickListener,
		OnItemSelectedListener {

	Button _add, _view, _fileView;
	EditText _filename, _chapter, _chapterpart1, _chapterpart2;
	Spinner _sChapter, _sChapterPart1, _sChapterPart2, _sFileName;
	ArrayAdapter<CharSequence> _AAChapter, _AAChapterPart1, _AAChapterPart2,
			_AAFileName;

	int spinnerLayout = R.layout.custom_spinner;
	int SpinnerItemLayout = R.layout.custom_spinner_item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlpros_teori);
		Initialize();
	}

	private void Initialize() {
		// TODO Auto-generated method stub
		_add = (Button) findViewById(R.id.bSQLPoTAdd);
		_view = (Button) findViewById(R.id.bSQLPoTViewDatabase);
		_fileView = (Button) findViewById(R.id.bSQLPoTViewFile);

		_filename = (EditText) findViewById(R.id.etSQLPoTFileNameAdd);
		_chapter = (EditText) findViewById(R.id.etSQLPoTChapter);
		_chapterpart1 = (EditText) findViewById(R.id.etSQLPoTChapterPart1);
		_chapterpart2 = (EditText) findViewById(R.id.etSQLPoTChapterPart2);

		_sChapter = (Spinner) findViewById(R.id.sSQLChapter);
		_sChapterPart1 = (Spinner) findViewById(R.id.sSQLChapterPart1);
		_sChapterPart2 = (Spinner) findViewById(R.id.sSQLChapterPart2);
		_sFileName = (Spinner) findViewById(R.id.sSQLFileName);

		_add.setOnClickListener(this);
		_view.setOnClickListener(this);
		_fileView.setOnClickListener(this);

		_sChapter.setOnItemSelectedListener(this);
		_sChapterPart1.setOnItemSelectedListener(this);
		_sChapterPart2.setOnItemSelectedListener(this);
		_sFileName.setOnItemSelectedListener(this);
		// InitializeArrayAdapters();
		InitializeChapterSpinner();
	}

	private void InitializeArrayAdapters() {
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, spinnerLayout, getResources().getStringArray(
						R.array.kapittler));
		adapter.setDropDownViewResource(SpinnerItemLayout);

		_AAChapter = ArrayAdapter.createFromResource(this,
				R.array.del_kapittel4, spinnerLayout);
		_AAChapter.setDropDownViewResource(SpinnerItemLayout);
	}

	private void ChangeChapterPart1Spinner(String[] array) {

		_AAChapterPart1 = new ArrayAdapter<CharSequence>(this, spinnerLayout,
				array);
		_AAChapterPart1.setDropDownViewResource(SpinnerItemLayout);
		_sChapterPart1.setAdapter(_AAChapterPart1);
	}

	private void ChangeChapterPart2Spinner(String[] array) {
		_AAChapterPart2 = new ArrayAdapter<CharSequence>(this, spinnerLayout,
				array);
		_AAChapterPart2.setDropDownViewResource(SpinnerItemLayout);
		_sChapterPart2.setAdapter(_AAChapterPart2);
	}

	private void ChangeFileNameSpinner(String[] array) {
		_AAFileName = new ArrayAdapter<CharSequence>(this, spinnerLayout, array);
		_AAFileName.setDropDownViewResource(SpinnerItemLayout);
		_sFileName.setAdapter(_AAFileName);
	}

	private void InitializeChapterSpinner() {
		// TODO Auto-generated method stub
		SQLDatabase entry = new SQLDatabase(SQLPros_Teori.this);
		entry.open();
		// String[] chapters = entry.getChapters();
		String[] chapters = entry.getColumnGrouped(
				new String[] { SQLDatabase.KEY_CHAPTER },
				SQLDatabase.KEY_CHAPTER, null);
		entry.close();

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, spinnerLayout, chapters);
		adapter.setDropDownViewResource(SpinnerItemLayout);
		_sChapter.setAdapter(adapter);
	}

	private String[] ModifyArray(String[] array) {
		String[] returnArray = new String[array.length + 1];
		returnArray[0] = "Choose chapter";
		for (int i = 1; i < returnArray.length; i++) {
			returnArray[i] = array[i - 1];
		}
		return returnArray;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSQLPoTAdd:
			boolean didItWork = true;
			try {
				String chapter = _chapter.getText().toString();
				String chapterPart1 = _chapterpart1.getText().toString();
				String chapterPart2 = _chapterpart2.getText().toString();
				String filename = _filename.getText().toString();

				SQLDatabase entry = new SQLDatabase(SQLPros_Teori.this);
				entry.open();
				entry.createEntry(chapter, chapterPart1, chapterPart2, filename);
				entry.close();
			} catch (Exception e) {
				didItWork = false;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Dop!!");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();
			} finally {
				if (didItWork) {
					Dialog d = new Dialog(this);
					d.setTitle("Heck Yea!");
					TextView tv = new TextView(this);
					tv.setText("Success");
					d.setContentView(tv);
					d.show();
				}
			}
			break;
		case R.id.bSQLPoTViewDatabase:
			Intent i = new Intent("com.bbv.prototype1.SQLVIEW");
			startActivity(i);
			break;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int index,
			long arg3) {
		switch (parent.getId()) {
		case R.id.sSQLChapter:
			if (index == 0)
				_sChapterPart1.setVisibility(android.view.View.GONE);
			else {
				String where = ((TextView) _sChapter.getSelectedView())
						.getText().toString();
				
				where = SQLDatabase.KEY_CHAPTER + " = '" + where + "' ";				
				where = SQLDatabase.KEY_CHAPTERPART1 + " != '' and " + where;
				
				SQLDatabase getPartition1 = new SQLDatabase(this);
				getPartition1.open();
				String[] getPartition1s = getPartition1.getColumnGrouped(
						new String[] { SQLDatabase.KEY_CHAPTERPART1 },
						SQLDatabase.KEY_CHAPTERPART1, where);
				ChangeChapterPart1Spinner(getPartition1s);
				_sChapterPart1.setVisibility(android.view.View.VISIBLE);
			}
			break;

		case R.id.sSQLChapterPart1:
			if (index == 0)
				_sChapterPart2.setVisibility(android.view.View.GONE);
			else {
				_sChapterPart2.setVisibility(android.view.View.VISIBLE);
			}
			break;

		case R.id.sSQLChapterPart2:
			break;

		case R.id.sSQLFileName:

			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
