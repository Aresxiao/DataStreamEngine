package dse;

import game.GameModel;

import java.util.Queue;

import constant.Constant;
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
			int size = sensorQueue.size();
			if(size!=0){
				System.out.println("sensorQueue size = "+size);
			}
			while(dataString == null){
				dataString = sensorQueue.poll();
				size = sensorQueue.size();
				
			}
			dataString = 1+","+Constant.LOCAL_BALL_ID+","+dataString;
			GameModel gameModel = dse.getGameModel();
			gameModel.updateGameView(dataString);
		}
	}
	
	
}
