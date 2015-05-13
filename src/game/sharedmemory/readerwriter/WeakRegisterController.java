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
	 * ��weak�У�ÿ�θ��Ķ��޸ı��ص�ֵ����ͬʱ���µ�ֵ��װ��msg���͸��Է���
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
