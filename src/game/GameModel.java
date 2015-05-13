package game;

import game.sharedmemory.*;
import game.sharedmemory.communication.message.Message;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.kvstore.KVStoreInMemory;
import game.sharedmemory.readerwriter.RegisterControllerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;


import constant.Constant;


public enum GameModel {
	
	INSTANCE;
	
	private static final String TAG = GameModel.class.getName();
	
	BallGoThread ballGoThread = new BallGoThread();
	
	List<AbstractBall> ballList = new ArrayList<AbstractBall>();
	Table table = new Table();
	
	public void initialize(){
		
		ballList.add(new GoalBall(Constant.TABLE_WIDTH/2-Constant.GOAL_BALL_SIZE/2,
				Constant.TABLE_HEIGHT/2-Constant.GOAL_BALL_SIZE/2, this));
		ballList.add(new PlayerBall(20, 20, this));
		ballList.add(new PlayerBall(140, 20, this));
		
		ballGoThread.setFlag(true);
		ballGoThread.start();
	}
	
	public void stopThread(){
		
		ballGoThread.setFlag(false);
	}
	
	/**
	 * @param ax 是x方向的加速度
	 * @param ay 是y方向的加速度
	 * @description 通过重力加速度来修改本地小球的状态
	 */
	public void onSensorChanged(float ax,float ay){
		AbstractBall ball = ballList.get(Constant.LOCAL_BALL_ID);
		float[] v = ball.calBallSpeedByAccelerate(ax, ay);
		
		Key key = new Key(Constant.LOCAL_BALL_ID);
		
		Value value = RegisterControllerFactory.INSTANCE.getRegisterController().read(key).getValue();
		value.setV(v[0], v[1]);
		RegisterControllerFactory.INSTANCE.getRegisterController().write(key, value);
	}
	
	public void overGame(){
		stopThread();
	}
	
	public List<AbstractBall> getBalls(){
		
		return this.ballList;
	}
	
	
}
