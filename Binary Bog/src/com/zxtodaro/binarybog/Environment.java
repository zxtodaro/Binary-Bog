package com.zxtodaro.binarybog;

import java.util.Random;

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
	
	private Paint water = null;
	private Paint land = null;
	private Frog frog = new Frog();
	private Lilypad lilypad = new Lilypad();
	private Random r = new Random();
	Lilypad[] lilypads;
	
	public Environment(Context context, AttributeSet aSet) {
		
		super(context, aSet);
		frog.setBmp(BitmapFactory.decodeResource(getResources(), R.drawable.frog_player));
		lilypad.setBmp(BitmapFactory.decodeResource(getResources(), R.drawable.lilypad2));
		water = new Paint();
		water.setColor(Color.parseColor("#66CCFF"));
		land = new Paint();
		land.setColor(Color.parseColor("#663300"));
		
	}
	
	//Draws playspace
		@Override
		public void onDraw(Canvas canvas) {
		if ((lilypad.getX() < 1) || (lilypad.getY() < 1)) {
			
			//find & set boundaries
			lilypad.setBoundX((getWidth() / 8 * 7) - lilypad.getWidth());
			lilypad.setBoundY(getHeight() - lilypad.getHeight());
			
			//place lilypad on screen
			lilypad.setX(r.nextInt(lilypad.getBoundX()));
			lilypad.setY(r.nextInt(lilypad.getBoundY()));
			
			}
		
		//check if frog has been initialized on screen, if not place him on screen
		if ((frog.getX() < 1) || (frog.getY() < 1)){
			frog.setX((int)(getWidth() / 2) - frog.getWidth() / 2);
			frog.setY((int)getHeight() - frog.getHeight());
		}
		canvas.drawRect(0, 0, (getWidth() / 8 * 1), getHeight(), land);
		canvas.drawRect((getWidth() / 8 * 7), 0, getWidth(), getHeight(), land);
		canvas.drawBitmap(lilypad.getBmp(), lilypad.getX(), lilypad.getY(), null);
		canvas.drawBitmap(frog.getBmp(), frog.getX(), frog.getY(), null);
		}
		
		public Indicator onPress(float x, float y) {
			
			//create lilypad rectangle for collision
			Rect lilyBox = new Rect(lilypad.getX(), lilypad.getY(), lilypad.getX()+lilypad.getBmp().getWidth(),
					lilypad.getY()+lilypad.getBmp().getHeight());
			//check to see where on the board the user pressed
			if (lilyBox.contains((int)x, (int)y)) {
				frog.setX(lilypad.getX());
				frog.setY(lilypad.getY());
				invalidate();
			return Indicator.HOP;
			} 
			else {
			//not on lilypad
			return Indicator.DROWN;
			}
		}
		
}
