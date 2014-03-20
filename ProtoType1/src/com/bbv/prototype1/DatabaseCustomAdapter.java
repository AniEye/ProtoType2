package com.bbv.prototype1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DatabaseCustomAdapter extends BaseAdapter implements
		OnClickListener {
	/*********
	 * Adapter class extends with BaseAdapter and implements with
	 * OnClickListener This is taken from androidexample.com this is the link:
	 * http
	 * ://androidexample.com/How_To_Create_A_Custom_Listview_-_Android_Example
	 * /index.php?view=article_discription&aid=67&aaid=92
	 ************/

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList data;
	private static LayoutInflater inflater = null;
	public Resources res;
	DatabaseContent tempValues = null;
	int i = 0;

	/************* CustomAdapter Constructor *****************/
	public DatabaseCustomAdapter(Activity a, ArrayList d, Resources resLocal) {

		/********** Take passed values **********/
		activity = a;
		data = d;
		res = resLocal;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/******** What is the size of Passed Arraylist Size ************/
	public int getCount() {

		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView Chapter;
		public TextView ChapterPart1;
		public TextView ChapterPart2;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.database_list_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.Chapter = (TextView) vi.findViewById(R.id.DLIChapter);
			holder.ChapterPart1 = (TextView) vi.findViewById(R.id.DLIChapterPart1);
			holder.ChapterPart2 = (TextView) vi.findViewById(R.id.DLIChapterPart2);
			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			holder.Chapter.setText("No Data");

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (DatabaseContent) data.get(position);

			/************ Set Model values in Holder elements ***********/

			holder.Chapter.setText(tempValues.getChapter());
			holder.ChapterPart1.setText(tempValues.getChapterPart1());
			holder.ChapterPart2.setText(tempValues.getChapterPart2());
			
//			setImageResource(res.getIdentifier(
//					"com.androidexample.customlistview:drawable/"
//							+ tempValues.getImage(), null, null));

			/******** Set Item Click Listner for LayoutInflater for each row *******/

			vi.setOnClickListener(new OnItemClickListener(position));
		}
		return vi;
	}

	@Override
	public void onClick(View v) {
		Log.v("CustomAdapter", "=====Row button clicked=====");
	}

	/********* Called when Item click in ListView ************/
	private class OnItemClickListener implements OnClickListener {
		private int mPosition;

		OnItemClickListener(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View arg0) {

			SQLView sct = (SQLView) activity;

			/****
			 * Call onItemClick Method inside CustomListViewAndroidExample Class
			 * ( See Below )
			 ****/

			sct.onItemClick(mPosition);
		}
	}
}
