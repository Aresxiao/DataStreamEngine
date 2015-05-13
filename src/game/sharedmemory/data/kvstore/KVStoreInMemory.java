package game.sharedmemory.data.kvstore;

import java.util.concurrent.*;

import android.util.Log;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;

public enum KVStoreInMemory implements IKVStore{
	
	INSTANCE;
	private static final String TAG = KVStoreInMemory.class.getName();
	
	private final ConcurrentMap<Key, VersionValue> key_vval_map = new ConcurrentHashMap<Key, VersionValue>();
	
	private final Object[] locks = new Object[10];
	{
		for(int i = 0; i < locks.length; i++) 
			locks[i] = new Object();
	}
	
	@Override
	public void put(Key key, VersionValue vval) {
		// TODO Auto-generated method stub
		final int hash = key.hashCode() & 0x7FFFFFFF;
		//Log.i(TAG,key.toString()+vval.toString());
		synchronized (locks[hash % locks.length])	// allowing concurrent writers
		{
			VersionValue current_vval = this.getVersionValue(key);
			//Log.i(TAG, "read use get method");
			if (current_vval.compareTo(vval) < 0){	//	newer VersionValue
				//Log.i(TAG, "put a new version value");
				this.key_vval_map.put(key, vval);
			}
		}
	}
	
	@Override
	public VersionValue getVersionValue(Key key) {
		// TODO Auto-generated method stub
		VersionValue vval = this.key_vval_map.get(key);
		if (vval == null)
			return VersionValue.RESERVED_VERSIONVALUE;
		return vval;
	}
	
	@Override
	public void remove(Key key) {
		// TODO Auto-generated method stub
		
		int hash = key.hashCode() & 0x7FFFFFFF;
		synchronized (locks[hash % locks.length])
		{
			this.key_vval_map.remove(key);
		}
	}

}