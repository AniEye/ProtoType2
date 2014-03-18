package com.bbv.prototype1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WebViewTeori extends WebViewBase {

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
		setAssetManager();
		ReloadTheory(getArguments());
	}
	
	public void reload(Bundle aBundle){
		_filePath = "file:///android_asset/"
				+ getFilePathFromTeoriBundle(aBundle) + "_.html";
		loadFile(_filePath);
	}

	@Override
	public void Reload(Bundle aBundle) {
		_filePath = "file:///android_asset/"
				+ getFilePathFromTeoriBundle(aBundle) + "_.html";
		loadFile(_filePath);
	}

}
