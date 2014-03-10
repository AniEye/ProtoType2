package com.bbv.prototype1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Ovinger extends Fragment_Base{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRootView(R.layout.fragment_2, inflater, container);
		Initialize();
		return _rootView;
	}

	@Override
	protected void Initialize() {
		// TODO Auto-generated method stub
		
	}

}