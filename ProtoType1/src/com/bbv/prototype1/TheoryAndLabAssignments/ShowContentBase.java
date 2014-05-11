package com.bbv.prototype1.TheoryAndLabAssignments;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public abstract class ShowContentBase extends Activity implements
		OnClickListener {

	protected DrawerLayout _drawerLayout;
	protected ListView _listView;
	protected ActionBarDrawerToggle _actionDrawerToggle;
	protected String[] _testArray;
	protected CharSequence _title;
	protected CharSequence _activity_title;
	protected WebViewBase _nWVB;
	protected int _currentIndex = 0, _PriorityIndex;
	protected ShowCalculator _nSCalc;
	protected Button _bPrevious, _bNext;
	protected FragmentManager _fragManag;
	protected Toast _Toast;
	protected AssetManager _assetManag;
	protected InputStream _InputStream;
	protected String[] _DrawerMenyList;
	protected StringBuffer _StringBuffer;
	protected BufferedReader _BufferedReader;
	protected boolean _bTitle, _bNextOrLast, _bList, _bReferenceList,
			_bListFound;
	protected String _filePath, _currentFoundReferenceList;
	protected Bundle _currentBundle, _nextBundle, _previousBundle;
	protected ArrayList<String> _StringArray, _ReferenceArray;
	protected ArrayList<NavigationDrawerItemContent> _NavigatorItemContentList;

	public final static String KEY_PRIORITY_INDEX = "PriorityIndex";
	public final static String KEY_LIST_ARRAY = "ListArray";
	public final static String KEY_CURRENT_INDEX = "CurrentIndex";
	public final static String KEY_DRAWERLOADEDNUMBER = "DrawerLoadedNumber";

	public final static String KEY_OVING = "SOving";
	public final static String KEY_PROS_TEORI = "BProsTeori";
	public static final String KEY_CHOSENCALC = "TheChoosenCalc";

	public final static String KEY_CHAPTER = "Teori_Chapter";
	public final static String KEY_CHAPTERPART1 = "Teori_ChapterPart1";
	public final static String KEY_CHAPTERPART2 = "Teori_ChapterPart2";

	public final static String KEY_SHOWCONTENT = "com.bbv.prototype1.SHOWCONTENT";

	public static enum _DrawerMenuListIndex {
		MainMenu, Theory, Exercise, Calculator
	};

	protected final static String KEY_LOGCAT = "ShowContent";

	/**
	 * Creates a new Instance of a StringBuffer
	 */
	protected void newStringBuffer() {
		_StringBuffer = new StringBuffer();
	}

	/**
	 * Creates a new inputstream reference using a filepath
	 * 
	 * @param filepath
	 * @throws IOException
	 */
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

	/**
	 * Used to create a new TheoryBundle based on a filepath
	 * 
	 * @param filepath
	 * @return a new TheoryBundle
	 */
	protected Bundle createNewTeoriBundle(String filepath) {
		Bundle newBundle = new Bundle();
		filepath = filepath.trim();
		String[] filePathDevided = filepath.split("/");
		newBundle.putString(KEY_CHAPTER, filePathDevided[0]);
		newBundle.putString(KEY_CHAPTERPART1, null);
		newBundle.putString(KEY_CHAPTERPART2, null);
		if (filePathDevided.length > 1) {// maybe also check if the content in
											// the presiding indexes aren't
											// empty
			newBundle.putString(KEY_CHAPTERPART1, filePathDevided[1]);
			if (filePathDevided.length > 2) {
				newBundle.putString(KEY_CHAPTERPART2, filePathDevided[2]);
			}
		}
		return newBundle;
	}

	/**
	 * Using a string(filepath) creates a new OvingBundle
	 * 
	 * @param newfilepath
	 * @return a new OvingBundle
	 */
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
		String filepath = "Pros_og_Teori/";
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

	/**
	 * used to retrive
	 * 
	 * @param aBundle
	 * @return
	 */
	protected String getFilePathFromOvingBundle(Bundle aBundle) {
		return "Ovinger/" + aBundle.getString(ShowContentBase.KEY_OVING) + "/";
	}

	/**
	 * Used to initialize the AssetMananger
	 */
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

	/**
	 * Will set the content of the NavigatorDrawer with a list of
	 * NavigatorDrawerItemContents
	 * 
	 * @param list
	 */
	public void setNavigationDrawerContent(
			ArrayList<NavigationDrawerItemContent> list) {
		ArrayList<NavigationDrawerItemContent> aList = new ArrayList<NavigationDrawerItemContent>();
		if (list != null) {
			aList = list;
		} else {
			NavigationDrawerItemContent item = new NavigationDrawerItemContent();
			item.setTitle("No data");
			aList.add(item);
		}
		_listView.setAdapter(new NavigationDrawerAdapter(this, aList, null));
	}

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

		return super.onOptionsItemSelected(item);

	}

	/**
	 * Setting the title of the actionbar
	 * 
	 * @param title
	 */
	protected void setActivityTitle(CharSequence title) {
		_activity_title = title;
	}

	/**
	 * Takes in an ArrayList and returns it inform of a String-Array
	 * @param ArrayList-String
	 * @return String-Array
	 */
	protected String[] arrayListToStringArray(ArrayList<String> list) {
		int length = list.size();
		String[] array = new String[length];
		for (int i = 0; i < length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * Shows a toast on the device with the message from parameter message. Also
	 * prints a logcat with info.
	 * 
	 * @param message
	 *            - The message displayed by the toast
	 */
	protected void showToast(String message) {
		try {
			_Toast.getView().isShown(); // true if visible
			_Toast.setText(message);
		} catch (Exception e) { // invisible if exception
			_Toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		}
		Log.println(Log.DEBUG, "calc", "Displayed toast saying: " + message);
		_Toast.show();
	}

	/**
	 * Creates a new Instance of an arrayList containing NavigationDrawerItemContent
	 * where it at the same time adds all the titles of the items
	 * that are going to be shown in the NavigationDrawer
	 */
	protected void newDrawerContentList() {
		_NavigatorItemContentList = new ArrayList<NavigationDrawerItemContent>();
		for (int i = 0; i < _DrawerMenyList.length; i++) {
			NavigationDrawerItemContent item = new NavigationDrawerItemContent();
			item.setTitle(_DrawerMenyList[i]);
			_NavigatorItemContentList.add(item);
		}
	}

	/**
	 * This will hide the buttons next and last
	 * Used when the user enters and returns from ShowCalculator
	 * @param show
	 */
	protected void displayNextLast(boolean show) {
		if (show) {
			_bNext.setVisibility(android.view.View.VISIBLE);
			_bPrevious.setVisibility(android.view.View.VISIBLE);
		} else {
			_bNext.setVisibility(android.view.View.INVISIBLE);
			_bPrevious.setVisibility(android.view.View.INVISIBLE);
		}
	}

}
