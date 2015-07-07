package game.sharedmemory;

import java.util.List;
import game.GameModel;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.Version;
import game.sharedmemory.data.VersionValue;
import game.sharedmemory.data.kvstore.KVStoreInMemory;
import game.sharedmemory.readerwriter.RegisterControllerFactory;
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
	protected int ballId; // 小球编号
	
	protected final float initLocx;
	protected final float initLocy;
	
	protected int type;
	
	private float vAttenuation = Constant.V_ATTENUATION;
	private float timeSpan = Constant.TIME_SPAN;
	private float vMin = Constant.V_MIN;
	
	boolean InHoleflag=false;//球是否进洞的标志
	int whichHole;
	
	protected float radius;
	protected float d;
	
	public AbstractBall (int type,float x, float y){
		this.type = type;
		ballId = id;
		id++;
		initLocx = x;
		initLocy = y;
	}
	
	public int getType(){
		return this.type;
	}
	
	public int getBallId(){
		return this.ballId;
	}
	
	public float[] calBallSpeedByAccelerate(float x_Accelerate,float y_Accelerate){
		
		float[] v = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballId)).getValue().getV();
		
		v[0] = v[0] - x_Accelerate * timeSpan*Constant.SENSITIVITY;
		v[1] = v[1] + y_Accelerate * timeSpan*Constant.SENSITIVITY;
		
		return v;
	}
	
	/** 恢复初始状态 */
	public void resetState(){
		
		Key key = new Key(ballId);
		Version version = new Version(0);
		Value value = new Value(initLocx, initLocy);
		VersionValue versionValue = new VersionValue(version, value);
		KVStoreInMemory.INSTANCE.put(key, versionValue);
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
	public float getD(){
		return this.d;
	}
	
	public float getRadius(){
		
		return this.radius;
	}
	
	public boolean isStoped(){
		
		float[] v = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballId)).getValue().getV();
		
		return (v[0] == 0 && v[1] == 0);
	}
	
	public void stopBall(){
		Value value = RegisterControllerFactory.INSTANCE.getRegisterController().read(new Key(ballId)).getValue();
		value.setV(0, 0);
		Key[] keys = {new Key(ballId)};
		Value[] values = {value};
		RegisterControllerFactory.INSTANCE.getRegisterController().write(keys, values);
	}
	
	public boolean isInHole(){
		return InHoleflag;
	}
	
	public float getVMin(){
		return this.vMin;
	}
	
	public void go(){
		//Log.i(TAG, "ballId = " + ballId);
		VersionValue vval = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballId));
		float[] v = vval.getValue().getV();
		float[] loc = vval.getValue().getLoc();
		
		if(isStoped()||Math.sqrt(v[0]*v[0]+v[1]*v[1])<vMin){
			
			return ;
		}
		Log.i(TAG, "v[0] = " + v[0] + ",v[1] = " + v[1] + ",loc[0] = " + loc[0] + ",loc[1] = " + loc[1]);
		// Log.d(TAG, ballId+":vx = "+vx+",vy = "+vy+",locx = "+locx+",locy = "+locy);
		float tempX = loc[0]+v[0]*timeSpan;	//球要去的下一个位置
		float tempY = loc[1]+v[1]*timeSpan;
		
		Log.i(TAG, "tempX = " + tempX + ",tempY = " + tempY);
		if(this.canGo(tempX, tempY, v[0], v[1])){
			v[0]*=vAttenuation;
			v[1]*=vAttenuation;
			
			Value value = vval.getValue();
			value.setV(v[0], v[1]);
			value.setLoc(tempX, tempY);
			//KVStoreInMemory.INSTANCE.put(new Key(ballId), vval);
		}
	}
	
	public boolean canGo(float tempX,float tempY ,float vx, float vy){
		boolean canGoFlag=true;
		
		/**  对于目标球，要判断是否已经进框    */
		
		float[] center = {tempX + radius, tempY + radius};
		
		if(this.type == AbstractBall.GOAL_BALL){
			if(tempX > Constant.NFRAMEA_X && (tempX + Constant.GOAL_BALL_SIZE) < (Constant.NFRAMEA_X + Constant.NFRAMEA_WIDTH)&&
					tempY > Constant.NFRAMEA_Y && (tempY + Constant.GOAL_BALL_SIZE) < (Constant.NFRAMEA_Y + Constant.NFRAMEA_HEIGHT)){
				InHoleflag = true;
				whichHole = 1;
				return true;
			}
			if(tempX > Constant.SFRAMEA_X && (tempX + Constant.GOAL_BALL_SIZE) < (Constant.SFRAMEA_X + Constant.SFRAMEA_WIDTH)&&
					tempY > Constant.SFRAMEA_Y && (tempY + Constant.GOAL_BALL_SIZE) < (Constant.SFRAMEA_Y + Constant.SFRAMEA_HEIGHT)){
				InHoleflag = true;
				whichHole = 2;
				return true;
			}
		}
		
		List<AbstractBall> balls = gameModel.getBalls();
		for(AbstractBall b:balls){
			if(b!=this && GameModel.INSTANCE.collisionCalculate(new float[]{tempX, tempY}, this, b)){
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
		
		if(center[0] < radius||(center[0] + radius) > Constant.TABLE_WIDTH){
			VersionValue versionValue = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballId));
			Value value = versionValue.getValue();
			value.setV(-vx, vy);
			
			canGoFlag = false;
		}
		if(center[1] < radius||(center[1] + radius) > Constant.TABLE_HEIGHT){
			VersionValue versionValue = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballId));
			Value value = versionValue.getValue();
			value.setV(vx, -vy);
			
			canGoFlag = false;
		}
		
		return canGoFlag;
	}
	
	public abstract void drawSelf(Canvas canvas,Paint paint);
	
}
