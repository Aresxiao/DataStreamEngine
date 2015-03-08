package dse;

import game.GameModel;

import java.util.concurrent.BlockingQueue;

import constant.Constant;
/**
 * @author XiaoGeng
 * ��������ר�Ŵ���sensorQueue�����е����ݣ��������Ϊ�գ���������
 */
public class SensorQueueThread extends Thread{
	DataStreamEngine dse;
	BlockingQueue<String> sensorQueue;
	
	public SensorQueueThread(DataStreamEngine dse){
		this.dse = dse;
		sensorQueue = dse.sensorQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				
				int size = sensorQueue.size();
				if(size!=0){
					//System.out.println("sensorQueue size = "+size+"<<<<<<<<<<<<<<<<<<<<<");
				}
				String dataString = sensorQueue.take();
				
				dataString = 1+","+Constant.LOCAL_BALL_ID+","+dataString;
				GameModel gameModel = dse.getGameModel();
				gameModel.updateGameView(dataString);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
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
			*/
		}
	}
	
}
