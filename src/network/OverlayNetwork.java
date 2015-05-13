package network;

import game.sharedmemory.communication.Message;


public interface OverlayNetwork {
	/**
	 * 用来发送数据
	 * @param string
	 * 发送的时候需要把数据组织成string类型数据。
	 */
	
	public void sendData(Message msg);
	
	public Message receiveData();
	public void connect();
	
}
