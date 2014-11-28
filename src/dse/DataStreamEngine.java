package dse;

import network.OverlayNetwork;
import constant.Constant;
import game.GameModel;

/**
 * 
 * This class is the main part of engine.It can do some processing.
 * @author GengXiao
 * 
 */
public class DataStreamEngine implements DSEInterface{
	
	float accelearate_x,accelearate_y,accelearate_z;
	GameModel gameModel;
	OverlayNetwork overlayNetwork;
	
	DataProcessingThread dataProcessingThread;
	
	public DataStreamEngine(GameModel gameModel){
		this.gameModel = gameModel;
	}
	
	/**
	 * 该方法用来暴露给Sensor模块，Sensor产生数据就会调用该方法。
	 */
	public void setAccelerate(float x, float y, float z){
		// TODO Auto-generated method stub
		this.accelearate_x = x;
		this.accelearate_y = y;
		this.accelearate_z = z;
		int ballId = Constant.LOCAL_BALL_ID;
		String acceData = 1+","+ballId+","+x+","+y+","+z;
		gameModel.updateGameView(acceData);
	}
	
	/**
	 * 这个方法是暴露给OverlayNetwork，OverlayNetwork接收到数据就会调用这个方法。
	 * @param data为String类型，
	 * @return void。
	 */
	public void updateDSEState(String data) {
		// TODO Auto-generated method stub
		gameModel.updateGameView(data);
	}
	
	/**
	 * 这里有很多种处理方式
	 * @param type为int类型，指明要进行什么样的处理，data为String类型，为所要进行处理的数据。
	 * type为1，表示需要发送数据；
	 * 目前type还没有扩充。
	 */
	public void dataProcessFromGame(int type, String data) {
		// TODO Auto-generated method stub
		switch (type) {
		case 1:
			overlayNetwork.sendData(data);
			break;
		default:
			break;
		}
	}
	
	public GameModel getGameModel (){
		return gameModel;
	}
	
	public void setOverlayNetwork(OverlayNetwork overlayNetwork){
		this.overlayNetwork = overlayNetwork;
		
	}
	
	public void startThread(){
		dataProcessingThread = new DataProcessingThread(this);
		dataProcessingThread.setFlag(true);
		dataProcessingThread.start();
	}
	
	public OverlayNetwork getOverlayNetwork(){
		return overlayNetwork;
	}
}
