package com.bbv.prototype1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Fragment_1 extends Fragment implements OnClickListener {

	protected FrameLayout flTextContent;
	protected Button _Next, _Last;
	protected View rootView;

	public Fragment_1() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_1, container, false);
		Initialize();

		return rootView;
	}

	protected void Initialize() {
		_Next = (Button) rootView.findViewById(R.id.bNext);
		_Next.setOnClickListener(this);
		_Last = (Button) rootView.findViewById(R.id.bLast);
		_Last.setOnClickListener(this);
		flTextContent = (FrameLayout) rootView
				.findViewById(R.id.flText_Content);
		
		
		Fragment newFragment = new Fragment_2();		
		FragmentManager fm = getFragmentManager();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNext:
			Fragment newFragment = new Fragment_2();		
			FragmentManager fm = getFragmentManager();
			fm.beginTransaction().replace(R.id.flText_Content, newFragment).commit();
			break;
		case R.id.bLast:
			break;
		}

	}

}
