package com.bbv.prototype1;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public abstract class WebViewBase extends Fragment {

	protected WebView _WebView;
	protected View _rootView;
	protected AssetManager _assetManag;
	protected String _filePath;
	protected Bundle _currentBundle, _nextBundle, _previousBundle;

	public final static String KEY_OVING = "SOving";
	public final static String KEY_PROS_TEORI = "BProsTeori";

	public final static String KEY_CHAPTER = "Teori_Chapter";
	public final static String KEY_CHAPTERPART1 = "Teori_ChapterPart1";
	public final static String KEY_CHAPTERPART2 = "Teori_ChapterPart2";

	@Override
	public abstract View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	protected abstract void Initialize();

	protected void setWebView() {
		_WebView = (WebView) _rootView.findViewById(R.id.WVTest);
		_WebView.getSettings().setBuiltInZoomControls(true);
	}

	protected void setAssetManager() {
		_assetManag = getActivity().getAssets();
	}

	protected void setRootView(int Layout, LayoutInflater inflater,
			ViewGroup container) {
		_rootView = inflater.inflate(Layout, container, false);
	}

	protected void loadFile(String filepath) {
		_WebView.loadUrl(filepath);
	}

	protected Bundle createNewTeoriBundle(String filepath) {
		Bundle newBundle = new Bundle();
		filepath = filepath.trim();
		String[] filePathDevided = filepath.split("/");
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

	/**
	 * This method will return a string representation of the content in the
	 * bundle given that the content is relevant to showing the theory part
	 * 
	 * @param incommingBundle
	 * @return string
	 */
	public static String getFilePathFromTeoriBundle(Bundle incommingBundle) {
		String filepath = "pros_og_teori_text/";
		Log.e("WebViewBase", "Running getFilePathFromTeoriBundle");

		if (incommingBundle.getString(KEY_CHAPTER) != null) {
			filepath = filepath + incommingBundle.getString(KEY_CHAPTER) + "/";
			Log.e("WebViewBase",
					"Key chapter found: "
							+ incommingBundle.getString(KEY_CHAPTER));
			if (incommingBundle.getString(KEY_CHAPTERPART1) != null) {
				filepath = filepath
						+ incommingBundle.getString(KEY_CHAPTERPART1) + "/";
				Log.e("WebViewBase",
						"Key chapter found: "
								+ incommingBundle.getString(KEY_CHAPTERPART1));
				if (incommingBundle.getString(KEY_CHAPTERPART2) != null)
					filepath = filepath
							+ incommingBundle.getString(KEY_CHAPTERPART2) + "/";
				Log.e("WebViewBase",
						"Key chapter found: "
								+ incommingBundle.getString(KEY_CHAPTERPART2));
			}
		}

		Log.e("WebViewBase", "Finishing getFilePathFromTeoriBundle with: "
				+ filepath);
		return filepath;
	}

	private Bundle createNewOvingBundle(String newfilepath) {
		Bundle newBundle = new Bundle();
		newfilepath = newfilepath.trim();
		newBundle.putString(KEY_OVING, newfilepath);
		return newBundle;

	}

	private String getFilePathFromOvingBundle(Bundle aBundle) {
		return "Ovinger/" + aBundle.getString(KEY_OVING);
	}

	public abstract void Reload(Bundle aBundle);

	public void ReloadTheory(Bundle theoryBundle) {
		_filePath = "file:///android_asset/"
				+ getFilePathFromTeoriBundle(theoryBundle) + "_.html";
		loadFile(_filePath);

		_WebView.reload();
	}
}
