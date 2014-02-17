package com.bbv.prototype1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class SQLView extends Activity {

	ListView list;
	DatabaseCustomAdapter adapter;
	public SQLView CustomListView = null;
	public ArrayList<DatabaseContent> CustomListViewValuesArr = new ArrayList<DatabaseContent>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		CustomListView = this;// whyyyyyy

		CustomListView = this;

		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		setListData();

		Resources res = getResources();
		list = (ListView) findViewById(R.id.SQLListView); // List defined in XML
															// ( See
		// Below )

		/**************** Create Custom Adapter *********/
		adapter = new DatabaseCustomAdapter(CustomListView,
				CustomListViewValuesArr, res);
		list.setAdapter(adapter);
	}

	/****** Function to set data in ArrayList *************/
	public void setListData() {

		SQLDatabase info = new SQLDatabase(this);
		info.open();
		// String data = info.getData();
		DatabaseContent[] data = info.getData();
		info.close();

		for (int i = 0; i < data.length; i++) {

			final DatabaseContent sched = new DatabaseContent();

			/******* Firstly take data in model object ******/
			sched.setChapter(data[i].getChapter());
			sched.setChapterPart1(data[i].getChapterPart1());
			sched.setChapterPart2(data[i].getChapterPart2());
			sched.setFileName(data[i].getFileName());

			/******** Take Model Object in ArrayList **********/
			CustomListViewValuesArr.add(sched);
		}

	}

	/***************** This function used by adapter ****************/
	public void onItemClick(int mPosition) {
		DatabaseContent tempValues = (DatabaseContent) CustomListViewValuesArr
				.get(mPosition);

		// SHOW ALERT

		Toast.makeText(
				CustomListView,
				"Chapter: " + tempValues.getChapter() + " ChapterPart1: "
						+ tempValues.getChapterPart1() + " ChapterPart2: "
						+ tempValues.getChapterPart2() + " FileName: "
						+ tempValues.getFileName(), Toast.LENGTH_LONG).show();
	}
}
