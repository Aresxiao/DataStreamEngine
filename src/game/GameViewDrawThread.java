package game;

import constant.Constant;
import android.view.SurfaceHolder;


public class GameViewDrawThread extends Thread{

	private boolean flag = true;
	private int sleepSpan = 10;
	GameView gameView;
	SurfaceHolder surfaceHolder;
	
	public GameViewDrawThread(GameView gameView){
		this.gameView = gameView;
		this.surfaceHolder = gameView.getHolder();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(this.flag){
			
			gameView.repaint();
			
			try {
				Thread.sleep(sleepSpan);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
