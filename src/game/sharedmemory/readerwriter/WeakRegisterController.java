package game.sharedmemory.readerwriter;

import java.util.Collections;

import android.util.Log;
import game.sharedmemory.communication.message.IPMessage;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.VersionValue;
import game.sharedmemory.data.kvstore.KVStoreInMemory;

public class WeakRegisterController extends AbstractRegisterController{
	
	private static final String TAG = WeakRegisterController.class.getName();
	
	private WeakRegisterController(){ }
	
	public static WeakRegisterController instance = null;
	
	public static synchronized WeakRegisterController INSTANCE(){
		if (instance == null)
			instance = new WeakRegisterController();
		return instance;
	}
	
	@Override
	public VersionValue read(Key key) {
		// TODO Auto-generated method stub
		//Log.i(TAG,"read() method " + key.toString());
		this.op_cnt++;
		
		return KVStoreInMemory.INSTANCE.getVersionValue(key);
	}
	
	/**
	 * 在weak中，每次更改都修改本地的值，并同时把新的值封装成msg发送给对方。
	 */
	//@Override
	@Deprecated
	public void write(Key key, Value val) {
		// TODO Auto-generated method stub
		this.op_cnt++;
		VersionValue vval = KVStoreInMemory.INSTANCE.getVersionValue(key);
		
		VersionValue new_vval = new VersionValue(vval.getVersion().increment(), val);
		KVStoreInMemory.INSTANCE.put(key, new_vval);
		
		//writeRemote(key, new_vval);
		//Log.i(TAG,new_vval.toString());
	}
	
	@Override
	public void handleMessage(IPMessage message) {
		// TODO Auto-generated method stub
		Key[] keys = message.getKeys();
		VersionValue[] versionValues = message.getVersionValues();
		
		for(int i = 0; i < keys.length; i++){
			KVStoreInMemory.INSTANCE.put(keys[i], versionValues[i]);
		}
	}
	
	@Override
	public void write(Key[] keys, Value[] values) {
		// TODO Auto-generated method stub
		this.op_cnt++;
		VersionValue[] versionValues = new VersionValue[keys.length];
		for(int i = 0; i < keys.length; i++){
			VersionValue vval = KVStoreInMemory.INSTANCE.getVersionValue(keys[i]);
			versionValues[i] = new VersionValue(vval.getVersion().increment(), values[i]);
			KVStoreInMemory.INSTANCE.put(keys[i], versionValues[i]);
		}
		
		writeRemote(keys, versionValues);
		
	}
	
}
