package com.example.datastreamengine;

public class Ball {
	
	boolean isGoalBall;
	private float x;
	private float y;
	private float radius;
	
	static final float vMax = Constant.V_MAX;//定义最大速度，所有球的最大速度不能超过这个速度。
	
	//定义两个方向上的速度。
	float vx;
	float vy;
	boolean InHoleflag=false;//球是否进洞的标志
	
	public Ball(boolean goal){
		isGoalBall=goal;
		if (isGoalBall) {
			radius=Constant.GOAL_BALL_SIZE/2;
		} 
		else {
			radius=Constant.PLAYER_BALL_SIZE/2;
		}
	}
	
	public void init(){
		
	}
	
	public void drawSelf(){
		
	}
	
	public float getX(){
		return x;
		
	}
	public float getY(){
		return y;
	}
}
