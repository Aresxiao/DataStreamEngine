package game.sharedmemory.readerwriter;

import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.message.AtomicityReadPhaseAckMessage;
import game.sharedmemory.communication.message.AtomicityWritePhaseAckMessage;
import game.sharedmemory.communication.message.IPMessage;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;
import game.sharedmemory.data.kvstore.KVStoreInMemory;
import group.GroupConfig;

public enum AtomicRegisterServer implements IMessageHandler{
	INSTANCE;

	@Override
	public void handleMessage(IPMessage message) {
		// TODO Auto-generated method stub
		String fromIP = message.getSenderIP();
		String myIP = GroupConfig.INSTANCE.getLocalNode().getNodeIp();
		int cnt = message.getCnt();
		Key key = message.getKey();
		
		if(message.getMsgType() == IPMessage.ATOMIC_READ_PHASE_MESSAGE){
			VersionValue versionValue = KVStoreInMemory.INSTANCE.getVersionValue(key);
			IPMessage atomicityReadPhaseAckMsg = new AtomicityReadPhaseAckMessage(myIP, cnt, key, versionValue);
			atomicityReadPhaseAckMsg.setReceiverIP(fromIP);
			MessagingService.INSTANCE.send(atomicityReadPhaseAckMsg);
		}
		else {
			VersionValue vvalNow = KVStoreInMemory.INSTANCE.getVersionValue(key);
			VersionValue vvalMax = VersionValue.max(message.getVersionValue(), vvalNow);
			KVStoreInMemory.INSTANCE.put(key, vvalMax);
			IPMessage atomicityWritePhaseAckMsg = new AtomicityWritePhaseAckMessage(myIP, cnt);
			atomicityWritePhaseAckMsg.setReceiverIP(fromIP);
			MessagingService.INSTANCE.send(atomicityWritePhaseAckMsg);
		}
	}
	
}
