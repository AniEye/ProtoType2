package com.bbv.prototype1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SQLTest extends Activity implements OnClickListener,
		OnItemSelectedListener {
	// Retrieving data
	protected TextView _chapter, _chapterPart1, _chapterPart2, _FileName;
	protected Spinner _sTable, _sChapterPart1, _sChapterPart2, _sChapter,
			_sFileName;
	protected Button _bView;
	// Submitting data
	protected EditText _eChapter, _eChapterPart1, _eChapterPart2, _eFileName;
	protected Button _bAdd, _bDatabaseCreate, _bDatabaseDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqltest);
		InitializeSQLChangerEntries();
		InitializeSQLRetriveEntries();
	}

	private void InitializeSQLRetriveEntries() {
		_chapter = (TextView) findViewById(R.id.tvSQLChapter);
		_chapterPart1 = (TextView) findViewById(R.id.tvSQLChapterPart_1);
		_chapterPart2 = (TextView) findViewById(R.id.tvSQLChapterPart_2);

		_sChapter = (Spinner) findViewById(R.id.sSQLChapter);
		_sChapterPart1 = (Spinner) findViewById(R.id.sSQLChapterPart1);
		_sChapterPart2 = (Spinner) findViewById(R.id.sSQLChapterPart2);
		_sTable = (Spinner) findViewById(R.id.sSQLTable);
		_sFileName = (Spinner) findViewById(R.id.sSQLFileName);

		_bView = (Button) findViewById(R.id.bSQLViewDatabase);

		_sChapter.setOnItemSelectedListener(this);
		_sChapterPart1.setOnItemSelectedListener(this);
		_sChapterPart2.setOnItemSelectedListener(this);
		_sTable.setOnItemSelectedListener(this);
		_sFileName.setOnItemSelectedListener(this);

		_bView.setOnClickListener(this);
	}

	private void InitializeSQLChangerEntries() {
		_eChapter = (EditText) findViewById(R.id.etSQLChapter);
		_eChapterPart1 = (EditText) findViewById(R.id.etSQLChapterPart1);
		_eChapterPart2 = (EditText) findViewById(R.id.etSQLChapterPart2);
		_eFileName = (EditText) findViewById(R.id.etSQLFileNameAdd);

		_bAdd = (Button) findViewById(R.id.bSQLAdd);
		_bDatabaseCreate = (Button) findViewById(R.id.bSQLCreateDatabse);
		_bDatabaseDelete = (Button) findViewById(R.id.bSQLDeleteDatabase);

		_bAdd.setOnClickListener(this);
		_bDatabaseCreate.setOnClickListener(this);
		_bDatabaseDelete.setOnClickListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int index,
			long arg3) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sSQLChapter:
			break;
		case R.id.sSQLChapterPart1:
			break;
		case R.id.sSQLChapterPart2:
			break;
		case R.id.sSQLTable:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSQLViewDatabase:
			Log.println(Log.ERROR, "SQLDB", "Creating view intent");
			Intent i = new Intent("com.bbv.prototype1.SQLTESTVIEW");
			Log.println(Log.ERROR, "SQLDB", "Starting view intent");
			startActivity(i);
			break;
		case R.id.bSQLAdd:
			boolean didItWork = true;
			try {
				String chapter = _eChapter.getText().toString();
				String chapterPart1 = _eChapterPart1.getText().toString();
				String chapterPart2 = _eChapterPart2.getText().toString();
				String fileName = _eFileName.getText().toString();
				SQLPros_Og_Teori entry = new SQLPros_Og_Teori(SQLTest.this);
				entry.open();
				entry.createEntry(chapter, chapterPart1, chapterPart2, fileName);
				entry.close();
			} catch (Exception e) {
				didItWork = false;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Dang it!");
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
		case R.id.bSQLCreateDatabse:
			SQLPros_Og_Teori DBCreate = new SQLPros_Og_Teori(SQLTest.this);
			DBCreate.open();
			DBCreate.create();
			DBCreate.close();
			break;
		case R.id.bSQLDeleteDatabase:
			SQLPros_Og_Teori DBDelete = new SQLPros_Og_Teori(SQLTest.this);
			DBDelete.open();
			DBDelete.delete();
			DBDelete.close();
			break;
		}

	}

}
