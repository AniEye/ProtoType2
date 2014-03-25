package com.bbv.prototype1;

import java.io.IOException;
import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ShowContent extends ShowContentBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcontent);

		Initialize();
		StartFirstDocument(savedInstanceState);
	}

	private void StartFirstDocument(Bundle savedInstanceState) {

		if (savedInstanceState == null) {
			Log.i(KEY_LOGCAT,
					"Start doing what is needed when savedInstace = null");
			if (getTheoryBundle() != null) {
				setPriorityIndex(_DrawerMenuListIndex.Theory.ordinal());
				setCurrentIndex(_DrawerMenuListIndex.Theory.ordinal());
				_filePath = getFilePathFromTeoriBundle(getTheoryBundle());
				_nWVB = new WebViewTeori();
				_nWVB.setArguments(getTheoryBundle());
			} else if (getOvingBundle() != null) {
				setPriorityIndex(_DrawerMenuListIndex.Exercise.ordinal());
				setCurrentIndex(_DrawerMenuListIndex.Exercise.ordinal());
				_filePath = getFilePathFromOvingBundle(getOvingBundle());
				_nWVB = new WebViewOving();
				_nWVB.setArguments(getOvingBundle());
			}
		} else {
			Log.i(KEY_LOGCAT,
					"Start doing what is needed when savedInstace != null");
			if (getCurrentIndex() == _DrawerMenuListIndex.Theory.ordinal()) {
				_filePath = getFilePathFromTeoriBundle(getTheoryBundle());
				_nWVB = new WebViewTeori();
				_nWVB.setArguments(getTheoryBundle());
			} else if (getCurrentIndex() == _DrawerMenuListIndex.Exercise
					.ordinal()) {
				_filePath = getFilePathFromOvingBundle(getOvingBundle());
				_nWVB = new WebViewOving();
				_nWVB.setArguments(getOvingBundle());
			}
			retriveDrawer();

		}
		decodeDataDocument();

		_fragManag.beginTransaction().replace(R.id.flViskos, _nWVB).commit();
		Log.i(KEY_LOGCAT, "replacing the fragment with fragment manager");
		_listView.setItemChecked(getCurrentIndex(), true);

		_drawerLayout.closeDrawer(_listView);
	}

	private void retriveDrawer() {
		newDrawerContentList();
		for (int i = 0; i < _DrawerMenyList.length; i++) {
			if (this.getIntent().getStringArrayExtra(_DrawerMenyList[i]) != null) {
				_NavigatorItemContentList.get(i).setStringList(
						this.getIntent()
								.getStringArrayExtra(_DrawerMenyList[i]));
			}
		}
		setNavigationDrawerContent(_NavigatorItemContentList);

	}

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
			_filePath = getFilePathFromTeoriBundle(getTheoryBundle());
			_nWVB.Reload(getTheoryBundle());

			decodeDataDocument();

		} else if (getCurrentIndex() == _DrawerMenuListIndex.Exercise.ordinal()) {
			setOvingBundle(_currentBundle);
			_filePath = getFilePathFromOvingBundle(getOvingBundle());
			_nWVB.Reload(getOvingBundle());

			decodeDataDocument();
		}

		Log.i(KEY_LOGCAT, "Finishing onClick");
		Log.i(KEY_LOGCAT, "  ");
	}

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
					// if(!str.isEmpty()){
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
					setNavigationDrawerContent(_NavigatorItemContentList);
					saveDrawerContent();
				}

			}
			_InputStream.close();
		} catch (IOException e) {
			showToast("File does not exist");
			Log.e(KEY_LOGCAT,
					"Didn't open or find _.txt, wrong database reference");
		}
		Log.i(KEY_LOGCAT, "Finishing decodeDataDocument");
		Log.i(KEY_LOGCAT, "  ");
	}

	private void saveDrawerContent() {
		for (int i = 0; i < _NavigatorItemContentList.size(); i++) {
			if (_NavigatorItemContentList.get(i).getStringList() != null) {
				this.getIntent().putExtra(_DrawerMenyList[i],
						_NavigatorItemContentList.get(i).getStringList());
			}
		}

	}

	private void Initialize() {
		// _activity_title = getTitle();
		setActivityTitle("Menyen >.<");
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		// boolean drawerOpen = _drawerLayout.isDrawerOpen(_listView);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	// setter and getter for the theory and oving bundles
	protected void setTheoryBundle(Bundle aBundle) {
		this.getIntent().putExtra(KEY_PROS_TEORI, aBundle);
		Log.i(KEY_LOGCAT, "Finishing setTheoryBundle");
		Log.i(KEY_LOGCAT, "  ");
	}

	protected void setOvingBundle(Bundle aBundle) {
		this.getIntent().putExtra(KEY_OVING, aBundle);
		Log.i(KEY_LOGCAT, "Finishing setOvingBundle");
		Log.i(KEY_LOGCAT, "  ");
	}

	protected Bundle getTheoryBundle() {
		return this.getIntent().getBundleExtra(KEY_PROS_TEORI);
	}

	protected Bundle getOvingBundle() {
		return this.getIntent().getBundleExtra(KEY_OVING);
	}

	// setter and getter for priorityindex
	protected void setPriorityIndex(int index) {
		this.getIntent().putExtra(KEY_PRIORITY_INDEX, index);
		Log.i(KEY_LOGCAT, "Finishing setPriorityIndex");
		Log.i(KEY_LOGCAT, "  ");
	}

	protected int getPriorityIndex() {
		return this.getIntent().getIntExtra(KEY_PRIORITY_INDEX, -1);
	}

	// setter and getter for currentindex
	protected int getCurrentIndex() {
		return this.getIntent().getIntExtra(KEY_CURRENT_INDEX, -1);
	}

	protected void setCurrentIndex(int index) {
		this.getIntent().putExtra(KEY_CURRENT_INDEX, index);
		Log.i(KEY_LOGCAT, "Finishing setCurrentIndex");
		Log.i(KEY_LOGCAT, "  ");
	}
}
