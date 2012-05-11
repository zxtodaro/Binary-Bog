package com.zxtodaro.binarybog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SelectMode extends Activity {
	private int mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//no sleepy androidy
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	
		//set no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//no notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.selectmode);
	
	
	}
		
	public void gotoBinaryPlay(View v) {
		
		Button btnBinaryPlay = (Button) findViewById(R.id.PlayBinary);
		Intent play = new Intent();
		play.setClass(v.getContext(), Play.class);
		mode = 0;
		//create bundle
		Bundle bundle = new Bundle();
		//add to bundle
		bundle.putInt("MODE", mode);
		play.putExtras(bundle);
		startActivityForResult(play,0);

	}
	
	public void gotoOctalPlay(View v) {
			
		Button btnOctalPlay = (Button) findViewById(R.id.PlayOctal);
		Intent play = new Intent();
		play.setClass(v.getContext(), Play.class);
		//create bundle
		Bundle bundle = new Bundle();
		mode = 1;
		//add to bundle
		bundle.putInt("MODE", mode);
		play.putExtras(bundle);
		startActivityForResult(play,0);

	}
	
}
