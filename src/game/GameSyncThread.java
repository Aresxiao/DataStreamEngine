package game;

import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import constant.Constant;
import dse.DSEInterface;

public class GameSyncThread extends Thread{
	
	GameModel gameModel;
	private boolean flag = true;
	private int sleepSpan;
	BlockingQueue<String> queue;
	int synCount;
	SimpleDateFormat format;
	
	public GameSyncThread(GameModel gameModel){
		queue = new LinkedBlockingQueue<String>();
		this.gameModel = gameModel;
		sleepSpan = 7;
		synCount = 0;
		format = new SimpleDateFormat("HH:mm:ss.SSS");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		queue.clear();
		while(flag){
			DSEInterface dse = gameModel.dse;
			if(dse != null){
				//System.out.println(format.format(System.currentTimeMillis())+"++++");
				gameModel.ballGoThread.setIsWait(true);
				synchronized (Constant.MUTEX_OBJECT) {
					if(queue.isEmpty()){
						String data = gameModel.getBallState(Constant.LOCAL_BALL_ID);
						data = 2+","+data;
						dse.updateDSEState(3, data);
					}
					else {
						try {
							String data = queue.take();
							dse.updateDSEState(3, data);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					synCount++;
					if(Constant.isDebug)
						System.out.println("gameSyncThread : synCount = "+synCount);
					try {
						Constant.MUTEX_OBJECT.wait();
						System.out.println(format.format(System.currentTimeMillis())+"-----");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
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
	
	public void addQueue(String data){
		queue.offer(data);
	}
}
