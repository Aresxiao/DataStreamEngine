package network;

import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.message.Message;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import buffer.BufferManager;


import android.util.Log;

import constant.Constant;


public enum APNetwork implements OverlayNetwork {
	
	INSTANCE;
	
	private static final String TAG = APNetwork.class.getName();
	
	int port = Constant.PORT;
	boolean connectedFalg;
	
	int countReceive = 0;
	int countSend = 0;
	
	ServerSocket serverSocket = null;
	Socket socket = null;
	DataInputStream inputStream = null;
	DataOutputStream outputStream = null;
	
	private static final Executor exec = Executors.newCachedThreadPool();
	
	/**
	 * @description 调用这个方法会尝试和另一台设备建立网络连接
	 */
	public void connect(){
		
		Runnable connectTask = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean haveException = true;
				
				try {
					socket = new Socket(Constant.OTHER_HOST_IP, port);
					haveException = false;
					connectedFalg = true;
					inputStream = new DataInputStream(socket.getInputStream());
					outputStream = new DataOutputStream(socket.getOutputStream());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(haveException){
					try {
						serverSocket = new ServerSocket(port);
						socket = serverSocket.accept();
						connectedFalg = true;
						outputStream = new DataOutputStream(socket.getOutputStream());
						inputStream = new DataInputStream(socket.getInputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				System.out.println("connectedFlag == true");
				if(connectedFalg == true){
					
					new Thread(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							while(true){
								
								try {
									
									String data = inputStream.readUTF();
									
									if(countReceive < 10){
										Log.i(TAG, data);
										countReceive++;
									}
									BufferManager.INSTANCE.addReceiveQueue(data);
									
								} catch (OptionalDataException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}).start();
					//System.out.println("APNetwork: start network Thread");
				}
			}
		};
		
		exec.execute(connectTask);
		
	}
	
	public boolean getStatus(){
		return connectedFalg;
	}
	
	public int getPort(){
		return port;
	}
	
	/**
	 * @param port
	 */
	public void setPort(int port){
		this.port = port;
	}
	
	/**
	 * @param string 是需要发送的数据
	 */
	public void sendMsg(Message msg) {
		// TODO Auto-generated method stub
		
		try {
			if(connectedFalg){
				
				outputStream.writeUTF(msg.getJSONObjectString());
				outputStream.flush();
				if(countSend < 10){
					Log.i(TAG, "send msg "+msg.toString());
					countSend++;
				}
				//
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String receiveMsg() {
		// TODO Auto-generated method stub
		
		String data = null;
		
		try {
			data = inputStream.readUTF();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
}
