package com.example.datastreamengine;

import com.example.InputPkg.DataType;

public class NetworkDataSendThread extends Thread{
	APNetwork network;
	
	GameView gameView;
	private boolean flag;
	private int sleepSpan=3;
	
	public NetworkDataSendThread(GameView gameView){
		this.gameView = gameView;
		network = (APNetwork) gameView.activity.getNetwork();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while((!network.getStatus())&&flag){
			
		}
		while(flag){
			float[] values = gameView.activity.accelerateSensor.getValues();
			//gameView.alBalls.get(2).setSpeed(values[0], values[1]);
			//DataType dt=new DataType();
			//
			String dataString = values[0]+" "+values[1];
			//dataString = System.currentTimeMillis()+"系统时间";
			network.sendData(dataString);
			try {
				Thread.sleep(sleepSpan);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
}
