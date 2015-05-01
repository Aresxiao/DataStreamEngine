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
	ObjectInputStream inputStream = null;
	ObjectOutputStream outputStream = null;
	
	private static final Executor exec = Executors.newCachedThreadPool();
	
	/**
	 * @description ������������᳢�Ժ���һ̨�豸������������
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
						
						inputStream = new ObjectInputStream(socket.getInputStream());
						System.out.println("server: get input stream");
						outputStream = new ObjectOutputStream(socket.getOutputStream());
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
						System.out.println("success connect to server1111111111");
						outputStream = new ObjectOutputStream(socket.getOutputStream());
						System.out.println("get output stream");
						inputStream = new ObjectInputStream(socket.getInputStream());
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
				System.out.println("connectedFlag == true");
				if(connectedFalg == true){
					
					new Thread(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							while(true){
								
								try {
									inputStream.readUTF();
									//DataStreamEngine.INSTANCE.addReceiveQueue(msg);
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
					System.out.println("APNetwork: start network Thread");
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
	 * @param string ����Ҫ���͵�����
	 */
	public void sendData(Message msg) {
		// TODO Auto-generated method stub
		try {
			if(connectedFalg){
				//outputStream.writeObject(msg);
				outputStream.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Message receiveData() {
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
