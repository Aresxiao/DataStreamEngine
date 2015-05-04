package game.sharedmemory.data.kvstore;

import game.sharedmemory.data.*;

public interface IKVStore {
	
	public void put(Key key, VersionValue vval);
	
	public VersionValue getVersionValue(Key key);
	
	public void remove(Key key);
}
