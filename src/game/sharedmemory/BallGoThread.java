package game.sharedmemory;

import game.GameModel;


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
			//让所有的球走
			
			for(AbstractBall b:gameModel.getBalls()){
				b.go();
				if(b.getType() == AbstractBall.GOAL_BALL){
					if(b.isInHole()){
						b.stopBall();
						//int whichHole = b.getWhichHole();
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
