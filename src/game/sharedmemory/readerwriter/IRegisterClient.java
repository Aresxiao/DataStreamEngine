package game.sharedmemory.readerwriter;

import game.sharedmemory.data.*;

public interface IRegisterClient {

	/**
	 * "get" operation invocation
	 * @param key key to get
	 * @return Versioned value associated with the key
	 */
	public VersionValue get(Key key);

	/**
	 * "put" operation invocation
	 * @param key {@link Key} to put
	 * @param val non-versioned value associated with the key
	 * @return {@link VersionValue} to put associated with the key
	 */
	public VersionValue put(Key key, Value vval);
}
