package game;

import constant.Constant;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import main.MainActivity;

public class GameWinNView extends SurfaceView implements SurfaceHolder.Callback{
	
	MainActivity activity;
	Paint paint;
	
	public GameWinNView(MainActivity mainActivity){
		super(mainActivity);
		activity = mainActivity;
		this.getHolder().addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		paint.setTextSize(40);
		
		canvas.drawColor(Color.WHITE);	//ªÊ÷∆±≥æ∞
		paint.setColor(Color.MAGENTA);
		canvas.drawText(Constant.WINNTEXT, Constant.WINTEXTLOCX, Constant.WINTEXTLOCY, paint);
		
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
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


