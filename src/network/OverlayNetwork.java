package network;

import android.os.AsyncTask;

public interface OverlayNetwork {
	/**
	 * ������������
	 * @param string
	 * ���͵�ʱ����Ҫ��������֯��string�������ݡ�
	 */
	
	public void sendData(String string);
	
	public String receiveData();
	public void connect();
	
}
