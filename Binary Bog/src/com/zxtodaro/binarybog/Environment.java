package com.zxtodaro.binarybog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class Environment extends View {
	
	private int lilypadX = -1;
	private int lilypadY =  -1;
	private int frogX = -1;
	private int frogY = -1;
	private Bitmap lilypadBmp = null;
	private Bitmap frogBmp = null;
	private Paint water = null;
	private Paint land = null;
	private boolean isFlagHidden = false;
	private int lilyBoundX = -1;
	private int lilyBoundY = -1;
	
	
	//Hides the flag based on the screen boundaries
	public void hideTheFlag(){
		lilypadX = (int) Math.ceil(Math.random() * lilyBoundX);
		lilypadY = (int) Math.ceil(Math.random() * lilyBoundY);
		isFlagHidden = true;
		invalidate();
		}
	
	public Environment(Context context, AttributeSet aSet) {
		super(context, aSet);
		lilypadBmp = BitmapFactory.decodeResource(getResources(), R.drawable.lilypad2);
		frogBmp = BitmapFactory.decodeResource(getResources(), R.drawable.frog_player);
		water = new Paint();
		water.setColor(Color.parseColor("#66CCFF"));
		land = new Paint();
		land.setColor(Color.parseColor("#663300"));
		
		
	}
	
	//Draws playspace
		@Override
		public void onDraw(Canvas canvas) {
		if ((lilypadX < 1) || (lilypadY < 1)) {
		lilypadX = (int)(getWidth() / 2) - lilypadBmp.getWidth() / 2;
		lilypadY = (int)(getHeight() / 2) - lilypadBmp.getHeight() / 2;
		frogX = (int)(getWidth() / 2) - frogBmp.getWidth() / 2;
		frogY = (int)getHeight() - frogBmp.getHeight();
		lilyBoundX = (int)(getWidth() / 8 * 7) - lilypadBmp.getWidth();
		lilyBoundY = (int)getHeight() - lilypadBmp.getHeight();
		}
		canvas.drawRect(0, 0, (getWidth() / 8 * 1), getHeight(), land);
		canvas.drawRect((getWidth() / 8 * 7), 0, getWidth(), getHeight(), land);
		if (!isFlagHidden) {
		canvas.drawBitmap(lilypadBmp, lilypadX, lilypadY, null);
		canvas.drawBitmap(frogBmp, frogX, frogY, null);
		}
		}
	
	//Allows the user to give up and reveals flag
	public void giveUp(){
		isFlagHidden = false;
		invalidate();
		}
	
	
	public Indicators takeAGuess(float x, float y, Canvas canvas) {
		//this is the area that contains our flag
		Rect lilyBox = new Rect(lilypadX, lilypadY, lilypadX+lilypadBmp.getWidth(), lilypadY+lilypadBmp.getHeight());
		//check to see where on the board the user pressed
		if (lilyBox.contains((int) x, (int)y)) {
		canvas.drawBitmap(frogBmp, x, y, null);
		//found it
		isFlagHidden = false;
		invalidate();
		return Indicators.HOP;
		} else {
		//not on lilypad
		return Indicators.DROWN;
		}
	}
		
}
