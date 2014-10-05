package com.example.datastreamengine;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	MainActivity activity;
	Paint paint;
	List<Ball> alBalls;
	
	BallGoThread ballGoThread ;
	
	public GameView(MainActivity activity ) {
		super(activity);
		// TODO Auto-generated constructor stub
		
		this.activity = activity;
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);//注册回调接口		
	}
	
	

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		canvas.drawColor(Color.WHITE);
	}



	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
