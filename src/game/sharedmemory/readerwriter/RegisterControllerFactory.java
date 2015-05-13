package game.sharedmemory.readerwriter;

public enum RegisterControllerFactory {
	INSTANCE;
	
	private AbstractRegisterController controller = null;
	
	public void setRegisterController(int alg_type){
		
		controller = WeakRegisterController.INSTANCE();
	}
	
	public AbstractRegisterController getRegisterController(){
		
		return this.controller;
	}
	
}
