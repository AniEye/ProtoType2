package com.bbv.prototype1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pros_og__teori);
		Initialize();
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

		InitializingChapterSpinner();
	}

	private void InitializingChapterSpinner() {
		SQLDatabase chapter = new SQLDatabase(Pros_og_Teori.this);
		chapter.open();
		String[] chapters = chapter.getColumnGrouped(
				new String[] { SQLDatabase.KEY_CHAPTER },
				SQLDatabase.KEY_CHAPTER, null);
		chapter.close();
		ChapterAdapter(chapters);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pros_og__teori, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int index,
			long arg3) {
		switch (parent.getId()) {
		case R.id.sKapittel:
			if (index == 0) {
				_sChapterPart1.setVisibility(android.view.View.GONE);
			} else {
				String where = SQLDatabase.KEY_CHAPTERPART1 + " != ''";
				where = SQLDatabase.KEY_CHAPTER + " = '"
						+ getStringOfSelected(_sChapter) + "' and " + where;

				SQLDatabase getPartition1 = new SQLDatabase(this);
				getPartition1.open();
				String[] getPartition1s = getPartition1.getColumnGrouped(
						new String[] { SQLDatabase.KEY_CHAPTERPART1 },
						SQLDatabase.KEY_CHAPTERPART1, where);
				getPartition1.close();
				if (getPartition1s.length > 1) {
					ChapterPart1Adapter(getPartition1s);
					_sChapterPart1.setVisibility(android.view.View.VISIBLE);
				}
			}
			break;
		case R.id.sDelKapittel:
			if (index == 0) {
				_sChapterPart2.setVisibility(android.view.View.GONE);
			} else {
				String where = SQLDatabase.KEY_CHAPTERPART2 + " != '' ";
				where = SQLDatabase.KEY_CHAPTERPART1 + " = '"
						+ getStringOfSelected(_sChapterPart1) + "' and "
						+ where;
				where = SQLDatabase.KEY_CHAPTER + " = '"
						+ getStringOfSelected(_sChapter) + "' and " + where;

				SQLDatabase getPartition2 = new SQLDatabase(this);
				getPartition2.open();
				String[] getPartition2s = getPartition2.getColumnGrouped(
						new String[] { SQLDatabase.KEY_CHAPTERPART2 },
						SQLDatabase.KEY_CHAPTERPART2, where);
				getPartition2.close();
				if (getPartition2s.length > 1) {
					ChapterPart2Adapter(getPartition2s);
					_sChapterPart2.setVisibility(android.view.View.VISIBLE);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		Bundle newBundle = new Bundle();
		if (_sChapter.getSelectedItemPosition() > 0) {
			newBundle
					.putString("Teori_Chapter", getStringOfSelected(_sChapter));
		} else {
			newBundle.putString("Teori_Chapter", null);
		}
		if (_sChapterPart1.getSelectedItemPosition() > 0) {
			newBundle.putString("Teori_ChapterPart1",
					getStringOfSelected(_sChapterPart1));

		} else {
			newBundle.putString("Teori_ChapterPart1", null);
		}
		if (_sChapterPart2.getSelectedItemPosition() > 0) {
			newBundle.putString("Teori_ChapterPart2",
					getStringOfSelected(_sChapterPart2));
		} else {
			newBundle.putString("Teori_ChapterPart2", null);
		}

		Intent is = new Intent("com.bbv.prototype1.VIS_TEORI");
		is.putExtras(newBundle);
		startActivity(is);

	}
}