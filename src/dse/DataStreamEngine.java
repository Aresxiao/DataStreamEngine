package dse;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.APNetwork;
import network.Message;
import network.OverlayNetwork;

/**
 * 
 * @description This class is the main part of engine.It can do some processing.
 * @author GengXiao
 * 
 */
public enum DataStreamEngine {
	
	INSTANCE;
	
	BlockingQueue<String> sensorQueue = new LinkedBlockingQueue<String>();
	BlockingQueue<Message> receiveQueue = new LinkedBlockingQueue<Message>();
	BlockingQueue<Message> sendQueue = new LinkedBlockingQueue<Message>();
	
	OverlayNetwork overlayNetwork = null;
	
	SendQueueThread sendQueueThread;
	ReceiveQueueThread receiveQueueThread;
	SensorQueueThread sensorQueueThread;
	
	
	/**
	 * @param overlayNetwork 
	 */
	public void setOverlayNetwork(OverlayNetwork overlayNetwork){
		
		this.overlayNetwork = overlayNetwork;
	}
	
	/**
	 * @description ����һ���߳�ר�Ŵ���sensor����
	 */
	public void startSensorThread(){
		
		sensorQueue.clear();
		sensorQueueThread = new SensorQueueThread();
		sensorQueueThread.start();
	}
	
	/**
	 * @description �����߳�ר�Ŵ��������ϵ��շ�����
	 */
	public void startNetworkThread(){
		setOverlayNetwork(APNetwork.INSTANCE);
		receiveQueueThread = new ReceiveQueueThread(this);
		sendQueueThread = new SendQueueThread(this);
		receiveQueueThread.start();
		sendQueue.clear();
		sendQueueThread.start();
	}
	
	public OverlayNetwork getOverlayNetwork(){
		return overlayNetwork;
	}
	
	public void addReceiveQueue(Message msg){
		receiveQueue.offer(msg);
	}
	
	public void addSendQueue(Message msg){
		
		sendQueue.offer(msg);
	}
	
	public void addSensorQueue(String data){
		sensorQueue.offer(data);
	}
}


