package com.bbv.prototype1;

import java.util.ArrayList;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SQLPros_Teori extends Activity implements OnClickListener,
		OnItemSelectedListener {

	Button _add, _view, _fileView,_delAll,_delEnt,_dbrecreate;
	EditText _filename, _chapter, _chapterpart1, _chapterpart2;
	Spinner _sChapter, _sChapterPart1, _sChapterPart2;
	ArrayAdapter<CharSequence> _AAChapter, _AAChapterPart1, _AAChapterPart2;

	int spinnerLayout = R.layout.custom_spinner;
	int SpinnerItemLayout = R.layout.custom_spinner_item;

	String _ContentIndexChapter = null, _ContentIndexChapterPart1 = null,
			_ContentIndexChapterPart2 = null;

	DatabaseContent[] DBContent;
	int _FileNameSpinnerCurrentIndex = 0;

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
		_delAll = (Button)findViewById(R.id.bSQLPoTDelAll);
		_delEnt = (Button)findViewById(R.id.bSQLPoTDelEnt);
		_dbrecreate = (Button)findViewById(R.id.bSQLPoTRecreate);

		_filename = (EditText) findViewById(R.id.etSQLPoTFileNameAdd);
		_chapter = (EditText) findViewById(R.id.etSQLPoTChapter);
		_chapterpart1 = (EditText) findViewById(R.id.etSQLPoTChapterPart1);
		_chapterpart2 = (EditText) findViewById(R.id.etSQLPoTChapterPart2);

		_sChapter = (Spinner) findViewById(R.id.sSQLChapter);
		_sChapterPart1 = (Spinner) findViewById(R.id.sSQLChapterPart1);
		_sChapterPart2 = (Spinner) findViewById(R.id.sSQLChapterPart2);


		_add.setOnClickListener(this);
		_view.setOnClickListener(this);
		_fileView.setOnClickListener(this);
		_delAll.setOnClickListener(this);
		_delEnt.setOnClickListener(this);
		_dbrecreate.setOnClickListener(this);

		_sChapter.setOnItemSelectedListener(this);
		_sChapterPart1.setOnItemSelectedListener(this);
		_sChapterPart2.setOnItemSelectedListener(this);
		

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

	/**
	 * this is used to set up the chapter spinner in the beginning this spinner
	 * does not need to be changed after first being created
	 */
	private void InitializeChapterSpinner() {

		Database entry = new Database(SQLPros_Teori.this);
		entry.open();

		String[] chapters = entry.getColumnGrouped(
				new String[] { Database.KEY_CHAPTER },
				Database.KEY_CHAPTER, null);
		entry.close();

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, spinnerLayout, chapters);
		adapter.setDropDownViewResource(SpinnerItemLayout);
		_sChapter.setAdapter(adapter);
	}

	/**
	 * this is run when a button or something that has been given an
	 * OnClickListener is pressed
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

				Database entry = new Database(SQLPros_Teori.this);
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
		case R.id.bSQLPoTDelAll:
			Database dbs = new Database(SQLPros_Teori.this);
			dbs.open();
			dbs.deleteTableContent(null);
			InitializeChapterSpinner();
			dbs.close();
			break;
		case R.id.bSQLPoTDelEnt:
			break;
		case R.id.bSQLPoTRecreate:
			Database DBrec = new Database(SQLPros_Teori.this);
			DBrec.open();
			DBrec.createFromExisting();
			DBrec.close();
			InitializeChapterSpinner();
			break;
		}

	}

	private String makeFileName(int index) {
		String filename = "";
		if (!DBContent[index].getChapter().isEmpty()) {
			filename = DBContent[index].getChapter() + "/";
			if (!DBContent[index].getChapterPart1().isEmpty()) {
				filename = filename + DBContent[index].getChapterPart1() + "/";
				if (!DBContent[index].getChapterPart2().isEmpty())
					filename = filename + DBContent[index].getChapterPart2()
							+ "/";
			}
		}
		return filename;
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
				where = Database.KEY_CHAPTER + " = '" + where + "' ";
				where = Database.KEY_CHAPTERPART1 + " != '' and " + where;

				Database getPartition1 = new Database(this);
				getPartition1.open();
				String[] getPartition1s = getPartition1.getColumnGrouped(
						new String[] { Database.KEY_CHAPTERPART1 },
						Database.KEY_CHAPTERPART1, where);
				getPartition1.close();
				ChangeChapterPart1Spinner(getPartition1s);
				_sChapterPart1.setVisibility(android.view.View.VISIBLE);
			}
			break;

		case R.id.sSQLChapterPart1:
			if (index == 0) {
				_sChapterPart2.setVisibility(android.view.View.GONE);
				_ContentIndexChapterPart1 = null;
			} else {
				String where = ((TextView) _sChapterPart1.getSelectedView())
						.getText().toString();
				_ContentIndexChapterPart1 = where;
				where = Database.KEY_CHAPTERPART1 + " = '" + where + "' ";
				where = Database.KEY_CHAPTERPART2 + " != '' and " + where;

				Database getPartition1 = new Database(this);
				getPartition1.open();
				String[] getPartition1s = getPartition1.getColumnGrouped(
						new String[] { Database.KEY_CHAPTERPART2 },
						Database.KEY_CHAPTERPART2, where);
				getPartition1.close();
				ChangeChapterPart2Spinner(getPartition1s);
				_sChapterPart2.setVisibility(android.view.View.VISIBLE);
			}
			break;

		case R.id.sSQLChapterPart2:
			if (index == 0) {
				_ContentIndexChapterPart2 = null;
			} else {
				String where = ((TextView) _sChapterPart2.getSelectedView())
						.getText().toString();
				_ContentIndexChapterPart2 = where;
			}
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
