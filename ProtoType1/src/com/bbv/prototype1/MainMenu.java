package com.bbv.prototype1;



import android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RemoteViews.RemoteView;

public class MainMenu extends Activity implements OnClickListener{
	
	String[] menuList = {"Pros_og_Teori","Kalkulator"};
	Button calc,viskos,teori,kalk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);

		teori =(Button) findViewById(R.id.bTeori);
		kalk = (Button)findViewById(R.id.bKalk);
		teori.setOnClickListener(this);
		kalk.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		String selected="";
		switch(v.getId()){
		case R.id.bTeori:
			selected = menuList[0];
			break;
		case R.id.bKalk:
			selected = menuList[1];
			break;
		}
		try {
			Class selectedClass = Class.forName("com.bbv.prototype1."+selected);
			Intent createIntent = new Intent(MainMenu.this, selectedClass);
			startActivity(createIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
