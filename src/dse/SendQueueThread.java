package dse;

import java.util.concurrent.BlockingQueue;

import network.Message;
import network.OverlayNetwork;

import constant.Constant;

/**
 * @author XiaoGeng
 * 该类用是DSE中专门用来处理sendQueue队列中的数据，当为空时，线程阻塞。
 */
public class SendQueueThread extends Thread {
	
	private boolean flag;
	DataStreamEngine dse;
	BlockingQueue<Message> sendQueue;
	private int sleepSpan;
	int sendCount = 0;
	
	public SendQueueThread(DataStreamEngine dataStreamEngine){
		dse = dataStreamEngine;
		sendQueue = dataStreamEngine.sendQueue;
		flag = true;
		sleepSpan = 7;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			
			try {
				Message msg = sendQueue.take();
				OverlayNetwork overlayNetwork = dse.getOverlayNetwork();
				
				overlayNetwork.sendData(msg);
				//sendCount++;
				//if(Constant.isDebug)
					//System.out.println("sendQueueThread: sendCount = "+sendCount);
				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
}
