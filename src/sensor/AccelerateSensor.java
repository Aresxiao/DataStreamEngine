package sensor;

import dse.DSEInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerateSensor implements SensorEventListener{
	
	float x,y,z;
	DSEInterface dseInterface;
	public AccelerateSensor(DSEInterface dse){
		x=0;
		y=0;
		z=0;
		this.dseInterface = dse;
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float[] values = event.values;
		x=values[0];
		y=values[1];
		z=values[2];
		String dataString = x+","+y+","+z;
		//System.out.println(dataString+"-----------------");
		dseInterface.updateDSEState(2, dataString);
	}
	
	
	/**
	 * 此方法用来设置传感器的频率。
	 * @param type
	 * ={1,2,3,4}，type只有这四种取值，1对应于传感器中normal的延迟，2对应于UI的延迟，3对应于Game的延迟，4对应于fastest延迟。
	 * 如果取其他值，默认采用的是Game延迟。
	 */
	public void setFrequecy(SensorManager sensorManager,Sensor sensor, int type){
		switch (type) {
		case 1:
			sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL);
			break;
		case 2:sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
			break;
		case 3:sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
			break;
		case 4:sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
			break;
		default:
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
			break;
		}
	}
}
