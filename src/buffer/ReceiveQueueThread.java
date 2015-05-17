package buffer;

import game.GameModel;
import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.message.Message;
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
				int type = jsonObject.getInt("type");
				int id = jsonObject.getInt("key.id");
				int seqno = jsonObject.getInt("version.seqno");
				float vx = (float) jsonObject.getDouble("value.vx");
				float vy = (float) jsonObject.getDouble("value.vy");
				float locx = (float) jsonObject.getDouble("value.locx");
				float locy = (float) jsonObject.getDouble("value.locy");
				
				Key key = new Key(id);
				Version version = new Version(seqno);
				Value value = new Value(vx, vy, locx, locy);
				VersionValue versionValue = new VersionValue(version, value);
				Message msg = new Message(key, versionValue);
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
