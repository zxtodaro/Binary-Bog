package com.zxtodaro.binarybog;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
	
	//declare player
	public Frog frog;
	
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
		
		//draw player frog
		frog.doDraw(c);
		
		synchronized(lilypads) {
			if (lilypads.size() > 0) {
				for (Iterator<Lilypad> i = lilypads.iterator(); i.hasNext();) {
					i.next().doDraw(c);
				}
			}
		}
	}
	
	
	public void surfaceCreated(SurfaceHolder holder) {
		width = this.getWidth();
		height = this.getHeight();
		lBound = (width / 8) * 1;
		rBound = (width / 8) * 7;
		
		//instantiate background & set to drawable
		background = BitmapFactory.decodeResource(getResources(), R.drawable.river2);
		
		//instantiate land
		land = new Paint();
		
		//set land color
		land.setColor(Color.parseColor("#8B4726"));
		
		frog = new Frog(getResources(), (int)(width / 2), (int)(height));
		
		if (!thread.isAlive()) {
			thread = new EnvThread(this);
			thread.setRun(true);
			thread.start();
			
		}
		Play.setSurface();
		
	}

	
	public void animate(long runTime) {
		
		if (frog.isHopped()) {
			frog.animation(runTime);
		}
		synchronized(lilypads) {
			if (lilypads.size() > 0) {
				for (Iterator<Lilypad> i = lilypads.iterator(); i.hasNext();) {
					i.next().animation(runTime);
				}
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
		
}
