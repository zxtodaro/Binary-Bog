package com.zxtodaro.binarybog;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Lilypad {
	//x & y coordinates
	private float X;
	private float Y;
	
	//bitmap for lilypad
	private Bitmap bmp;
	
	//boolean value of lilypad (0, or 1)
	private int valueB;
	
	//octal value
	private int valueO;
	
	//random number generator for item image, value, and lane
	private Random r = new Random();
	
	//y speed
	private static double speedY;
	
	//starting X value
	private int laneX;
	
	//set whether the lilypad has been hopped on (used) or not
	private boolean used;
	
	//check if the lilypad is being leaptFrom
	private boolean leaptFrom;
	
	//opacity
	private int alpha;
	
	//paint for fade out
	private Paint fadeOut;
	
	//constructor
	public Lilypad(Resources res, int mode) {
		
		//set used to false
		used = false;
		//set leaptFrom to false
		leaptFrom = false;
		//set opacity to 100%
		alpha = 255;
		//set alpha level of paint
		fadeOut = new Paint();
		fadeOut.setAlpha(alpha);
		
		if (mode == 0) {
		//set valueO to negative so doDraw draws the correct label
		valueO = -1;
		//Randomly create one or zero
		valueB = r.nextInt(2);
		
		//randomly choose bitmap
		boolean bmpRandom = r.nextBoolean();
		
		//set lilypad bitmap based on if random number(valueB) is 0 or 1, set it to 3 or 4 if random boolean is false
		if (valueB == 1) {
			if (bmpRandom) { 
			bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad1);
			}
			else {
				bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad2);
			}
		}
		else {
			if (bmpRandom) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad3);
			}
			else {
				bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad4);
			}
		}
		}
		
		else if (mode == 1) {
			//set valueB to negative so doDraw draws the correct label
			valueB = -1;
			//randomly set octal value
			valueO = r.nextInt(8);
			//randomly set bitmap
			int bmpRandom = r.nextInt(4);
			
			//set lilypad bitmap based on random value
			switch (bmpRandom) {
			case 0: bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad1);
			break;
			case 1: bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad2);
			break;
			case 2: bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad3);
			break;
			case 3: bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad4);
				}
		}

		
		//set travel lane
		laneX = r.nextInt(9);
		
		switch (laneX) {
		case 0:
			X = Environment.lBound;
			break;
		case 1:
			X = Environment.lBound + bmp.getWidth();
			break;
		case 2:
			X = Environment.lBound + (bmp.getWidth() * 2);
			break;
		case 3:
			X = Environment.lBound + (bmp.getWidth() * 3);
			break;
		case 4:
			X = Environment.lBound + (bmp.getWidth() * 4);
			break;
		case 5:
			X = Environment.lBound + (bmp.getWidth() * 5);
			break;
		case 6:
			X = Environment.lBound + (bmp.getWidth() * 6);
			break;
		case 7:
			X = Environment.lBound + (bmp.getWidth() * 7);
			break;
		case 8:
			X = Environment.lBound + (bmp.getWidth() * 8);
			break;
		}
		
		Y = 0 - bmp.getHeight();
		speedY = .009;
	}
	
	public void animation(long runTime) {
		Y += speedY * (runTime * 5f);
		
		//fade lilypad if marked as leaptFrom
		if ((alpha > 0) && (isLeaptFrom())) {
			alpha -= 5;
			Log.i("Alpha", String.valueOf(alpha));
		 setAlpha(alpha);
		 }
	}
	
    private void setAlpha(int alpha) {
		fadeOut.setAlpha(alpha);
	}

	public boolean outOfBounds() {
        if (Y - bmp.getHeight() >= Environment.height) {
            return true;
        }
        
        return false;
    }
    
    public void doDraw(Canvas c, Paint label) {
    	c.drawBitmap(bmp, X, Y, fadeOut);

    	
    	if (valueO < 0) {
    	   	c.drawText(String.valueOf(valueB), X + (bmp.getWidth() / 2), Y + (bmp.getHeight() / 2), label);
    	}
    	else {
    		c.drawText(String.valueOf(valueO), X + (bmp.getWidth() / 2), Y + (bmp.getHeight() / 2), label);
    	}
    }
	
	//get x position
	public int getX() {
		return (int)X;
	}
	//set x position
	public void setX(int lilypadX) {
		this.X = lilypadX;
	}
	//get y position
	public int getY() {
		return (int)Y;
	}
	//set y position
	public void setY(int lilypadY) {
		this.Y = lilypadY;
	}
	
	//set speed of lilypad
	public void setSpeed(double s) {
		this.speedY = s;
	}
	
	//check if lilypad is 1 or 0
	public int isOne() {
		return valueB;
	}
	//set lilypad to 1 or 0
	public void setOne(int one) {
		this.valueB = one;
	}
	//check octal value of lilypad
	public int getValueO() {
		return valueO;
	}
	//check bitmap of lilypad
	public Bitmap getBmp() {
		return bmp;
	}
	// set the bitmap of lilypad
	public void setBmp(Bitmap lilypadBmp) {
		this.bmp = lilypadBmp;
	}
	//get image width
	public int getWidth() {
		return bmp.getWidth();
	}
	//get image height
	public int getHeight() {
		return bmp.getHeight();
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public boolean isLeaptFrom() {
		return leaptFrom;
	}
	
	public void setLeaptFrom (boolean leaptFrom) {
		this.leaptFrom = leaptFrom;
	}
}
