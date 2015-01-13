package game;

import java.util.ArrayList;
import java.util.List;

import constant.Constant;

import dse.DataStreamEngine;


public class GameModel {
	
	//GameView gameView;
	DataStreamEngine dse;
	
	GameViewDrawThread drawThread;
	BallGoThread ballGoThread;
	
	List<Ball> ballList;
	Table table;
	//boolean isOver;
	public GameModel() {
		// TODO Auto-generated constructor stub
		//this.gameView = gameView;
		dse=null;
		init();
		startThread();
		
	}
	
	public void init(){
		table = new Table();
		
		ballList = new ArrayList<Ball>();
		ballList.add(new Ball(true, Constant.TABLE_WIDTH/2-Constant.GOAL_BALL_SIZE/2, 
				Constant.TABLE_HEIGHT/2-Constant.GOAL_BALL_SIZE/2, this));
		ballList.add(new Ball(false, 20, 20,this));
		ballList.add(new Ball(false, 140, 20,this));
	}
	
	public void startThread(){
		ballGoThread = new BallGoThread(this);
		ballGoThread.setFlag(true);
		ballGoThread.start();
	}
	
	public void stopThread(){
		ballGoThread.setFlag(false);
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
			Ball ball = ballList.get(ballId);
			float x_Accelerate = Float.parseFloat(strArray[2]);
			float y_Accelerate = Float.parseFloat(strArray[3]);
			ball.setBallSpeedByAccelerate(x_Accelerate, y_Accelerate);
		}
			break;
		case 2:{
			int ballId = Integer.parseInt(strArray[1]);
			Ball ball = ballList.get(ballId);
			float x_Speed = Float.parseFloat(strArray[2]);
			float y_Speed = Float.parseFloat(strArray[3]);
			float x_Loc = Float.parseFloat(strArray[4]);
			float y_Loc = Float.parseFloat(strArray[5]);
			ball.setBallSpeedBySpeed(x_Speed, y_Speed);
			ball.setBallLocation(x_Loc, y_Loc);
		}
			break;
		case 3:{
				int len = Integer.parseInt(strArray[1]);
				for(int i = 0;i < len;i++){
					
				}
			}
			break;
		default:
			break;
		}
	}
	
	
	public String getBallState(int ballId){
		Ball ball = ballList.get(ballId);
		float x_Speed = ball.getVX();
		float y_Speed = ball.getVY();
		float x_Loc = ball.getX();
		float y_Loc = ball.getY();
		String speedData = ballId+","+x_Speed+","+y_Speed+","+x_Loc+","+y_Loc;
		return speedData;
	}
	
	public void overGame(){
		stopThread();
	}
	
	/**
	 * 把指定的小球状态发送出去。
	 */
	public void pushState(int[] buff){
		if(dse!=null){
			for(int i = 0;i < buff.length;i++){
				String data = getBallState(buff[i]);
				data = 2+","+data;
				dse.dataProcessFromGame(1, data);
				System.out.println("push current state");
			}
		}
	}
	
}
