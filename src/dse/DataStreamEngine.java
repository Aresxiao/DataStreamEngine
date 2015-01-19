package dse;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
	
	BlockingQueue<String> sensorQueue;
	BlockingQueue<String> receiveQueue;
	BlockingQueue<String> sendQueue;
	float accelearate_x,accelearate_y,accelearate_z;
	GameModel gameModel;
	OverlayNetwork overlayNetwork;
	
	SendQueueThread sendQueueThread;
	ReceiveQueueThread receiveQueueThread;
	SensorQueueThread sensorQueueThread;
	
	public DataStreamEngine(GameModel gameModel){
		this.gameModel = gameModel;
		sendQueue = new LinkedBlockingQueue<String>();
		receiveQueue = new LinkedBlockingQueue<String>();
		sensorQueue = new LinkedBlockingQueue<String>();
		overlayNetwork = null;
		
	}
	
	/**
	 * �÷���������¶��Sensorģ�飬Sensor�������ݾͻ���ø÷�����
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
	 * ��������Ǳ�¶��OverlayNetwork��OverlayNetwork���յ����ݾͻ�������������
	 * @param data
	 * ΪString���ͣ�type Ϊint ����,ָ��������Դ,1��ʾ���յ����������ݣ�2��ʾ���Ա��ش��������ݡ�
	 */
	public void updateDSEState(int type,String data) {
		
		switch (type) {
		case 1:
			addReceiveQueue(data);
			break;
		case 2:
			addSensorQueue(data);
			break;
		default:
			break;
		}
		
		// TODO Auto-generated method stub
	}
	
	/**
	 * �����кܶ��ִ���ʽ
	 * @param type
	 * Ϊint���ͣ�ָ��Ҫ����ʲô���Ĵ���dataΪString���ͣ�Ϊ��Ҫ���д�������ݡ�
	 * typeΪ1����ʾ��Ҫ�������ݣ�
	 */
	public void dataProcessFromGame(int type, String data) {
		// TODO Auto-generated method stub
		switch (type) {
		case 1:
			if(overlayNetwork!=null)
				overlayNetwork.sendData(data);
			break;
		default:
			break;
		}
	}
	
	public GameModel getGameModel(){
		return gameModel;
	}
	/**
	 * ��������
	 * @param overlayNetwork
	 */
	public void setOverlayNetwork(OverlayNetwork overlayNetwork){
		this.overlayNetwork = overlayNetwork;
	}
	
	public void startSensorThread(){
		sensorQueueThread = new SensorQueueThread(this);
		sensorQueueThread.start();
	}
	
	public void startNetworkThread(){
		receiveQueueThread = new ReceiveQueueThread(this);
		sendQueueThread = new SendQueueThread(this);
		receiveQueueThread.start();
		sendQueueThread.start();
	}
	
	public OverlayNetwork getOverlayNetwork(){
		return overlayNetwork;
	}
	
	public void addReceiveQueue(String data){
		receiveQueue.offer(data);
	}
	
	public void addSendQueue(String data){
		sendQueue.offer(data);
	}
	
	public void addSensorQueue(String data){
		sensorQueue.offer(data);
		
	}
}


