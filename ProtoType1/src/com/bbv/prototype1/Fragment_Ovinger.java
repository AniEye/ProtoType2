package com.bbv.prototype1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_Ovinger extends Fragment_Base implements OnClickListener{
	//rotation doesn't work in this class yet
	protected Button _Next,_Last;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRootView(R.layout.fragment_1, inflater, container);
		Initialize();
		_visReff.setTitle(getFilePathFromBundle(getArguments()));
		return _rootView;
	}
	
	@Override
	protected void Initialize() {
		_Next = (Button) _rootView.findViewById(R.id.bNext);
		_Next.setOnClickListener(this);
		_Last = (Button) _rootView.findViewById(R.id.bLast);
		_Last.setOnClickListener(this);
		setLinearLayout(R.id.llFrag);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private String getFilePathFromBundle(Bundle aBundle){	
		return "Ovinger/" + aBundle.getString(KEY_OVING); 
	}

}