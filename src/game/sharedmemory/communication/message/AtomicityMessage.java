package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;

public class AtomicityMessage extends IPMessage{
	
	
	public AtomicityMessage(String ip, int cnt, Key[] keys, VersionValue[] versionValues){
		super(ip, cnt);
		
		this.msgType = IPMessage.ATOMIC_MESSAGE;
		this.keys = keys;
		this.versionValues = versionValues;
	}
	
}
