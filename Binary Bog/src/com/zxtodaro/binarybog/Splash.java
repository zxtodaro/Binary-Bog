package com.zxtodaro.binarybog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
protected boolean _active = true;
protected int _splashtime = 5000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		//timed thread for Splash
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while(_active &&(waited < _splashtime)) {
						sleep(100);
						if(_active) {
							waited += 100;
						}
					} 
				} catch(InterruptedException e) {
					//do nothing
				} finally {
					finish();
					startActivity(new Intent("com.zxtodaro.binarybog.splash.Splash"));
				}
			}
		};
		splashThread.start();
		
	}
	

}
