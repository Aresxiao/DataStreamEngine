package game.sharedmemory;

import constant.Constant;
import game.GameModel;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class PlayerBall extends AbstractBall {

	public PlayerBall(float x, float y, GameModel gameModel) {
		
		super(AbstractBall.PLAYER_BALL);
		this.gameModel = gameModel;
		ballStateMap.put("locx", x);
		ballStateMap.put("locy", y);
		ballStateMap.put("d", Constant.PLAYER_BALL_SIZE);
		ballStateMap.put("radius", Constant.PLAYER_BALL_SIZE/2);
		ballStateMap.put("vx", 0f);
		ballStateMap.put("vy", 0f);
		
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		
		float locx = this.read("locx");
		float locy = this.read("locy");
		float radius = this.read("radius");
		
		Matrix m1 = new Matrix();
		m1.setTranslate( locx+Constant.X_OFFSET,locy+Constant.Y_OFFSET );
		paint.reset();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth((float) 2.0);
		canvas.drawCircle(locx + Constant.PLAYER_BALL_SIZE/2, locy+Constant.PLAYER_BALL_SIZE/2, radius, paint);
		canvas.drawText(Integer.toString(ballId), locx+Constant.PLAYER_BALL_SIZE/2, locy+Constant.PLAYER_BALL_SIZE/2, paint);
		
	}

	@Override
	public Float read(String key) {
		// TODO Auto-generated method stub
		if(ballStateMap.containsKey(key))
			return ballStateMap.get(key);
		
		return null;
	}
	
	@Override
	public void write(String key, float value) {
		// TODO Auto-generated method stub
		if(ballStateMap.containsKey(key))
			ballStateMap.put(key, value);
		if(this.ballId == Constant.LOCAL_BALL_ID){
			String data = this.ballId + " "+key+" "+value;
			gameModel.pushState(data);
		}
	}

}
