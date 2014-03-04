package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Fragment_1 extends Fragment implements OnClickListener {

	protected FrameLayout flTextContent;
	protected Button _Next, _Last;
	protected View rootView;
	protected String _Chapter, _ChapterPart1, _ChapterPart2;
	protected LinearLayout _LinLay;
	protected ScrollView _Scroll;
	protected int _ViewHeight, _ViewWidth;
	protected Bundle currentBundle,nextBundle,lastBundle;
	protected AssetManager as;

	// make sure it can read special characters like זרו
	//example of solution is unicodereader
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_1, container, false);
		Initialize();
		try{
		currentBundle = getActivity().getIntent().getExtras();
		as = getActivity().getAssets();
		}catch(Exception e){
			Log.println(Log.ERROR, "FileView", "Didn't get bundle");
		}
		decodeDocument();
		return rootView;
	}
	protected void decodeDocument(){
		try {
			
			String filename = makeTeoriFileName(currentBundle) + "_.txt";
			String str = "";
			try {

				
				Log.println(Log.ERROR, "FileView", "Did get bundle");
				
				InputStream is = as.open(filename);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));

				Boolean _bTitle = false;
				Boolean _bText = false;
				Boolean _bImage = false;
				Boolean _bNextAndLast = false;
				Boolean _bLast = false;

				StringBuffer buffer = new StringBuffer();
				if (is != null) {
					while ((str = reader.readLine()) != null) {

						if (str.contains("<title>")) {
							_bTitle = true;
							buffer = new StringBuffer();
							continue;

						} else if (str.contains("</title")) {
							_bTitle = false;
							TextView addTitle = new TextView(getActivity());
							addTitle.setText(buffer.toString());
							addTitle.setTextColor(Color.RED);
							addTitle.setGravity(Gravity.CENTER);
							addTitle.setTextSize(30);
							_LinLay.addView(addTitle);
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
						} else if (str.contains("<image>")) {
							_bImage = true;
							buffer = new StringBuffer();
							continue;
						} else if (str.contains("</image>")) {
							_bImage = false;
							try {
								String _imageFilename = makeTeoriFileName(currentBundle)
										+ buffer.toString();

								InputStream iis = getActivity().getAssets()
										.open(_imageFilename);
								ImageView image = new ImageView(getActivity());
								image.setPadding(0, 10, 0, 10);
								Bitmap thePicture = BitmapFactory
										.decodeStream(iis);
								image.setImageBitmap(thePicture);
								_LinLay.addView(image);
							} catch (Exception e) {
								Log.println(Log.ERROR, "Sizeing",
										"The picture didn't open");
							}
							continue;
						} else if (str.contains("<next>")||str.contains("<last>")) {
							_bNextAndLast = true;
							buffer = new StringBuffer();
							continue;
						} else if (str.contains("</next>")) {
							_bNextAndLast = false;
							nextBundle = createNewBundle(buffer.toString());
							continue;
						}else if(str.contains("</last>")){
							_bNextAndLast = false;
							lastBundle = createNewBundle(buffer.toString());
							continue;
						}

						if (_bText) {
							buffer.append(str);
							buffer.append("\n");
						} else if (_bImage) {
							buffer.append(str);
						} else if (_bTitle) {
							buffer.append(str);
						}else if(_bNextAndLast){
							buffer.append(str);
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
	}

	protected void Initialize() {
		_Next = (Button) rootView.findViewById(R.id.bNext);
		_Next.setOnClickListener(this);
		_Last = (Button) rootView.findViewById(R.id.bLast);
		_Last.setOnClickListener(this);	
		_LinLay = (LinearLayout) rootView.findViewById(R.id.llFrag);
	}

	public Bundle createNewBundle(String filePath) {
		Bundle newBundle = new Bundle();
		filePath =filePath.trim();
		String[] filePathDevided = filePath.split("/");
		if (filePathDevided.length > 1) {
			newBundle.putString("Teori_Chapter", filePathDevided[0]);
			newBundle.putString("Teori_ChapterPart1", filePathDevided[1]);
			newBundle.putString("Teori_ChapterPart2", null);
			if (filePathDevided.length > 2) {
				newBundle.putString("Teori_ChapterPart2", filePathDevided[2]);
			}
		}
return newBundle;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNext:
			_LinLay.removeAllViews();
			currentBundle=nextBundle;
			decodeDocument();
			break;
		case R.id.bLast:
			_LinLay.removeAllViews();
			currentBundle=lastBundle;
			decodeDocument();
			break;
		}

	}

	private String makeTeoriFileName(Bundle theBundle) {
		String filename = "pros_og_teori_text/";
		if (theBundle.getString("Teori_Chapter") != null) {
			filename = filename + theBundle.getString("Teori_Chapter") + "/";
			if (theBundle.getString("Teori_ChapterPart1") != null) {
				filename = filename + theBundle.getString("Teori_ChapterPart1")
						+ "/";
				if (theBundle.getString("Teori_ChapterPart2") != null)
					filename = filename
							+ theBundle.getString("Teori_ChapterPart2") + "/";
			}
		}

		return filename;
	}

}
