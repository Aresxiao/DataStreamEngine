package game;

import constant.Constant;


public class BallGoThread extends Thread {
	
	GameModel gameModel;
	private boolean flag = true;
	private boolean isWait = false;
	//ArrayList<Ball> 
	
	private int sleepSpan=7;
	public BallGoThread(GameModel gameModel){
		
		this.gameModel = gameModel;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(flag){
			//�����е�����
			if(isWait){
				synchronized (Constant.MUTEX_OBJECT) {
					try {
						isWait = false;
						Constant.MUTEX_OBJECT.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			for(Ball b:gameModel.ballList){
				b.go();
				if(b.isGoalBall()){
					if(b.InHoleflag){
						b.stopBall();
						int whichHole = b.getWhichHole();
						flag=false;
						gameModel.overGame();
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
