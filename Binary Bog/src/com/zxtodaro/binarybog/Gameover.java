package com.zxtodaro.binarybog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Gameover extends Activity {
	
		private int level;
		private int score;
		private int mode;
		private String convert;
		private String converted;
		private Context ct;
		private Toast lost;

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
			
			setContentView(R.layout.gameover);
			
			//Set application context and set toast text
			ct = getApplicationContext();
			lost = Toast.makeText(ct, "You didn't win this time. Try again.", 5);
			//position toast
			lost.setGravity(Gravity.CENTER, 0, -200);
			//show toast
			lost.show();
			
			Bundle bundle = getIntent().getExtras();
			mode = bundle.getInt("MODE");
			score = bundle.getInt("SCORE");
			level = bundle.getInt("LEVEL");
			convert = bundle.getString("CONVERT");
			converted = bundle.getString("SOLUTION");
			
			TextView tvScore = (TextView) findViewById(R.id.Score);
			TextView tvLevel = (TextView) findViewById(R.id.Level);
			TextView tvConvert = (TextView) findViewById(R.id.Convert);
			TextView tvSolution = (TextView) findViewById(R.id.Solution);
			
			tvScore.setText("Score: " + String.valueOf(score));
			tvLevel.setText("Level: " + String.valueOf(level));
			tvConvert.setText("Convert: " + convert);
			tvSolution.setText("Solution: " + converted);
		
		}
		
		public void gotoRetry(View v) {
			
			//new intent to carry bundle
			Intent retry = new Intent();
			//create bundle to pass level back to game so user can retry at same level
			Bundle bundle = new Bundle();
			//add to bundle
			bundle.putInt("LEVEL", level);
			bundle.putInt("MODE", mode);
			//register button
			Button btnRetry = (Button) findViewById(R.id.Retry);
			//add bundle to intent
			retry.putExtras(bundle);
			//set intent to play class
			retry.setClass(getBaseContext(), Play.class);
			//start new activity
			startActivity(retry);

		}
		
		public void gotoMainMenu(View v) {
			
			Button btnMainMenu = (Button) findViewById(R.id.MainMenu);
			Intent mainmenu = new Intent(v.getContext(), MainMenu.class);
			startActivity(mainmenu);

		}

		public void gotoHelp(View v) {
			
			Button btnHelp = (Button) findViewById(R.id.Help);
			Intent tutorial = new Intent(v.getContext(), Tutorial.class);
			startActivity(tutorial);

		}	
		
}
