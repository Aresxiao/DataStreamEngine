package dse;

import network.OverlayNetwork;
import game.GameModel;
import constant.Constant;

/**
 * @author XiaoGeng
 * ��������DSE��ר����������sendQueue�����е����ݣ���Ϊ��ʱ���߳�������
 */
public class SendQueueThread extends Thread {
	
	private boolean flag;
	DataStreamEngine dse;
	private int sleepSpan;
	
	public SendQueueThread(DataStreamEngine dataStreamEngine){
		dse = dataStreamEngine;
		flag = true;
		sleepSpan = 7;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			GameModel gameModel = dse.getGameModel();
			String data = gameModel.getBallState(Constant.LOCAL_BALL_ID);
			data = 2+","+data;
			OverlayNetwork overlayNetwork = dse.getOverlayNetwork();
			overlayNetwork.sendData(data);
			
			try {
				Thread.sleep(sleepSpan);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
}
