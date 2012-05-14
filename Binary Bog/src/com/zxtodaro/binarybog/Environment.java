package com.zxtodaro.binarybog;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/*DECLARE ALL VARIABLES & PROPERTIES*/
public class Environment extends SurfaceView implements SurfaceHolder.Callback {
	//declare height and width
	public static float height;
	public static float width;
	
	//declare left & right boundary
	public static float lBound;
	public static float rBound;
	
	//Background image
	private Bitmap background; 
	
	//declare thread
	private EnvThread thread;
	
	//paint for land
	private Paint land;
	
	//paint for HUD
	private Paint text;
	private Paint label;
	private Paint value;
	
	//declare player
	public Frog frog;
	
	//player's score
	private int score;
	
	//player's game set
	private int level;
	
	//integer value of the number to convert
	private int intConvert;
	
	//string value of winning number(decimal)
	private String strConvert;
	
	//string value of number(bin)
	private String strConverted;
	
	//String to hold guesses
	private String guess;
	
	//create array to hold lilypads
	public ArrayList<Lilypad> lilypads = new ArrayList<Lilypad>();
	
	//constructor
	public Environment(Context context) {
		super(context);
		getHolder().addCallback(this);
		thread = new EnvThread(this);
	}
	
	/*THIS IS WHERE PLAY RECEIVES THE SURFACE FROM ENVIRONMENT AND THE ENVIRONMENT THREAD IS STARTED*/
	public void surfaceCreated(SurfaceHolder holder) {
		width = this.getWidth();
		height = this.getHeight();
		lBound = (width / 8) * 1;
		rBound = (width / 8) * 7;
		
		//instantiate background & set to drawable
		background = BitmapFactory.decodeResource(getResources(), R.drawable.river);
		
		//instantiate paint for land
		land = new Paint();
		
		//set land color
		land.setColor(Color.parseColor("#8B4500"));
		
		//instantiate paint for HUD
		text = new Paint();
		label = new Paint();
		value = new Paint();
		
		//set values for HUD text
		text.setColor(Color.BLACK);
		text.setTextAlign(Align.LEFT);
		text.setStyle(Style.FILL);
		text.setTextSize(55);
		
		//set values for HUD label
		label.setColor(Color.BLACK);
		label.setTextAlign(Align.LEFT);
		label.setStyle(Style.FILL);
		label.setTextSize(36);
		
		//set values for lilypad value
		//set custom font
    	Typeface tfValue = Typeface.createFromAsset(getResources().getAssets(), "fonts/Forum-Regular.ttf"); 
    	value.setTypeface(tfValue);
    	
		value.setColor(Color.BLACK);
		value.setTextAlign(Align.CENTER);
		value.setStyle(Style.FILL);
		value.setFakeBoldText(true);
		value.setTextSize(48);
		
		//instantiate frog for player token
		frog = new Frog(getResources(), (int)(width / 2), (int)(height));
		
		if (!thread.isAlive()) {
			thread = new EnvThread(this);
			thread.setRun(true);
			thread.start();
			
		}
		Play.setSurface();
		
	}

	/*THIS IS WHERE ALL DRAWING TO THE CANVAS TAKES PLACE*/
	public void doDraw(Canvas c) {
		
		//draw river for background
		c.drawBitmap(background, lBound, 0, null);
		
		//draw land rectangles
		c.drawRect(0, 0, (getWidth() / 8 * 1), getHeight(), land);
		c.drawRect((getWidth() / 8 * 7), 0, getWidth(), getHeight(), land);
		
		synchronized(lilypads) {
			if (lilypads.size() > 0) {
				for (Iterator<Lilypad> i = lilypads.iterator(); i.hasNext();) {
					i.next().doDraw(c, value);
					
				}
			}
		}
	
			//draw HUD information
			c.drawText("Convert: ", c.getWidth()/16 * 0, c.getHeight() / 15 * 1, label);
			c.drawText(strConvert, c.getWidth()/16 * 1, c.getHeight() / 15 * 2, text);
			c.drawText("Score: ", c.getWidth() / 8 * 7, c.getHeight() / 15 * 13, label);
			c.drawText(String.valueOf(score), c.getWidth() / 8 * 7, c.getHeight() / 15 * 14, text);
			c.drawText("Guess: ", c.getWidth() / 8 * 7, c.getHeight() / 15 * 1, label);
			c.drawText(guess, c.getWidth() / 8 * 7, c.getHeight() / 15 * 2, text);
			
			//draw frog player
			frog.doDraw(c);
			
	}

	/*THIS IS WHERE ALL ANIMATION MUST TAKE PLACE*/
	public void animate(long runTime) {
		
		//animate the lilypads
		synchronized(lilypads) {
			if (lilypads.size() > 0) {
				for (Iterator<Lilypad> i = lilypads.iterator(); i.hasNext();) {
					i.next().animation(runTime);
				}
			}
		}
		
		//animate the frog
		synchronized(frog) {
			//if the frog is at initial position don't animate
		if (frog.isHopped()) {
			//if he has moved animate him
			frog.animation(runTime);
		}
		}
	}
	
	//If the surface changes recalculate height and width
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		width = this.getWidth();
		height = this.getHeight();
	}
	
	//if the surface is destroyed stop the thread from running
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (thread.isAlive()) {
			thread.setRun(false);
		}
	}

	//check land value
	public Paint getLand() {
		return land;
	}

	//set land value
	public void setLand(Paint land) {
		this.land = land;
	}

	//check left boundary
	public float getlBound() {
		return lBound;
	}

	//set left boundary
	public void setlBound(float lBound) {
		this.lBound = lBound;
	}

	//check right boundary
	public float getrBound() {
		return rBound;
	}

	//set right boundary
	public void setrBound(float rBound) {
		this.rBound = rBound;
	}
	
	//check score
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	//increase score if won
	public void incrementScore() {
		score += 1;
	}
	
	//Reset score
	public void resetScore() {
		score = 0;
	}
	
	//get set number
	public int getLevel() {
		return level;
	}
	
	//set level
	public void setLevel(int level) {
		this.level = level;
	}
	
	//increment set number
	public void incrementLevel() {
		level += 1;
	}
	
	//reset set number
	public void resetLevel() {
		level = 0;
	}
	
	//check for solution as string
	public String getStrConverted() {
		return strConverted;
	}

	//set the solution as a string
	public void setStrConverted(String strConverted) {
		this.strConverted = strConverted;
	}

	//guess as a string
	public String getGuess() {
		return guess;
	}

	//set guess as a string
	public void setGuess(String guess) {
		this.guess = guess;
	}

	//set values for game
	public void setGameValues(int intConvert, String strCon,
			String strCond) {
		this.strConvert = strCon;
		this.strConverted = strCond;
	}
}
