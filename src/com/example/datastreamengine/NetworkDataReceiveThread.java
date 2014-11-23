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
			
			String rcvSpeed=network.receiveData();
			if(rcvSpeed!=null){
				String[] str = rcvSpeed.split(" ");
				//if(str[0].equals("fs"))
				System.out.println(str[0]+"  "+str[1]);
				float x=Float.parseFloat(str[0]);
				float y = Float.parseFloat(str[1]);
				gameView.alBalls.get(Constant.CLIENT_BALL_ID).setSpeed(x,y);
			}
			
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
