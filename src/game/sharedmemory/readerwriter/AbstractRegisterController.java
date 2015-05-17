package game.sharedmemory.readerwriter;

import android.util.Log;
import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.message.Message;
import game.sharedmemory.data.*;
import game.sharedmemory.data.kvstore.KVStoreInMemory;

public abstract class AbstractRegisterController implements IRegister, IMessageHandler {
	
	private static final String TAG = AbstractRegisterController.class.getName();
	
	protected int op_cnt;
	
	@Override
	public abstract VersionValue read(Key key);
	
	@Override
	public abstract void write(Key key, Value val);
	
	public void writeRemote(Key key,VersionValue versionValue){
		VersionValue vval = versionValue.clone();
		
		Message msg = new Message(key, vval);
		
		MessagingService.INSTANCE.send(msg);
		
		
	}
	
	@Override
	public void handleMessage(Message message){
		// TODO Auto-generated method stub
		
		Key key = message.getKey();
		VersionValue versionValue = message.getVersionValue();
		KVStoreInMemory.INSTANCE.put(key, versionValue);
		
	}
	
}
