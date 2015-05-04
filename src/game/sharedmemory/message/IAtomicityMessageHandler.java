package game.sharedmemory.message;

import game.sharedmemory.communication.*;

public interface IAtomicityMessageHandler {
	
	public void handleAtomicityMessage(AtomicityMessage atomicityMessage);
}
