package game.sharedmemory;

import java.io.Serializable;

public class Value implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8979141031800309461L;
	
	public static final Value RESERVED_VALUE = new Value(0f);
	
	private float val;
	private int sendCount;
	
	public Value(float val){
		this.val = val;
		sendCount = 1;
	}
	
	public Value(float val, int sendCount){
		this.val = val;
		this.sendCount = sendCount;
	}
	
	public float getVal(){
		
		return this.val;
	}
	
	public boolean isNeedSend(){
		
		return sendCount == 0?false:true;
	}
	
	public void setSendCount(int sendCount){
		
		this.sendCount = sendCount;
	}
	
	public int getSendCount(){
		
		return this.sendCount;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "value: sendCount = " + sendCount + ",val = " + val;
	}
	
	
}
