package com.zxtodaro.binarybog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Cont extends Activity {
	
	private int level;
	private int score;
	
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
		
		setContentView(R.layout.cont);
		
		Bundle bundle = getIntent().getExtras();
		score = bundle.getInt("SCORE");
		level = bundle.getInt("LEVEL");
		
		TextView tvScore = (TextView) findViewById(R.id.Score);
		TextView tvLevel = (TextView) findViewById(R.id.Level);
		
		tvScore.setText("Score: " + String.valueOf(score));
		tvLevel.setText("Level: " + String.valueOf(level));
	
	}
	
	public void gotoCont(View v) {
		//new intent to carry bundle
		Intent cont = new Intent();
		//create bundle to pass score back to game so user can continue
		Bundle bundle = new Bundle();
		//add to bundle
		bundle.putInt("SCORE", score);
		bundle.putInt("LEVEL", level);
		//register button
		Button btnContinue = (Button) findViewById(R.id.Continue);
		//add bundle to intent
		cont.putExtras(bundle);
		//set intent to play class
		cont.setClass(getBaseContext(), Play.class);
		//start new activity
		startActivity(cont);
	}
}
