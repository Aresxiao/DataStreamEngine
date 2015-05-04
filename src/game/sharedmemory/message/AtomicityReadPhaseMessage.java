package game.sharedmemory.message;

import game.sharedmemory.communication.AtomicityMessage;
import game.sharedmemory.data.*;

public class AtomicityReadPhaseMessage extends AtomicityMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4273754284309958262L;

	public AtomicityReadPhaseMessage(String ip, int cnt, Key key)
	{
		super(ip, cnt, key, VersionValue.RESERVED_VERSIONVALUE);
	}
	
	/**
	 * Show the READ_PHASE message
	 */
	@Override
	public String toString()
	{
		return "[READ_PHASE]: " + super.toString();
	}
}
