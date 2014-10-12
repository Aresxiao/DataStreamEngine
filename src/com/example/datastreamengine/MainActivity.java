package com.example.datastreamengine;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/*
 * @author GengXiao
 * 
 */

public class MainActivity extends Activity{

	SensorManager sensorManager;
	Sensor mAccelerometer;
	
	AccelerateSensor accelerateSensor;
	APNetwork network;
	
	//屏幕宽度和高度
	private int tableWidth;
	private int tableHeight;
	
	
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        // 获取窗口管理器,获得屏幕宽和高
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;
        
        Constant.initConst(tableWidth, tableHeight);
        
        final GameView gameView = new GameView(this);
        setContentView(gameView);
        
        accelerateSensor = new AccelerateSensor();
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(accelerateSensor, mAccelerometer,SensorManager.SENSOR_DELAY_GAME);
		
		
		network = new APNetwork();
		new Thread(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Thread start");
				network.connect();
			}
		}.start();
		
	}
	
	public APNetwork getNetwork(){
		return network;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sensorManager.unregisterListener(accelerateSensor);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		sensorManager.registerListener(accelerateSensor, mAccelerometer,SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensorManager.unregisterListener(accelerateSensor);
	}
	
	
}

