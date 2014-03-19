package com.bbv.prototype1;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.datatype.Duration;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
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

		if (this.getIntent().getBundleExtra(KEY_PROS_TEORI) != null) {

			_currentBundle = this.getIntent().getBundleExtra(KEY_PROS_TEORI);
			_currentIndex = 0;
			Log.i(KEY_LOGCAT, "theorybundle from intent is(chapter): "
					+ _currentBundle.getString(KEY_CHAPTER));
			Log.i(KEY_LOGCAT, "Retrieved theorybundle from intent");
		} else if (this.getIntent().getBundleExtra(KEY_OVING) != null) {
			_currentBundle = this.getIntent().getBundleExtra(KEY_OVING);
			_currentIndex = 1;
			Log.i(KEY_LOGCAT, "ovingbundle from intent is(oving): "
					+ _currentBundle.getString(KEY_OVING));
			Log.i(KEY_LOGCAT, "Retrieved ovingbundle from intent");
		}
		if (savedInstanceState == null) {
			if (this.getIntent().getBundleExtra(KEY_PROS_TEORI) != null)
				_PriorityIndex = 0;
			else if (this.getIntent().getBundleExtra(KEY_OVING) != null)
				_PriorityIndex = 1;

			setPriorityIndex(_PriorityIndex);
		} else {
			_PriorityIndex = this.getIntent()
					.getIntExtra(KEY_PRIORITY_INDEX, 0);
		}

		Initialize();

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

		selectItem(_currentIndex);

	}

	@Override
	protected void selectItem(int position) {

		Log.i(KEY_LOGCAT, "Running selectItem");
		_fragManag = getFragmentManager();
		Log.i(KEY_LOGCAT, "got fragment manager");
		switch (position) {
		case 0:
			if (this.getIntent().getBundleExtra(KEY_PROS_TEORI) != null) {
				_currentIndex = 0;
				_nWVB = new WebViewTeori();
				Log.i(KEY_LOGCAT, "created new instance of webViewTeori");
				_currentBundle = this.getIntent()
						.getBundleExtra(KEY_PROS_TEORI);
				_nWVB.setArguments(_currentBundle);
				Log.i(KEY_LOGCAT, "gave it the arguments needed");
			} else {
				Toast.makeText(this, "Ingen teori lagt til som referanse",
						Toast.LENGTH_LONG).show();
			}
			break;
		case 1:
			if (this.getIntent().getBundleExtra(KEY_OVING) != null) {
				_currentIndex = 1;
				_nWVB = new WebViewOving();
				Log.i(KEY_LOGCAT, "created new instance of webViewOving");
				_currentBundle = this.getIntent().getBundleExtra(KEY_OVING);
				_nWVB.setArguments(_currentBundle);
				Log.i(KEY_LOGCAT, "gave it the arguments needed");
			} else {
				Toast.makeText(this, "Ingen øving lagt til som referanse",
						Toast.LENGTH_LONG).show();
			}
			break;
		}
		if (_currentIndex == 0) {
			_filePath = getFilePathFromTeoriBundle(_currentBundle);
		} else if (_currentIndex == 1) {
			_filePath = getFilePathFromOvingBundle(_currentBundle);
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

	protected void setPriorityIndex(int index) {
		this.getIntent().putExtra(KEY_PRIORITY_INDEX, index);
		Log.i(KEY_LOGCAT, "Finishing setPriorityIndex");
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
		if (_currentIndex == 0) {
			_filePath = getFilePathFromTeoriBundle(_currentBundle);
		} else if (_currentIndex == 1) {
			_filePath = getFilePathFromOvingBundle(_currentBundle);
		}
		decodeDataDocument();

		if (_currentIndex == 0) {
			setTheoryBundle(_currentBundle);
		} else if (_currentIndex == 1) {
			setOvingBundle(_currentBundle);
		}
		_nWVB.Reload(_currentBundle);

		Log.i(KEY_LOGCAT, "Finishing onClick");
		Log.i(KEY_LOGCAT, "  ");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		// Log.i(KEY_LOGCAT, "Running onSaveInstance");
		// Log.i(KEY_LOGCAT,
		// "Is theoryBundle empty: "+getIntent().getBundleExtra(WebViewBase.KEY_PROS_TEORI).isEmpty());
		//
		// if
		// (!this.getIntent().getBundleExtra(WebViewBase.KEY_PROS_TEORI).isEmpty())
		// {
		// outState.putBundle(WebViewBase.KEY_PROS_TEORI, getIntent()
		// .getBundleExtra(WebViewBase.KEY_PROS_TEORI));
		// Log.i(KEY_LOGCAT, "Saving theoryBundle");
		// }
		//
		// Log.i(KEY_LOGCAT, "Finishing onsaveinstanceState");
		// Log.i(KEY_LOGCAT, "  ");
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
						if (_currentIndex == 0) {
							setNextBundle(createNewTeoriBundle(_StringBuffer
									.toString()));
						} else if (_currentIndex == 1) {
							setNextBundle(createNewOvingBundle(_StringBuffer
									.toString()));
						}
						Log.i(KEY_LOGCAT,
								"Created the bundle for the next page");
						continue;
					} else if (str.contains("</last>")) {
						_bNextOrLast = false;
						if (_currentIndex == 0) {
							setPreviousBundle(createNewTeoriBundle(_StringBuffer
									.toString()));
						} else if (_currentIndex == 1) {
							setPreviousBundle(createNewOvingBundle(_StringBuffer
									.toString()));
						}
						Log.i(KEY_LOGCAT,
								"Created the bundle for the previous page");
						continue;
					} else if (str.contains("<list>")) {
						if (_currentIndex == _PriorityIndex) {
							// make sure that this priority thing works
							_bList = true;
							_StringArray = new ArrayList<String>();
						} else {
							_bList = false;
						}
						continue;
					} else if (str.contains("</list>")) {
						_bList = false;
						if (!_StringArray.isEmpty())
							setListViewArray(arrayListToStringArray(_StringArray));
						continue;
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

}
