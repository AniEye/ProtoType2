package com.bbv.prototype1;

import java.util.Locale;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fragment_1 extends Fragment {
	public CharSequence title = "This is page 1";

	public Fragment_1() {

	}

	public CharSequence getTitle() {
		return title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_1, container, false);
		return rootView;
	}
}
