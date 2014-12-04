package dse;

import game.GameModel;

import java.util.Queue;
/**
 * @author XiaoGeng
 * 该类用来专门处理sensorQueue队列中的数据，如果队列为空，就阻塞。
 */
public class SensorQueueThread extends Thread{
	DataStreamEngine dse;
	Queue<String> sensorQueue;
	
	public SensorQueueThread(DataStreamEngine dse){
		this.dse = dse;
		sensorQueue = dse.sensorQueue;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			String dataString = sensorQueue.poll();
			while(dataString == null){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dataString = sensorQueue.poll();
			}
			GameModel gameModel = dse.getGameModel();
			gameModel.updateGameView(dataString);
		}
	}
	
	
}
