package network;

import game.sharedmemory.communication.message.IPMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.AsyncTask;
import android.util.Log;
import buffer.BufferManager;

import constant.Constant;


public enum AtomicAPNetwork implements OverlayNetwork{
	INSTANCE;
	
	private static final String TAG = AtomicAPNetwork.class.getName();
	
	/** �����洢���ӵ����������  */
	Map<String, DataOutputStream> replicaMap = new HashMap<String, DataOutputStream>();
	
	private static final Executor exec = Executors.newCachedThreadPool();
	
	/** ����msg�е�ip���͸���Ӧserver  */
	@Override
	public void sendMsg(IPMessage msg) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String receiveMsg() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Socket socketLocal = new Socket(Constant.LOCAL_HOST_IP, Constant.PORT);
					DataOutputStream outputStream = new DataOutputStream(socketLocal.getOutputStream());
					replicaMap.put(Constant.LOCAL_HOST_IP, outputStream);
					Log.i(TAG, "1.launch a connect to " + Constant.LOCAL_HOST_IP);
					
					Socket socketRemote = new Socket(Constant.OTHER_HOST_IP, Constant.PORT);
					Log.i(TAG, "2.launch a connect to " + Constant.OTHER_HOST_IP);
					outputStream = new DataOutputStream(socketRemote.getOutputStream());
					replicaMap.put(Constant.OTHER_HOST_IP, outputStream);
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	public void startToListen(){
		try {
			ServerSocket serverSocket = new ServerSocket(Constant.PORT);
			Log.i(TAG, "startToListen");
			while(true){
				Socket socket = serverSocket.accept();
				String hostAddress = socket.getInetAddress().getHostAddress();
				Log.i(TAG, "addr = " + hostAddress);
				DataInputStream inputStream = new DataInputStream(socket.getInputStream());
				
				HandleSocket handleSocket = new HandleSocket(inputStream);
				handleSocket.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class HandleSocket extends Thread{
		
		private DataInputStream inputStream;
		
		public HandleSocket(DataInputStream inputStream){
			this.inputStream = inputStream;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i(TAG, "handle socket");
			while(true){
				try {
					String data = inputStream.readUTF();
					BufferManager.INSTANCE.addReceiveQueue(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * ����������ÿ����һ��socket����ͽ���һ�����ӣ����ҿ����߳�
	 */
	public class ServerTask extends AsyncTask<String, Void, Void>{
		
		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG,"doInBackgound");
			AtomicAPNetwork.INSTANCE.startToListen();
			return null;
		}
		
	}
}
