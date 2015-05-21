package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import constant.Constant;

import game.sharedmemory.communication.message.Message;

public enum AtomicAPNetwork implements OverlayNetwork{
	INSTANCE;
	
	@Override
	public void sendMsg(Message msg) {
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
		
	}
	
	public void startToListen(){
		try {
			ServerSocket serverSocket = new ServerSocket(Constant.PORT);
			
			while(true){
				Socket socket = serverSocket.accept();
				
				DataInputStream inputStream = new DataInputStream(socket.getInputStream());
				DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
