package game.sharedmemory.readerwriter;

import game.sharedmemory.communication.message.IPMessage;


public interface IMessageHandler {
	
	public void handleMessage(IPMessage message);
}
