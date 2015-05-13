package game.sharedmemory.readerwriter;

import game.sharedmemory.data.*;

public interface IRegister {
	
	/**
	 * "get" operation invocation
	 * @param key key to get
	 * @return Versioned value associated with the key
	 */
	public VersionValue read(Key key);

	/**
	 * "put" operation invocation
	 * @param key {@link Key} to put
	 * @param val non-versioned value associated with the key
	 * @return {@link VersionValue} to put associated with the key
	 */
	public void write(Key key, Value vval);
	
}
