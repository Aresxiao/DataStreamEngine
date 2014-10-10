package com.example.datastreamengine;

import java.util.ArrayList;

public class BallGoThread extends Thread {
	
	GameView gameView;
	private boolean flag = true;
	AccelerateSensor accelerateSensor;
	//ArrayList<Ball> 
	
	private int sleepSpan=7;
	public BallGoThread(GameView gameView){
		this.gameView = gameView;
		accelerateSensor = gameView.activity.accelerateSensor;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(flag){
			//�����е�����
			for(Ball b:gameView.alBalls){
				//float[] values=accelerateSensor.getValues();
				//System.out.println("--x="+values[0]+"y="+values[1]+"z="+values[2]+"--");
				//b.setSpeed(values[0], values[1]);
				b.go();
				if(b.isGoalBall()){
					if(b.InHoleflag){
						
						b.stopBall();
						flag=false;
						gameView.overGame();
					}
				}
			}
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
