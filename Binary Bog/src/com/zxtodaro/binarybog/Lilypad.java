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
	
	
	public Indicator onPress(float x, float y) {
		
		//create lilypad rectangle for collision
		Rect lilyBox = new Rect(X, Y, X+Bmp.getWidth(), Y+Bmp.getHeight());
		//check to see where on the board the user pressed
		if (lilyBox.contains((int)x, (int)y)) {
		return Indicator.HOP;
		} 
		else {
		//not on lilypad
		return Indicator.DROWN;
		}
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
	
	public void setBoundy(int y) {
		this.BoundX = y;
	}
}
