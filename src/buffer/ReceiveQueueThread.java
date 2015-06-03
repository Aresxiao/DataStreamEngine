package buffer;

import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.message.IPMessage;
import java.util.concurrent.BlockingQueue;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
/**
 * @author XiaoGeng
 * 该类专门用来处理receiveQueue队列中的数据，当队列为空时，阻塞。
 */

public class ReceiveQueueThread extends Thread{
	
	private static String TAG = ReceiveQueueThread.class.getName();
	
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
				if(msg == null){
					Log.i(TAG, "get a msg is null");
				}
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
