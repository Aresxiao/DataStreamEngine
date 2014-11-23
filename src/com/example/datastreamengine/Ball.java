package com.example.datastreamengine;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Ball {
	static int id=0;
	
	int ballId;
	GameView gameView;
	boolean goalBall;
	private float x;
	private float y;
	private float radius;
	
	static float d;
	static final float vMax = Constant.V_MAX;	//定义最大速度，所有球的最大速度不能超过这个速度。
	//定义两个方向上的速度。
	float vx;
	float vy;
	boolean InHoleflag=false;//球是否进洞的标志
	
	float vAttenuation = Constant.V_ATTENUATION;
	float timeSpan = Constant.TIME_SPAN;
	float vMin = Constant.V_MIN;
	static ArrayList<Float> locationXY = new ArrayList<Float>();
	
	
	
	public Ball(boolean goal,GameView gameView,float x,float y){
		this.gameView = gameView;
		goalBall=goal;
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		if (goalBall) {
			d=Constant.GOAL_BALL_SIZE;
			radius=Constant.GOAL_BALL_SIZE/2;
		} 
		else {
			id++;
			ballId=id;
			d=Constant.PLAYER_BALL_SIZE;
			radius=Constant.PLAYER_BALL_SIZE/2;
		}
		
	}
	
	public void init(Ball goalBall){
		
	}
	
	public void drawSelf(Canvas canvas,Paint paint){
		Matrix m1 = new Matrix();
		m1.setTranslate( x+Constant.X_OFFSET,y+Constant.Y_OFFSET );
		if(goalBall){			//是目标球
			paint.reset();
			paint.setColor(Color.BLUE);
			canvas.drawCircle(x+Constant.GOAL_BALL_SIZE/2, y+Constant.GOAL_BALL_SIZE/2, radius, paint);
		}
		else{					//玩家小球
			
			paint.reset();
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth((float) 2.0);
			canvas.drawCircle(x+Constant.PLAYER_BALL_SIZE/2, y+Constant.PLAYER_BALL_SIZE/2, radius, paint);
			canvas.drawText(Integer.toString(ballId), x+Constant.PLAYER_BALL_SIZE/2, y+Constant.PLAYER_BALL_SIZE/2, paint);
		}
		
	}
	
	public boolean canGo(float tempX,float tempY){
		boolean canGoFlag=true;
		
		//对于目标球，要判断是否已经进框
		float[] center = {tempX+radius,tempY+radius};
		
		if(isGoalBall()){
			if(tempX>Constant.FRAMEA_X&&(tempX+Constant.GOAL_BALL_SIZE)<(Constant.FRAMEA_X+Constant.FRAMEA_WIDTH)&&
					tempY>Constant.FRAMEA_Y&&(tempY+Constant.GOAL_BALL_SIZE)<(Constant.FRAMEA_Y+Constant.FRAMEA_HEIGHT)){
				InHoleflag=true;
				return true;
			}
		}
		
		for(Ball b:gameView.alBalls){
			if(b!=this && CollisionUtil.collisionCalculate(new float[]{tempX,tempY}, this, b)){
				canGoFlag = false;
			}
		}
		
		if(canGoFlag==false){
			return false;
		}
		
		
		
		if(center[0]<radius||(center[0]+radius)>Constant.TABLE_WIDTH){
			vx=-vx;
			canGoFlag = false;
		}
		if(center[1]<radius||(center[1]+radius)>Constant.TABLE_HEIGHT){
			vy=-vy;
			canGoFlag=false;
		}
		
		return canGoFlag;
	}
	
	public void go(){
		if(isStoped()||Math.sqrt(vx*vx+vy*vy)<vMin){
			//vx=0;
			//vy=0;
			return ;
		}
		//System.out.println(ballId+"can go");
		float tempX = x+vx*timeSpan;	//球要去的下一个位置
		float tempY = y+vy*timeSpan;
		
		if(this.canGo(tempX, tempY)){
			vx*=vAttenuation;
			vy*=vAttenuation;
			x=tempX;
			y=tempY;
		}
		
	}
	
	public void stopBall(){
		vx=0;
		vy=0;
	}
	
	//判断球是否停止的方法
	public boolean isStoped(){
		return (vx==0 && vy==0);
	}
	
	public boolean isWin(){
		return InHoleflag;
	}
	
	//根据速度和方向改变球速度的方法
	public void changeVxy(float v,float angle)
	{
		double angrad=Math.toRadians(angle);//将角度转换成弧度
		vx=(float) (v*Math.cos(angrad));//计算x,y方向的速度
		vy=(float) (v*Math.sin(angrad));
	}
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	
	public float getVX(){
		return vx;
	}
	
	public float getVY(){
		return vy;
	}
	
	public void setSpeed(float xSpeed,float ySpeed){
		vx=vx-xSpeed*timeSpan*Constant.SENSITIVITY;
		vy=vy+ySpeed*timeSpan*Constant.SENSITIVITY;
		//System.out.println(xSpeed+" --- "+ySpeed+"   :    "+vx+" --- "+vy);
	}
	
	public float getRadius(){
		return radius;
	}
	
	public boolean isGoalBall(){
		return goalBall;
	}
	
}
