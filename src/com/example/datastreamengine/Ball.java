package com.example.datastreamengine;

public class Ball {
	
	boolean isGoalBall;
	private float x;
	private float y;
	private float radius;
	
	static final float vMax = Constant.V_MAX;//��������ٶȣ������������ٶȲ��ܳ�������ٶȡ�
	
	//�������������ϵ��ٶȡ�
	float vx;
	float vy;
	boolean InHoleflag=false;//���Ƿ�����ı�־
	
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
