package game;

import constant.Constant;
import dse.DSEInterface;

public class GameSyncThread extends Thread{
	
	GameModel gameModel;
	private boolean flag = true;
	private int sleepSpan;
	
	public GameSyncThread(GameModel gameModel){
		this.gameModel = gameModel;
		sleepSpan = 7;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			DSEInterface dse = gameModel.dse;
			if(dse != null){
				String data = gameModel.getBallState(Constant.LOCAL_BALL_ID);
				data = 2+","+data;
				dse.updateDSEState(3, data);
			}
			try {
				Thread.sleep(sleepSpan);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
}
