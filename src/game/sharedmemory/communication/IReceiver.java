package game.sharedmemory.communication;

import game.sharedmemory.communication.message.Message;

public interface IReceiver {
	
	public void onReceive(Message msg);
}
