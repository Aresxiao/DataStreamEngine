package game.sharedmemory;

import game.GameModel;
import game.Obstacle;
import java.util.ArrayList;


import constant.Constant;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.widget.Toast;

@Deprecated
public class Ball {
	static int id=0;
	
	int whichHole;
	int ballId;
	
	//GameView gameView;
	GameModel gameModel;
	//SharedMemoryController controller;
	
	boolean goalBall;
	private float x;		// x��y��С���ڻ��Ƶ�ʱ�����Ͻǵ�����λ��
	private float y;
	private float radius;
	
	static float d;
	static final float vMax = Constant.V_MAX;	//��������ٶȣ������������ٶȲ��ܳ�������ٶȡ�
	//�������������ϵ��ٶȡ�
	float vx;
	float vy;
	boolean InHoleflag=false;//���Ƿ�����ı�־
	
	float vAttenuation = Constant.V_ATTENUATION;
	float timeSpan = Constant.TIME_SPAN;
	float vMin = Constant.V_MIN;
	static ArrayList<Double> locationXY = new ArrayList<Double>();
	
	public Ball(boolean goal,float x,float y, GameModel gameModel){
		whichHole = -1;
		//this.gameView = gameView;
		this.gameModel = gameModel;
		goalBall=goal;
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		if (goalBall) {
			ballId = 0;
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
	
	public void setGameModel(GameModel gameModel){
		this.gameModel = gameModel;
	}
	
	public void drawSelf(Canvas canvas,Paint paint){
		Matrix m1 = new Matrix();
		m1.setTranslate( x+Constant.X_OFFSET,y+Constant.Y_OFFSET );
		if(goalBall){			//��Ŀ����
			paint.reset();
			paint.setColor(Color.BLUE);
			canvas.drawCircle(x+Constant.GOAL_BALL_SIZE/2, y+Constant.GOAL_BALL_SIZE/2, radius, paint);
		}
		else{					//���С��
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
		
		//����Ŀ����Ҫ�ж��Ƿ��Ѿ�����
		float[] center = {tempX+radius,tempY+radius};
		
		if(isGoalBall()){
			if(tempX>Constant.NFRAMEA_X&&(tempX+Constant.GOAL_BALL_SIZE)<(Constant.NFRAMEA_X+Constant.NFRAMEA_WIDTH)&&
					tempY>Constant.NFRAMEA_Y&&(tempY+Constant.GOAL_BALL_SIZE)<(Constant.NFRAMEA_Y+Constant.NFRAMEA_HEIGHT)){
				InHoleflag=true;
				whichHole = 1;
				return true;
			}
			if(tempX>Constant.SFRAMEA_X&&(tempX+Constant.GOAL_BALL_SIZE)<(Constant.SFRAMEA_X+Constant.SFRAMEA_WIDTH)&&
					tempY>Constant.SFRAMEA_Y&&(tempY+Constant.GOAL_BALL_SIZE)<(Constant.SFRAMEA_Y+Constant.SFRAMEA_HEIGHT)){
				InHoleflag=true;
				whichHole = 2;
				return true;
			}
		}
		/*
		for(int i = 0;i < controller.getBallAmount();i++){
			Ball b = controller.read(i);
			if(b != this && controller.collisionCalculate(new float[]{tempX, tempY},this, b))
				canGoFlag = false;
		}
		*/
		
		//�жϺ�����С������ײ
		/*
		for(Ball b:gameModel.ballList){
			if(b!=this && CollisionUtil.collisionCalculate(new float[]{tempX,tempY}, this, b)){
				canGoFlag = false;
				if((b.ballId+this.ballId) == Constant.LOCAL_BALL_ID){
					gameModel.pushState(new int[]{b.ballId,this.ballId});
				}
			}
		}
		
		
		ArrayList<Obstacle> obstacles = gameModel.table.getObstacles();
		for(Obstacle obstacle: obstacles){
			
			float[][] p = obstacle.getFourCornerLocation();		//���ȼ�����ϰ���ǵ���ײ��
			for(int i = 0;i < p.length;i++){
				if(CollisionUtil.calcuDisSquare(p[i], center)<radius*radius){
					vx = -vx;
					vy = -vy;
					canGoFlag = false;
					//collisionWithCornerFlag = true;
					break;
				}
			}
			if(!canGoFlag)
				break;
			
			if(CollisionUtil.collisionCalculate(obstacle, center, this)){
				canGoFlag = false;
				return canGoFlag;
			}
		}
		*/
		
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
		float tempX = x+vx*timeSpan;	//��Ҫȥ����һ��λ��
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
	
	//�ж����Ƿ�ֹͣ�ķ���
	public boolean isStoped(){
		return (vx==0 && vy==0);
	}
	
	public boolean isWin(){
		return InHoleflag;
	}
	
	//�����ٶȺͷ���ı����ٶȵķ���
	public void changeVxy(float v,float angle)
	{
		double angrad=Math.toRadians(angle);//���Ƕ�ת���ɻ���
		vx=(float) (v*Math.cos(angrad));//����x,y������ٶ�
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
	
	public float getVMIN(){
		return vMin;
	}
	
	public void setBallSpeedByAccelerate(float x_Accelerate,float y_Accelerate){
		
		vx=vx-x_Accelerate*timeSpan*Constant.SENSITIVITY;
		vy=vy+y_Accelerate*timeSpan*Constant.SENSITIVITY;
		//System.out.println(xSpeed+" --- "+ySpeed+"   :    "+vx+" --- "+vy);
	}
	
	public void setBallSpeedBySpeed(float x_Speed,float y_Speed){
		vx = x_Speed;
		vy = y_Speed;
	}
	
	public void setBallLocation(float x_Loc,float y_Loc){
		x = x_Loc;
		y = y_Loc;
	}
	
	public float getRadius(){
		return radius;
	}
	
	public boolean isGoalBall(){
		return goalBall;
	}
	
	public float[] getCenterLocation(){
		float[] center = {x+radius,y+radius};
		return center;
	}
	
	public int getWhichHole(){
		return whichHole;
	}
	
	public static float getD(){
		return d;
	}
	
	public int getBallId(){
		return ballId;
	}
}
