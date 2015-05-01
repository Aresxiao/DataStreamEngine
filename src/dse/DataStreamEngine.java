package dse;


import game.GameModel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;

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
	
	private static final String TAG = DataStreamEngine.class.getName();
	
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
	 * @description 启动一个线程专门处理sensor数据
	 */
	public void startSensorThread(){
		
		sensorQueue.clear();
		sensorQueueThread = new SensorQueueThread();
		sensorQueueThread.start();
	}
	
	/**
	 * @description 启动线程专门处理网络上的收发数据
	 */
	public void startNetworkThread(){
		setOverlayNetwork(APNetwork.INSTANCE);
		System.out.println(TAG + " start network thread");
		receiveQueueThread = new ReceiveQueueThread();
		sendQueueThread = new SendQueueThread();
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


