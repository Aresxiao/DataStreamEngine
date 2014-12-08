package main;

import game.GameModel;
import game.GameView;
import network.APNetwork;
import sensor.AccelerateSensor;

import com.example.datastreamengine.R;

import constant.Constant;
import dse.DSEInterface;
import dse.DataStreamEngine;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/**
 * This is Android MainActivity class. In this class, do some initial things, 
 * like network connect, set table height and width.
 * 
 * @version 1.0
 * @author GengXiao
 * @since 2014
 */

public class MainActivity extends Activity{

	SensorManager sensorManager;
	Sensor mAccelerometer;
	
	AccelerateSensor accelerateSensor;
	/** */
	APNetwork network;
	DataStreamEngine dse;
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
        
        GameModel gameModel = new GameModel(gameView);
        
        dse = new DataStreamEngine(gameModel);
        
        accelerateSensor = new AccelerateSensor(dse);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//sensorManager.registerListener(accelerateSensor, mAccelerometer,SensorManager.SENSOR_DELAY_GAME);
		accelerateSensor.setFrequecy(sensorManager, mAccelerometer, 3); //maybe this will have some problem
		dse.startSensorThread();
		
		network = new APNetwork(dse);
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("ready to connect");
				network.connect();
				System.out.println("Thread start");
				dse.setOverlayNetwork(network);
				dse.startNetworkThread();
			}
		}.start();
	}
	
	/**
	 * @return network
	 */
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
		//sensorManager.registerListener(accelerateSensor, mAccelerometer,SensorManager.SENSOR_DELAY_GAME);
		accelerateSensor.setFrequecy(sensorManager, mAccelerometer, 3);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensorManager.unregisterListener(accelerateSensor);
	}
	
	
}

