package com.zxtodaro.binarybog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class BinaryBogActivity extends Activity {
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
			//set no title
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			//no notification bar
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			setContentView(R.layout.splash);
			
			//timed thread for Splash
			Thread splashThread = new Thread() {
				@Override
				public void run() {
					try {
						int splashTimer = 0;
						while(splashTimer < 2500) {
							sleep(100);
							splashTimer += 100;
							}
						startActivity(new Intent("android.intent.action.MAINMENU"));
						} 
					catch(InterruptedException e) {
						//do nothing
					} finally {
						finish();
					}
				}
			};
			splashThread.start();
			
		}
}