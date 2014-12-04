package dse;

import game.GameModel;

import java.util.Queue;
/**
 * 
 * @author XiaoGeng
 * 该类专门用来处理receiveQueue队列中的数据，当队列为空时，阻塞。
 */
public class ReceiveQueueThread extends Thread{
	
	DataStreamEngine dse;
	Queue<String> receiveQueue;
	public ReceiveQueueThread(DataStreamEngine dse){
		this.dse = dse;
		receiveQueue = dse.receiveQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			String dataString = receiveQueue.poll();
			while(dataString==null){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dataString = receiveQueue.poll();
			}
			GameModel gameModel = dse.getGameModel();
			gameModel.updateGameView(dataString);
		}
	}
	
}
