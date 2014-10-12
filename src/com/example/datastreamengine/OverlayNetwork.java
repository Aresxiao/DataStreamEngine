package com.example.datastreamengine;

public interface OverlayNetwork {
	public void sendData(Object object);
	public void receiveData(Object object);
	public void connect();
}
