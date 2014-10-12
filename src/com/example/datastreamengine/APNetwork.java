package com.example.datastreamengine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.widget.Toast;

public class APNetwork implements OverlayNetwork {

	String hostIP;
	int port;
	boolean serverFlag;
	boolean connectedFalg;
	
	ServerSocket serverSocket;
	Socket socket;
	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	
	
	public APNetwork(){
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
	}
	
	public void connect(){
		System.out.println(port+" : "+hostIP);
		if(serverFlag){
			try {
				
				serverSocket = new ServerSocket(port);
				socket = serverSocket.accept();
				System.out.println("server: Connect success");
				connectedFalg = true;
				inputStream = new ObjectInputStream(socket.getInputStream());
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				
				socket = new Socket(hostIP,port);
				
				connectedFalg = true;
				System.out.println("success connect to server");
				inputStream = new ObjectInputStream(socket.getInputStream());
				outputStream = new ObjectOutputStream(socket.getOutputStream());
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
	
	public void sendData(Object object) {
		// TODO Auto-generated method stub
		try {
			outputStream.writeObject(object);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveData(Object object) {
		// TODO Auto-generated method stub
		
		try {
			object = inputStream.readObject();
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
	}

}
