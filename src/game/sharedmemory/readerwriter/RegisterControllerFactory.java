package game.sharedmemory.readerwriter;

import android.util.Log;

public enum RegisterControllerFactory {
	INSTANCE;
	
	private static String TAG = RegisterControllerFactory.class.getName();
	
	public static final int NO_SUCH_REGISTER = -1;
	public static final int WEAK_REGISTER = 0;
	public static final int ATOMIC_REGISTER = 1;
	
	private AbstractRegisterController controller = null;
	
	public void setRegisterController(int alg_type){
		if(alg_type == WEAK_REGISTER){
			
			controller = WeakRegisterController.INSTANCE();
			Log.i(TAG, "WEAK_REGISTER");
		}
		else {
			controller = AtomicRegisterController.INSTANCE();
			Log.i(TAG, "ATOMIC_REGISTER");
		}
	}
	
	public AbstractRegisterController getRegisterController(){
		
		return this.controller;
	}
	
}
