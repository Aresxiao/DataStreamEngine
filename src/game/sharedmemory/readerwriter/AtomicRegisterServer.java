package game.sharedmemory.readerwriter;

import android.util.Log;
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
	
	private static final String TAG = AtomicRegisterServer.class.getName();
	
	@Override
	public void handleMessage(IPMessage message) {
		// TODO Auto-generated method stub
		String fromIP = message.getSenderIP();
		String myIP = GroupConfig.INSTANCE.getLocalNode().getNodeIp();
		int cnt = message.getCnt();
		Key[] keys = message.getKeys();
		
		if(message.getMsgType() == IPMessage.ATOMIC_READ_PHASE_MESSAGE){
			VersionValue[] versionValues = new VersionValue[keys.length];
			for(int i = 0; i < keys.length; i++){
				versionValues[i] = KVStoreInMemory.INSTANCE.getVersionValue(keys[i]);
			}
			IPMessage atomicityReadPhaseAckMsg = new AtomicityReadPhaseAckMessage(myIP, cnt, keys, versionValues);
			atomicityReadPhaseAckMsg.setReceiverIP(fromIP);
			MessagingService.INSTANCE.send(atomicityReadPhaseAckMsg);
		}
		else {
			VersionValue[] versionValues = message.getVersionValues();
			for(int i = 0; i < keys.length; i++){
				VersionValue vvalNow = KVStoreInMemory.INSTANCE.getVersionValue(keys[i]);
				VersionValue vvalMax = VersionValue.max(versionValues[i],vvalNow);
				
				KVStoreInMemory.INSTANCE.put(keys[i], vvalMax);
			}
			//VersionValue vvalNow = KVStoreInMemory.INSTANCE.getVersionValue(key);
			//VersionValue vvalMax = VersionValue.max(message.getVersionValue(), vvalNow);
			// Log.i(TAG, "msg:"+message.toString()+",\n vvalMax = "+vvalMax.toString());
			//KVStoreInMemory.INSTANCE.put(key, vvalMax);
			IPMessage atomicityWritePhaseAckMsg = new AtomicityWritePhaseAckMessage(myIP, cnt);
			atomicityWritePhaseAckMsg.setReceiverIP(fromIP);
			MessagingService.INSTANCE.send(atomicityWritePhaseAckMsg);
		}
	}
	
}
