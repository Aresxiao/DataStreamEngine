package game.sharedmemory.communication;

import game.sharedmemory.communication.message.Message;
import game.sharedmemory.readerwriter.RegisterControllerFactory;
import network.APNetwork;

public enum MessagingService implements IReceiver{
	INSTANCE;
	
	@Override
	public void onReceive(Message msg) {
		// TODO Auto-generated method stub
		RegisterControllerFactory.INSTANCE.getRegisterController().handleMessage(msg);
	}
	
	public void send(Message msg){
		APNetwork.INSTANCE.sendData(msg);
	}
	
}
