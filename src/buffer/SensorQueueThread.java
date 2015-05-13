package buffer;

import game.GameModel;

import java.util.concurrent.BlockingQueue;

import constant.Constant;
/**
 * @author Ares
 * ��������ר�Ŵ���sensorQueue�����е����ݣ��������Ϊ�գ���������
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
