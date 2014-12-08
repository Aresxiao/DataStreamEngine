package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


import constant.Constant;
import dse.DSEInterface;

public class APNetwork implements OverlayNetwork {

	String hostIP;
	int port;
	boolean serverFlag;
	boolean connectedFalg;
	
	ServerSocket serverSocket;
	Socket socket;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	DSEInterface dse;
	
	public APNetwork(DSEInterface dseInterface){
		port = Constant.PORT;
		connectedFalg = false;
		if(Constant.SERVERFLAG>0){
			serverFlag = true;
			hostIP = null;
		}
		else{
			serverFlag = false;
			hostIP = Constant.HOST_IP;
		}
		this.dse = dseInterface;
	}
	
	public void connect(){
		//System.out.println(port+" : "+hostIP);
		if(serverFlag){
			try {
				
				serverSocket = new ServerSocket(port);
				socket = serverSocket.accept();
				System.out.println("server: Connect success");
				connectedFalg = true;
				inputStream = new DataInputStream(socket.getInputStream());
				outputStream = new DataOutputStream(socket.getOutputStream());
				new Thread(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						while(true){
							try {
								String dataString = inputStream.readUTF();
								dse.updateDSEState(1,dataString);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				socket = new Socket(hostIP,port);
				System.out.println("Client");
				connectedFalg = true;
				System.out.println("success connect to server");
				inputStream = new DataInputStream(socket.getInputStream());
				outputStream = new DataOutputStream(socket.getOutputStream());
				System.out.println("Success connect");
				new Thread(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						while(true){
							try {
								String dataString = inputStream.readUTF();
								dse.updateDSEState(1,dataString);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
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
	
	public void setPort(int port){
		this.port = port;
	}
	
	public void sendData(String string) {
		// TODO Auto-generated method stub
		try {
			
			outputStream.writeUTF(string);
			outputStream.flush();
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
