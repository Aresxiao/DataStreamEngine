package game.sharedmemory.readerwriter;

public enum RegisterClientFactory {
	
	INSTANCE;
	
	private AbstractAtomicityRegisterClient atomicity_register_client = null;
	
	public void setAtomicityRegisterClient(int alg_type)
	{
		this.atomicity_register_client = MWMRAtomicityRegisterClient.INSTANCE();
	}
	
	public AbstractAtomicityRegisterClient getAtomicityRegisterClient()
	{
		return this.atomicity_register_client;
	}
	
}
