package game.sharedmemory.communication;

import game.sharedmemory.communication.message.IPMessage;


public interface IReceiver {
	
	public void onReceive(IPMessage msg);
}
