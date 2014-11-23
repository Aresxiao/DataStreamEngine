package network;

public interface OverlayNetwork {
	/**
	 * 用来发送数据
	 * @param string,发送的时候需要把数据组织成string类型数据。
	 */
	public void sendData(String string);
	
	public String receiveData();
	public void connect();
}
