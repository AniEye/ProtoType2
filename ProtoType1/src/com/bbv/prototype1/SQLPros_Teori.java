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

	String _ContentIndexChapter = null, _ContentIndexChapterPart1 = null,
			_ContentIndexChapterPart2 = null;

	/**
	 * Sets contentview and does the Initialize() method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlpros_teori);
		Initialize();
	}

	
	/**
	 * creates a reference to all the relevant elements in the xml file and give
	 * some of them a listener
	 */
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

		/**
		 * this method will set up the chapter spinner based on the relevant
		 * database
		 */
		InitializeChapterSpinner();
	}

	/**
	 * this 3 methods is to reset the other spinners based on the new arrays
	 * passed in, these arrays have been retrieved from the relevant database
	 * based on the changes made to the different spinners * @param array
	 */
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

	private void ChangeFileNameSpinner() {
		SQLDatabase getFileName = new SQLDatabase(this);
		getFileName.open();

		String[] gotFileNames = getFileName.getFileName(_ContentIndexChapter,
				_ContentIndexChapterPart1, _ContentIndexChapterPart2);

		getFileName.close();
		_AAFileName = new ArrayAdapter<CharSequence>(this, spinnerLayout,
				gotFileNames);

		_AAFileName.setDropDownViewResource(SpinnerItemLayout);
		_sFileName.setAdapter(_AAFileName);

	}

	/**
	 * this is used to set up the chapter spinner in the beginning this spinner
	 * does not need to be changed after first being created
	 */
	private void InitializeChapterSpinner() {

		SQLDatabase entry = new SQLDatabase(SQLPros_Teori.this);
		entry.open();

		String[] chapters = entry.getColumnGrouped(
				new String[] { SQLDatabase.KEY_CHAPTER },
				SQLDatabase.KEY_CHAPTER, null);
		entry.close();

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, spinnerLayout, chapters);
		adapter.setDropDownViewResource(SpinnerItemLayout);
		_sChapter.setAdapter(adapter);
	}

	/**
	 * this is run when a button or something that has been given an OnClickListener
	 * is pressed
	 */
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
	

	public String[] getStrings(String where) {
		SQLDatabase getArray = new SQLDatabase(this);
		getArray.open();
		String[] getTheArray = new String[] {};
		// String[] gotTheArray =

		return new String[4];
	}

	/**
	 * this itemSelected is used for the spinners and runs when one of the
	 * spinner has changed the selected item
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int index,
			long arg3) {
		switch (parent.getId()) {
		case R.id.sSQLChapter:
			if (index == 0) {
				_sChapterPart1.setVisibility(android.view.View.GONE);
				_ContentIndexChapter = null;
			} else {
				String where = ((TextView) _sChapter.getSelectedView())
						.getText().toString();
				_ContentIndexChapter = where;
				where = SQLDatabase.KEY_CHAPTER + " = '" + where + "' ";
				where = SQLDatabase.KEY_CHAPTERPART1 + " != '' and " + where;

				SQLDatabase getPartition1 = new SQLDatabase(this);
				getPartition1.open();
				String[] getPartition1s = getPartition1.getColumnGrouped(
						new String[] { SQLDatabase.KEY_CHAPTERPART1 },
						SQLDatabase.KEY_CHAPTERPART1, where);
				getPartition1.close();
				ChangeChapterPart1Spinner(getPartition1s);
				_sChapterPart1.setVisibility(android.view.View.VISIBLE);
			}
			ChangeFileNameSpinner();
			break;

		case R.id.sSQLChapterPart1:
			if (index == 0) {
				_sChapterPart2.setVisibility(android.view.View.GONE);
				_ContentIndexChapterPart1 = null;
			} else {
				String where = ((TextView) _sChapterPart1.getSelectedView())
						.getText().toString();
				_ContentIndexChapterPart1 = where;
				where = SQLDatabase.KEY_CHAPTERPART1 + " = '" + where + "' ";
				where = SQLDatabase.KEY_CHAPTERPART2 + " != '' and " + where;

				SQLDatabase getPartition1 = new SQLDatabase(this);
				getPartition1.open();
				String[] getPartition1s = getPartition1.getColumnGrouped(
						new String[] { SQLDatabase.KEY_CHAPTERPART2 },
						SQLDatabase.KEY_CHAPTERPART2, where);
				getPartition1.close();
				ChangeChapterPart2Spinner(getPartition1s);
				_sChapterPart2.setVisibility(android.view.View.VISIBLE);
			}
			ChangeFileNameSpinner();
			break;

		case R.id.sSQLChapterPart2:
			if (index == 0) {
				_ContentIndexChapterPart2 = null;
			} else {
				String where = ((TextView) _sChapterPart2.getSelectedView())
						.getText().toString();
				_ContentIndexChapterPart2 = where;
			}
			ChangeFileNameSpinner();
			break;

		case R.id.sSQLFileName:

			break;
		}
	}

	/**
	 * never used in this class
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
