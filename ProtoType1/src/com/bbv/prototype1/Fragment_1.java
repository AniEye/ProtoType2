package com.bbv.prototype1;

import android.app.Fragment;
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
	public CharSequence title = "This is page 1";
	protected FrameLayout flTextContent;
	protected Button _Next, _Last;
	protected TextView tvTextTitle;
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
		tvTextTitle = (TextView) rootView.findViewById(R.id.tvText_Title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNext:
			break;
		case R.id.bLast:
			break;
		}

	}
	private void setContentTitle(CharSequence aTitle){
		tvTextTitle.setText(aTitle);
	}

}
