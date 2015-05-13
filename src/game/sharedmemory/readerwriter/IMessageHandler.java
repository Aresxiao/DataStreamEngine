package game.sharedmemory.readerwriter;

import game.sharedmemory.communication.Message;

public interface IMessageHandler {
	
	public void handleMessage(Message message);
}
