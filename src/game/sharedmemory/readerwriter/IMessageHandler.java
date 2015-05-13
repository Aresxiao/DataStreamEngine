package game.sharedmemory.readerwriter;

import game.sharedmemory.communication.message.Message;

public interface IMessageHandler {
	
	public void handleMessage(Message message);
}
