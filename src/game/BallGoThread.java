package game;


public class BallGoThread extends Thread {
	
	GameModel gameModel;
	private boolean flag = true;
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
	
}
