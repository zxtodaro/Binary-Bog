package com.zxtodaro.binarybog;

import android.graphics.Bitmap;

public class Frog {
	private int X = -1;
	private int Y = -1;
	private int BoundX = -1;
	private int BoundY = -1;
	private Bitmap Bmp = null;
	
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
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
		return Bmp;
	}
	public void setBmp(Bitmap bmp) {
		Bmp = bmp;
	}
	
}
