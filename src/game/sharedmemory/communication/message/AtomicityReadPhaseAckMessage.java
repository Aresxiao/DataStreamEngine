package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;

public class AtomicityReadPhaseAckMessage extends AtomicityMessage{
	
	/**
	 * @description 这是读阶段的Ack信息
	 * @param ip
	 * @param cnt
	 * @param key
	 * @param versionValue
	 */
	public AtomicityReadPhaseAckMessage(String ip, int cnt, Key[] keys, VersionValue[] versionValues){
		super(ip, cnt, keys, versionValues);
		this.msgType = IPMessage.ATOMIC_READ_PHASE_ACK_MESSAGE;
	}
}
