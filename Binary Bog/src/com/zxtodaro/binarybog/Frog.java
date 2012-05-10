package com.zxtodaro.binarybog;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Frog {
	private float X;
	private float Y;
	private int BoundX;
	private int BoundY;
	private Bitmap bmp;
	private boolean hopped;
	private double speed;
	
	public Frog (Resources res, int x, int y) {
		bmp = BitmapFactory.decodeResource(res, R.drawable.frog_player);
		this.X = x - bmp.getWidth() / 2;
		this.Y = y - bmp.getHeight();
		hopped = false;
		speed = .009;
	}
	
    public void doDraw(Canvas c) {
        c.drawBitmap(bmp, X, Y, null);
    }
    
    public void animation(long runTime) {
    		Y += speed * (runTime * 5f);
    }
    
    public boolean outOfBounds() {
        if (Y - bmp.getHeight() >= Environment.height) {
            return true;
        }
        
        return false;
    }
	
	public float getX() {
		return X;
	}
	public void setX(int x) {
		this.X = x;
	}
	public float getY() {
		return Y;
	}
	public void setY(int y) {
		this.Y = y;
	}
	public int getBoundX() {
		return BoundX;
	}
	public void setBoundX(int boundX) {
		BoundX = boundX;
	}
	public int getBoundY() {
		return BoundY;
	}
	public void setBoundY(int boundY) {
		BoundY = boundY;
	}
	public Bitmap getBmp() {
		return bmp;
	}
	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}
	
	public int getWidth() {
		return bmp.getWidth();
	}
	
	public int getHeight() {
		return bmp.getHeight();
	}
	
	public boolean isHopped() {
		return hopped;
	}

	public void setHopped(boolean hopped) {
		this.hopped = hopped;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
