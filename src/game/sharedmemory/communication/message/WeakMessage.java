package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;

import game.sharedmemory.data.VersionValue;


public class WeakMessage extends IPMessage{
	
	public WeakMessage( Key key, VersionValue versionValue){
		super(null, 0);
		this.msgType = IPMessage.WEAK_MESSAGE;
		
		this.key = key;
		this.versionValue = versionValue;
	}
	
	/**
	 * @param key 是{@link Key}类型
	 * @param value 是{@link Value}类型
	 */
	public void put(Key key, VersionValue versionValue){
		
		this.key = key;
		this.versionValue = versionValue;
	}
	
}
