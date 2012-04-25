package com.linden.sp;

import com.linden.sp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainMenu extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Log.d("Main Menu", "Made it");
		//Set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//set view main.xml
		setContentView(R.layout.main);
		
		//Sets up Career button and action
		Button btnCareer = (Button) findViewById (R.id.btnCareer);
		btnCareer.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.CAREER"));
			}
		});

		//Sets up Survival button and action
		Button btnSurvival = (Button) findViewById (R.id.btnSurvival);
		btnSurvival.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.SURVIVAL"));
			}
		});
		
		//Sets up Help button and action
		Button btnHelp = (Button) findViewById (R.id.btnHelp);
		btnHelp.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.HELP"));
			}
		});
		
		//Sets up Settings button and action
		Button btnSettings = (Button) findViewById (R.id.btnSettings);
		btnSettings.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.SETTINGS"));
			}
		});
		
		//sets up About button and action
		Button btnAbout = (Button) findViewById (R.id.btnAbout);
		btnAbout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.ABOUT"));
			}
		});
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
