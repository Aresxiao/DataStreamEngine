package com.example.datastreamengine;

import com.example.InputPkg.DataType;

public class NetworkDataReceiveThread extends Thread{
	APNetwork network;
	
	GameView gameView;
	private boolean flag;
	private int sleepSpan=3;
	
	public NetworkDataReceiveThread(GameView gameView){
		this.gameView = gameView;
		network = (APNetwork) gameView.activity.getNetwork();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while((!network.getStatus())&&flag){
			
		}
		while(flag){
			DataType dt=new DataType();
			
			network.receiveData(dt);
			gameView.alBalls.get(Constant.CLIENT_BALL_ID).setSpeed(dt.x, dt.y);
			try {
				Thread.sleep(sleepSpan);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
}
