package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;

public class AtomicityMessage extends IPMessage{
	
	
	public AtomicityMessage(String ip, int cnt, Key key, VersionValue versionValue){
		super(ip, cnt);
		
		this.msgType = IPMessage.ATOMIC_MESSAGE;
		this.key = key;
		this.versionValue = versionValue;
	}
	
	@Override
	public String getJSONObjectString() {
		// TODO Auto-generated method stub
		return super.getJSONObjectString();
	}
	
	
}
