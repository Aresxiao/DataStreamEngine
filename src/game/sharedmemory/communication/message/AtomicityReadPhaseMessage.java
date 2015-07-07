package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;


public class AtomicityReadPhaseMessage extends AtomicityMessage {
	
	public AtomicityReadPhaseMessage(String ip, int cnt, Key[] keys){
		super(ip, cnt, keys, VersionValue.RESERVED_VERSION_VALUES);
		
		this.msgType = IPMessage.ATOMIC_READ_PHASE_MESSAGE;
	}
}
