package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;

import game.sharedmemory.data.VersionValue;


public class WeakMessage extends IPMessage{
	
	public WeakMessage( Key[] keys, VersionValue[] versionValues){
		super(IPMessage.DEFAULT_WORD, 0);
		this.msgType = IPMessage.WEAK_MESSAGE;
		
		this.keys = keys;
		this.versionValues = versionValues;
	}
	
	/**
	 * @param keys 是{@link Key[]}类型
	 * @param values 是{@link Value[]}类型
	 */
	public void put(Key[] keys, VersionValue[] versionValues){
		
		this.keys = keys;
		this.versionValues = versionValues;
	}
	
}
