package game.sharedmemory.readerwriter;

import game.sharedmemory.communication.*;



public interface IAtomicityMessageHandler {

	public void handleAtomicityMessage(AtomicityMessage atomicityMessage);
}
