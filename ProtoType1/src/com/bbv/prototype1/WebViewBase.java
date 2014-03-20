package com.bbv.prototype1;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public abstract class WebViewBase extends Fragment {

	protected WebView _WebView;
	protected View _rootView;
	protected String _filePath;

	protected final static String KEY_LOGCAT = "WebViewBase";

	@Override
	public abstract View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	protected abstract void Initialize();

	protected void setWebView() {
		_WebView = (WebView) _rootView.findViewById(R.id.WVTest);	
		_WebView.getSettings().setBuiltInZoomControls(true);	
		_WebView.getSettings().setJavaScriptEnabled(true);
	}

	protected void setRootView(int Layout, LayoutInflater inflater,
			ViewGroup container) {
		_rootView = inflater.inflate(Layout, container, false);
	}

	protected void loadFile(String filepath) {
		_WebView.loadUrl(filepath);
	}

	/**
	 * This method will return a string representation of the content in the
	 * bundle given that the content is relevant to showing the theory part
	 * 
	 * @param incommingBundle
	 * @return string
	 */
	protected String getFilePathFromTeoriBundle(Bundle incommingBundle) {
		String filepath = "pros_og_teori_text/";
		Log.i(KEY_LOGCAT, "Running getFilePathFromTeoriBundle");

		if (incommingBundle.getString(ShowContentBase.KEY_CHAPTER) != null) {
			filepath = filepath
					+ incommingBundle.getString(ShowContentBase.KEY_CHAPTER)
					+ "/";
			Log.i(KEY_LOGCAT,
					"Key chapter found: "
							+ incommingBundle
									.getString(ShowContentBase.KEY_CHAPTER));
			if (incommingBundle.getString(ShowContentBase.KEY_CHAPTERPART1) != null) {
				filepath = filepath
						+ incommingBundle
								.getString(ShowContentBase.KEY_CHAPTERPART1)
						+ "/";
				Log.i(KEY_LOGCAT,
						"Key chapterpart1 found: "
								+ incommingBundle
										.getString(ShowContentBase.KEY_CHAPTERPART1));
				if (incommingBundle.getString(ShowContentBase.KEY_CHAPTERPART2) != null) {
					filepath = filepath
							+ incommingBundle
									.getString(ShowContentBase.KEY_CHAPTERPART2)
							+ "/";
					Log.i(KEY_LOGCAT,
							"Key chapterpart2 found: "
									+ incommingBundle
											.getString(ShowContentBase.KEY_CHAPTERPART2));
				}
			}
		}

		Log.i(KEY_LOGCAT, "Finishing getFilePathFromTeoriBundle with: "
				+ filepath);
		return filepath;
	}

	protected String getFilePathFromOvingBundle(Bundle aBundle) {
		return "Ovinger/" + aBundle.getString(ShowContentBase.KEY_OVING)+ "/";
	}

	public abstract void Reload(Bundle aBundle);

}
