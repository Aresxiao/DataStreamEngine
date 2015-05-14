package network;

import game.sharedmemory.communication.message.Message;


public interface OverlayNetwork {
	/**
	 * ������������
	 * @param string
	 * ���͵�ʱ����Ҫ��������֯��string�������ݡ�
	 */
	
	public void sendMsg(Message msg);
	
	public Message receiveData();
	public void connect();
	
}
