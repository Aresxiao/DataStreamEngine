package dse;

import game.GameModel;

import java.util.concurrent.BlockingQueue;

import network.Message;

import constant.Constant;
/**
 * @author XiaoGeng
 * ����ר����������receiveQueue�����е����ݣ�������Ϊ��ʱ��������
 */
public class ReceiveQueueThread extends Thread{
	
	int receiveCount = 0;
	BlockingQueue<Message> receiveQueue;
	public ReceiveQueueThread(){
		
		receiveQueue = DataStreamEngine.INSTANCE.receiveQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Message msg = receiveQueue.take();
				
				GameModel.INSTANCE.onReceive(msg);
				/*
				receiveCount++;
				if(Constant.isDebug)
					System.out.println("receiveQueueThread: receiveCount = " + receiveCount);
				*/
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
		}
	}
	
}
