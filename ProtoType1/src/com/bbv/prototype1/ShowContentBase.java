package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public abstract class ShowContentBase extends Activity implements
		OnClickListener {

	protected DrawerLayout _drawerLayout;
	protected ListView _listView;
	protected ActionBarDrawerToggle _actionDrawerToggle;
	protected String[] _testArray;
	protected CharSequence _title;
	protected CharSequence _activity_title;
	protected WebViewBase _nWVB;
	protected int _currentIndex = 0,_PriorityIndex;
	protected Button _bPrevious, _bNext;
	protected FragmentManager _fragManag;
	protected AssetManager _assetManag;
	protected InputStream _InputStream;
	protected StringBuffer _StringBuffer;
	protected BufferedReader _BufferedReader;
	protected boolean _bTitle, _bNextOrLast, _bList;
	protected String _filePath;
	protected Bundle _currentBundle, _nextBundle, _previousBundle;
	protected ArrayList<String> _StringArray;

	public final static String KEY_PRIORITY_INDEX = "PriorityIndex";
	public final static String KEY_OVING = "SOving";
	public final static String KEY_PROS_TEORI = "BProsTeori";

	public final static String KEY_CHAPTER = "Teori_Chapter";
	public final static String KEY_CHAPTERPART1 = "Teori_ChapterPart1";
	public final static String KEY_CHAPTERPART2 = "Teori_ChapterPart2";

	public final static String KEY_SHOWCONTENT = "com.bbv.prototype1.SHOWCONTENT";

	protected final static String KEY_LOGCAT = "ShowContent";

	/**
	 * Creates a new Instance of a StringBuffer
	 */
	protected void newStringBuffer() {
		_StringBuffer = new StringBuffer();
	}

	public void setInputStream(String filepath) throws IOException {
		_InputStream = _assetManag.open(filepath);
	}

	/**
	 * Takes an InputStream to create a new BufferedReader
	 * 
	 * @param InputStream
	 *            input
	 */
	public void setBufferedReader(InputStream input) {
		_BufferedReader = new BufferedReader(new InputStreamReader(input));
	}

	protected Bundle createNewTeoriBundle(String filepath) {
		Bundle newBundle = new Bundle();
		filepath = filepath.trim();
		String[] filePathDevided = filepath.split("/");
		newBundle.putString(KEY_CHAPTER, filePathDevided[0]);
		newBundle.putString(KEY_CHAPTERPART1, null);
		newBundle.putString(KEY_CHAPTERPART2, null);
		if (filePathDevided.length > 1) {//maybe also check if the content in the presiding indexes aren't empty
			newBundle.putString(KEY_CHAPTERPART1, filePathDevided[1]);
			if (filePathDevided.length > 2) {
				newBundle.putString(KEY_CHAPTERPART2, filePathDevided[2]);
			}
		}
		return newBundle;
	}

	protected Bundle createNewOvingBundle(String newfilepath) {
		Bundle newBundle = new Bundle();
		newfilepath = newfilepath.trim();
		newBundle.putString(KEY_OVING, newfilepath);
		return newBundle;

	}

	/**
	 * This method will return a string representation of the content in the
	 * bundle given that the content is relevant to showing the theory part
	 * 
	 * @param incommingBundle
	 * @return string
	 */
	protected String getFilePathFromTeoriBundle(Bundle incommingBundle) {
		String filepath = "pros_og_teori_text/";
		Log.i(KEY_LOGCAT, "Running getFilePathFromTeoriBundle");

		if (incommingBundle.getString(KEY_CHAPTER) != null) {
			filepath = filepath + incommingBundle.getString(KEY_CHAPTER) + "/";
			Log.i(KEY_LOGCAT,
					"Key chapter found: "
							+ incommingBundle.getString(KEY_CHAPTER));
			if (incommingBundle.getString(KEY_CHAPTERPART1) != null) {
				filepath = filepath
						+ incommingBundle.getString(KEY_CHAPTERPART1) + "/";
				Log.i(KEY_LOGCAT,
						"Key chapterpart1 found: "
								+ incommingBundle.getString(KEY_CHAPTERPART1));
				if (incommingBundle.getString(KEY_CHAPTERPART2) != null) {
					filepath = filepath
							+ incommingBundle.getString(KEY_CHAPTERPART2) + "/";
					Log.i(KEY_LOGCAT, "Key chapterpart2 found: "
							+ incommingBundle.getString(KEY_CHAPTERPART2));
				}
			}
		}

		Log.i(KEY_LOGCAT, "Finishing getFilePathFromTeoriBundle with: "
				+ filepath);
		return filepath;
	}

	protected String getFilePathFromOvingBundle(Bundle aBundle) {
		return "Ovinger/" + aBundle.getString(ShowContentBase.KEY_OVING) + "/";
	}

	protected void setAssetManager() {
		_assetManag = this.getAssets();
	}

	/**
	 * used to set/override the title in the action bar
	 * 
	 * @param CharSequence
	 */
	public void setTitle(CharSequence title) {
		_title = title;
		getActionBar().setTitle(_title);
	}

	/**
	 * used to set the bundle for the page that should be shown when pressing
	 * forrige/previous
	 * 
	 * @param aBundle
	 */
	public void setPreviousBundle(Bundle aBundle) {
		_previousBundle = aBundle;
	}

	/**
	 * used to set the bundle for the page that should be shown when pressing
	 * neste/next
	 * 
	 * @param aBundle
	 */
	public void setNextBundle(Bundle aBundle) {
		_nextBundle = aBundle;
	}

	public void setListViewArray(String[] list) {
		_listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
				list));
		_listView.setOnItemClickListener(new DrawerItemClickListener());
	}

	/* The click listener for ListView in the navigation drawer */
	protected class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	protected abstract void selectItem(int position);

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		_actionDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		_actionDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (_actionDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void setActivityTitle(CharSequence title) {
		_activity_title = title;
	}

	protected String[] arrayListToStringArray(ArrayList<String> list) {
		int length = list.size();
		String[] array = new String[length];
		for (int i = 0; i < length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
