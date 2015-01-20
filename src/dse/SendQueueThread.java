package dse;

import java.util.concurrent.BlockingQueue;

import network.OverlayNetwork;

import constant.Constant;

/**
 * @author XiaoGeng
 * ��������DSE��ר����������sendQueue�����е����ݣ���Ϊ��ʱ���߳�������
 */
public class SendQueueThread extends Thread {
	
	private boolean flag;
	DataStreamEngine dse;
	BlockingQueue<String> sendQueue;
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
				String data = sendQueue.take();
				OverlayNetwork overlayNetwork = dse.getOverlayNetwork();
				overlayNetwork.sendData(data);
				sendCount++;
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(Constant.isDebug)
				System.out.println("sendQueueThread: sendCount = "+sendCount);
			synchronized (Constant.MUTEX_OBJECT) {
				try {
					Constant.MUTEX_OBJECT.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
}
