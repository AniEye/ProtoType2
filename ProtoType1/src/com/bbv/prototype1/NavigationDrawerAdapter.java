package com.bbv.prototype1;

import java.util.ArrayList;

import com.bbv.prototype1.ShowContentBase.DrawerItemClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationDrawerAdapter extends BaseAdapter implements
		OnClickListener {

	private ShowContent _Activity;
	private ArrayList<NavigationDrawerItemContent> _ContentArray;
	private static LayoutInflater _inflater = null;
	public Resources _res;
	private NavigationDrawerItemContent _tempItemContent = null;
	private ArrayList<TableLayout> _TableList = new ArrayList<TableLayout>();

	private static final String KEY_LOGCAT = "NavigationDrawerAdapter";

	public NavigationDrawerAdapter(Activity activity,
			ArrayList<NavigationDrawerItemContent> contentArray,
			Resources resources) {

		_Activity = (ShowContent) activity;

		_ContentArray = contentArray;
		_res = resources;

		/*********** Layout inflator to call external xml layout () ***********/
		_inflater = (LayoutInflater) _Activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView _Title;
		public TableLayout _Table;
	}

	// ****************getters****************
	@Override
	public int getCount() {
		if (_ContentArray.size() <= 0) {
			return 1;
		}
		return _ContentArray.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View _view = convertView;
		ViewHolder _holder;

		if (convertView == null) {
			/******
			 * Inflate navigation_drawer_item.xml file for each row ( Defined
			 * below )
			 *******/
			_view = _inflater.inflate(R.layout.navigation_drawer_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			_holder = new ViewHolder();
			_holder._Title = (TextView) _view.findViewById(R.id.NDITitle);
			_holder._Table = (TableLayout) _view.findViewById(R.id.NDITable);
			_TableList.add(_holder._Table);

			/************ Set holder with LayoutInflater ************/
			_view.setTag(_holder);

		} else
			_holder = (ViewHolder) _view.getTag();

		// if (_ContentArray.size() <= 0) {
		// _holder._Title.setText("No Data");
		//
		// } else {
		/***** Get each Model object from Arraylist ********/
		_tempItemContent = null;
		_tempItemContent = _ContentArray.get(position);

		/************ Set Model values in Holder elements ***********/
		SpannableString span = new SpannableString(_tempItemContent.getTitle());
		span.setSpan(new UnderlineSpan(), 0, span.length(), 0);
		_holder._Title.setText(span);
		Log.e(KEY_LOGCAT, "_tableList.size() is: " + _TableList.size());
		_holder._Title.setTag(position);
		_holder._Title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int thePosition = (Integer) v.getTag();
				if (_ContentArray.get(thePosition).getStringList() != null
						&& _ContentArray.get(thePosition).getStringList().length != 0) {

					if (_TableList.get(thePosition).getVisibility() == android.view.View.GONE) {
						_TableList.get(thePosition).setVisibility(
								android.view.View.VISIBLE);
					} else {
						_TableList.get(thePosition).setVisibility(
								android.view.View.GONE);
					}

				} else {
					Toast.makeText(_Activity, "No references",
							Toast.LENGTH_LONG).show();
				}
				Log.e(KEY_LOGCAT,
						"Length of textview list is: " + _TableList.size());
				for (int i = 0; i < _ContentArray.size(); i++) {
					if (i != thePosition) {
						_TableList.get(i).setVisibility(android.view.View.GONE);
					}
				}
			}
		});

		if (_tempItemContent.getStringList() != null) {
			if (_tempItemContent.getStringList().length != 0) {
				for (int i = 0; i < _tempItemContent.getStringList().length; i++) {
					TextView textView = (TextView) View.inflate(_Activity,
							R.layout.list_item, null);
					String[] splitting = _tempItemContent.getStringList()[i]
							.split("/");

					textView.setTag(_tempItemContent.getStringList()[i]);
					textView.setText(splitting[splitting.length - 1]);
					textView.setOnClickListener(this);
					_holder._Table.addView(textView);
				}
			}
		}
		// }

		return _view;
	}

	// ***************listeners**************************
	@Override
	public void onClick(View v) {

		String textReference = (String) v.getTag();
		Log.v(KEY_LOGCAT, "Item pressed had tag: " + textReference);
		String[] splitted = textReference.split("/");
		if (splitted[0].contentEquals("pros_og_teori_text")
				&& splitted.length > 1) {
			Bundle newBundle = new Bundle();

			newBundle.putString(ShowContentBase.KEY_CHAPTER, splitted[1]);
			if (splitted.length > 2) {
				newBundle.putString(ShowContentBase.KEY_CHAPTERPART1,
						splitted[2]);
				if (splitted.length > 3) {
					newBundle.putString(ShowContentBase.KEY_CHAPTERPART2,
							splitted[3]);
				}
			}

			_Activity.setTheoryBundle(newBundle);
			_Activity.setCurrentIndex(0);
			try {
				_Activity._nWVB = new WebViewTeori();
				_Activity._nWVB.setArguments(_Activity.getTheoryBundle());
				_Activity._filePath = _Activity
						.getFilePathFromTeoriBundle(_Activity.getTheoryBundle());
				_Activity.decodeDataDocument();

				_Activity._fragManag.beginTransaction()
						.replace(R.id.flViskos, _Activity._nWVB).commit();
				// _Activity._listView.setItemChecked(position, true);

				_Activity._drawerLayout.closeDrawer(_Activity._listView);

			} catch (Exception e) {
				Toast.makeText(_Activity, "The file does not exist",
						Toast.LENGTH_LONG);
			}
		} else if (splitted[0].contentEquals("Ovinger") && splitted.length>1) {
			Bundle newBundle = new Bundle();
			newBundle.putString(ShowContentBase.KEY_OVING, splitted[1].trim());
			
			_Activity.setOvingBundle(newBundle);
			_Activity.setCurrentIndex(1);
			try {
				_Activity._nWVB = new WebViewOving();
				_Activity._nWVB.setArguments(_Activity.getOvingBundle());
				_Activity._filePath = _Activity
						.getFilePathFromOvingBundle(_Activity.getOvingBundle());
				_Activity.decodeDataDocument();

				_Activity._fragManag.beginTransaction()
						.replace(R.id.flViskos, _Activity._nWVB).commit();
				// _Activity._listView.setItemChecked(position, true);

				_Activity._drawerLayout.closeDrawer(_Activity._listView);

			} catch (Exception e) {
				Toast.makeText(_Activity, "The file does not exist",
						Toast.LENGTH_LONG);
			}
		}

		else {
			Toast.makeText(_Activity, "This will not open anything",
					Toast.LENGTH_LONG).show();
		}
	}
}
