package game.sharedmemory;

import network.Message;
import constant.Constant;
import dse.DataStreamEngine;
import game.GameModel;
import android.graphics.*;
import android.util.Log;

public class PlayerBall extends AbstractBall {

	private static final String TAG = PlayerBall.class.getName();
	public PlayerBall(float x, float y, GameModel gameModel) {
		
		super(AbstractBall.PLAYER_BALL);
		this.gameModel = gameModel;
		ballStateMap.put(new Key("locx"),new Value(x));
		ballStateMap.put(new Key("locy"), new Value(y));
		ballStateMap.put(new Key("d"), new Value(Constant.PLAYER_BALL_SIZE));
		ballStateMap.put(new Key("radius"), new Value(Constant.PLAYER_BALL_SIZE/2));
		ballStateMap.put(new Key("vx"), new Value(0f));
		ballStateMap.put(new Key("vy"), new Value(0f));
		
	}
	
	/**
	 * @description 绘制方法，根据当前小球的状态绘制
	 */
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		
		float locx = this.read(new Key("locx")).getVal();
		float locy = this.read(new Key("locy")).getVal();
		float radius = this.read(new Key("radius")).getVal();
		
		Matrix m1 = new Matrix();
		m1.setTranslate( locx+Constant.X_OFFSET,locy+Constant.Y_OFFSET );
		paint.reset();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth((float) 2.0);
		canvas.drawCircle(locx + Constant.PLAYER_BALL_SIZE/2, locy+Constant.PLAYER_BALL_SIZE/2, radius, paint);
		canvas.drawText(Integer.toString(ballId), locx+Constant.PLAYER_BALL_SIZE/2, locy+Constant.PLAYER_BALL_SIZE/2, paint);
		
	}
	
	/**
	 * @description override 父类{@link AbstractBall}}的read(Key key)方法
	 * @return Value类型
	 */
	@Override
	public Value read(Key key) {
		// TODO Auto-generated method stub
		if(ballStateMap.containsKey(key))
			return ballStateMap.get(key);
		
		return null;
	}
	
	/**
	 * @description override 父类{@link AbstractBall}}的write(Key key, Value value)方法
	 */
	@Override
	public void write(Key key, Value value) {
		// TODO Auto-generated method stub
		
		if(ballStateMap.containsKey(key)){
			//Log.d(TAG, Constant.LOCAL_BALL_ID+":"+this.ballId+" : "+key+" : "+value);
			ballStateMap.put(key, value);
		}
		
		if(value.isNeedSend() && (ballId == Constant.LOCAL_BALL_ID)){
			value.setSendCount(0);
			Message msg = new Message(ballId, key, value);
			Log.i(TAG, msg.toString());
			DataStreamEngine.INSTANCE.addSendQueue(msg);
		}
	}
}
