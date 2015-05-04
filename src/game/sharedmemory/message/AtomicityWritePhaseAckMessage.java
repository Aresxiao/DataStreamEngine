package game.sharedmemory.message;

import game.sharedmemory.communication.AtomicityMessage;
import game.sharedmemory.data.*;

public class AtomicityWritePhaseAckMessage extends AtomicityMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4592373692815591786L;

	public AtomicityWritePhaseAckMessage(String ip, int cnt)
	{
		super(ip, cnt, Key.RESERVED_KEY, VersionValue.RESERVED_VERSIONVALUE);
	}
	
	/**
	 * Show the WRITE_PHASE_ACK message
	 */
	@Override
	public String toString()
	{
		return "[WRITE_PHASE_ACK]: " + super.toString();
	}
}
