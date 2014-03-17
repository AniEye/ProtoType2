package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_Ovinger extends Fragment_Base implements OnClickListener {
	// rotation doesn't work in this class yet
	protected Button _Next, _Last;
	protected Boolean _bTitle = false, _bText = false, _bImage = false,
			_bNextOrLast = false, _bList = false, _bReadBothfiles = false,
			_bIntent = false;
	protected StringBuffer _buffer;
	protected String _filename;
	protected AssetManager _as;
	protected int _TextSize;
	protected Bundle _currentBundle, _nextBundle, _lastBundle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRootView(R.layout.fragment_1, inflater, container);
		Initialize();
		try {
			_currentBundle = getArguments();
			_visReff.setTitle(getFilePathFromBundle(_currentBundle));
			decodeContentDocument();
		} catch (Exception e) {
			Log.e("Fragment_Ovinger", "Didn't find bundle");
		}
		return _rootView;
	}

	@Override
	protected void Initialize() {
		_Next = (Button) _rootView.findViewById(R.id.bNext);
		_Next.setOnClickListener(this);
		_Last = (Button) _rootView.findViewById(R.id.bLast);
		_Last.setOnClickListener(this);
		_as = getActivity().getAssets();
		setLinearLayout(R.id.llFrag);
	}

	protected void decodeContentDocument() {

		try {
			String str = "";
			// InputStream is = _as.open(_filename);
			InputStream is = _as.open(getFilePathFromBundle(_currentBundle));
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			if (is != null) {
				while ((str = reader.readLine()) != null) {

					if (str.contains("<h1>")) {
						_TextSize = getResources().getDimensionPixelSize(
								R.dimen.H1Size);
					} else if (str.contains("<h2>")) {
						_TextSize = getResources().getDimensionPixelSize(
								R.dimen.H2Size);
					} else if (str.contains("<h3>")) {
						_TextSize = getResources().getDimensionPixelSize(
								R.dimen.H3Size);
					} else if (str.contains("<h4>")) {
						_TextSize = getResources().getDimensionPixelSize(
								R.dimen.H4Size);
					} else if (str.contains("<h5>")) {
						_TextSize = getResources().getDimensionPixelSize(
								R.dimen.H5Size);
					}

					if (str.contains("<title>")) {
						_bTitle = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</title")) {
						_bTitle = false;
						_visReff.setTitle(_buffer.toString());

						_linlay.addView(createTextView(_buffer.toString(),
								Color.RED, _TextSize, Gravity.CENTER));
						continue;
					} else if (str.contains("<text>")) {
						_bText = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</text>")) {
						_bText = false;
						_linlay.addView(createTextView(_buffer.toString(),
								Color.CYAN, _TextSize, Gravity.NO_GRAVITY));
						continue;
					} else if (str.contains("<image>")) {
						_bImage = true;
						_buffer = new StringBuffer();
						continue;
					} else if (str.contains("</image>")) {
						_bImage = false;
						_linlay.addView(createImageView(getFilePathFromBundle(getArguments())
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

	@Override
	public void onClick(View v) {
		_linlay.removeAllViews();
		switch (v.getId()) {
		case R.id.bNext:
			_currentBundle = _nextBundle;
			 _visReff.setOvingerBundle(_currentBundle);
			break;
		case R.id.bLast:
			_currentBundle = _lastBundle;
			_visReff.setOvingerBundle(_currentBundle);
			break;
		}
		decodeContentDocument();
	}

	private Bundle createNewBundle(String newfilepath) {
		if (!newfilepath.isEmpty()) {
			Bundle newBundle = new Bundle();
			newfilepath = newfilepath.trim();
			newBundle.putString(KEY_OVING, newfilepath);
			return newBundle;
		} else {
			return _currentBundle;
		}
	}

	private String getFilePathFromBundle(Bundle aBundle) {
		return "Ovinger/" + aBundle.getString(KEY_OVING);
	}
}