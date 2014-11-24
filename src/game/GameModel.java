package game;

public class GameModel {
	
	GameView gameView;
	
	GameViewDrawThread drawThread;
	BallGoThread ballGoThread;
	boolean isOver;
	public GameModel(GameView gameView) {
		// TODO Auto-generated constructor stub
		this.gameView = gameView;
		isOver = false;
	}
	
	/**
	 * @param data,GameView中的data包含三类数据，1：加速度数据，:2：速度数据，3：位置信息(也有可能是其他信息，目前还没有想到)
	 * 如果是1、2、3类信息，那么第二位应该包含Ball的index。
	 * 
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
			ball.setBallSpeedBySpeed(x_Speed, y_Speed);
		}
			break;
		case 3:{
			int ballId = Integer.parseInt(strArray[1]);
			Ball ball = gameView.getBallById(ballId);
			float x_Loc = Float.parseFloat(strArray[2]);
			float y_Loc = Float.parseFloat(strArray[3]);
			ball.setBallLocation(x_Loc, y_Loc);
		}
			break;
		default:
			break;
		}
	}
	
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
}
