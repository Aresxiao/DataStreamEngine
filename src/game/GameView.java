
package game;


import java.util.ArrayList;
import java.util.List;

import main.MainActivity;
import constant.Constant;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * 这是GameView类，主要是展示的功能。
 * @author GengXiao
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	
	MainActivity activity;
	Paint paint;
	List<Ball> alBalls;
	GameViewDrawThread drawThread;
	Table table;
	BallGoThread ballGoThread ;
	
	boolean isOver;
	
	public GameView(MainActivity activity ) {
		super(activity);
		// TODO Auto-generated constructor stub
		
		this.activity = activity;
		isOver=false;
		getHolder().addCallback(this);	//注册回调接口		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		canvas.drawColor(Color.WHITE);
		table.drawSelf(canvas, paint);
		paint.reset();
		List<Ball> alBallsTemp=new ArrayList<Ball>(alBalls);
		for(int i = 0;i<alBallsTemp.size();i++){
			Ball ball = alBallsTemp.get(i);
			ball.drawSelf(canvas, paint);
		}
	}

	
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		createAllThread();
		table = new Table();
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
        // 设置去锯齿
        paint.setAntiAlias(true);
        
		alBalls = new ArrayList<Ball>();
		alBalls.add(new Ball(true, this, Constant.TABLE_WIDTH/2-Constant.GOAL_BALL_SIZE/2, 
				Constant.TABLE_HEIGHT/2-Constant.GOAL_BALL_SIZE/2));
		alBalls.add(new Ball(false, this, 20, 20));
		alBalls.add(new Ball(false, this, 140, 20));
		startAllThread();
	}
	
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry){//不断地循环，直到其它线程结束
        	joinAllThread();
            retry = false;
        }
	}
	
	void createAllThread(){
		
		drawThread = new GameViewDrawThread(this);
		ballGoThread = new BallGoThread(this);
		
	}
	
	void startAllThread(){
		drawThread.setFlag(true);
		drawThread.start();
		ballGoThread.setFlag(true);
		ballGoThread.start();
		
	}

	void stopAllThread(){
		drawThread.setFlag(false);
		ballGoThread.setFlag(false);
	}
	
	void joinAllThread(){
		
		try {
			drawThread.join();
			ballGoThread.join();
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void overGame(){
		isOver=true;
		stopAllThread();
	}
	
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
	
	public Ball getBallById(int id){
		return alBalls.get(id);
	}
}
