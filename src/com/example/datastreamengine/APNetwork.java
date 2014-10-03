package com.example.datastreamengine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class APNetwork implements OverlayNetwork {

	String host;
	int port;
	ServerSocket serverSocket;
	Socket socket;
	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	public APNetwork(){
		host=null;
		port = -1;
	}
	
	public APNetwork(int port){
		host=null;
		this.port = port;
	}
	public APNetwork(String host,int port){
		this.host = host;
		this.port = port;
	}
	
	public void listenConnect() throws IOException{
		serverSocket = new ServerSocket(port);
		socket = serverSocket.accept();
		inputStream = new ObjectInputStream(socket.getInputStream());
		outputStream = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public void connectServer() throws UnknownHostException, IOException{
		socket = new Socket(host,port);
		inputStream = new ObjectInputStream(socket.getInputStream());
		outputStream = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public String getHost(){
		if(host==null)
			return null;
		return host;
	}
	
	public int getPort(){
		return port;
	}
	
	public void setHost(String host){
		this.host = host;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	public void sendData(String string) {
		// TODO Auto-generated method stub
		
	}

	public void receiveData(String string) {
		// TODO Auto-generated method stub
		
	}

}
