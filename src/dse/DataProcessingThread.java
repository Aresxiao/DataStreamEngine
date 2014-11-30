package dse;

import game.GameModel;
import constant.Constant;

public class DataProcessingThread extends Thread {
	
	private boolean flag;
	DataStreamEngine dse;
	private int sleepSpan ;
	private int msgCount;
	
	
	public DataProcessingThread(DataStreamEngine dataStreamEngine){
		dse = dataStreamEngine;
		flag = true;
		sleepSpan = 7;
		msgCount=0;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			GameModel gameModel = dse.getGameModel();
			String data = gameModel.getBallState(Constant.LOCAL_BALL_ID);
			data = 2+","+data;
			dse.dataProcessFromGame(1, data);
			
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
