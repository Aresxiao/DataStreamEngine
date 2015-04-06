package game.sharedmemory;

import constant.Constant;
import game.GameModel;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class GoalBall extends AbstractBall {

	public GoalBall(float x, float y, GameModel gameModel) {
		
		super(AbstractBall.GOAL_BALL);
		this.gameModel = gameModel;
		ballStateMap.put("locx", x);
		ballStateMap.put("locy", y);
		ballStateMap.put("d", Constant.GOAL_BALL_SIZE);
		ballStateMap.put("radius", Constant.GOAL_BALL_SIZE/2);
		ballStateMap.put("vx", 0f);
		ballStateMap.put("vy", 0f);
		
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		float locx = this.read("locx");
		float locy = this.read("locy");
		float radius = this.read("radius");
		
		Matrix m1 = new Matrix();
		m1.setTranslate( locx+Constant.X_OFFSET,locy+Constant.Y_OFFSET );
		paint.reset();
		paint.setColor(Color.BLUE);
		canvas.drawCircle(locx+Constant.GOAL_BALL_SIZE/2, locy+Constant.GOAL_BALL_SIZE/2, radius, paint);
		
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
		
	}

}
