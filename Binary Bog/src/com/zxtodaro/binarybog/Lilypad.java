package com.zxtodaro.binarybog;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.TextView;

public class Lilypad {
	//x & y coordinates
	private float X;
	private float Y;
	
	//bitmap for lilypad
	private Bitmap bmp;
	
	//binary value (0, or 1)
	private boolean valueB;
	
	//octal value
	private int valueO;
	
	//textview of value
	TextView tvValue;
	
	//random number generator for item image, value, and lane
	private Random r = new Random();
	
	//y speed
	private static double speedY;
	
	//starting X value
	private int laneX;
	
	//constructor
	public Lilypad(Resources res) {
		
		//Randomly create one or zero
		valueB = r.nextBoolean();
		
		//randomly choose bitmap
		boolean bmpB = r.nextBoolean();
		
		//set octal value
		valueO = r.nextInt(7);
		
		//instantiate and set bmp for lilypad
		//set lilypad bitmap to 1 or 2 if random boolean is true (1), set it to 3 or 4 if random boolean is false
		if (valueB) {
			if (bmpB) { 
			bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad1);
			}
			else {
				bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad2);
			}
		}
		else {
			if (bmpB) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad3);
			}
			else {
				bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad4);
			}
		}
		
		//set travel lane
		laneX = r.nextInt(8);
		
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
	}
	
    public boolean outOfBounds() {
        if (Y - bmp.getHeight() >= Environment.height) {
            return true;
        }
        
        return false;
    }
    
    public void doDraw(Canvas c) {
    	c.drawBitmap(bmp, X, Y, null);
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
	
	//check if lilypad is 1(true) or 0(false)
	public boolean isOne() {
		return valueB;
	}
	//set lilypad to 1(true) or 0(false)
	public void setOne(boolean one) {
		this.valueB = one;
	}
	//check octal value of lilypad
	public int isValueO() {
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
}
