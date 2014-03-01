package com.bbv.prototype1;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Vis_Teori extends Activity {
	protected DrawerLayout _drawerLayout;
	protected ListView _listView;
	protected ActionBarDrawerToggle _actionDrawerToggle;
	protected String[] _testArray;
	protected CharSequence _title;
	protected CharSequence _activity_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vis_teori);

		Initialize(savedInstanceState);
		try {

		} catch (Exception e) {

		}
	}

	// this is not fixed up yet
	private void checkForBundle() {

	}

	private void Initialize(Bundle savedInstanceState) {
		_activity_title = getTitle();
		_drawerLayout = (DrawerLayout) findViewById(R.id.dlVis_Teori);
		_listView = (ListView) findViewById(R.id.lvVis_Teori);
		_testArray = getResources().getStringArray(R.array.test_array);

		_listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
				_testArray));
		_listView.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// helo
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
		if (savedInstanceState == null) {
			selectItem(0);
		}
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
		boolean drawerOpen = _drawerLayout.isDrawerOpen(_listView);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch (item.getItemId()) {
		// case android.R.id.home:
		// if (_drawerLayout.isDrawerOpen(_listView)) {
		// _drawerLayout.closeDrawer(_listView);
		// } else {
		// _drawerLayout.openDrawer(_listView);
		// }
		// return true;
		//
		// default:
		// return super.onOptionsItemSelected(item);
		// }
		if (_actionDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_settings:
			// create intent to perform web search for this planet
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
			// catch event that there's no activity to handle intent
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				Toast.makeText(this, "app not available", Toast.LENGTH_LONG)
						.show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	protected void selectItem(int position) {
		Fragment newFragment = new Fragment_1();
		_title = "This is page 1";
		FragmentManager fm = getFragmentManager();
		switch (position) {
		case 0:
			newFragment = new Fragment_1();
			_title = "This is page 1";
			break;
		case 1:
			newFragment = new Fragment_2();
			_title = "This is page 2";
			break;
		}
		fm.beginTransaction().replace(R.id.flViskos, newFragment).commit();
		_listView.setItemChecked(position, true);

		_drawerLayout.closeDrawer(_listView);
		// if to change the title depending on the selected item do it here
		setTitle(_title);
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

}