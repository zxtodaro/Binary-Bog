package com.zxtodaro.binarybog;

import java.util.Iterator;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

	public class Play extends Activity implements OnTouchListener {
		
		Environment gameEnv;
		Thread playThread;
		static int loopSpeed = 20;
		private int lilypadSpawnRate = 100;	
		//time since last lilypad
		private int lilypadSplit = 0;
		//track lilypads on screen
		private int lilypadCount = 0;
		
		
		//(re) initialize all starting variables
		static boolean hasSurface = false;
		//is the game loop running
		boolean running = false;
		//has the game ended
		boolean gameOver = false;
		//how many iterations of the game loop have completed
		private int runTime = 0;
		//what is the player's score
		private int score = 0;
		
		
		//constructor
		public Play() {
			super();
			score = 0;
		}
		
		
		// Called when the activity is first created.
		//on create check for saved data
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			//no sleepy androidy
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			
			//set no title
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			//no notification bar
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			//create instance of Environment
			gameEnv = new Environment(this);
			
			//set view to gameEnv
			setContentView(gameEnv);
			
			//point action listener to this instance of Environment
			gameEnv.setOnTouchListener(this);
			
		}
		
	    @Override
	    public void onStart() {
	    	super.onStart();
	    	//implement the game loop thread
	    	playThread = new Thread(new Runnable() {
				public void run() {
					// While the game is still running and the game ISN'T over
					while (running && !gameOver) {
						// Run the game loop\
						gameLoop();
						
						try {
							Thread.sleep(loopSpeed);
						} catch (Throwable t) {
							}
						}
					
					// If the game is marked as over, then kill the activity
					if (gameOver) {
						finish();
					}
				}
			});
	    }
		
		
		//game loop
		public void gameLoop() {		
			//check if the game is running
			if (running) {	
				//check if surface has been created
				if (hasSurface) {
					//make lilypads until game is over
					lilypadMaker();
				}
			}
			/**see runTime() line **/
			runTime();
			
		}
		//creates lilypads and places them in the lilypads array which is instantiated on Environment creation
		private void lilypadMaker() {
			//check if enough time has passed to make a lilypad & check if more than 5 lilypads exist on screen
			if ((runTime - lilypadSplit) > lilypadSpawnRate && (lilypadCount<5)) {
				synchronized(gameEnv.lilypads) {
					gameEnv.lilypads.add(new Lilypad(getResources()));
					
					//set time since last item to runtime(loops passed)
					lilypadSplit = runTime;
					
					//increase count of lilypads on screen
					lilypadCount++;
				}
			}
			
			//loop through each lilypad and see if it has gone off screen
			//if it has remove it from the array and decrement lilypad on screen count
			synchronized(gameEnv.lilypads) {
				for (Iterator<Lilypad> i = gameEnv.lilypads.iterator(); i.hasNext();) {
					Lilypad listItem = i.next();
					
					if (listItem.outOfBounds()) {
						i.remove();						
						//decrement lilypads on screen
						lilypadCount--;
						//create new lilypad on destruction of another
						lilypadSplit = 0;
					}
				}
			}	
			
		}
		
		//increment runTime
		private void runTime() {
			runTime++;		
		}
		
	//when user touches check if they press within the bounding box of all lilypads in array
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == gameEnv.getId()) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				for (Iterator<Lilypad> i = gameEnv.lilypads.iterator(); i.hasNext();) {
					Lilypad listItem = i.next();
					//create bounding box for each lilypad on screen and see if the user touched a lilypad
					Rect hop = new Rect(listItem.getX(), listItem.getY(), listItem.getX()+listItem.getWidth(), listItem.getY()+listItem.getHeight());
					if (hop.contains((int)(event.getX()), (int)(event.getY()))) {
						gameEnv.frog.setX(listItem.getX());
						gameEnv.frog.setY(listItem.getY());
						
						//if player hops and has not hopped before, change hopped to true
						if (gameEnv.frog.isHopped() == false) {
							gameEnv.frog.setHopped(true);
						}
						
						if (listItem.isOne()) {
							Log.i("boolean","I'm a one");
						}
						else {
							Log.i("boolean", "I'm a zero");
						}
					}
				}

			return true;
			}
		}
		return false;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		hasSurface = false;
		
	}
	
	//set hasSurface to true
	public static void setSurface() {
		hasSurface = true;
		
	}
	
	protected void onResume() {
		super.onResume();
		
		if(!gameOver) {
			running = true;
			playThread.start();
			
		}
	}
}
