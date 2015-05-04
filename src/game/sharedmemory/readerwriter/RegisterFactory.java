package game.sharedmemory.readerwriter;

public enum RegisterFactory {
	
	INSTANCE;
	
	private AbstractAtomicityRegisterClient atomicity_register_client = null;
}
