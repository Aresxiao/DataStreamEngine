package network;

import data.SharedData;

public class NetworkReceiveThread extends Thread{
	
	OverlayNetwork overlayNetwork;
	SharedData sharedData;
	
	public NetworkReceiveThread(SharedData sharedData,OverlayNetwork network){
		this.sharedData = sharedData;
		this.overlayNetwork = network;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			String data = overlayNetwork.receiveData();
			sharedData.addData(data);
		}
	}
	
	
}
