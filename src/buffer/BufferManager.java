package buffer;

import game.sharedmemory.communication.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;

/**
 * 
 * @description This class is the main part of engine.It can do some processing.
 * @author GengXiao
 * 
 */

public enum BufferManager {
	
	INSTANCE;
	
	private static final String TAG = BufferManager.class.getName();
	
	BlockingQueue<String> sensorQueue = new LinkedBlockingQueue<String>();
	BlockingQueue<Message> receiveQueue = new LinkedBlockingQueue<Message>();
	
	ReceiveQueueThread receiveQueueThread;
	SensorQueueThread sensorQueueThread;
	
	/**
	 * @description 启动一个线程专门处理sensor数据
	 */
	public void startThread(){
		
		sensorQueue.clear();
		receiveQueue.clear();
		sensorQueueThread = new SensorQueueThread();
		sensorQueueThread.start();
		receiveQueueThread = new ReceiveQueueThread();
		receiveQueueThread.start();
	}
	
	public void addReceiveQueue(Message msg){
		receiveQueue.offer(msg);
	}
	
	public void addSensorQueue(String data){
		sensorQueue.offer(data);
	}
}
