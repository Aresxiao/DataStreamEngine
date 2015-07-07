package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;


public class AtomicityWritePhaseMessage extends AtomicityMessage {
	
	/**
	 * @description 这是写阶段的信息构造函数
	 * @param ip
	 * @param cnt
	 * @param keys
	 * @param versionValues
	 */
	public AtomicityWritePhaseMessage(String ip, int cnt, Key[] keys, VersionValue[] versionValues){
		super(ip, cnt, keys, versionValues);
		this.msgType = IPMessage.ATOMIC_WRITE_PHASE_MESSAGE;
	}
}
