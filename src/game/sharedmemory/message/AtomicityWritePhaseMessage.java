package game.sharedmemory.message;

import game.sharedmemory.communication.AtomicityMessage;
import game.sharedmemory.data.*;

public class AtomicityWritePhaseMessage extends AtomicityMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2419921302363625623L;

	public AtomicityWritePhaseMessage(String ip, int cnt, Key key, VersionValue vval){
		super(ip, cnt, key, vval);
	}
	
	/**
	 * Show the WRITE_PHASE message
	 */
	@Override
	public String toString(){
		
		return "[WRITE_PHASE]: " + super.toString();
	}
}
