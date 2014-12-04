package network;

import data.SharedData;

public class NetworkSendThread extends Thread{
	
	SharedData sharedData;
	OverlayNetwork overlayNetwork;
	
	public NetworkSendThread(SharedData sharedData,OverlayNetwork network){
		this.sharedData = sharedData;
		this.overlayNetwork = network;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			String data = sharedData.getData();
			while(data==null){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				data = sharedData.getData();
			}
			overlayNetwork.sendData(data);
		}
	}
	
}
