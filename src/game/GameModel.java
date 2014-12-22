package game;

import dse.DataStreamEngine;


public class GameModel {
	
	GameView gameView;
	DataStreamEngine dse;
	
	GameViewDrawThread drawThread;
	BallGoThread ballGoThread;
	//boolean isOver;
	public GameModel(GameView gameView) {
		// TODO Auto-generated constructor stub
		this.gameView = gameView;
		//isOver = false;
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
			Ball ball = gameView.getBallById(ballId);
			float x_Accelerate = Float.parseFloat(strArray[2]);
			float y_Accelerate = Float.parseFloat(strArray[3]);
			ball.setBallSpeedByAccelerate(x_Accelerate, y_Accelerate);
		}
			break;
		case 2:{
			int ballId = Integer.parseInt(strArray[1]);
			Ball ball = gameView.getBallById(ballId);
			float x_Speed = Float.parseFloat(strArray[2]);
			float y_Speed = Float.parseFloat(strArray[3]);
			float x_Loc = Float.parseFloat(strArray[4]);
			float y_Loc = Float.parseFloat(strArray[5]);
			ball.setBallSpeedBySpeed(x_Speed, y_Speed);
			ball.setBallLocation(x_Loc, y_Loc);
		}
			break;
		default:
			break;
		}
	}
	/*
	public void createAllThread(){
		drawThread = new GameViewDrawThread(gameView);
		ballGoThread = new BallGoThread(gameView,this);
	}
	
	void startAllThread(){
		drawThread.setFlag(true);
		drawThread.start();
		ballGoThread.setFlag(true);
		ballGoThread.start();
	}
	
	void stopAllThread(){
		drawThread.setFlag(false);
		ballGoThread.setFlag(false);
	}
	
	public void overGame(){
		isOver=true;
		stopAllThread();
	}
	*/
	
	public String getBallState(int ballId){
		Ball ball = gameView.getBallById(ballId);
		float x_Speed = ball.getVX();
		float y_Speed = ball.getVY();
		float x_Loc = ball.getX();
		float y_Loc = ball.getY();
		String speedData = ballId+","+x_Speed+","+y_Speed+","+x_Loc+","+y_Loc;
		return speedData;
	}
	
}
