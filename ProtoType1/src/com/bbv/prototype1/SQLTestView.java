package com.bbv.prototype1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SQLTestView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqltestview);
		Log.println(Log.ERROR, "SQLDB", "Creating textview");
		TextView info = (TextView)findViewById(R.id.tvSQLViewInfo);
		Log.println(Log.ERROR, "SQLDB", "Creating dbReference");
		SQLPros_Og_Teori db = new SQLPros_Og_Teori(this);
		Log.println(Log.ERROR, "SQLDB", "opening db");
		db.open();
		Log.println(Log.ERROR, "SQLDB", "getting data from db");
		String data = db.getData();
		Log.println(Log.ERROR, "SQLDB", "closing db");
		db.close();
		Log.println(Log.ERROR, "SQLDB", "setting text from db");
		info.setText(data);
	}

}
