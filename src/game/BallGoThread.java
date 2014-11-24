package game;

import sensor.AccelerateSensor;

public class BallGoThread extends Thread {
	
	GameView gameView;
	GameModel gameModel;
	private boolean flag = true;
	//ArrayList<Ball> 
	
	private int sleepSpan=7;
	public BallGoThread(GameView gameView,GameModel gameModel){
		this.gameView = gameView;
		this.gameModel = gameModel;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(flag){
			//让所有的球走
			for(Ball b:gameView.alBalls){
				b.go();
				if(b.isGoalBall()){
					if(b.InHoleflag){
						b.stopBall();
						flag=false;
						gameModel.overGame();
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
	
}
