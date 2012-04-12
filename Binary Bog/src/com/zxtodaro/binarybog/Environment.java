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
	
	private int frogX = -1;
	private int frogY = -1;
	private Bitmap frogBmp = null;
	private Paint water = null;
	private Paint land = null;
	private Frog frog = null;
	private Lilypad lilypad = null;
	
	public Environment(Context context, AttributeSet aSet) {
		
		super(context, aSet);
		frogBmp = BitmapFactory.decodeResource(getResources(), R.drawable.frog_player);
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
			lilypad.setX((int)(getWidth() / 2) - lilypad.getWidth() / 2);
			lilypad.setY((int)(getHeight() / 2) - lilypad.getHeight() / 2);
			
		//find & set boundaries
			lilypad.setBoundX((int)(getWidth() / 8 * 7) - lilypad.getWidth());
			lilypad.setBoundy((int)getHeight() - lilypad.getHeight());
			}
		//check if frog has been initialized on screen, if not place him on screen
		if ((frog.getX() < 1) || (frog.getY() < 1)){
			frog.setX((int)(getWidth() / 2) - frogBmp.getWidth() / 2);
			frog.setY((int)getHeight() - frogBmp.getHeight());
		}
		canvas.drawRect(0, 0, (getWidth() / 8 * 1), getHeight(), land);
		canvas.drawRect((getWidth() / 8 * 7), 0, getWidth(), getHeight(), land);
		canvas.drawBitmap(lilypad.getBmp(), lilypad.getX(), lilypad.getY(), null);
		canvas.drawBitmap(frog.getBmp(), frog.getX(), frog.getY(), null);
		}
		
}
