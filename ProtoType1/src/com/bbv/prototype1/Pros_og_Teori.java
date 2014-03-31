package com.bbv.prototype1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Pros_og_Teori extends Activity implements OnItemSelectedListener,
		OnClickListener {

	Spinner _sChapter, _sChapterPart1, _sChapterPart2;
	Button gVidere;
	ArrayAdapter<CharSequence> _AAChapter, _AAChapterPart1, _AAChapterPart2;
	int spinnerLayout = R.layout.custom_spinner;
	int SpinnerItemLayout = R.layout.custom_spinner_item;
	String _WhereFile;
	Bundle newBundle;
	final static String POT_KEY_CHAPTER = "POTKC";
	final static String POT_KEY_CHAPTERPART1 = "POTKCP1";
	final static String POT_KEY_CHAPTERPART2 = "POTKCP2";
	boolean savedInstanceExists = false;
	String chapterPart1 = null, chapterPart2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pros_og__teori);
		Initialize();
		if (savedInstanceState != null) {
			savedInstanceExists = true;
			Log.e("Pros_Saved", "SavedInstanceState is not null");
			String chapter = savedInstanceState
					.getString(ShowContentBase.KEY_CHAPTER);
			Log.e("Pros_Saved", "chapter is: " + chapter);

			if (savedInstanceState.getString(ShowContentBase.KEY_CHAPTERPART1) != null) {
				chapterPart1 = savedInstanceState
						.getString(ShowContentBase.KEY_CHAPTERPART1);
				if (savedInstanceState
						.getString(ShowContentBase.KEY_CHAPTERPART2) != null) {
					chapterPart2 = savedInstanceState
							.getString(ShowContentBase.KEY_CHAPTERPART2);

				}

			}
			setSelectedFromSavedInstance(chapter, _sChapter);

		}
	}

	private void setSelectedFromSavedInstance(String savedString,
			Spinner whatSpinner) {

		for (int i = 0; i < whatSpinner.getCount(); i++) {

			if (savedString.contentEquals((String) whatSpinner
					.getItemAtPosition(i))) {
				whatSpinner.setSelection(i);
				break;
			}

		}
	}

	private void ChapterAdapter(String[] array) {
		_AAChapter = new ArrayAdapter<CharSequence>(this, spinnerLayout, array);
		_AAChapter.setDropDownViewResource(SpinnerItemLayout);
		_sChapter.setAdapter(_AAChapter);
	}

	private void ChapterPart1Adapter(String[] array) {
		_AAChapterPart1 = new ArrayAdapter<CharSequence>(this, spinnerLayout,
				array);
		_AAChapterPart1.setDropDownViewResource(SpinnerItemLayout);
		_sChapterPart1.setAdapter(_AAChapterPart1);
	}

	private void ChapterPart2Adapter(String[] array) {
		_AAChapterPart2 = new ArrayAdapter<CharSequence>(this, spinnerLayout,
				array);
		_AAChapterPart2.setDropDownViewResource(SpinnerItemLayout);
		_sChapterPart2.setAdapter(_AAChapterPart2);
	}

	private void Initialize() {
		_sChapter = (Spinner) findViewById(R.id.sKapittel);
		_sChapterPart1 = (Spinner) findViewById(R.id.sDelKapittel);
		_sChapterPart2 = (Spinner) findViewById(R.id.sDDKapittel);
		gVidere = (Button) findViewById(R.id.bVidere_ProsTeori);

		_sChapter.setOnItemSelectedListener(this);
		_sChapterPart1.setOnItemSelectedListener(this);

		gVidere.setOnClickListener(this);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		InitializingChapterSpinner();
	}

	private void InitializingChapterSpinner() {
		Database chapter = new Database(Pros_og_Teori.this);
		chapter.open();
		String[] chapters = chapter.getColumnGrouped(
				new String[] { Database.KEY_CHAPTER },
				Database.KEY_CHAPTER, null);
		chapter.close();
		ChapterAdapter(chapters);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pros_og__teori, menu);
		return true;
	}

	public void settingUpChapterPart1() {
		Log.e("WhatEver", "SettingUpChapterPart1 is run");

		String where = Database.KEY_CHAPTERPART1 + " != ''";
		where = Database.KEY_CHAPTER + " = '"
				+ getStringOfSelected(_sChapter) + "' and " + where;

		Database getPartition1 = new Database(this);
		getPartition1.open();
		String[] getPartition1s = getPartition1.getColumnGrouped(
				new String[] { Database.KEY_CHAPTERPART1 },
				Database.KEY_CHAPTERPART1, where);
		getPartition1.close();
		if (getPartition1s.length > 1) {
			ChapterPart1Adapter(getPartition1s);
			_sChapterPart1.setVisibility(android.view.View.VISIBLE);

		}
		if (chapterPart1 != null) {
			setSelectedFromSavedInstance(chapterPart1, _sChapterPart1);
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int index,
			long arg3) {
		String where;
		switch (parent.getId()) {
		case R.id.sKapittel:
			where = Database.KEY_CHAPTERPART1 + " != ''";
			where = Database.KEY_CHAPTER + " = '"
					+ getStringOfSelected(_sChapter) + "' and " + where;

			Database getPartition1 = new Database(this);
			getPartition1.open();
			String[] getPartition1s = getPartition1.getColumnGrouped(
					new String[] { Database.KEY_CHAPTERPART1 },
					Database.KEY_CHAPTERPART1, where);
			getPartition1.close();
			if (getPartition1s.length > 1) {
				ChapterPart1Adapter(getPartition1s);
				_sChapterPart1.setVisibility(android.view.View.VISIBLE);

			}
			if (chapterPart1 != null) {
				setSelectedFromSavedInstance(chapterPart1, _sChapterPart1);
			}

			break;
		case R.id.sDelKapittel:
			if (index == 0) {
				_sChapterPart2.setVisibility(android.view.View.GONE);
			} else {
				where = Database.KEY_CHAPTERPART2 + " != '' ";
				where = Database.KEY_CHAPTERPART1 + " = '"
						+ getStringOfSelected(_sChapterPart1) + "' and "
						+ where;
				where = Database.KEY_CHAPTER + " = '"
						+ getStringOfSelected(_sChapter) + "' and " + where;

				Database getPartition2 = new Database(this);
				getPartition2.open();
				String[] getPartition2s = getPartition2.getColumnGrouped(
						new String[] { Database.KEY_CHAPTERPART2 },
						Database.KEY_CHAPTERPART2, where);
				getPartition2.close();
				if (getPartition2s.length > 1) {
					ChapterPart2Adapter(getPartition2s);
					_sChapterPart2.setVisibility(android.view.View.VISIBLE);
				}
				if (chapterPart1 != null) {
					setSelectedFromSavedInstance(chapterPart2, _sChapterPart2);
				}
			}
			break;

		}

	}

	private String getStringOfSelected(Spinner s) {
		return ((TextView) s.getSelectedView()).getText().toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onClick(View v) {
		newBundle = new Bundle();

		newBundle.putString(ShowContentBase.KEY_CHAPTER,
				getStringOfSelected(_sChapter));

		if (_sChapterPart1.getSelectedItemPosition() > 0) {
			newBundle.putString(ShowContentBase.KEY_CHAPTERPART1,
					getStringOfSelected(_sChapterPart1));

		} else {
			newBundle.putString(ShowContentBase.KEY_CHAPTERPART1, null);
		}
		if (_sChapterPart2.getSelectedItemPosition() > 0) {
			newBundle.putString(ShowContentBase.KEY_CHAPTERPART2,
					getStringOfSelected(_sChapterPart2));
		} else {
			newBundle.putString(ShowContentBase.KEY_CHAPTERPART2, null);
		}

		Intent is = new Intent(ShowContent.KEY_SHOWCONTENT);
		 is.putExtra(ShowContentBase.KEY_PROS_TEORI, newBundle);
		startActivityForResult(is, 2);

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==2){
			finish();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(ShowContentBase.KEY_CHAPTER,
				getStringOfSelected(_sChapter));
		Log.e("Pros_Saved", "onSavedInstanceState is run");
		if (_sChapterPart1.getSelectedItemPosition() > 0) {
			outState.putString(ShowContentBase.KEY_CHAPTERPART1,
					getStringOfSelected(_sChapterPart1));

		} else {
			outState.putString(ShowContentBase.KEY_CHAPTERPART1, null);
		}
		if (_sChapterPart2.getSelectedItemPosition() > 0) {
			outState.putString(ShowContentBase.KEY_CHAPTERPART2,
					getStringOfSelected(_sChapterPart2));
		} else {
			outState.putString(ShowContentBase.KEY_CHAPTERPART2, null);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}