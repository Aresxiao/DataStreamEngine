package game;

import game.sharedmemory.*;

import java.util.ArrayList;
import java.util.List;

import constant.Constant;


public enum GameModel {
	
	INSTANCE;
	
	BallGoThread ballGoThread = new BallGoThread();
	GameSyncThread synchThread = null;
	
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
	
	public void startsynchThread(){
		
		synchThread = new GameSyncThread(this);
		synchThread.setFlag(true);
		synchThread.start();
		
	}
	
	public void stopThread(){
		
		ballGoThread.setFlag(false);
		
	}
	
	/**
	 * @param data
	 * data��String���ͣ��ֳ�������Դ��2��ʾ��Դ�����磬���������ݰ�������Ball���ٶ��Լ�λ����Ϣ��1��ʾ��Դ�ڱ��ش�������
	 * ���ݰ����Ľ���ֻ�м��ٶ���Ϣ��
	 */
	public void updateGameView(String data){
		String[] strArray = data.split(",");
		int type = Integer.parseInt(strArray[0]);
		switch (type) {
		case 1:{
			int ballId = Integer.parseInt(strArray[1]);
			AbstractBall ball = ballList.get(ballId);
			float x_Accelerate = Float.parseFloat(strArray[2]);
			float y_Accelerate = Float.parseFloat(strArray[3]);
			
			float[] v = ball.calBallSpeedByAccelerate(x_Accelerate, y_Accelerate);
			ball.write(new Key("vx"), new Value(v[0]));
			ball.write(new Key("vy"), new Value(v[1]));
		}
			break;
		case 2:{
			int ballId = Integer.parseInt(strArray[1]);
			AbstractBall ball = ballList.get(ballId);
			ball.write(new Key(strArray[2]), new Value(Float.parseFloat(strArray[3])));
			
		}
			break;
		default:
			break;
		}
	}
	/**
	 * @param ax ��x����ļ��ٶ�
	 * @param ay ��y����ļ��ٶ�
	 * @description ͨ���������ٶ����޸ı���С���״̬
	 */
	public void onSensorChanged(float ax,float ay){
		AbstractBall ball = ballList.get(Constant.LOCAL_BALL_ID);
		float[] v = ball.calBallSpeedByAccelerate(ax, ay);
		
		ball.write(new Key("vx"),new Value(v[0]));
		ball.write(new Key("vy"),new Value(v[1]));
	}
	
	public void overGame(){
		stopThread();
	}
	
	public List<AbstractBall> getBalls(){
		
		return this.ballList;
	}
}
