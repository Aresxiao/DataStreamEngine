package dse;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.APNetwork;
import network.OverlayNetwork;

/**
 * 
 * This class is the main part of engine.It can do some processing.
 * @author GengXiao
 * 
 */
public enum DataStreamEngine {
	
	INSTANCE;
	
	BlockingQueue<String> sensorQueue = new LinkedBlockingQueue<String>();
	BlockingQueue<String> receiveQueue = new LinkedBlockingQueue<String>();
	BlockingQueue<String> sendQueue = new LinkedBlockingQueue<String>();
	
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
	 * 启动一个线程专门处理sensor数据
	 */
	public void startSensorThread(){
		sensorQueueThread = new SensorQueueThread();
		sensorQueueThread.start();
	}
	
	/**
	 * 启动线程专门处理网络上的收发数据
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
	
	public void addReceiveQueue(String data){
		receiveQueue.offer(data);
	}
	
	public void addSendQueue(String data){
		
		sendQueue.offer(data);
	}
	
	public void addSensorQueue(String data){
		sensorQueue.offer(data);
	}
}


