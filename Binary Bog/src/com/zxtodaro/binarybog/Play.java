package com.zxtodaro.binarybog;

import java.util.Iterator;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

	public class Play extends Activity implements OnTouchListener {
		
		Environment gameEnv;
		Thread playThread;
		//game speed; lower lessens sleep time
		static int loopSpeed = 25;
		//higher increases spawn delay
		//spawn rate of lilypads
		private int lilypadSpawnRate = 90;
		//time since last lilypad
		private int lilypadSplit = 0;
		//track lilypads on screen
		private int lilypadCount = 0;
		//integer value of the number to convert
		private int intConvert;	
		//string value of winning number(int)
		private String strConvert;	
		//string value of number(bin)
		private String strConverted;	
		//String to hold guesses
		private String guess;
		//random number generator
		private Random r;
		//music player
		private static MediaPlayer music;
		//game mode(binary, octal, etc.)
		private int mode;

		
		//(re) initialize all starting variables
		static boolean hasSurface = false;
		//is the game loop running
		boolean running = false;
		//has the game ended
		boolean gameOver = false;
		//how many iterations of the game loop have completed
		private int runTime = 0;
		
		
		//constructor
		public Play() {
			super();
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
			
					
			music = MediaPlayer.create(getApplicationContext(), R.raw.willowandthelight);
			/* 
			"Willow and the Light" Kevin MacLeod (incompetech.com) Licensed under Creative Commons "Attribution 3.0" http://creativecommons.org/licenses/by/3.0/
			*/
			music.setLooping(true);
			
			music.seekTo(0);
			music.start();
			
			//check if play was called on by activity
			if (getIntent().toString().endsWith(".Play (has extras) }")) {
				Bundle bundle = getIntent().getExtras();
				mode = bundle.getInt("MODE");
				gameEnv.setScore(bundle.getInt("SCORE"));
				gameEnv.setLevel(bundle.getInt("LEVEL"));
				Log.i("Mode: ", String.valueOf(mode));
				
			}
			
			else {
				Bundle bundle = getIntent().getExtras();
				mode = bundle.getInt("MODE");
				gameEnv.setScore(bundle.getInt("SCORE"));
				gameEnv.setLevel(bundle.getInt("LEVEL"));
				Log.i("Score:",String.valueOf(bundle.getInt("SCORE")));
				Log.i("Intent: ", String.valueOf(getIntent()));
			}
			
			//instantiate random number generator
			r = new Random();
			
			
			if (mode == 0) {
				
				//create number to be converted
				switch (gameEnv.getLevel()) {
				case 0:
					intConvert = r.nextInt(4);
					break;
				case 1:
					intConvert = r.nextInt(8 - 4 + 1) + 4;
					break;
				case 2:
					intConvert = r.nextInt(16 - 8 + 1) + 8;
					break;
				case 3:
					intConvert = r.nextInt(32 - 16 + 1) + 16;
					break;
				case 4:
					intConvert = r.nextInt(64 - 32 + 1) + 32;
					break;
				
				}
				
				//convert number to binary string
				strConverted = Integer.toBinaryString(intConvert);
				
			}
			
			else if (mode == 1) {
				switch (gameEnv.getLevel()) {
				case 0:
					intConvert = r.nextInt(16);
					break;
				case 1:
					intConvert = r.nextInt(32 - 16 + 1) + 16;
					break;
				case 2:
					intConvert = r.nextInt(40 - 32 + 1) + 32;
					break;
				case 3:
					intConvert = r.nextInt(48 - 32 + 1) + 32;
					break;
				case 4:
					intConvert = r.nextInt(64 - 40 + 1) + 40;
					break;
				
				}
				//convert number to octal string
				strConverted = Integer.toOctalString(intConvert);
				
			}
			
			//instantiate and set the value of the number to be converted
			strConvert = String.valueOf(intConvert);
			
			//set the initial guess to empty string
			gameEnv.setGuess("");
			
			gameEnv.setGameValues(intConvert, strConvert, strConverted);
			
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
					//make lilypads until game is no longer running
					lilypadMaker();
				}
			}
			//increase runTime
			runTime();
			
		}
		
		//creates lilypads and places them in the lilypads array which is instantiated on Environment creation
		private void lilypadMaker() {
			//check if enough time has passed to make a lilypad & check if more than 7 lilypads exist on screen
			if ((runTime - lilypadSplit) > lilypadSpawnRate && (lilypadCount<9)) {
				synchronized(gameEnv.lilypads) {
					gameEnv.lilypads.add(new Lilypad(getResources(), mode));
					
					//set time since last item to runtime(loops passed)
					lilypadSplit = runTime;
					
					//increase count of lilypads on screen
					lilypadCount++;
				}
			}
			
			//loop through each lilypad and see if it has gone off screen
			//if it has, remove it from the array and decrement lilypad on screen count
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
						
						
			//check if the frog has moved off the screen
			synchronized(gameEnv.frog) {
					
					if (gameEnv.frog.outOfBounds()) {
						//reset score due to loss
						gameEnv.resetScore();						
						
						//New intent & Bundle
						Intent gameover = new Intent();
						
						//create bundle for retry
						Bundle bundle = new Bundle();
						//add to bundle
						bundle.putInt("SCORE", gameEnv.getScore());
						bundle.putInt("LEVEL", gameEnv.getLevel());
						bundle.putInt("MODE", mode);
						bundle.putString("CONVERT", strConvert);
						bundle.putString("CONVERTED", strConverted);
						//set intent to gameover class
						gameover.setClass(getBaseContext(), Gameover.class);
						//add bundle to intent
						gameover.putExtras(bundle);
						//set GameOver to stop thread
						setGameOver(true);
						//start new activity
						startActivity(gameover);
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
						if (!gameEnv.frog.isHopped()) {
							gameEnv.frog.setHopped(true);
						}
						
						if (mode ==0) {
							if (listItem.isOne()) {
								Log.i("boolean","I'm a one");
								gameEnv.setGuess(gameEnv.getGuess() + "1");
							}
							else {
								Log.i("boolean", "I'm a zero");
								gameEnv.setGuess(gameEnv.getGuess() + "0");
							}
						}
						else if (mode ==1) {
							switch (listItem.getValueO()) {
							case 0: gameEnv.setGuess(gameEnv.getGuess() + "0");
							Log.i("Value: ", "I'm a zero");
							break;
							case 1: gameEnv.setGuess(gameEnv.getGuess() + "1");
							Log.i("Value: ", "I'm a one");
							break;
							case 2: gameEnv.setGuess(gameEnv.getGuess() + "2");
							Log.i("Value: ", "I'm a two");
							break;
							case 3: gameEnv.setGuess(gameEnv.getGuess() + "3");
							Log.i("Value: ", "I'm a three");
							break;
							case 4: gameEnv.setGuess(gameEnv.getGuess() + "4");
							Log.i("Value: ", "I'm a four");
							break;
							case 5: gameEnv.setGuess(gameEnv.getGuess() + "5");
							Log.i("Value: ", "I'm a five");
							break;
							case 6: gameEnv.setGuess(gameEnv.getGuess() + "6");
							Log.i("Value: ", "I'm a six");
							break;
							case 7: gameEnv.setGuess(gameEnv.getGuess() + "7");
							Log.i("Value: ", "I'm a seven");
							break;
							}
						}

						checkWin();
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
		music.stop();
		
	}
	
	//set hasSurface to true
	public static void setSurface() {
		hasSurface = true;
		
	}
	
	protected void onPause() {
		super.onPause();
		running = false;
		music.pause();
		
	}
	
	protected void onResume() {
		super.onResume();
		
		if(!gameOver) {
			running = true;
			playThread.start();
			music.start();
			
		}
	}

	//check if the player has the correct solution
	protected void checkWin() {
		Context ct = getApplicationContext();
		if (gameEnv.getGuess().length() == gameEnv.getStrConverted().length() && gameEnv.getGuess().equals(gameEnv.getStrConverted())) {
			Toast won = Toast.makeText(ct, "You found the solution!", 5);
			won.setGravity(Gravity.CENTER, 0, -200);
			won.show();
			
			if ((gameEnv.getScore() != 0) && (gameEnv.getScore() % 5 == 0)) {
				gameEnv.incrementScore();
				gameEnv.incrementLevel();
			}
			
			else {
			gameEnv.incrementScore();
			}
			
			//New intent and Bundle
			Intent cont = new Intent();
			
			//Create bundle for continue
			Bundle bundle = new Bundle();
			//add to bundle
			bundle.putInt("SCORE", gameEnv.getScore());
			bundle.putInt("LEVEL", gameEnv.getLevel());
			bundle.putInt("MODE", mode);
			//set intent to continue class
			cont.setClass(getBaseContext(), Cont.class);
			//add bundle to intent
			cont.putExtras(bundle);
			//set GameOver to stop thread
			setGameOver(true);
			//start new activity
			startActivity(cont);
		}
		
		if (gameEnv.getGuess().length() > gameEnv.getStrConverted().length()) {
			
			//New intent & Bundle
			Intent gameover = new Intent();
			
			//create bundle for retry
			Bundle bundle = new Bundle();
			//add to bundle
			bundle.putInt("SCORE", gameEnv.getScore());
			bundle.putInt("LEVEL", gameEnv.getLevel());
			bundle.putInt("MODE", mode);
			//set intent to gameover class
			gameover.setClass(getBaseContext(), Gameover.class);
			//add bundle to intent
			gameover.putExtras(bundle);
			//set GameOver to stop thread
			setGameOver(true);
			//start new activity
			startActivity(gameover);
		}
	}
	
	public void setGameOver(boolean b) {
		gameOver = b;
	}
}
