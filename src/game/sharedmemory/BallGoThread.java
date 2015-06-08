package game.sharedmemory;

import android.util.Log;
import game.GameModel;


public class BallGoThread extends Thread {
	
	private static final String TAG = BallGoThread.class.getName();
	
	private boolean flag = true;
	private boolean isWait = false;
	//ArrayList<Ball> 
	
	private int sleepSpan = 7;
	public BallGoThread(){
		//Log.i(TAG, "new a BallGoThread");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.i(TAG, "ballGoThread is running");
		while(flag){
			//让所有的球走
			//Log.d(TAG, "it is running");
			for(AbstractBall b:GameModel.INSTANCE.getBalls()){
				//Log.i(TAG, "b.id = " + b.ballId);
				b.go();
				if(b.getType() == AbstractBall.GOAL_BALL){
					if(b.isInHole()){
						b.stopBall();
						//int whichHole = b.getWhichHole();
						flag=false;
						GameModel.INSTANCE.overGame();
						//gameModel.overGame(whichHole);
					}
				}
			}
			try {
				Thread.sleep(sleepSpan);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	public void setIsWait(boolean isWait){
		this.isWait = isWait;
	}
	
	public boolean getIsWait(){
		return this.isWait;
	}
}
