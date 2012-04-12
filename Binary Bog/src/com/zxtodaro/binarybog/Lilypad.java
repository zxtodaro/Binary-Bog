package com.zxtodaro.binarybog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Lilypad {
	private int X = -1;
	private int Y = -1;
	private int BoundX = -1;
	private int BoundY = -1;
	private Bitmap Bmp = null;
	private boolean one = false;
	
	public Lilypad() {
		X = -1;
		Y = -1;
		BoundX = -1;
		BoundY = -1;
		Bmp = null;
		one = false;
	}
	

	public int getX() {
		return X;
	}

	public void setX(int lilypadX) {
		this.X = lilypadX;
	}

	public int getY() {
		return Y;
	}

	public void setY(int lilypadY) {
		this.Y = lilypadY;
	}

	public boolean isOne() {
		return one;
	}

	public void setOne(boolean one) {
		this.one = one;
	}

	public Bitmap getBmp() {
		return Bmp;
	}

	public void setBmp(Bitmap lilypadBmp) {
		this.Bmp = lilypadBmp;
	}

	public int getWidth() {
		return Bmp.getWidth();
	}
	
	public int getHeight() {
		return Bmp.getHeight();
	}
	
	public void setBoundX(int x) {
		this.BoundX = x;
	}
	
	public void setBoundY(int y) {
		this.BoundY = y;
	}
	
	public int getBoundX() {
		return BoundX;
	}
	
	public int getBoundY() {
		return BoundY;
	}
}
