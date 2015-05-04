package game.sharedmemory.readerwriter;

import game.sharedmemory.communication.*;
import game.sharedmemory.data.*;
import game.sharedmemory.data.kvstore.*;
import game.sharedmemory.message.*;

public enum AtomicityRegisterServer implements IAtomicityMessageHandler{

	INSTANCE;
	
	@Override
	public void handleAtomicityMessage(AtomicityMessage atomicityMessage)
	{
		String from_ip = atomicityMessage.getSenderIP();
		String my_ip = "";
		int cnt = atomicityMessage.getCnt();
		Key key = atomicityMessage.getKey();
		
		/**
		 * responds to the message of type {@link AtomicityReadPhaseMessage} 
		 * with message of type {@link AtomicityReadAckPhaseMessage},
		 * including the {@link Key} and the {@link VersionValue} 
		 * (it may be {@link RESERVED_VERSIONVALUE}) found
		 * in the server replica
		 */
		if (atomicityMessage instanceof AtomicityReadPhaseMessage)
		{	
			// TODO: refactor KVStore
			VersionValue vval = KVStoreInMemory.INSTANCE.getVersionValue(key);
			IPMessage atomicity_read_phase_ack_msg = new AtomicityReadPhaseAckMessage(my_ip, cnt, key, vval);
			MessagingService.INSTANCE.sendOneWay(from_ip, atomicity_read_phase_ack_msg);
		}
		else // (atomicityMessage instanceof AtomicityWritePhaseMessage)
		{
			VersionValue vval_now = KVStoreInMemory.INSTANCE.getVersionValue(key);
			VersionValue vval_max = VersionValue.max(atomicityMessage.getVersionValue(), vval_now);
			KVStoreInMemory.INSTANCE.put(key, vval_max);
			IPMessage atomicity_write_phase_ack_rmsg = new AtomicityWritePhaseAckMessage(my_ip, cnt);
			MessagingService.INSTANCE.sendOneWay(from_ip, atomicity_write_phase_ack_rmsg);
		}
			
	}
}
