package com.zxtodaro.binarybog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

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
	
	//declare player
	public Frog frog;
	
	//what is the player's score
	private int score = 0;
	
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
	
	
	public void doDraw(Canvas c) {
		
		//draw river for background
		c.drawBitmap(background, lBound, 0, null);
		
		//draw land rectangles
		c.drawRect(0, 0, (getWidth() / 8 * 1), getHeight(), land);
		c.drawRect((getWidth() / 8 * 7), 0, getWidth(), getHeight(), land);
		
		synchronized(lilypads) {
			if (lilypads.size() > 0) {
				for (Iterator<Lilypad> i = lilypads.iterator(); i.hasNext();) {
					i.next().doDraw(c);
				}
			}
		}
		
		//draw frog player
			frog.doDraw(c);

			c.drawText(strConvert, c.getWidth()/16 * 1, c.getHeight()/15*1, text);
			c.drawText(strConverted, c.getWidth() / 8 * 7, c.getHeight() / 15 * 1, text);
			c.drawText(guess, c.getWidth() / 8 * 7, c.getHeight() / 15 * 13, text);
			
	}
	
	
	public void surfaceCreated(SurfaceHolder holder) {
		width = this.getWidth();
		height = this.getHeight();
		lBound = (width / 8) * 1;
		rBound = (width / 8) * 7;
		
		//instantiate background & set to drawable
		background = BitmapFactory.decodeResource(getResources(), R.drawable.river2);
		
		//instantiate paint for land
		land = new Paint();
		
		//set land color
		land.setColor(Color.parseColor("#8B4500"));
		
		//instantiate paint for HUD
		text = new Paint();
		
		//set values for HUD paint
		text.setColor(Color.BLACK);
		text.setTextAlign(Align.LEFT);
		text.setStyle(Style.FILL);
		text.setTextSize(65);
		
		//instantiate frog for player token
		frog = new Frog(getResources(), (int)(width / 2), (int)(height));
		
		if (!thread.isAlive()) {
			thread = new EnvThread(this);
			thread.setRun(true);
			thread.start();
			
		}
		Play.setSurface();
		
	}

	
	public void animate(long runTime) {
		synchronized(lilypads) {
			if (lilypads.size() > 0) {
				for (Iterator<Lilypad> i = lilypads.iterator(); i.hasNext();) {
					i.next().animation(runTime);
				}
			}
		}
		
		synchronized(frog) {
		if (frog.isHopped()) {
			frog.animation(runTime);
		}
		}
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		width = this.getWidth();
		height = this.getHeight();
	}
	

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
	
	//increase score if won
	public void incrementScore() {
		score += 1;
	}
	
	//check score
	public int getScore() {
		return score;
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
	public void setGameValues(int intCon, String strCon,
			String strCond) {
		this.intConvert = intCon;
		this.strConvert = strCon;
		this.strConverted = strCond;
	}
}
