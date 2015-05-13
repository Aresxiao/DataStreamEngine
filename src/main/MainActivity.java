package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import buffer.BufferManager;

import com.example.datastreamengine.R;


import game.GameModel;
import game.GameView;
import game.GameWinNView;
import game.sharedmemory.readerwriter.RegisterControllerFactory;
import network.APNetwork;
import sensor.AccelerateSensor;
import timingservice.ADBExecutor;
import timingservice.TimingService;


import constant.Constant;
import constant.WhatMessage;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/**
 * This is Android MainActivity class. In this class, do some initial things, 
 * like network connect, set table height and width. This is our program entry.
 * 
 * @version 1.0
 * @author Ares
 * @since 2014
 * 
 */

public class MainActivity extends Activity{
	
	private volatile static MainActivity activity;
	
	private final Logger log4android = Logger.getLogger(MainActivity.class);
	private static final Executor exec = Executors.newCachedThreadPool();
	private ServerSocket server_socket = null;
	int currentView;
	
	AccelerateSensor accelerateSensor;
	
	//屏幕宽度和高度
	private int tableWidth;
	private int tableHeight;
	
	GameWinNView gameWinNView;
	GameView gameView;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case WhatMessage.GAME_VIEW:
				gotoGameView();
				break;
			case WhatMessage.OVER_GAMEWINN:
				gotoWinNView();
				break;
			case WhatMessage.OVER_GAMEWINS:
				gotoWinSView();
				break;
			default:
				break;
			}
			
		}
	};
	
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		activity = this;
		
		/**
		 * 注册电池广播
		 */
		BatteryReceiver batteryReceiver = BatteryReceiver.getInstance();
        IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
        
        /** log配置  */
        ConfigureLog4J.INSTANCE.configure();
        
        /** 时间线程 */
        //exec.execute(this.TimePollingDaemon);
        //System.out.println("Time is "+TimingService.INSTANCE.pollingTime());
        
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        /** 获取窗口管理器,获得屏幕宽和高 */
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;
        Constant.initConst(tableWidth, tableHeight);
        
        RegisterControllerFactory.INSTANCE.setRegisterController(1);
        
        GameModel.INSTANCE.initialize();
        gameView = new GameView(this);
        setContentView(gameView);
        
        log4android.debug("battery is "+batteryReceiver.getRemainBattery());
        
        accelerateSensor = new AccelerateSensor();
        BufferManager.INSTANCE.startThread();
        
		//APNetwork.INSTANCE.connect();
	}
	
	public static synchronized MainActivity INSTANCE(){
		 return activity;
	}
	
	/**
	 * @param what
	 * 发送Handler消息的方法。
	 */
    public void sendMessage(int what){
    	//Message msg1 = handler.obtainMessage(what); 
    	//handler.sendMessage(msg1);
    	//System.out.println("send msg");
    }
	
    private void gotoWinNView(){
    	if(gameWinNView==null){
    		gameWinNView = new GameWinNView(this);
    	}
    	this.setContentView(gameWinNView);
    	currentView = WhatMessage.OVER_GAMEWINN;
    }
    
    private void gotoWinSView() {
    	if(gameWinNView==null){
    		gameWinNView = new GameWinNView(this);
    	}
    	this.setContentView(gameWinNView);
    	currentView = WhatMessage.OVER_GAMEWINN;
	}
    
    private void gotoGameView(){
    	
    	this.setContentView(gameView);
    	currentView = WhatMessage.GAME_VIEW;
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
		accelerateSensor.stopSensor();
		ConfigureLog4J.INSTANCE.shutdown();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		accelerateSensor.startSensor();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		ConfigureLog4J.INSTANCE.shutdown();
	}
	
	// Daemon thread for establishing and maintaining the time polling connection
	private Runnable TimePollingDaemon = new Runnable()
	{
		public void run()
		{
			establishDeviceHostConnection();
		}
	};
	
	/**
	 * Starting as a ServerSocket. 
	 * Listen to client Socket, accept, and store it for further communication.
	 */
	private void establishDeviceHostConnection()
	{
		if (this.server_socket != null)
		{
			Log.d("com.info", "Server socket has been already created. Do not create it again.");
			return;
		}
		
		try
		{
			this.server_socket = new ServerSocket();
			this.server_socket.bind(new InetSocketAddress("localhost", ADBExecutor.ANDROID_PORT));
			
			TimingService.INSTANCE.setHostSocket(server_socket.accept());
			
			// receive (and consume) {@link AuthMsg} from PC and enable the time-polling functionality.
			TimingService.INSTANCE.receiveAuthMsg();
			
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
		
}

