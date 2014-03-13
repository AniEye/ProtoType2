package com.bbv.prototype1;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class Fragment_Base extends Fragment {

	protected LinearLayout _linlay;
	protected View _rootView;
	protected Vis_Teori _visReff;
	protected Bundle _currentBundle;
	public final static String KEY_OVING = "SOving";
	public final static String KEY_PROS_TEORI ="BProsTeori";
	
	public final static String KEY_CHAPTER = "Teori_Chapter";
	public final static String KEY_CHAPTERPART1 = "Teori_ChapterPart1";
	public final static String KEY_CHAPTERPART2 = "Teori_ChapterPart2";
	
	public final static String KEY_VIS_TEORI = "com.bbv.prototype1.VIS_TEORI";

	@Override
	public abstract View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	protected abstract void Initialize();

	protected TextView createTextView(String text, int color, int TextSize,
			int Gravity) {
		TextView addTitle = new TextView(getActivity());
		addTitle.setText(text);
		addTitle.setTextColor(color);
		addTitle.setGravity(Gravity);
		addTitle.setTextSize(TextSize);
		return addTitle;
	}

	protected ImageView createImageView(String ImagePath) {
		try {
			InputStream iis = getActivity().getAssets().open(ImagePath);
			ImageView image = new ImageView(getActivity());
			image.setPadding(0, 10, 0, 10);
			Bitmap thePicture = BitmapFactory.decodeStream(iis);
			image.setImageBitmap(thePicture);
			return image;
		} catch (Exception e) {
			Log.println(Log.ERROR, "Image", "Image not found");
			return null;
		}
	}

	protected void setRootView(int Layout,LayoutInflater inflater, ViewGroup container){
		_rootView = inflater.inflate(Layout, container, false);
	}
	
	protected String[] arrayListToStringArray(ArrayList<String> list) {
		int length = list.size();
		String[] array = new String[length];
		for (int i = 0; i < length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	protected void setLinearLayout(int id){
		_linlay = (LinearLayout) _rootView.findViewById(R.id.llFrag);
	}
	
	protected void setVisTeori(Vis_Teori vis) {
		_visReff = vis;
	}

	public void setBundle(Bundle theBundle) {
		_currentBundle=theBundle;
		
	}
}
