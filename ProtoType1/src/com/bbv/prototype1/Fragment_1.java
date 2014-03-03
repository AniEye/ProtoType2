package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment_1 extends Fragment implements OnClickListener {

	protected FrameLayout flTextContent;
	protected TextView _title;
	protected Button _Next, _Last;
	protected View rootView;
	protected String _Chapter, _ChapterPart1, _ChapterPart2;
	protected LinearLayout _LinLay;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_1, container, false);
		Initialize();
		try {
			Bundle getBundle = getActivity().getIntent().getExtras();
			String filename = "pros_og_teori_text/" + makeFileName(getBundle);
			String str = "";
			// String filename = "pros_og_teori_text/"
			try {

				Log.println(Log.ERROR, "FileView", "Did get bundle");
				AssetManager as = getActivity().getAssets();
				InputStream is = as.open(filename);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String aText = "";

				Boolean _bTitle = false;
				Boolean _bText = false;
				Boolean _bImage = false;
				int x=0,y=0;
				StringBuffer buffer = new StringBuffer();
				if (is != null) {
					while ((str = reader.readLine()) != null) {

						if (str.contains("<title>")) {
							_bTitle = true;
							buffer = new StringBuffer();
							continue;

						} else if (str.contains("</title")) {
							_bTitle = false;
							_title.setText(buffer.toString());
							continue;

						} else if (str.contains("<text>")) {
							_bText = true;
							buffer = new StringBuffer();
							continue;
						} else if (str.contains("</text>")) {
							_bText = false;
							TextView addText = new TextView(getActivity());
							addText.setText(buffer.toString());
							addText.setTextColor(Color.CYAN);
							Log.println(Log.ERROR, "FileView",
									"The buffer reads" + buffer);
							_LinLay.addView(addText);
							continue;
						}else if(str.contains("<image>")){
							_bImage = true;
							continue;
						}else if(str.contains("</image>")){
							_bImage=false;
							ImageView image = new ImageView(getActivity());
							image.setMaxHeight(y);
							image.setMaxWidth(x);
//							image.set//have to get the picture from the assets folder
						}
						if (_bTitle || _bText) {
							buffer.append(str);
							if (str.isEmpty())
								buffer.append("\n");
						}
					}
				}
				is.close();
			} catch (IOException e) {
				Log.println(Log.ERROR, "FileView", "Didn't open file");
			}

		} catch (Exception e) {
			Log.println(Log.ERROR, "FileView", "Didn't get bundle");
		}
		return rootView;
	}

	protected void Initialize() {
		_Next = (Button) rootView.findViewById(R.id.bNext);
		_Next.setOnClickListener(this);
		_Last = (Button) rootView.findViewById(R.id.bLast);
		_Last.setOnClickListener(this);
		// flTextContent = (FrameLayout) rootView
		// .findViewById(R.id.flText_Content);
		_title = (TextView) rootView.findViewById(R.id.tvText_Title);
		_LinLay = (LinearLayout) rootView.findViewById(R.id.llFrag);
		// Fragment newFragment = new Fragment_2();
		// FragmentManager fm = getFragmentManager();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNext:
			// Fragment newFragment = new Fragment_2();
			// FragmentManager fm = getFragmentManager();
			// fm.beginTransaction().replace(R.id.flText_Content,
			// newFragment).commit();
			break;
		case R.id.bLast:
			break;
		}

	}

	private String makeFileName(Bundle theBundle) {
		String filename = "";
		if (theBundle.getString("Teori_Chapter") != null) {
			filename = theBundle.getString("Teori_Chapter") + "/";
			if (theBundle.getString("Teori_ChapterPart1") != null) {
				filename = filename + theBundle.getString("Teori_ChapterPart1")
						+ "/";
				if (theBundle.getString("Teori_ChapterPart2") != null)
					filename = filename
							+ theBundle.getString("Teori_ChapterPart2") + "/";
			}
		}
		filename = filename + "_.txt";
		return filename;
	}

}
