package com.bbv.prototype1;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class TestNewListAdapter extends Activity {
	ListView _listView;
	ArrayList<NavigationDrawerItemContent> _listArray;
	NavigationDrawerItemContent _newItem;
	NavigationDrawerAdapter adapter;

	private final static String KEY_NAVIGATOR_CONTENT_LIST = "NavigatorContentList";
	private final static String KEY_LOGCAT = "TestNewListAdapter";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testnewlistadapter);
		_listView = (ListView) findViewById(R.id.TNLA_LV);
		if (savedInstanceState == null) {
			createStaticItemList();
			Log.v(KEY_LOGCAT, "SavedInstance is null");
		} else {
			Log.v(KEY_LOGCAT, "SavedInstance is not null");
			getTheList();
			
		}
		
	}
	
	protected void setAdapter(){
	_listView.setAdapter(null);
	Log.e(KEY_LOGCAT, "Size of array is: "+_listArray.size());
		_listView.setAdapter(new NavigationDrawerAdapter(TestNewListAdapter.this, _listArray, null));
	}

	protected void createStaticItemList() {
		_listArray = new ArrayList<NavigationDrawerItemContent>();

		_newItem = new NavigationDrawerItemContent();
		_newItem.setTitle("Øvinger");
		_newItem.setStringList(new String[] {
				"pros_og_teori_text/1. Leire og vektmaterialer",
				"pros_og_teori_text/1. Leire og vektmaterialer/1.3 Strukturer til leirmineraler" });
		_listArray.add(_newItem);

		_newItem = new NavigationDrawerItemContent();
		_newItem.setTitle("Teori");
		_newItem.setStringList(new String[] {
				"pros_og_teori_text/1. Leire og vektmateria", "2. bronkitt" });
		_listArray.add(_newItem);

		_newItem = new NavigationDrawerItemContent();
		_newItem.setTitle("Test 3");
		_newItem.setStringList(new String[] { "pros_og_teori_text/", "Two",
				"Three" });
		_listArray.add(_newItem);

		_newItem = new NavigationDrawerItemContent();
		_newItem.setTitle("Test 4");
		_newItem.setStringList(new String[] { "One", "Two" });
		_listArray.add(_newItem);

		_newItem = new NavigationDrawerItemContent();
		_newItem.setTitle("Test 5");
		_newItem.setStringList(new String[] { "One", "Two" });
		_listArray.add(_newItem);

		_newItem = new NavigationDrawerItemContent();
		_newItem.setTitle("Test 6");
		_newItem.setStringList(new String[] { "One", "Two", "3", "4" });
		_listArray.add(_newItem);

		_newItem = new NavigationDrawerItemContent();
		_newItem.setTitle("Test 7");
		_newItem.setStringList(null);
		_listArray.add(_newItem);

		storringTheList();
		setAdapter();
	}

	protected void storringTheList() {
		String[] list = new String[_listArray.size()];
		for (int i = 0; i < _listArray.size(); i++) {
			this.getIntent().putExtra(_listArray.get(i).getTitle(),
					_listArray.get(i).getStringList());
			list[i] = _listArray.get(i).getTitle();
		}
		this.getIntent().putExtra(KEY_NAVIGATOR_CONTENT_LIST, list);
	}

	protected void getTheList() {
		String[] titleList = this.getIntent().getStringArrayExtra(
				KEY_NAVIGATOR_CONTENT_LIST);
		_listArray = new ArrayList<NavigationDrawerItemContent>();
		NavigationDrawerItemContent item;
		for (int i = 0; i < titleList.length; i++) {
			item = new NavigationDrawerItemContent();
			item.setTitle(titleList[i]);
			item.setStringList(this.getIntent().getStringArrayExtra(
					titleList[i]));
			_listArray.add(item);
			Log.e(KEY_LOGCAT, "The title: " + item.getTitle());
			if (item.getStringList() != null)
				for (int j = 0; j < item.getStringList().length; j++) {
					Log.i(KEY_LOGCAT,
							"Index " + j + ": " + item.getStringList()[j]);
				}
		}
		setAdapter();
	}
}
