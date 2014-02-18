package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FileView extends Activity {
	// public static final String _KEY_FOLDER = "Folder";
	public static final String _Folder_pros = "pros_og_teori_text";

	TextView _head, _content;
	String _FileName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fileview);
		try {
			Bundle theBundle = this.getIntent().getExtras();

			_FileName = theBundle.getString("Folder");

		} catch (Exception e) {
			_FileName = null;
		}
		Initializa();
	}

	private void Initializa() {
		// TODO Auto-generated method stub
		_head = (TextView) findViewById(R.id.tvFileOrigin);
		_content = (TextView) findViewById(R.id.tvFileView);
		if (_FileName != null)
			setTextViews();
		else {
			_head.setText("error");
			_content.setText("file not opening");
		}

	}

	public void setTextViews() {

		_head.setText(_FileName);
		
		String str = "";
		StringBuffer buf = new StringBuffer();
		try {
			AssetManager as = getAssets();
			InputStream is = as.open(_FileName);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			if (is != null) {
				while ((str = reader.readLine()) != null) {
					buf.append(str + "\n");
				}
			}
			is.close();
		} catch (IOException e) {

		}
		_content.setText(buf.toString());

	}

}
