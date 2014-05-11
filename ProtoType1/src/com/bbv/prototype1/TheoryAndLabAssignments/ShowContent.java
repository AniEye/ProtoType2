package com.bbv.prototype1.TheoryAndLabAssignments;

import java.io.IOException;
import java.util.ArrayList;
import com.bbv.prototype1.R;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
		if (savedInstanceState == null) {
			Log.i(KEY_LOGCAT,
					"Start doing what is needed when savedInstace = null");

			if (getTheoryBundle() != null) {// if coming from Pros_og_Teori.java
				setPriorityIndex(_DrawerMenuListIndex.Theory.ordinal());
				setCurrentIndex(_DrawerMenuListIndex.Theory.ordinal());
				_filePath = getFilePathFromTeoriBundle(getTheoryBundle());

				_nWVB.setArguments(getTheoryBundle());
				decodeDataDocument();
				_fragManag.beginTransaction().replace(R.id.flViskos, _nWVB)
						.commit();

			} else if (getOvingBundle() != null) {// if coming from Ovinger.java
				setPriorityIndex(_DrawerMenuListIndex.Exercise.ordinal());
				setCurrentIndex(_DrawerMenuListIndex.Exercise.ordinal());
				_filePath = getFilePathFromOvingBundle(getOvingBundle());
				_nWVB.setArguments(getOvingBundle());
				decodeDataDocument();

				_fragManag.beginTransaction().replace(R.id.flViskos, _nWVB)
						.commit();
			}
		} else {
//			Log.i(KEY_LOGCAT,
//					"Start doing what is needed when savedInstace != null");
//			if (getCurrentIndex() == _DrawerMenuListIndex.Theory.ordinal()) {
//				_filePath = getFilePathFromTeoriBundle(getTheoryBundle());
//
//				_nWVB.setArguments(getTheoryBundle());
//				decodeDataDocument();
//
//				_fragManag.beginTransaction().replace(R.id.flViskos, _nWVB)
//						.commit();
//			} else if (getCurrentIndex() == _DrawerMenuListIndex.Exercise
//					.ordinal()) {
//				_filePath = getFilePathFromOvingBundle(getOvingBundle());
//
//				_nWVB.setArguments(getOvingBundle());
//				decodeDataDocument();
//
//				_fragManag.beginTransaction().replace(R.id.flViskos, _nWVB)
//						.commit();
//			} else if (getCurrentIndex() == _DrawerMenuListIndex.Calculator
//					.ordinal()) {
//				_nSCalc = new ShowCalculator();
//				_nSCalc.setArguments(getCalculatorBundle());
//				setTitle("Kalkulator");
//			}

		}

		Log.i(KEY_LOGCAT, "replacing the fragment with fragment manager");

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	// setter and getter for the theory and oving bundles
	/**
	 * Saves the TheoryBundle in the instance
	 * 
	 * @param a
	 *            TheoryBundle
	 */
	protected void setTheoryBundle(Bundle aBundle) {
		this.getIntent().putExtra(KEY_PROS_TEORI, aBundle);
		Log.i(KEY_LOGCAT, "Finishing setTheoryBundle");
	}

	/**
	 * Saves the OvingBundle in the instance
	 * 
	 * @param a
	 *            OvingBundle
	 */
	protected void setOvingBundle(Bundle aBundle) {
		this.getIntent().putExtra(KEY_OVING, aBundle);
		Log.i(KEY_LOGCAT, "Finishing setOvingBundle");
	}

	/**
	 * Saves the CalculatorBundle in the instance
	 * 
	 * @param a
	 *            CalculatorBundle
	 */
	protected void setCalculatorBundle(Bundle abundle) {
		this.getIntent().putExtra(KEY_CHOSENCALC, abundle);
	}

	/**
	 * Gets the TheoryBundle from the instance
	 * 
	 * @return a TheoryBundle
	 */
	protected Bundle getTheoryBundle() {
		return this.getIntent().getBundleExtra(KEY_PROS_TEORI);
	}

	/**
	 * Gets the OvingBundle from the instance
	 * 
	 * @return a OvingBundle
	 */
	protected Bundle getOvingBundle() {
		return this.getIntent().getBundleExtra(KEY_OVING);
	}

	/**
	 * Gets the CalculatorBundle from the instance
	 * 
	 * @return a CalculatorBundle
	 */
	protected Bundle getCalculatorBundle() {
		return this.getIntent().getBundleExtra(KEY_CHOSENCALC);
	}

	// setter and getter for priorityindex
	/**
	 * Stores the priorityIndex in the instance
	 * 
	 * @param index
	 */
	protected void setPriorityIndex(int index) {
		this.getIntent().putExtra(KEY_PRIORITY_INDEX, index);
		Log.i(KEY_LOGCAT, "Finishing setPriorityIndex");
	}

	/**
	 * Gets the PriorityIndex from the instance
	 * 
	 * @return PriorityIndex
	 */
	protected int getPriorityIndex() {
		return this.getIntent().getIntExtra(KEY_PRIORITY_INDEX, -1);
	}

	// setter and getter for currentindex
	/**
	 * Gets the CurrentIndex from the instance
	 * 
	 * @return CurrentIndex
	 */
	protected int getCurrentIndex() {
		return this.getIntent().getIntExtra(KEY_CURRENT_INDEX, -1);
	}

	/**
	 * Stores the CurrentIndex in the instance
	 * 
	 * @param index
	 */
	protected void setCurrentIndex(int index) {
		this.getIntent().putExtra(KEY_CURRENT_INDEX, index);
		Log.i(KEY_LOGCAT, "Finishing setCurrentIndex");
		Log.i(KEY_LOGCAT, "  ");
	}

	/**
	 * Stores the number of items in the NavigationDrawer that has been loaded
	 * 
	 * @param number
	 */
	protected void setItemInDrawerLoaded(int number) {
		this.getIntent().putExtra(KEY_DRAWERLOADEDNUMBER, number);
	}

	/**
	 * Retrieves the number of items in the NavigationDrawer that has been
	 * loaded
	 * 
	 * @return AmountLoaded
	 */
	protected int getItemInDrawerLoaded() {
		return this.getIntent().getIntExtra(KEY_DRAWERLOADEDNUMBER, 0);
	}
}
