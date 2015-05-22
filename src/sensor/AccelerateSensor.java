package sensor;

import buffer.BufferManager;
import game.GameModel;
import main.MainActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
/**
 * @author GengXiao
 * @version 1.0
 *  This is sensor class, to manage sensor.
 */
public class AccelerateSensor implements SensorEventListener{
	
	private static final String TAG = AccelerateSensor.class.getName();
	private MainActivity activity;
	float x,y;
	SensorManager sensorManager;
	public AccelerateSensor(MainActivity activity){
		x=0;
		y=0;
		this.activity = activity;  
		sensorManager = (SensorManager) this.activity.getSystemService(Context.SENSOR_SERVICE);
		startSensor();
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float[] values = event.values;
		x=values[0];
		y=values[1];
		
		String dataString = x+","+y;
		BufferManager.INSTANCE.addSensorQueue(dataString);
		//System.out.println(dataString+"-----------------");
	}
	
	public void startSensor(){
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void stopSensor(){
		sensorManager.unregisterListener(this);
	}
	
}
