package game.sharedmemory;

import network.Message;
import constant.Constant;
import dse.DataStreamEngine;
import game.GameModel;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public class GoalBall extends AbstractBall {

	private static final String TAG = GoalBall.class.getName();
	
	public GoalBall(float x, float y, GameModel gameModel) {
		
		super(AbstractBall.GOAL_BALL);
		this.gameModel = gameModel;
		ballStateMap.put(new Key("locx"), new Value(x));
		ballStateMap.put(new Key("locy"), new Value(y));
		ballStateMap.put(new Key("d"), new Value(Constant.GOAL_BALL_SIZE));
		ballStateMap.put(new Key("radius"), new Value(Constant.GOAL_BALL_SIZE/2));
		ballStateMap.put(new Key("vx"), new Value(0f));
		ballStateMap.put(new Key("vy"), new Value(0f));
		
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		float locx = this.read(new Key("locx")).getVal();
		float locy = this.read(new Key("locy")).getVal();
		float radius = this.read(new Key("radius")).getVal();
		
		Matrix m1 = new Matrix();
		m1.setTranslate( locx+Constant.X_OFFSET,locy+Constant.Y_OFFSET );
		paint.reset();
		paint.setColor(Color.BLUE);
		canvas.drawCircle(locx+Constant.GOAL_BALL_SIZE/2, locy+Constant.GOAL_BALL_SIZE/2, radius, paint);
		
	}
	
	@Override
	public Value read(Key key) {
		// TODO Auto-generated method stub
		if(ballStateMap.containsKey(key))
			return ballStateMap.get(key);
		
		return null;
	}
	
	@Override
	public void write(Key key, Value value) {
		// TODO Auto-generated method stub
		if(ballStateMap.containsKey(key))
			ballStateMap.put(key, value);
		if(value.getVal() == 2){
			value.setSendCount(0);
			Message msg = new Message(ballId, key, value);
			Log.i(TAG, msg.toString());
			DataStreamEngine.INSTANCE.addSendQueue(msg);
		}
	}

}
