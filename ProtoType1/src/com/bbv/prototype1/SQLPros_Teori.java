package com.bbv.prototype1;

import com.bbv.prototype1.R;
import com.bbv.prototype1.Database.Database;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SQLPros_Teori extends Activity implements OnClickListener,
		OnItemSelectedListener {

	Button _dbrecreate;
	Spinner _sChapter, _sChapterPart1, _sChapterPart2;
	ArrayAdapter<CharSequence> _AAChapter, _AAChapterPart1, _AAChapterPart2;

	int spinnerLayout = R.layout.custom_spinner;
	int SpinnerItemLayout = R.layout.custom_spinner_item;

	String _ContentIndexChapter = null, _ContentIndexChapterPart1 = null,
			_ContentIndexChapterPart2 = null;

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
		_dbrecreate = (Button) findViewById(R.id.bSQLPoTRecreate);

		_sChapter = (Spinner) findViewById(R.id.sSQLChapter);
		_sChapterPart1 = (Spinner) findViewById(R.id.sSQLChapterPart1);
		_sChapterPart2 = (Spinner) findViewById(R.id.sSQLChapterPart2);

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
				new String[] { Database.KEY_CHAPTER }, Database.KEY_CHAPTER,
				null);
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
		case R.id.bSQLPoTRecreate:
			Database DBrec = new Database(SQLPros_Teori.this);
			DBrec.open();
			DBrec.createFromExisting();
			DBrec.close();
			InitializeChapterSpinner();
			break;
		}

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
