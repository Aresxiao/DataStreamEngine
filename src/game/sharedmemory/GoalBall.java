package game.sharedmemory;


import constant.Constant;
import game.GameModel;
import game.sharedmemory.communication.message.Message;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.Version;
import game.sharedmemory.data.VersionValue;
import game.sharedmemory.data.kvstore.KVStoreInMemory;
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
		Key key = new Key(ballId);
		Version version = new Version(0);
		Value value = new Value(x, y);
		VersionValue versionValue = new VersionValue(version, value);
		KVStoreInMemory.INSTANCE.put(key, versionValue);
		d = Constant.GOAL_BALL_SIZE;
		radius = Constant.GOAL_BALL_SIZE / 2;
		Log.i(TAG,"goalball ballId = " + ballId);
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		float[] loc = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballId)).getValue().getLoc();
		//Log.i(TAG, "ballId = "+ballId+",x = "+loc[0]+",y = "+loc[1]);
		Matrix m1 = new Matrix();
		m1.setTranslate( loc[0]+Constant.X_OFFSET,loc[1]+Constant.Y_OFFSET );
		paint.reset();
		paint.setColor(Color.BLUE);
		canvas.drawCircle(loc[0]+Constant.GOAL_BALL_SIZE/2, loc[1]+Constant.GOAL_BALL_SIZE/2, radius, paint);
		
	}
	
}
