package buffer;

import game.GameModel;
import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.message.IPMessage;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.Version;
import game.sharedmemory.data.VersionValue;

import java.util.concurrent.BlockingQueue;

import org.json.JSONException;
import org.json.JSONObject;


import constant.Constant;
/**
 * @author XiaoGeng
 * 该类专门用来处理receiveQueue队列中的数据，当队列为空时，阻塞。
 */

public class ReceiveQueueThread extends Thread{
	
	int receiveCount = 0;
	BlockingQueue<String> receiveQueue;
	public ReceiveQueueThread(){
		
		receiveQueue = BufferManager.INSTANCE.receiveQueue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				String data = receiveQueue.take();
				
				JSONObject jsonObject = new JSONObject(data);
				IPMessage msg = IPMessage.getIPMessageFromJSON(jsonObject);
				MessagingService.INSTANCE.onReceive(msg);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
