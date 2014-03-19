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

		if (savedInstanceState == null) {
			Log.i(KEY_LOGCAT,
					"Start doing what is needed when savedInstace = null");
			if (getTheoryBundle() != null) {
				setPriorityIndex(0);
				setCurrentIndex(0);
			} else if (getOvingBundle() != null) {
				setPriorityIndex(1);
				setCurrentIndex(1);
			}
		} else {
			Log.i(KEY_LOGCAT,
					"Start doing what is needed when savedInstace != null");

			Log.i(KEY_LOGCAT, "PriorityIndex is: " + getPriorityIndex());
			Log.i(KEY_LOGCAT, "CurrentIndex is: " + getCurrentIndex());

			// try {
			// setListViewArray(getListArray());
			// Log.e(KEY_LOGCAT, "Managed to retrive listarray");
			// } catch (Exception e) {
			// Log.e(KEY_LOGCAT, "Failed to retrive listarray");
			// }

		}

		Initialize();

	}

	@Override
	protected void selectItem(int position) {

		Log.i(KEY_LOGCAT, "Running selectItem");
		_fragManag = getFragmentManager();
		Log.i(KEY_LOGCAT, "got fragment manager");

		switch (position) {
		case 0:
			if (getTheoryBundle() != null) {
				setCurrentIndex(0);
				_nWVB = new WebViewTeori();
				Log.i(KEY_LOGCAT, "created new instance of webViewTeori");
				_nWVB.setArguments(getTheoryBundle());
				Log.i(KEY_LOGCAT, "gave it the arguments needed");
			} else {
				Toast.makeText(this, "Ingen teori lagt til som referanse",
						Toast.LENGTH_LONG).show();
			}
			break;
		case 1:
			if (getOvingBundle() != null) {
				setCurrentIndex(1);
				_nWVB = new WebViewOving();
				Log.i(KEY_LOGCAT, "created new instance of webViewOving");
				_nWVB.setArguments(getOvingBundle());
				Log.i(KEY_LOGCAT, "gave it the arguments needed");
			} else {
				Toast.makeText(this, "Ingen øving lagt til som referanse",
						Toast.LENGTH_LONG).show();
			}
			break;
		}
		if (getCurrentIndex() == 0) {
			_filePath = getFilePathFromTeoriBundle(getTheoryBundle());
		} else if (getCurrentIndex() == 1) {
			_filePath = getFilePathFromOvingBundle(getOvingBundle());
		}
		decodeDataDocument();

		_fragManag.beginTransaction().replace(R.id.flViskos, _nWVB).commit();
		Log.i(KEY_LOGCAT, "replacing the fragment with fragment manager");
		_listView.setItemChecked(position, true);

		_drawerLayout.closeDrawer(_listView);

		Log.i(KEY_LOGCAT, "Finishing selectItem");
		Log.i(KEY_LOGCAT, "  ");
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
		if (getCurrentIndex() == 0) {
			setTheoryBundle(_currentBundle);
			_filePath = getFilePathFromTeoriBundle(getTheoryBundle());
			_nWVB.Reload(getTheoryBundle());
			decodeDataDocument();
		} else if (getCurrentIndex() == 1) {
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
			_bTitle = _bNextOrLast = false;
			setAssetManager();
			setInputStream(_filePath + "_.txt");
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
						if (getCurrentIndex() == 0) {
							setNextBundle(createNewTeoriBundle(_StringBuffer
									.toString()));
						} else if (getCurrentIndex() == 1) {
							setNextBundle(createNewOvingBundle(_StringBuffer
									.toString()));
						}
						Log.i(KEY_LOGCAT,
								"Created the bundle for the next page");
						continue;
					} else if (str.contains("</last>")) {
						_bNextOrLast = false;
						if (getCurrentIndex() == 0) {
							setPreviousBundle(createNewTeoriBundle(_StringBuffer
									.toString()));
						} else if (getCurrentIndex() == 1) {
							setPreviousBundle(createNewOvingBundle(_StringBuffer
									.toString()));
						}
						Log.i(KEY_LOGCAT,
								"Created the bundle for the previous page");
						continue;
					} else {
						// this is where i left off
						if (getCurrentIndex() == getPriorityIndex()) {
							if (str.contains("<list>")) {
								// make sure that this priority thing works
								_bList = true;
								_StringArray = new ArrayList<String>();
								continue;
							} else if (str.contains("</list>")) {
								_bList = false;
								if (!_StringArray.isEmpty()) {
									String[] stringarray = arrayListToStringArray(_StringArray);
									setListViewArray(stringarray);
									Log.i(KEY_LOGCAT,
											"Length of stored string: "
													+ stringarray.length);
									setListArray(stringarray);
								}
								continue;
							}
						}
					}

					if (_bTitle || _bNextOrLast) {
						_StringBuffer.append(str);
					} else if (_bList) {
						if (!str.isEmpty()) {
							if (str.contains("#")) {
								String[] SplitString = str.split("#");
								_StringArray.add(SplitString[0]);
								if (SplitString.length > 1) {
									SplitString = SplitString[1].split("/");
									newStringBuffer();
									if (SplitString[0]
											.contentEquals("pros_og_teori_text")) {
										for (int i = 1; i < SplitString.length - 1; i++) {
											_StringBuffer.append(SplitString[i]
													+ "/");
										}
										_StringBuffer
												.append(SplitString[SplitString.length - 1]);
										setTheoryBundle(createNewTeoriBundle(_StringBuffer
												.toString()));
									} else if (SplitString[0]
											.contentEquals("Ovinger")) {
										for (int i = 1; i < SplitString.length - 1; i++) {
											_StringBuffer.append(SplitString[i]
													+ "/");
										}
										_StringBuffer
												.append(SplitString[SplitString.length - 1]);
										setOvingBundle(createNewOvingBundle(_StringBuffer
												.toString()));
									}
								}
							} else {
								_StringArray.add(str);
							}
						}
					}
				}
			}
			_InputStream.close();
		} catch (IOException e) {
			Log.e(KEY_LOGCAT, "Didn't open or find _.txt");
		}
		Log.i(KEY_LOGCAT, "Finishing decodeDataDocument");
		Log.i(KEY_LOGCAT, "  ");
	}

	private void Initialize() {
		// _activity_title = getTitle();
		setActivityTitle("Menyen >.<");
		_drawerLayout = (DrawerLayout) findViewById(R.id.dlVis_Teori);
		_listView = (ListView) findViewById(R.id.lvVis_Teori);
		_bNext = (Button) findViewById(R.id.bNext);
		_bPrevious = (Button) findViewById(R.id.bPrevious);

		// this can later be removed
		_testArray = getResources().getStringArray(R.array.test_array);
		setListViewArray(_testArray);
		//

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

		selectItem(getCurrentIndex());

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

	// setter and getter for listarray
	// maybe remove this later if found out that it's useless
	protected void setListArray(String[] anArray) {
		this.getIntent().putExtra(KEY_LIST_ARRAY, anArray);
		Log.i(KEY_LOGCAT, "Finishing storeCurrentIndex");
		Log.i(KEY_LOGCAT, "  ");
	}

	protected String[] getListArray() throws Exception {
		return this.getIntent().getStringArrayExtra(KEY_LIST_ARRAY);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}
