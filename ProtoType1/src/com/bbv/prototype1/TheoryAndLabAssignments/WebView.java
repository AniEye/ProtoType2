package com.bbv.prototype1.TheoryAndLabAssignments;

import com.bbv.prototype1.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WebView extends WebViewBase {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRootView(R.layout.webview, inflater, container);
		Initialize();
		return _rootView;
	}

	@Override
	protected void Initialize() {
		setWebView();
		Reload(getArguments());
	}

	@Override
	public void Reload(Bundle aBundle) {
		//only difference between this one and theory, check this out later
		if(getFilePathFromLabBundle(aBundle)!=null){
			_filePath = getFilePathFromLabBundle(aBundle);
		}else if (getFilePathFromTheoryBundle(aBundle)!=null){
			_filePath = getFilePathFromTheoryBundle(aBundle);
		}		
		loadFile("file:///android_asset/" + _filePath + "_.html");

		Log.i(KEY_LOGCAT, "Finishing Reload");
	}

}
