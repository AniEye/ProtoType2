package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
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

public class Fragment_Teori extends Fragment_Base implements OnClickListener {

	protected Button _Next, _Last;
	protected String _Chapter, _ChapterPart1, _ChapterPart2;
	protected Bundle _currentBundle, _nextBundle, _lastBundle;
	protected AssetManager _as;
	protected Boolean _bTitle = false, _bText = false, _bImage = false,
			_bNextOrLast = false, _bList = false, _bReadBothfiles = false,
			_bIntent = false,_bBundle=false;
	protected StringBuffer _buffer;
	protected ArrayList<String> _listAL;
	protected String _filename;
	protected ArrayList<Intent> _intents;
	protected ArrayList<Bundle> _bundles;
	protected ArrayList<ArrayList<Bundle>> _list;
	

	// Connect fragment list items to links or actions that is to be done when
	// Clicked on one of the options in the list

	// also put a progress bar to make the user know that the page is still
	// rendering
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRootView(R.layout.fragment_1, inflater, container);
		Initialize();
		try {

			//_currentBundle = getActivity().getIntent().getExtras();
			_currentBundle = getArguments();

			_as = getActivity().getAssets();
			_filename = makeTeoriFileName(_currentBundle) + "_.txt";
			decodeContentDocument();

			_intents = new ArrayList<Intent>();
			_filename = makeTeoriFileName(_currentBundle) + "_list.txt";
			decodeListContent();
			_visReff.setIntentArray(_intents);

		} catch (Exception e) {
			Log.e("FileView", "Didn't get bundle");
		}
		return _rootView;
	}

	protected void decodeContentDocument() {

		try {
			String str = "";
			InputStream is = _as.open(_filename);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			if (is != null) {
				while ((str = reader.readLine()) != null) {

					if (str.contains("<title>")) {
						_bTitle = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</title")) {
						_bTitle = false;
						_visReff.setTitle(_buffer.toString());

						_linlay.addView(createTextView(_buffer.toString(),
								Color.RED, 35, Gravity.CENTER));
						continue;
					} else if (str.contains("<text>")) {
						_bText = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</text>")) {
						_bText = false;
						_linlay.addView(createTextView(_buffer.toString(),
								Color.CYAN, 20, Gravity.NO_GRAVITY));
						continue;
					} else if (str.contains("<image>")) {
						_bImage = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</image>")) {
						_bImage = false;
						_linlay.addView(createImageView(makeTeoriFileName(_currentBundle)
								+ _buffer.toString()));
						continue;
					} else if (str.contains("<next>") || str.contains("<last>")) {
						_bNextOrLast = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</next>")) {
						_bNextOrLast = false;
						_nextBundle = createNewBundle(_buffer.toString());
						continue;
					} else if (str.contains("</last>")) {
						_bNextOrLast = false;
						_lastBundle = createNewBundle(_buffer.toString());
						continue;
					}

					if (_bText) {
						_buffer.append(str);
						_buffer.append("\n");
					} else if (_bImage || _bTitle || _bNextOrLast) {
						_buffer.append(str);
					}
				}
			}
			is.close();
		} catch (IOException e) {
			Log.e("FileView", "Didn't open or find _.txt");
		}
	}

	protected void decodeListContent() {
		try {
			String str = "";
			InputStream is = _as.open(_filename);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			if (is != null) {
				while ((str = reader.readLine()) != null) {

					if (str.contains("<list>")) {
						_bList = true;
						_listAL = new ArrayList<String>();
						continue;
					} else if (str.contains("</list>")) {
						_bList = false;
						_visReff.setListViewArray(arrayListToStringArray(_listAL));
						continue;
					} else if (str.contains("<actions>")) {

					} else if (str.contains("</actions>")) {

					} else if (str.contains("<action>")) {

					} else if (str.contains("</action>")) {

					} else if (str.contains("<intent>")) {
						_bIntent = true;
						_buffer = new StringBuffer();
						continue;

					} else if (str.contains("</intent>")) {
						_bIntent = false;
						try {
							Class<?> theClass = Class
									.forName("com.bbv.prototype1."
											+ _buffer.toString());
							Intent newIntent = new Intent(getActivity(),
									theClass);
							

							_intents.add(newIntent);
						} catch (Exception e) {
							_intents.add(null);
							Log.e("Intent", "Intent is empty");
						}
						
						continue;
					}else if(str.contains("<bundle>")){
						_bBundle=true;
						_bundles = new ArrayList<Bundle>();
						continue;
					}else if(str.contains("</bundle>")){
						_bBundle=false;			
						_list.add(_bundles);
						continue;
					}

					if (_bList) {
						_listAL.add(str);
					} else if (_bIntent) {
						_buffer.append(str);
					}else if(_bBundle){
						Bundle newBundle= new Bundle();
						String[] newString = str.split("#");

						if(newString[0].equalsIgnoreCase("Ovinger")){
							newBundle.putString(Fragment_Base.KEY_OVING, newString[1]+"/_.txt");
						}else if(newString[0].equalsIgnoreCase("Teori")){
//							String[] nextString = newString[1].split("/");
						}
						_bundles.add(newBundle);
					}
				}
			}
			is.close();
		} catch (Exception e) {
			Log.e("File", "Didn't find or open _list");
		}
	}

	protected void Initialize() {
		_Next = (Button) _rootView.findViewById(R.id.bNext);
		_Next.setOnClickListener(this);
		_Last = (Button) _rootView.findViewById(R.id.bLast);
		_Last.setOnClickListener(this);
		setLinearLayout(R.id.llFrag);
	}

	public Bundle createNewBundle(String filePath) {
		Bundle newBundle = new Bundle();
		filePath = filePath.trim();
		String[] filePathDevided = filePath.split("/");
		newBundle.putString(KEY_CHAPTER, filePathDevided[0]);
		newBundle.putString(KEY_CHAPTERPART1, null);
		newBundle.putString(KEY_CHAPTERPART2, null);
		if (filePathDevided.length > 1) {
			newBundle.putString(KEY_CHAPTERPART1, filePathDevided[1]);
			if (filePathDevided.length > 2) {
				newBundle.putString(KEY_CHAPTERPART2, filePathDevided[2]);
			}
		}
		return newBundle;
	}

	@Override
	public void onClick(View v) {
		_linlay.removeAllViews();
		switch (v.getId()) {
		case R.id.bNext:
			_currentBundle = _nextBundle;
			_visReff.setTeoriBundle(_currentBundle);
			break;
		case R.id.bLast:
			_currentBundle = _lastBundle;
			_visReff.setTeoriBundle(_currentBundle);
			break;
		}
		
		_bReadBothfiles = false;//is this needed anymore??
		_filename = makeTeoriFileName(_currentBundle) + "_.txt";
		decodeContentDocument();
		_intents = new ArrayList<Intent>();
		_filename = makeTeoriFileName(_currentBundle) + "_list.txt";
		decodeListContent();
		_visReff.setIntentArray(_intents);
	}

	public String makeTeoriFileName(Bundle theBundle) {
		String filename = "pros_og_teori_text/";
		if (theBundle.getString(KEY_CHAPTER) != null) {
			filename = filename + theBundle.getString(KEY_CHAPTER) + "/";
			if (theBundle.getString(KEY_CHAPTERPART1) != null) {
				filename = filename + theBundle.getString(KEY_CHAPTERPART1)
						+ "/";
				if (theBundle.getString(KEY_CHAPTERPART2) != null)
					filename = filename
							+ theBundle.getString(KEY_CHAPTERPART2) + "/";
			}
		}

		return filename;
	}

}
