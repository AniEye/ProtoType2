package com.bbv.prototype1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FileView extends Activity {

	TextView _head,_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fileview);
		Initializa();
	}
	private void Initializa() {
		// TODO Auto-generated method stub
		_head = (TextView)findViewById(R.id.tvFileOrigin);
		_content = (TextView)findViewById(R.id.tvFileView);
	}
	public void setTextViews(){
		String _filename = "test.txt";
		_head.setText(_filename);
	}

}
