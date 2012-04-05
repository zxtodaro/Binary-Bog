package com.zxtodaro.binarybog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

	public class Play extends Activity implements OnTouchListener, OnClickListener {
	
		private Environment gameEnv = null;
		private boolean isFlagHidden = false;
		
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//no sleepy androidy
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.game);
		gameEnv = (Environment) findViewById(R.id.the_canvas);
		gameEnv.setOnTouchListener(this);
		Button b = (Button) findViewById(R.id.the_button);
		b.setOnClickListener(this);
		}
		
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.the_canvas) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (isFlagHidden) {

				}
			return true;
			}
		}
		return false;
	}
	
	public void onClick(View v) {
		if (v.getId() == R.id.the_button) {
			Button b = (Button) findViewById(R.id.the_button);
			isFlagHidden = !isFlagHidden;
			if (isFlagHidden) {
				b.setText("Give Up!");
				gameEnv.hideTheFlag();
			} else {
				b.setText("Hide the Flag!");
				gameEnv.giveUp();
			}
		}
	}
	

	

}
