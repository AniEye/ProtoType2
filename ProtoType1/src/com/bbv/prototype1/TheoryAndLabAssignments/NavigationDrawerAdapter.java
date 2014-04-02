package com.bbv.prototype1.TheoryAndLabAssignments;

import java.util.ArrayList;
import java.util.Arrays;

import com.bbv.prototype1.R;
import com.bbv.prototype1.R.id;
import com.bbv.prototype1.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

public class NavigationDrawerAdapter extends BaseAdapter implements
		OnClickListener {

	private ShowContent _Activity;
	private ArrayList<NavigationDrawerItemContent> _ContentArray;
	private static LayoutInflater _inflater = null;
	public Resources _res;
	private NavigationDrawerItemContent _tempItemContent = null;
	private ArrayList<TableLayout> _TableList = new ArrayList<TableLayout>();

	private static final String KEY_LOGCAT = "NavigationDrawerAdapter";// if
																		// needed

	/**
	 * The constructor takes in parameters for referencing the main activity and
	 * it's content.
	 * 
	 * @param activity
	 * @param contentArray
	 *            (NavigationDrawerItemContent)
	 * @param resources
	 */
	public NavigationDrawerAdapter(Activity activity,
			ArrayList<NavigationDrawerItemContent> contentArray,
			Resources resources) {

		_Activity = (ShowContent) activity;

		_ContentArray = contentArray;
		_res = resources;

		/** Layout inflator to call external xml layout () */
		_inflater = (LayoutInflater) _Activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/** Create a holder Class to contain inflated xml file elements */
	public static class ViewHolder {

		public TextView _Title;
		public TableLayout _Table;
	}

	// These getters aren't used at all
	/**
	 * Returns the Size of the array with the NavigationDrawerItemContent
	 */
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

	/**
	 * setting up every item in the NavigationDrawer
	 */
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
		
		if (_Activity.getItemInDrawerLoaded() < _ContentArray.size()) {
			/***** Get each Model object from Arraylist ********/
			_tempItemContent = null;
			_tempItemContent = _ContentArray.get(position);

			/************ Set Model values in Holder elements ***********/
			SpannableString span = new SpannableString(
					_tempItemContent.getTitle());
			span.setSpan(new UnderlineSpan(), 0, span.length(), 0);
			_holder._Title.setText(span);

			_holder._Title.setTag(position);
			_holder._Title.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int thePosition = (Integer) v.getTag();
					if (thePosition == ShowContentBase._DrawerMenuListIndex.MainMenu
							.ordinal()) {
						_Activity.setResult(2);
						_Activity.finish();

					} else {
						if (_ContentArray.get(thePosition).getStringList() != null
								&& _ContentArray.get(thePosition)
										.getStringList().length != 0) {

							if (_TableList.get(thePosition).getVisibility() == android.view.View.GONE) {
								_TableList.get(thePosition).setVisibility(
										android.view.View.VISIBLE);
							} else {
								_TableList.get(thePosition).setVisibility(
										android.view.View.GONE);
							}

						} else {
							_Activity.showToast("No references");
						}
						for (int i = 0; i < _ContentArray.size(); i++) {
							if (i != thePosition) {
								_TableList.get(i).setVisibility(
										android.view.View.GONE);
							}
						}
					}
				}
			});

			if (_tempItemContent.getStringList() != null) {
				if (_tempItemContent.getStringList().length != 0) {
					for (int i = 0; i < _tempItemContent.getStringList().length; i++) {
						TextView textView = (TextView) View.inflate(_Activity,
								R.layout.drawer_item, null);
						String[] splitting = _tempItemContent.getStringList()[i]
								.split("/");

						textView.setTag(_tempItemContent.getStringList()[i]);
						textView.setText(splitting[splitting.length - 1]);
						textView.setOnClickListener(this);
						_holder._Table.addView(textView);
					}
				}
			}
			_Activity
					.setItemInDrawerLoaded(_Activity.getItemInDrawerLoaded() + 1);
		}
		return _view;

	}

	// ***************listeners**************************
	/**
	 * This on click listener is used by all the reference items
	 */
	@Override
	public void onClick(View v) {

		String textReference = (String) v.getTag();
		String[] splitted = textReference.split("/");
		_Activity.displayNextLast(true);
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
			_Activity
					.setCurrentIndex(ShowContentBase._DrawerMenuListIndex.Theory
							.ordinal());

			try {
				_Activity._nWVB.Reload(_Activity.getTheoryBundle());

				_Activity._filePath = _Activity
						.getFilePathFromTeoriBundle(_Activity.getTheoryBundle());
				_Activity.decodeDataDocument();

				_Activity._fragManag.beginTransaction()
						.replace(R.id.flViskos, _Activity._nWVB).commit();

				_Activity._drawerLayout.closeDrawer(_Activity._listView);

				_TableList.get(
						ShowContentBase._DrawerMenuListIndex.Theory.ordinal())
						.setVisibility(android.view.View.GONE);

			} catch (Exception e) {
				_Activity.showToast("The file does not exist");
			}
		} else if (splitted[0].contentEquals("Ovinger") && splitted.length > 1) {

			Bundle newBundle = new Bundle();
			newBundle.putString(ShowContentBase.KEY_OVING, splitted[1].trim());

			_Activity.setOvingBundle(newBundle);
			_Activity
					.setCurrentIndex(ShowContentBase._DrawerMenuListIndex.Exercise
							.ordinal());

			try {
				_Activity._nWVB.Reload(_Activity.getOvingBundle());

				_Activity._filePath = _Activity
						.getFilePathFromOvingBundle(_Activity.getOvingBundle());
				_Activity.decodeDataDocument();

				_Activity._fragManag.beginTransaction()
						.replace(R.id.flViskos, _Activity._nWVB).commit();

				_Activity._drawerLayout.closeDrawer(_Activity._listView);
				_TableList
						.get(ShowContentBase._DrawerMenuListIndex.Exercise
								.ordinal()).setVisibility(
								android.view.View.GONE);

			} catch (Exception e) {
				_Activity.showToast("The file does not exist");
			}
		} else if (Arrays.asList(ShowCalculator._calcList)
				.contains(splitted[0])) {

			Bundle newBundle = new Bundle();
			newBundle.putString(ShowContentBase.KEY_CHOSENCALC, splitted[0]);
			_Activity.setCalculatorBundle(newBundle);
			_Activity.displayNextLast(false);
			_Activity.setTitle("Kalkulator");

			_Activity
					.setCurrentIndex(ShowContentBase._DrawerMenuListIndex.Calculator
							.ordinal());

			_Activity._nSCalc = new ShowCalculator();
			_Activity._nSCalc.setArguments(_Activity.getCalculatorBundle());
			
			_Activity._fragManag.beginTransaction()
					.replace(R.id.flViskos, _Activity._nSCalc).commit();

			_Activity._drawerLayout.closeDrawer(_Activity._listView);
			_TableList.get(
					ShowContentBase._DrawerMenuListIndex.Calculator.ordinal())
					.setVisibility(android.view.View.GONE);
		}

		else {
			_Activity.showToast("This will not open anything");
		}
	}
}
