package game.sharedmemory.readerwriter;

public enum RegisterControllerFactory {
	INSTANCE;
	
	public static final int NO_SUCH_REGISTER = -1;
	public static final int WEAK_REGISTER = 0;
	public static final int ATOMIC_REGISTER = 1;
	
	private AbstractRegisterController controller = null;
	
	public void setRegisterController(int alg_type){
		
		controller = WeakRegisterController.INSTANCE();
	}
	
	public AbstractRegisterController getRegisterController(){
		
		return this.controller;
	}
	
}
