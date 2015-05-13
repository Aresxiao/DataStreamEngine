package game.sharedmemory.readerwriter;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.VersionValue;
import game.sharedmemory.data.kvstore.KVStoreInMemory;

public class WeakRegisterController extends AbstractRegisterController{
	
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
		
		return KVStoreInMemory.INSTANCE.getVersionValue(key);
	}
	
	/**
	 * 在weak中，每次更改都修改本地的值，并同时把新的值封装成msg发送给对方。
	 */
	
	@Override
	public void write(Key key, Value val) {
		// TODO Auto-generated method stub
		
		VersionValue vval = KVStoreInMemory.INSTANCE.getVersionValue(key);
		
		VersionValue new_vval = new VersionValue(vval.getVersion().increment(), val);
		KVStoreInMemory.INSTANCE.put(key, new_vval);
		writeRemote(key, new_vval);
	}
	
	
}
