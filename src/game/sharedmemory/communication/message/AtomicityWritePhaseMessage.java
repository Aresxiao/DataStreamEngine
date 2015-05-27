package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;


public class AtomicityWritePhaseMessage extends AtomicityMessage {
	
	/**
	 * @description 这是写阶段的信息构造函数
	 * @param ip
	 * @param cnt
	 * @param key
	 * @param versionValue
	 */
	public AtomicityWritePhaseMessage(String ip, int cnt, Key key, VersionValue versionValue){
		super(ip, cnt, key, versionValue);
		this.msgType = IPMessage.ATOMIC_WRITE_PHASE_MESSAGE;
	}
}
