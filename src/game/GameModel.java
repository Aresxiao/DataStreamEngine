package game;

import game.sharedmemory.AbstractBall;
import game.sharedmemory.Ball;
import game.sharedmemory.BallGoThread;
import game.sharedmemory.GoalBall;
import game.sharedmemory.PlayerBall;

import java.util.ArrayList;
import java.util.List;

import constant.Constant;

import dse.DataStreamEngine;


public class GameModel {
	
	DataStreamEngine dse;
	
	GameViewDrawThread drawThread;
	BallGoThread ballGoThread;
	GameSyncThread synchThread;
	
	List<AbstractBall> ballList;
	Table table;
	//boolean isOver;
	public GameModel() {
		// TODO Auto-generated constructor stub
		dse=null;
		synchThread = null;
		table = new Table();
		ballList = new ArrayList<AbstractBall>();
		ballList.add(new GoalBall(Constant.TABLE_WIDTH/2-Constant.GOAL_BALL_SIZE/2, 
				Constant.TABLE_HEIGHT/2-Constant.GOAL_BALL_SIZE/2, this));
		ballList.add(new PlayerBall(20, 20, this));
		ballList.add(new PlayerBall(140, 20, this));
		
		ballGoThread = new BallGoThread(this);
		ballGoThread.setFlag(true);
		ballGoThread.start();
		
	}
	
	public void startThread(){
		
		synchThread = new GameSyncThread(this);
		synchThread.setFlag(true);
		synchThread.start();
		
	}
	
	
	public void stopThread(){
		ballGoThread.setFlag(false);
		//synchThread.setFlag(false);
	}
	
	public void setDSE(DataStreamEngine dse){
		this.dse = dse;
	}
	
	/**
	 * @param data
	 * data是String类型，分成两类来源，2表示来源于网络，网络中数据包含的是Ball的速度以及位置信息；1表示来源于本地传感器，
	 * 数据包含的仅仅只有加速度信息。
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
			ball.write("vx", v[0]);
			ball.write("vy", v[1]);
		}
			break;
		case 2:{
			int ballId = Integer.parseInt(strArray[1]);
			AbstractBall ball = ballList.get(ballId);
			ball.write(strArray[2], Float.parseFloat(strArray[3]));
			
			/*
			int ballId = Integer.parseInt(strArray[1]);
			Ball ball = ballList.get(ballId);
			float x_Speed = Float.parseFloat(strArray[2]);
			float y_Speed = Float.parseFloat(strArray[3]);
			float x_Loc = Float.parseFloat(strArray[4]);
			float y_Loc = Float.parseFloat(strArray[5]);
			ball.setBallSpeedBySpeed(x_Speed, y_Speed);
			ball.setBallLocation(x_Loc, y_Loc);
			*/
		}
			break;
		default:
			break;
		}
	}
	/*
	public String getBallState(int ballId){
		Ball ball = ballList.get(ballId);
		float x_Speed = ball.getVX();
		float y_Speed = ball.getVY();
		float x_Loc = ball.getX();
		float y_Loc = ball.getY();
		String speedData = ballId+","+x_Speed+","+y_Speed+","+x_Loc+","+y_Loc;
		return speedData;
	}
	*/
	public void overGame(){
		stopThread();
	}
	
	/**
	 * 把指定的小球状态发送出去。
	 */
	public void pushState(String data){
		dse.updateDSEState(3, data);
		/*
		if(dse!=null){
			String sendString="";
			for(int i = 0;i < buff.length;i++){
				String data = getBallState(buff[i]);
				sendString += " "+data;
			}
			sendString = 3+","+sendString;
			//dse.updateDSEState(3, sendString);
			if(synchThread!=null)
				synchThread.addQueue(sendString);
		}
		*/
	}
	
	public List<AbstractBall> getBalls(){
		
		return this.ballList;
	}
}
