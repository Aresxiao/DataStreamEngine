package network;

import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.message.Message;


import java.io.DataInputStream;
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
	
	String hostIP = null;
	int port = Constant.PORT;
	boolean serverFlag;
	boolean connectedFalg;
	
	int countReceive = 0;
	int countSend = 0;
	
	ServerSocket serverSocket = null;
	Socket socket = null;
	ObjectInputStream inputStream = null;
	ObjectOutputStream outputStream = null;
	DataInputStream in = null;
	private static final Executor exec = Executors.newCachedThreadPool();
	
	/**
	 * @description 调用这个方法会尝试和另一台设备建立网络连接
	 */
	public void connect(){
		
		Runnable connectTask = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(Constant.SERVERFLAG > 0){
					try {
						System.out.println("Server,Listen");
						serverSocket = new ServerSocket(port);
						socket = serverSocket.accept();
						System.out.println("server: Connect success");
						
						inputStream = new ObjectInputStream(socket.getInputStream());
						System.out.println("server: get input stream");
						outputStream = new ObjectOutputStream(socket.getOutputStream());
						System.out.println("server: get output stream");
						connectedFalg = true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					hostIP = Constant.HOST_IP;
					try {
						socket = new Socket(hostIP,port);
						System.out.println("Client");
						
						System.out.println("success connect to server1111111111");
						outputStream = new ObjectOutputStream(socket.getOutputStream());
						System.out.println("get output stream");
						inputStream = new ObjectInputStream(socket.getInputStream());
						System.out.println("Success connect");
						connectedFalg = true;
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("no host");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("failed to  connect");
					}
				}
				System.out.println("connectedFlag == true");
				if(connectedFalg == true){
					
					new Thread(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							while(true){
								
								try {
									
									Message msg = (Message) inputStream.readObject();
									
									if(countReceive < 10){
										Log.i(TAG, msg.toString());
										countReceive++;
									}
									BufferManager.INSTANCE.addReceiveQueue(msg);
									
								} catch (OptionalDataException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
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
	
	public String getHost(){
		if(hostIP==null)
			return null;
		return hostIP;
	}
	
	public int getPort(){
		return port;
	}
	
	public void setHost(String host){
		this.hostIP = host;
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
				
				outputStream.reset();
				outputStream.writeObject(msg);
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
	
	public Message receiveMsg() {
		// TODO Auto-generated method stub
		
		Message msg = null;
		
		try {
			msg = (Message)inputStream.readObject();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return msg;
	}
	
}
