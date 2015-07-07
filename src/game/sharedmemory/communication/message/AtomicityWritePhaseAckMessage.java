package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;

public class AtomicityWritePhaseAckMessage extends AtomicityMessage {
	
	public AtomicityWritePhaseAckMessage(String ip, int cnt){
		super(ip, cnt, Key.RESERVED_KEYS, VersionValue.RESERVED_VERSION_VALUES);
		this.msgType = IPMessage.ATOMIC_WRITE_PHASE_ACK_MESSAGE;
	}
}
