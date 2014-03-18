package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ShowContent extends Activity implements OnClickListener {
	protected DrawerLayout _drawerLayout;
	protected ListView _listView;
	protected ActionBarDrawerToggle _actionDrawerToggle;
	protected String[] _testArray;
	protected CharSequence _title;
	protected CharSequence _activity_title;
	protected WebViewBase _nWVB;
	protected int _currentIndex = 0;
	protected Button _bPrevious, _bNext;

	protected Bundle _currentBundle, _nextBundle, _previousBundle;

	public final static String KEY_SHOWCONTENT = "com.bbv.prototype1.SHOWCONTENT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcontent);
		Initialize(savedInstanceState);
		_currentBundle = this.getIntent().getBundleExtra(
				WebViewBase.KEY_PROS_TEORI);
	}

	private void Initialize(Bundle savedInstanceState) {
		_activity_title = getTitle();
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
		// if (savedInstanceState == null) {
		// selectItem(VTStatus);
		// } else {
		// selectItem(VTStatus);
		// }
		selectItem(_currentIndex);
	}

	private void selectItem(int position) {

		FragmentManager fm = getFragmentManager();
		switch (position) {
		case 0:
			_currentIndex = 0;
			_nWVB = new WebViewTeori();
			_nWVB.setArguments(this.getIntent().getBundleExtra(
					WebViewBase.KEY_PROS_TEORI));
			break;
		}
		getNewBundles(position);
		fm.beginTransaction().replace(R.id.flViskos, _nWVB).commit();
		_listView.setItemChecked(position, true);

		_drawerLayout.closeDrawer(_listView);
		// if to change the title depending on the selected item do it here
		// setTitle(_title);// if don't inside fragment, this not needed
	}

	public void setListViewArray(String[] list) {
		_listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
				list));
		_listView.setOnItemClickListener(new DrawerItemClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void setTitle(CharSequence title) {
		_title = title;
		getActionBar().setTitle(_title);
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

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNext:
			_currentBundle = _nextBundle;
			Log.e("ShowContent", "Next Button Presset");
			// setTeoriBundle(_currentBundle);
			break;
		case R.id.bPrevious:
			_currentBundle = _previousBundle;
			Log.e("ShowContent", "Previous Button Presset");
			// setTeoriBundle(_currentBundle);
			break;
		}
		getNewBundles(_currentIndex);
		_nWVB.Reload(_currentBundle);
		
	}

	public void getNewBundles(int position) {
		if (position == 0) {
			try {
				String str = "";
				String path = "If shown it failed";
				InputStream is = this.getAssets().open("pros_og_teori_text/1. Leire og vektmaterialer/_.txt");
				try {
					path = WebViewBase.getFilePathFromTeoriBundle(_currentBundle);
					is = this.getAssets().open(path + "/_.txt");
				} catch (Exception e) {
					e.printStackTrace();
				}

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				StringBuffer buffer = new StringBuffer();
				Boolean _bTitle = false, _bNextOrLast = false;

				if (is != null) {
					while ((str = reader.readLine()) != null) {

						if (str.contains("<title>")) {
							_bTitle = true;
							buffer = new StringBuffer();
							continue;
						} else if (str.contains("</title")) {
							_bTitle = false;
							setTitle(buffer.toString());
							continue;
						} else if (str.contains("<next>")
								|| str.contains("<last>")) {
							_bNextOrLast = true;
							buffer = new StringBuffer();
							continue;
						} else if (str.contains("</next>")) {
							_bNextOrLast = false;
							_nextBundle = _nWVB.createNewTeoriBundle(buffer
									.toString());
							continue;
						} else if (str.contains("</last>")) {
							_bNextOrLast = false;
							_previousBundle = _nWVB.createNewTeoriBundle(buffer
									.toString());
							continue;
						}

						if (_bTitle || _bNextOrLast) {
							buffer.append(str);
						}
					}
				}
				is.close();
			} catch (Exception e) {
				Log.e("ShowContent", "File not found or existing");
			}
		}
	}
}
