package com.zxtodaro.binarybog;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Frog {
	private float X;
	private float Y;
	private int BoundX;
	private int BoundY;
	private Bitmap bmp;
	private boolean hopped;
	private double speed;

	public Frog() {
		X = -1;
		Y = -1;
		BoundX = -1;
		BoundY = -1;
		bmp = null;
	}
	
	public Frog (Resources res, int X, int Y) {
		bmp = BitmapFactory.decodeResource(res, R.drawable.frog_player);
		this.X = X - bmp.getWidth() / 2;
		this.Y = Y - bmp.getHeight();
		hopped = false;
		speed = .009;
	}
	
    public void doDraw(Canvas c) {
        c.drawBitmap(bmp, X, Y, null);
    }
    
    public void animation(long runTime) {
    		Y += speed * (runTime * 5f);
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
}
