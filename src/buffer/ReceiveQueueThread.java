package buffer;

import game.GameModel;
import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.message.Message;

import java.util.concurrent.BlockingQueue;


import constant.Constant;
/**
 * @author XiaoGeng
 * ����ר����������receiveQueue�����е����ݣ�������Ϊ��ʱ��������
 */

public class ReceiveQueueThread extends Thread{
	
	int receiveCount = 0;
	BlockingQueue<Message> receiveQueue;
	public ReceiveQueueThread(){
		
		receiveQueue = BufferManager.INSTANCE.receiveQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Message msg = receiveQueue.take();
				MessagingService.INSTANCE.onReceive(msg);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
		}
	}
	
}
