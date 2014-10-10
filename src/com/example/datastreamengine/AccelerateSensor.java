package com.example.datastreamengine;

import android.R.integer;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerateSensor implements SensorEventListener{
	
	float x,y,z;
	
	public AccelerateSensor(){
		
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
		//System.out.println("--x="+x+"y="+y+"z="+z+"--");
	}
	
	public float[] getValues(){
		float[] values = {x,y,z};
		return values;
	}
	
}
