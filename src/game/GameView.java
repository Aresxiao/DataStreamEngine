
package game;


import game.sharedmemory.AbstractBall;
import java.util.ArrayList;
import java.util.List;

import main.MainActivity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * 这是GameView类，主要是展示的功能。
 * @author Ares
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String TAG = GameView.class.getName();
	MainActivity activity;
	Paint paint;
	GameViewDrawThread drawThread;
	int viewFrame = 0;
	//boolean isOver;
	
	public GameView(MainActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		//this.activity = activity;
		//isOver=false;
		if(activity != null){
			Log.i(TAG, "activity is not null");
		}
		getHolder().addCallback(this);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		canvas.drawColor(Color.WHITE);
		Table table = GameModel.INSTANCE.table;
		table.drawSelf(canvas, paint);
		paint.reset();
		List<AbstractBall> alBallsTemp=new ArrayList<AbstractBall>(GameModel.INSTANCE.ballList);
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
        // 设置去锯齿
        paint.setAntiAlias(true);
        
        drawThread = new GameViewDrawThread(this);
        drawThread.setFlag(true);
        drawThread.start();
        
	}
	
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry){//不断地循环，直到其它线程结束
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
