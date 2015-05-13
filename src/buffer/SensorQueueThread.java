package buffer;

import game.GameModel;

import java.util.concurrent.BlockingQueue;

import constant.Constant;
/**
 * @author Ares
 * 该类用来专门处理sensorQueue队列中的数据，如果队列为空，就阻塞。
 */

public class SensorQueueThread extends Thread{
	
	BlockingQueue<String> sensorQueue;
	
	public SensorQueueThread(){
		
		sensorQueue = BufferManager.INSTANCE.sensorQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				
				String dataString = sensorQueue.take();
				
				String[] v_str = dataString.split(",");
				
				GameModel.INSTANCE.onSensorChanged(Float.parseFloat(v_str[0]), Float.parseFloat(v_str[1]));
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
