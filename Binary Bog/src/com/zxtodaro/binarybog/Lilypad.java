package com.zxtodaro.binarybog;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Lilypad {
	//x & y coordinates
	private float X;
	private float Y;
	
	//bitmap for lilypad
	private Bitmap bmp;
	
	//value (0, or 1)
	private boolean one;
	
	//random number generator for item image, value, and lane
	private Random r = new Random();
	
	//y speed
	private static double speedY;
	
	private int laneX;
	
	//constructor
	public Lilypad(Resources res) {
		
		//Randomly create one or zero
		boolean b = r.nextBoolean();
		
		//instantiate and set bmp for lilypad
		if (b) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad2);
		}
		else {
			bmp = BitmapFactory.decodeResource(res, R.drawable.lilypad1);
		}
		
		//set travel lane
		laneX = r.nextInt(4);
		
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
		}
		
		Y = 0 - bmp.getHeight();
		speedY = .009;
		one = b;	
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
	//check if lilypad is 1(true) or 0(false)
	public boolean isOne() {
		return one;
	}
	//set lilypad to 1(true) or 0(false)
	public void setOne(boolean one) {
		this.one = one;
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
