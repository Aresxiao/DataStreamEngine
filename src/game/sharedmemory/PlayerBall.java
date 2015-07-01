package game.sharedmemory;


import constant.Constant;
import game.GameModel;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.Version;
import game.sharedmemory.data.VersionValue;
import game.sharedmemory.data.kvstore.KVStoreInMemory;
import android.graphics.*;
import android.util.Log;

public class PlayerBall extends AbstractBall {

	private static final String TAG = PlayerBall.class.getName();
	public PlayerBall(float x, float y, GameModel gameModel) {
		
		super(AbstractBall.PLAYER_BALL, x, y);
		this.gameModel = gameModel;
		
		Key key = new Key(ballId);
		
		Version version = new Version(0);
		
		Value value = new Value(x, y);
		
		VersionValue versionValue = new VersionValue(version, value);
		
		KVStoreInMemory.INSTANCE.put(key, versionValue);
		d = Constant.PLAYER_BALL_SIZE;
		radius = Constant.PLAYER_BALL_SIZE / 2;
		Log.i(TAG, "player ballid = " + ballId);
	}
	
	/**
	 * @description 绘制方法，根据当前小球的状态绘制
	 */
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		
		float[] loc = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballId)).getValue().getLoc();
		
		Matrix m1 = new Matrix();
		m1.setTranslate( loc[0]+Constant.X_OFFSET,loc[1]+Constant.Y_OFFSET );
		paint.reset();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth((float) 2.0);
		canvas.drawCircle(loc[0] + Constant.PLAYER_BALL_SIZE/2, loc[1]+Constant.PLAYER_BALL_SIZE/2, radius, paint);
		canvas.drawText(Integer.toString(ballId), loc[0]+Constant.PLAYER_BALL_SIZE/2, loc[1]+Constant.PLAYER_BALL_SIZE/2, paint);
		
	}
	
}
