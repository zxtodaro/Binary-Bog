package com.zxtodaro.binarybog;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class EnvThread extends Thread {
	private Environment gameEnv;
	private SurfaceHolder surfHolder;
	private boolean running = false;
	private long startTime;
	private long runTime;
	
	//Constructor
	public EnvThread(Environment e) {
		gameEnv = e;
		surfHolder = gameEnv.getHolder();
	}
	
	public void setRun(boolean b) {
		running = b;
	}

	@Override
	public void run() {
		Canvas c = null;
		startTime = System.currentTimeMillis();
		while (running) {
			//have the surface holder lock the canvas
			c = surfHolder.lockCanvas();
			
			//if the screen has content draw the game environment, then unlock the canvas and draw changes
			if (c != null) {
				gameEnv.animate(runTime);
				//draw
				gameEnv.doDraw(c);
				surfHolder.unlockCanvasAndPost(c);
				runTime = System.currentTimeMillis() - startTime;
			}
			startTime = System.currentTimeMillis();
			
		}
		
	}
	
}
