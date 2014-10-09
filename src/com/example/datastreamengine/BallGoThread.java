package com.example.datastreamengine;

import java.util.ArrayList;

public class BallGoThread extends Thread {
	
	GameView gameView;
	private boolean flag = true;
	
	//ArrayList<Ball> 
	
	private int sleepSpan=7;
	public BallGoThread(GameView gameView){
		this.gameView = gameView;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(flag){
			//让所有的球走
			for(Ball b:gameView.alBalls){
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
