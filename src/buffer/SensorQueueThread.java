package buffer;

import game.GameModel;

import java.util.concurrent.BlockingQueue;

import android.util.Log;

import constant.Constant;
/**
 * @author Ares
 * 该类用来专门处理sensorQueue队列中的数据，如果队列为空，就阻塞。
 */

public class SensorQueueThread extends Thread{
	
	private static String TAG = SensorQueueThread.class.getName();
	BlockingQueue<String> sensorQueue;
	
	public SensorQueueThread(){
		
		sensorQueue = BufferManager.INSTANCE.sensorQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				//Log.i(TAG, "SensorQueueThread is started");
				String dataString = sensorQueue.take();
				
				String[] v_str = dataString.split(",");
				//Log.i(TAG, "get data "+dataString);
				GameModel.INSTANCE.onSensorChanged(Float.parseFloat(v_str[0]), Float.parseFloat(v_str[1]));
				//Log.i(TAG, "v_str[0] = " + v_str[0] + ",v_str[1] = " + v_str[1]);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
