package network;

import game.sharedmemory.communication.message.IPMessage;



public interface OverlayNetwork {
	/**
	 * ������������
	 * @param string
	 * ���͵�ʱ����Ҫ��������֯��string�������ݡ�
	 */
	
	public void sendMsg(IPMessage msg);
	
	public String receiveMsg();
	public void connect();
	
}
