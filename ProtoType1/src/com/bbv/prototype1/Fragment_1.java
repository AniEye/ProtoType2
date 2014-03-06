package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Fragment;
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

	protected FrameLayout _flTextContent;
	protected Button _Next, _Last;
	protected View _rootView;
	protected String _Chapter, _ChapterPart1, _ChapterPart2;
	protected LinearLayout _LinLay;
	protected ScrollView _Scroll;
	protected int _ViewHeight, _ViewWidth;
	protected Bundle _currentBundle, _nextBundle, _lastBundle;
	protected AssetManager _as;
	protected Vis_Teori _visReff;
	protected Boolean _bTitle = false, _bText = false, _bImage = false,
			_bNextOrLast = false, _bList = false, _bReadBothfiles = false;
	protected StringBuffer _buffer;
	protected ArrayList<String> _listAL;
	protected String _filename;

	//conect fragment list items to links or actions that is to be done when
	//clicken on one of the options in the list
	
	// also put a progress bar to make the user know that the page is still
	// rendering
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		_rootView = inflater.inflate(R.layout.fragment_1, container, false);
		Initialize();
		try {
			_currentBundle = getActivity().getIntent().getExtras();
			_as = getActivity().getAssets();
			_filename = makeTeoriFileName(_currentBundle) + "_.txt";
			decodeContentDocument();

			_filename = makeTeoriFileName(_currentBundle) + "_list.txt";
			decodeListContent();

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

			_buffer = new StringBuffer();
			if (is != null) {
				while ((str = reader.readLine()) != null) {

					if (str.contains("<title>")) {
						_bTitle = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</title")) {
						_bTitle = false;
						createTitle(_buffer.toString());
						continue;
					} else if (str.contains("<text>")) {
						_bText = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</text>")) {
						_bText = false;
						createTextView(_buffer.toString());
						continue;
					} else if (str.contains("<image>")) {
						_bImage = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</image>")) {
						_bImage = false;
						createImageView(makeTeoriFileName(_currentBundle)
								+ _buffer.toString());
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
			Log.e( "FileView", "Didn't open or find _.txt");
		}
	}

	protected void decodeListContent() {
		try {
			String str = "";
			InputStream is = _as.open(_filename);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			_buffer = new StringBuffer();
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
					}

					if (_bList) {
						_listAL.add(str);
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
		_LinLay = (LinearLayout) _rootView.findViewById(R.id.llFrag);
	}

	public Bundle createNewBundle(String filePath) {
		Bundle newBundle = new Bundle();
		filePath = filePath.trim();
		String[] filePathDevided = filePath.split("/");
		newBundle.putString("Teori_Chapter", filePathDevided[0]);
		newBundle.putString("Teori_ChapterPart1", null);
		newBundle.putString("Teori_ChapterPart2", null);
		if (filePathDevided.length > 1) {
			newBundle.putString("Teori_ChapterPart1", filePathDevided[1]);
			if (filePathDevided.length > 2) {
				newBundle.putString("Teori_ChapterPart2", filePathDevided[2]);
			}
		}
		return newBundle;
	}

	@Override
	public void onClick(View v) {
		_LinLay.removeAllViews();
		switch (v.getId()) {
		case R.id.bNext:
			_currentBundle = _nextBundle;
			break;
		case R.id.bLast:
			_currentBundle = _lastBundle;
			break;
		}
		_bReadBothfiles = false;
		_filename = makeTeoriFileName(_currentBundle) + "_.txt";
		decodeContentDocument();
		_filename = makeTeoriFileName(_currentBundle) + "_list.txt";
		decodeListContent();
	}

	protected String[] arrayListToStringArray(ArrayList<String> list) {
		int length = list.size();
		String[] array = new String[length];
		for (int i = 0; i < length; i++) {
			array[i] = list.get(i);
		}
		return array;
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

	public void createTitle(String text) {
		TextView addTitle = new TextView(getActivity());
		addTitle.setText(text);
		addTitle.setTextColor(Color.RED);
		addTitle.setGravity(Gravity.CENTER);
		addTitle.setTextSize(30);
		_LinLay.addView(addTitle);
	}

	public void createTextView(String text) {
		TextView addText = new TextView(getActivity());
		addText.setText(text);
		addText.setTextColor(Color.CYAN);
		_LinLay.addView(addText);
	}

	public void createImageView(String ImagePath) {
		try {
			InputStream iis = getActivity().getAssets().open(ImagePath);
			ImageView image = new ImageView(getActivity());
			image.setPadding(0, 10, 0, 10);
			Bitmap thePicture = BitmapFactory.decodeStream(iis);
			image.setImageBitmap(thePicture);
			_LinLay.addView(image);
		} catch (Exception e) {
			Log.println(Log.ERROR, "Image", "Image not found");
		}
	}

	public void setVisTeori(Vis_Teori vis) {
		_visReff = vis;
	}
}
