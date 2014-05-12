package com.bbv.prototype1.TheoryAndLabAssignments;

import java.io.IOException;
import java.util.ArrayList;
import com.bbv.prototype1.R;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ShowContent extends ShowContentBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcontent);

		Initialize();
		StartFirstDocument(savedInstanceState);
	}

	/**
	 * Method for checking if there is something already stored in the intent or
	 * if this is the first time running this class, so it will either create a
	 * layout from a previous action or create a new one.
	 * 
	 * @param savedInstanceState
	 */
	private void StartFirstDocument(Bundle savedInstanceState) {
		_nWVB = new WebView();

		if (getTheoryBundle() != null) {// if coming from Pros_og_Teori.java
			setPriorityIndex(_DrawerMenuListIndex.Theory.ordinal());
			setCurrentIndex(_DrawerMenuListIndex.Theory.ordinal());
			_filePath = getFilePathFromTheoryBundle(getTheoryBundle());

			_nWVB.setArguments(getTheoryBundle());
			decodeDataDocument();
			_fragManag.beginTransaction().replace(R.id.flViskos, _nWVB)
					.commit();

		} else if (getLabBundle() != null) {// if coming from Ovinger.java
			setPriorityIndex(_DrawerMenuListIndex.Exercise.ordinal());
			setCurrentIndex(_DrawerMenuListIndex.Exercise.ordinal());
			_filePath = getFilePathFromLabBundle(getLabBundle());
			_nWVB.setArguments(getLabBundle());
			decodeDataDocument();

			_fragManag.beginTransaction().replace(R.id.flViskos, _nWVB)
					.commit();
		}

		_drawerLayout.closeDrawer(_listView);
	}

	/**
	 * the on click listener for the buttons next and previous this will simply
	 * set the currentbundle to that of the created bundles for either the next
	 * or previous page
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNext:
			_currentBundle = _nextBundle;
			Log.i(KEY_LOGCAT, "Next Button Presset");
			break;
		case R.id.bPrevious:
			_currentBundle = _previousBundle;
			Log.i(KEY_LOGCAT, "Previous Button Presset");
			break;
		}

		if (getCurrentIndex() == _DrawerMenuListIndex.Theory.ordinal()) {
			setTheoryBundle(_currentBundle);
			_filePath = getFilePathFromTheoryBundle(getTheoryBundle());
			_nWVB.Reload(getTheoryBundle());

			decodeDataDocument();

		} else if (getCurrentIndex() == _DrawerMenuListIndex.Exercise.ordinal()) {
			setLabBundle(_currentBundle);
			_filePath = getFilePathFromLabBundle(getLabBundle());
			_nWVB.Reload(getLabBundle());

			decodeDataDocument();
		}

		Log.i(KEY_LOGCAT, "Finishing onClick");
		Log.i(KEY_LOGCAT, "  ");
	}

	/**
	 * Used to retrieve the data stored in a text file in the current directory
	 * in the database where the user currently is
	 */
	public void decodeDataDocument() {
		try {
			String str = "";
			_bTitle = _bNextOrLast = _bReferenceList = _bListFound = false;
			setAssetManager();
			newDrawerContentList();
			setInputStream(_filePath + "_.txt");
			Log.e(KEY_LOGCAT, "found file");
			setBufferedReader(_InputStream);
			newStringBuffer();

			if (_InputStream != null) {
				while ((str = _BufferedReader.readLine()) != null) {

					if (str.contains("<title>")) {
						_bTitle = true;
						newStringBuffer();
						continue;
					} else if (str.contains("</title")) {
						_bTitle = false;
						setTitle(_StringBuffer.toString());
						Log.i(KEY_LOGCAT, "Set the title in the action bar");
						continue;
					} else if (str.contains("<next>") || str.contains("<last>")) {
						_bNextOrLast = true;
						newStringBuffer();
						continue;
					} else if (str.contains("</next>")) {
						_bNextOrLast = false;
						if (getCurrentIndex() == _DrawerMenuListIndex.Theory
								.ordinal()) {
							setNextBundle(createNewTeoriBundle(_StringBuffer
									.toString()));
						} else if (getCurrentIndex() == _DrawerMenuListIndex.Exercise
								.ordinal()) {
							setNextBundle(createNewOvingBundle(_StringBuffer
									.toString()));
						}
						Log.i(KEY_LOGCAT,
								"Created the bundle for the next page");
						continue;
					} else if (str.contains("</last>")) {
						_bNextOrLast = false;
						if (getCurrentIndex() == _DrawerMenuListIndex.Theory
								.ordinal()) {
							setPreviousBundle(createNewTeoriBundle(_StringBuffer
									.toString()));
						} else if (getCurrentIndex() == _DrawerMenuListIndex.Exercise
								.ordinal()) {
							setPreviousBundle(createNewOvingBundle(_StringBuffer
									.toString()));
						}
						Log.i(KEY_LOGCAT,
								"Created the bundle for the previous page");
						continue;
					} else {
						// this is where i left off
						if (getCurrentIndex() == getPriorityIndex()) {

							for (int i = 0; i < _DrawerMenyList.length; i++) {

								if (str.contains("<" + _DrawerMenyList[i] + ">")) {
									_bReferenceList = true;
									_ReferenceArray = new ArrayList<String>();
									_currentFoundReferenceList = "<"
											+ _DrawerMenyList[i] + ">";
									break;
								} else if (str.contains("</"
										+ _DrawerMenyList[i] + ">")) {
									_bReferenceList = false;
									_NavigatorItemContentList
											.get(i)
											.setStringList(
													arrayListToStringArray(_ReferenceArray));
									_currentFoundReferenceList = "</"
											+ _DrawerMenyList[i] + ">";
									break;
								}
							}
						}
					}
					
					if (_bTitle || _bNextOrLast) {
						_StringBuffer.append(str);
					} else if (_bList) {
						if (!str.isEmpty()) {
							_StringArray.add(str);
						}
					} else if (_bReferenceList
							&& !str.contains(_currentFoundReferenceList)) {
						if (!str.isEmpty()) {
							_ReferenceArray.add(str);
						}
					}
				}
				// setting the list after reading the hole file looking for all
				// the references
				if (getCurrentIndex() == getPriorityIndex()) {
					setItemInDrawerLoaded(0);
					setNavigationDrawerContent(_NavigatorItemContentList);
				}

			}
			_InputStream.close();
		} catch (IOException e) {
			showToast("File does not exist");
			Log.e(KEY_LOGCAT,
					"Didn't open or find _.txt, wrong database reference");
		}
		Log.i(KEY_LOGCAT, "Finishing decodeDataDocument");
	}

	/**
	 * Create the references and initialize the drawerlistener
	 */
	private void Initialize() {
		setActivityTitle("Meny");
		_drawerLayout = (DrawerLayout) findViewById(R.id.dlVis_Teori);
		_listView = (ListView) findViewById(R.id.lvVis_Teori);
		_bNext = (Button) findViewById(R.id.bNext);
		_bPrevious = (Button) findViewById(R.id.bPrevious);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		_actionDrawerToggle = new ActionBarDrawerToggle(this, _drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View v) {
				getActionBar().setTitle(_title);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View v) {
				getActionBar().setTitle(_activity_title);
				invalidateOptionsMenu();
			}
		};

		_drawerLayout.setDrawerListener(_actionDrawerToggle);
		_bNext.setOnClickListener(this);
		_bPrevious.setOnClickListener(this);

		_DrawerMenyList = getResources().getStringArray(R.array.DrawerMenuList);
		_fragManag = getFragmentManager();

	}
}
