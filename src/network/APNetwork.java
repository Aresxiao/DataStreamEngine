package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import constant.Constant;

import dse.DataStreamEngine;

public enum APNetwork implements OverlayNetwork {
	
	INSTANCE;
	String hostIP = null;
	int port = Constant.PORT;
	boolean serverFlag;
	boolean connectedFalg;
	
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
				if(Constant.SERVERFLAG > 0){
					try {
						System.out.println("Server,Listen");
						serverSocket = new ServerSocket(port);
						socket = serverSocket.accept();
						System.out.println("server: Connect success");
						connectedFalg = true;
						inputStream = new DataInputStream(socket.getInputStream());
						outputStream = new DataOutputStream(socket.getOutputStream());
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
						connectedFalg = true;
						System.out.println("success connect to server");
						inputStream = new DataInputStream(socket.getInputStream());
						outputStream = new DataOutputStream(socket.getOutputStream());
						System.out.println("Success connect");
						
						
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
				if(connectedFalg == true){
					new Thread(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							while(true){
								try {
									String data = inputStream.readUTF();
									DataStreamEngine.INSTANCE.addReceiveQueue(data);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}).start();
					
					DataStreamEngine.INSTANCE.startNetworkThread();
					
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
	public void sendData(String string) {
		// TODO Auto-generated method stub
		try {
			if(connectedFalg){
				outputStream.writeUTF(string);
				outputStream.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String receiveData() {
		// TODO Auto-generated method stub
		String string=null;
		try {
			string = inputStream.readUTF();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
	
}
