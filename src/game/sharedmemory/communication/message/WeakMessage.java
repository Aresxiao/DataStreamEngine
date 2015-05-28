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
	 * @param key ��{@link Key}����
	 * @param value ��{@link Value}����
	 */
	public void put(Key key, VersionValue versionValue){
		
		this.key = key;
		this.versionValue = versionValue;
	}
	
}
