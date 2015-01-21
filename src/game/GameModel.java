package game;

import java.util.ArrayList;
import java.util.List;

import constant.Constant;

import dse.DataStreamEngine;


public class GameModel {
	
	DataStreamEngine dse;
	
	GameViewDrawThread drawThread;
	BallGoThread ballGoThread;
	GameSyncThread synchThread;
	
	List<Ball> ballList;
	Table table;
	//boolean isOver;
	public GameModel() {
		// TODO Auto-generated constructor stub
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
		synchThread = new GameSyncThread(this);
		synchThread.setFlag(true);
		ballGoThread.setFlag(true);
		ballGoThread.start();
		synchThread.start();
	}
	
	
	public void stopThread(){
		ballGoThread.setFlag(false);
		synchThread.setFlag(false);
	}
	
	public void setDSE(DataStreamEngine dse){
		this.dse = dse;
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
			String[] tokens = data.split(" ");
			for(int i = 1;i < tokens.length;i++){
				String[] ballState = tokens[i].split(",");
				int ballId = Integer.parseInt(ballState[0]);
				Ball ball = ballList.get(ballId);
				float x_Speed = Float.parseFloat(ballState[1]);
				float y_Speed = Float.parseFloat(ballState[2]);
				float x_Loc = Float.parseFloat(ballState[3]);
				float y_Loc = Float.parseFloat(ballState[4]);
				ball.setBallSpeedBySpeed(x_Speed, y_Speed);
				ball.setBallLocation(x_Loc, y_Loc);
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
	 * ��ָ����С��״̬���ͳ�ȥ��
	 */
	public void pushState(int[] buff){
		if(dse!=null){
			String sendString="";
			for(int i = 0;i < buff.length;i++){
				String data = getBallState(buff[i]);
				sendString += " "+data;
			}
			sendString = 3+","+sendString;
			//dse.updateDSEState(3, sendString);
			synchThread.addQueue(sendString);
		}
	}
	
}
