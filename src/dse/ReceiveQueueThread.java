package dse;

import java.util.concurrent.BlockingQueue;

import network.Message;

import constant.Constant;
/**
 * @author XiaoGeng
 * ����ר����������receiveQueue�����е����ݣ�������Ϊ��ʱ��������
 */
public class ReceiveQueueThread extends Thread{
	
	int receiveCount = 0;
	DataStreamEngine dse;
	BlockingQueue<Message> receiveQueue;
	public ReceiveQueueThread(DataStreamEngine dse){
		this.dse = dse;
		receiveQueue = dse.receiveQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Message msg = receiveQueue.take();
				
				receiveCount++;
				if(Constant.isDebug)
					System.out.println("receiveQueueThread: receiveCount = "+receiveCount);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
		}
	}
	
}
