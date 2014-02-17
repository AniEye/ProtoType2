package com.bbv.prototype1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.view.ActionProvider.VisibilityListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SQLPros_Teori extends Activity implements OnClickListener,
		OnItemSelectedListener {

	Button _add, _view, _fileView;
	EditText _filename, _chapter, _chapterpart1, _chapterpart2;
	Spinner _sChapter, _sChapterPart1, _sChapterPart2, _sFileName;

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
	public void onItemSelected(AdapterView<?> arg0, View v, int index, long arg3) {
		switch (v.getId()) {
		case R.id.sSQLChapter:
			if (index == 0)
				_sChapterPart1.setVisibility(android.view.View.GONE);
			break;

		case R.id.sSQLChapterPart1:
			if (index == 0)
				_sChapterPart2.setVisibility(android.view.View.GONE);
			break;

		case R.id.sSQLChapterPart2:
			break;

		case R.id.sSQLFileName:
			if (index == 0)
				_sChapterPart1.setVisibility(android.view.View.GONE);
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
