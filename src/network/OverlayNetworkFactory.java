package network;

import android.util.Log;
import game.sharedmemory.readerwriter.RegisterControllerFactory;

public enum OverlayNetworkFactory {
	INSTANCE;
	
	private static final String TAG = OverlayNetworkFactory.class.getName();
	private OverlayNetwork overlayNetwork = null;
	
	public void setNetwork(int alg_type){
		if(alg_type == RegisterControllerFactory.ATOMIC_REGISTER){
			Log.i(TAG, "choose AtomicAPNetwork");
			overlayNetwork = AtomicAPNetwork.INSTANCE;
		}
		else {
			Log.i(TAG, "choose APNetwork");
			overlayNetwork = APNetwork.INSTANCE;
		}
	}
	
	public OverlayNetwork getOverlayNetwork(){
		
		return this.overlayNetwork;
	}
}
