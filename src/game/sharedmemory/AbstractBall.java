package game.sharedmemory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.GameModel;
import constant.Constant;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * @author Gengxiao
 * @since April 1,2015
 * @description AbstractBall have the generic attribute of ball.
 */

public abstract class AbstractBall {
	
	private static final String TAG = AbstractBall.class.getName();
	
	GameModel gameModel;
	
	public static final int GOAL_BALL = 1;
	public static final int PLAYER_BALL = 2;
	
	static int id = 0;
	protected int ballId;
	
	protected int type;
	
	private float vAttenuation = Constant.V_ATTENUATION;
	private float timeSpan = Constant.TIME_SPAN;
	private float vMin = Constant.V_MIN;
	
	boolean InHoleflag=false;//球是否进洞的标志
	int whichHole;
	
	Map<Key, Value> ballStateMap;
	
	public AbstractBall (int type){
		this.type = type;
		ballStateMap = new ConcurrentHashMap<Key, Value>();
		ballId = id;
		id++;
	}
	
	public int getType(){
		return this.type;
	}
	
	protected int getBallId(){
		return this.ballId;
	}
	
	public float[] calBallSpeedByAccelerate(float x_Accelerate,float y_Accelerate){
		
		
		float vx = ballStateMap.get(new Key("vx")).getVal();
		float vy = ballStateMap.get(new Key("vy")).getVal();
		
		vx=vx-x_Accelerate*timeSpan*Constant.SENSITIVITY;
		vy=vy+y_Accelerate*timeSpan*Constant.SENSITIVITY;
		
		return new float[]{vx,vy};
	}
	
	/*
	protected void setBallSpeedBySpeed(float vx,float vy){
		this.vx = vx;
		this.vy = vy;
	}
	
	protected void setBallLocation(float x_Loc,float y_Loc){
		this.x = x_Loc;
		this.y = y_Loc;
	}
	
	public void stopBall(){
		this.vx = 0;
		this.vy = 0;
	}
	*/
	public boolean isStoped(){
		
		return (ballStateMap.get(new Key("vx")).getVal() == 0 && ballStateMap.get(new Key("vy")).getVal() == 0);
		
	}
	
	public void stopBall(){
		this.write(new Key("vx"), new Value(0));
		this.write(new Key("vy"), new Value(0));
	}
	
	public boolean isInHole(){
		return InHoleflag;
	}
	
	public float getVMin(){
		return this.vMin;
	}
	
	public void go(){
		
		float vx = this.read(new Key("vx")).getVal();
		float vy = this.read(new Key("vy")).getVal();
		
		float locx = this.read(new Key("locx")).getVal();
		float locy = this.read(new Key("locy")).getVal();
		
		if(isStoped()||Math.sqrt(vx*vx+vy*vy)<vMin){
			
			return ;
		}
		// System.out.println(ballId+"can go");
		// Log.d(TAG, ballId+":vx = "+vx+",vy = "+vy+",locx = "+locx+",locy = "+locy);
		float tempX = locx+vx*timeSpan;	//球要去的下一个位置
		float tempY = locy+vy*timeSpan;
		
		
		if(this.canGo(tempX, tempY)){
			vx*=vAttenuation;
			vy*=vAttenuation;
			this.write(new Key("vx"), new Value(vx));
			this.write(new Key("vy"), new Value(vy));
			this.write(new Key("locx"), new Value(tempX));
			this.write(new Key("locy"), new Value(tempY));
		}
	}
	
	public abstract void drawSelf(Canvas canvas,Paint paint);
	
	public abstract Value read(Key key);
	
	public abstract void write(Key key, Value value);
	
	public boolean canGo(float tempX,float tempY){
		boolean canGoFlag=true;
		
		/**  对于目标球，要判断是否已经进框    */
		float[] center = {tempX+ballStateMap.get(new Key("radius")).getVal(),tempY+ballStateMap.get(new Key("radius")).getVal()};
		
		if(this.type == AbstractBall.GOAL_BALL){
			if(tempX>Constant.NFRAMEA_X&&(tempX+Constant.GOAL_BALL_SIZE)<(Constant.NFRAMEA_X+Constant.NFRAMEA_WIDTH)&&
					tempY>Constant.NFRAMEA_Y&&(tempY+Constant.GOAL_BALL_SIZE)<(Constant.NFRAMEA_Y+Constant.NFRAMEA_HEIGHT)){
				InHoleflag = true;
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
		
		List<AbstractBall> balls = gameModel.getBalls();
		for(AbstractBall b:balls){
			if(b!=this && CollisionUtil.collisionCalculate(new float[]{tempX,tempY}, this, b)){
				canGoFlag = false;
				/*
				if((b.ballId+this.ballId) == Constant.LOCAL_BALL_ID){
					gameModel.pushState(new int[]{b.ballId,this.ballId});
				}
				*/
			}
		}
		
		if(canGoFlag == false){
			return false;
		}
		
		if(center[0] < ballStateMap.get(new Key("radius")).getVal()||(center[0]+ballStateMap.get(new Key("radius")).getVal())>Constant.TABLE_WIDTH){
			float vx = ballStateMap.get(new Key("vx")).getVal();
			
			this.write(new Key("vx"), new Value(-vx));
			//ballStateMap.put(new Key("vx"), new Value(-vx));
			canGoFlag = false;
		}
		if(center[1] < ballStateMap.get(new Key("radius")).getVal()||(center[1]+ballStateMap.get(new Key("radius")).getVal())>Constant.TABLE_HEIGHT){
			float vy = ballStateMap.get(new Key("vy")).getVal();
			
			this.write(new Key("vy"), new Value(-vy));
			//ballStateMap.put(new Key("vy"), new Value(-vy));
			canGoFlag=false;
		}
		
		return canGoFlag;
	}
	
}
