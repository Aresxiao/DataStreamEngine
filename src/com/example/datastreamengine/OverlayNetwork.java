package com.example.datastreamengine;

public interface OverlayNetwork {
	public void sendData(String string);
	public String receiveData();
	public void connect();
}
