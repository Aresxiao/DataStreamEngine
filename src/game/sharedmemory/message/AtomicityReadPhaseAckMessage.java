package game.sharedmemory.message;


import game.sharedmemory.communication.AtomicityMessage;
import game.sharedmemory.data.*;

public class AtomicityReadPhaseAckMessage extends AtomicityMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7939728228906935583L;
	
	public AtomicityReadPhaseAckMessage(String ip, int cnt, Key key, VersionValue vval)
	{
		super(ip, cnt, key, vval);
	}
	
	/**
	 * Show the READ_PHASE_ACK message
	 */
	@Override
	public String toString()
	{
		return "[READ_PHASE_ACK:] " + super.toString();
	}
}
