package com.bbv.prototype1;

import java.util.ArrayList;

import android.R.fraction;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
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

	protected Fragment_Base _nFB = null;
	protected ArrayList<Intent> _intents;
	protected Bundle _savedInstanceBundle = null;
	protected ArrayList<ArrayList<Bundle>> _bundleList;
	protected int VTStatus = 0;
	protected int priorityPosition;
	protected Bundle priorityBundle;

	private final static String KEY_PRIORITY = "Priority";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vis_teori);
		findCurrentVTStatus(savedInstanceState);

		Initialize(savedInstanceState);
	}

	public void setTeoriBundle(Bundle newBundle) {
		this.getIntent().putExtra(Fragment_Base.KEY_PROS_TEORI, newBundle);
		setPriorityBundle(newBundle);
	}

	public void setOvingerBundle(Bundle newBundle) {
		this.getIntent().putExtra(Fragment_Base.KEY_OVING, newBundle);
		setPriorityBundle(newBundle);
	}

	public void setPriorityBundle(Bundle priority) {
		if (VTStatus == priorityPosition) {
			priorityBundle = priority;
		}
	}

	private void Initialize(Bundle savedInstanceState) {
		_activity_title = getTitle();
		_drawerLayout = (DrawerLayout) findViewById(R.id.dlVis_Teori);
		_listView = (ListView) findViewById(R.id.lvVis_Teori);

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
		// if (savedInstanceState == null) {
		// selectItem(VTStatus);
		// } else {
		// selectItem(VTStatus);
		// }
		selectItem(VTStatus);
	}

	private void findCurrentVTStatus(Bundle savedInBundle) {
		if (this.getIntent().getBundleExtra(Fragment_Base.KEY_PROS_TEORI) != null) {
			VTStatus = 0;
			if (savedInBundle == null) {
				priorityPosition = 0;
				priorityBundle=this.getIntent().getBundleExtra(Fragment_Base.KEY_PROS_TEORI);
			}
		} else if (this.getIntent().getBundleExtra(Fragment_Base.KEY_OVING) != null) {
			VTStatus = 1;
			if (savedInBundle == null) {
				priorityPosition = 1;
				priorityBundle=this.getIntent().getBundleExtra(Fragment_Base.KEY_OVING);
			}
		}
		if (savedInBundle != null) {
			priorityPosition = savedInBundle.getInt(KEY_PRIORITY);
		}
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

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	protected void selectItem(int position) {
		FragmentManager fm = getFragmentManager();

		switch (position) {
		case 0:
			VTStatus = 0;
			_nFB = new Fragment_Teori();
			_nFB.setArguments(this.getIntent().getBundleExtra(
					Fragment_Base.KEY_PROS_TEORI));
			_nFB.setVisTeori(this);

			break;
		case 1:
			VTStatus = 1;
			_nFB = new Fragment_Ovinger();
			_nFB.setArguments(this.getIntent().getBundleExtra(
					Fragment_Base.KEY_OVING));
			_nFB.setVisTeori(this);

		default:
			if (position != priorityPosition)
				goToNextPage(position);
			else{
				_nFB.setArguments(priorityBundle);
			}
		}

		fm.beginTransaction().replace(R.id.flViskos, _nFB).commit();
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

	public void setIntentArray(ArrayList<Intent> IntentArray) {
		_intents = IntentArray;
	}

	public void setBundleArray(ArrayList<ArrayList<Bundle>> bundleList) {
		if (priorityPosition == VTStatus)
			_bundleList = bundleList;
	}

	public void goToNextPage(int position) {
		if (!_bundleList.isEmpty()) {
			if (!_bundleList.get(position).isEmpty()) {
				_nFB.setArguments(_bundleList.get(position).get(0));
			}
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		_actionDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (this.getIntent().getBundleExtra(Fragment_Base.KEY_PROS_TEORI) != null) {
			outState.putBundle(Fragment_Base.KEY_PROS_TEORI, getIntent()
					.getBundleExtra(Fragment_Base.KEY_PROS_TEORI));
		} else if (this.getIntent().getBundleExtra(Fragment_Base.KEY_OVING) != null) {
			outState.putBundle(Fragment_Base.KEY_OVING, getIntent()
					.getBundleExtra(Fragment_Base.KEY_OVING));
		}
		outState.putInt(KEY_PRIORITY, priorityPosition);
		outState.putBundle(KEY_PRIORITY, priorityBundle);
	}

}