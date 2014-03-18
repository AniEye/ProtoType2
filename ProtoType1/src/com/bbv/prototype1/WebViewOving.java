package com.bbv.prototype1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WebViewOving extends WebViewBase {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRootView(R.layout.webview, inflater, container);
		return _rootView;
	}

	@Override
	protected void Initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Reload(Bundle aBundle) {
		// TODO Auto-generated method stub
		
	}


}
