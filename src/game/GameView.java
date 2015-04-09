
package game;


import game.sharedmemory.AbstractBall;
import java.util.ArrayList;
import java.util.List;

import main.MainActivity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * ����GameView�࣬��Ҫ��չʾ�Ĺ��ܡ�
 * @author GengXiao
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	
	MainActivity activity;
	Paint paint;
	//List<Ball> alBalls;
	GameViewDrawThread drawThread;
	
	//boolean isOver;
	GameModel gameModel;
	
	public GameView(MainActivity activity,GameModel gameModel) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.gameModel = gameModel;
		//this.activity = activity;
		//isOver=false;
		getHolder().addCallback(this);	//ע��ص��ӿ�		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		canvas.drawColor(Color.WHITE);
		Table table = gameModel.table;
		table.drawSelf(canvas, paint);
		paint.reset();
		List<AbstractBall> alBallsTemp=new ArrayList<AbstractBall>(gameModel.ballList);
		for(int i = 0;i<alBallsTemp.size();i++){
			AbstractBall ball = alBallsTemp.get(i);
			ball.drawSelf(canvas, paint);
		}
		
	}

	
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		//createAllThread();
		//table = new Table();
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
        // ����ȥ���
        paint.setAntiAlias(true);
        
        drawThread = new GameViewDrawThread(this);
        drawThread.setFlag(true);
        drawThread.start();
        /*
		alBalls = new ArrayList<Ball>();
		alBalls.add(new Ball(true, this, Constant.TABLE_WIDTH/2-Constant.GOAL_BALL_SIZE/2, 
				Constant.TABLE_HEIGHT/2-Constant.GOAL_BALL_SIZE/2));
		alBalls.add(new Ball(false, this, 20, 20));
		alBalls.add(new Ball(false, this, 140, 20));
		*/
		//startAllThread();
	}
	
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry){//���ϵ�ѭ����ֱ�������߳̽���
        	//joinAllThread();
			try {
				drawThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            retry = false;
        }
	}
	
	void createAllThread(){
		
		drawThread = new GameViewDrawThread(this);
		//ballGoThread = new BallGoThread(this);
		
	}
	
	void startAllThread(){
		drawThread.setFlag(true);
		drawThread.start();
		
		
	}

	void stopAllThread(){
		drawThread.setFlag(false);
	}
	
	void joinAllThread(){
		
		try {
			drawThread.join();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public void overGame(int whichHole){
		
		isOver=true;
		stopAllThread();
		//drawThread.setFlag(false);
		if(whichHole == 1){
			activity.sendMessage(WhatMessage.OVER_GAMEWINN);
		}
		else {
			activity.sendMessage(WhatMessage.OVER_GAMEWINS);
		}
	}
	*/
	public void repaint(){
		Canvas canvas = this.getHolder().lockCanvas();
		try
		{
			synchronized(canvas)
			{
				draw(canvas);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				this.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
	
}
