package dse;

import game.GameModel;

import java.util.concurrent.BlockingQueue;

import constant.Constant;
/**
 * 
 * @author XiaoGeng
 * 该类专门用来处理receiveQueue队列中的数据，当队列为空时，阻塞。
 */
public class ReceiveQueueThread extends Thread{
	
	int receiveCount = 0;
	DataStreamEngine dse;
	BlockingQueue<String> receiveQueue;
	public ReceiveQueueThread(DataStreamEngine dse){
		this.dse = dse;
		receiveQueue = dse.receiveQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				
				String dataString = receiveQueue.take();
				GameModel gameModel = dse.getGameModel();
				gameModel.updateGameView(dataString);
				
				receiveCount++;
				if(Constant.isDebug)
					System.out.println("receiveQueueThread: receiveCount = "+receiveCount);
				synchronized (Constant.MUTEX_OBJECT) {
					Constant.MUTEX_OBJECT.notify();
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			/*
			String dataString = receiveQueue.poll();
			int size = receiveQueue.size();
			if(size!=0)
				System.out.println("reciveQueue thread : "+size);
			while(dataString == null){
				dataString = receiveQueue.poll();
			}*/
			
		}
	}
	
}
