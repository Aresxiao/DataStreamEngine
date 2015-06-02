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
			if(controller instanceof WeakRegisterController)
				Log.i(TAG, "set WeakRegisterController");
			Log.i(TAG, "WEAK_REGISTER");
		}
		else {
			controller = AtomicRegisterController.INSTANCE();
			if(controller instanceof AtomicRegisterController)
				Log.i(TAG, "set AtomicRegisterController");
			Log.i(TAG, "ATOMIC_REGISTER");
		}
	}
	
	public AbstractRegisterController getRegisterController(){
		if(controller instanceof WeakRegisterController)
			Log.i(TAG, "get WeakRegisterController");
		else if(controller instanceof AtomicRegisterController)
			Log.i(TAG, "get AtomicRegisterController");
		else 
			Log.i(TAG, "get no controller");
		
		return this.controller;
	}
	
}
