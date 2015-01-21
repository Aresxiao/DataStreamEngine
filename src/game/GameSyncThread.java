package game;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import constant.Constant;
import dse.DSEInterface;

public class GameSyncThread extends Thread{
	
	GameModel gameModel;
	private boolean flag = true;
	private int sleepSpan;
	BlockingQueue<String> queue;
	int synCount;
	
	public GameSyncThread(GameModel gameModel){
		queue = new LinkedBlockingQueue<String>();
		this.gameModel = gameModel;
		sleepSpan = 7;
		synCount = 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			DSEInterface dse = gameModel.dse;
			if(dse != null){
				
				if(queue.isEmpty()){
					String data = gameModel.getBallState(Constant.LOCAL_BALL_ID);
					data = 2+","+data;
					dse.updateDSEState(3, data);
				}
				else {
					try {
						String data = queue.take();
						dse.updateDSEState(3, data);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				synCount++;
				if(Constant.isDebug)
					System.out.println("gameSyncThread : synCount = "+synCount);
				
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
	
	public void addQueue(String data){
		queue.offer(data);
	}
}
