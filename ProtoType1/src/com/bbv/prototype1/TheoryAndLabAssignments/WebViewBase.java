package com.bbv.prototype1.TheoryAndLabAssignments;

import com.bbv.prototype1.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public abstract class WebViewBase extends Fragment {

	protected WebView _WebView;
	protected View _rootView;
	protected String _filePath;

	protected final static String KEY_LOGCAT = "WebViewBase";//for debugging purposes

	/**
	 * a method needed to initialize the fragment
	 */
	@Override
	public abstract View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	/**
	 * a method for containing the work that shall be done at startup of the fragment
	 */
	protected abstract void Initialize();

	/**
	 * initializing the webview that is used in this fragment/class
	 */
	protected void setWebView() {
		_WebView = (WebView) _rootView.findViewById(R.id.WVTest);
		_WebView.getSettings().setBuiltInZoomControls(true);
		_WebView.getSettings().setJavaScriptEnabled(true);
	}

	/**
	 * initializing the view that is to be displayed
	 * @param Layout
	 * @param inflater
	 * @param container
	 */
	protected void setRootView(int Layout, LayoutInflater inflater,
			ViewGroup container) {
		_rootView = inflater.inflate(Layout, container, false);
	}

	/**
	 * this will load the incoming address or filepath
	 * @param filepath
	 */
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
	protected String getFilePathFromTheoryBundle(Bundle incommingBundle) {
		String filepath = "Pros_og_Teori/";
		Log.i(KEY_LOGCAT, "Running getFilePathFromTeoriBundle");

		if (incommingBundle.getString(ShowContentBase.KEY_CHAPTER) != null) {
			filepath = filepath
					+ incommingBundle.getString(ShowContentBase.KEY_CHAPTER)
					+ "/";
			Log.i(KEY_LOGCAT, "Key chapter found: ");
			if (incommingBundle.getString(ShowContentBase.KEY_CHAPTERPART1) != null) {
				filepath = filepath
						+ incommingBundle
								.getString(ShowContentBase.KEY_CHAPTERPART1)
						+ "/";
				Log.i(KEY_LOGCAT, "Key chapterpart1 found: ");
				if (incommingBundle.getString(ShowContentBase.KEY_CHAPTERPART2) != null) {
					filepath = filepath
							+ incommingBundle
									.getString(ShowContentBase.KEY_CHAPTERPART2)
							+ "/";
					Log.i(KEY_LOGCAT, "Key chapterpart2 found: ");
				}
			}
		} else {
			filepath = null;
		}

		Log.i(KEY_LOGCAT, "Finishing getFilePathFromTeoriBundle with: "
				+ filepath);
		return filepath;
	}
	
	/**
	 * This method will return a string representation of the content in the
	 * bundle given that the content is relevant to showing the oving part
	 * @param aBundle
	 * @return
	 */
	protected String getFilePathFromLabBundle(Bundle aBundle) {
		if (aBundle.getString(ShowContentBase.KEY_OVING) != null) {
			return "Ovinger/" + aBundle.getString(ShowContentBase.KEY_OVING)
					+ "/";
		} else
			return null;
	}

	/**
	 * abstract class for reloading to be used in ShowContent to reload the page/open new page
	 * in the webview
	 * @param aBundle
	 */
	public abstract void Reload(Bundle aBundle);

}
