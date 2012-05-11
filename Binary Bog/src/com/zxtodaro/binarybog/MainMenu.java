package com.zxtodaro.binarybog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//no sleepy androidy
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	
		//set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.mainmenu);
	
	
	}
		
	public void gotoSelectMode(View v) {
		
		Button btnSelectMode = (Button) findViewById(R.id.SelectMode);
		Intent mode = new Intent();
		mode.setClass(v.getContext(), SelectMode.class);
		startActivityForResult(mode,0);

	}
	
/*	public void gotoSettings(View v) {
			
		Button btnSettings = (Button) findViewById(R.id.Settings);
		Intent settings = new Intent();
		settings.setClass(v.getContext(), Settings.class);
		startActivityForResult(settings,0);

	}
*/
	public void gotoAbout(View v) {
		
		Button btnAbout = (Button) findViewById(R.id.About);
		Intent about = new Intent();
		about.setClass(v.getContext(), About.class);
		startActivityForResult(about,0);

	}
	
}
