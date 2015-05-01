package network;

import game.sharedmemory.Key;
import game.sharedmemory.Value;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
	
	private static final long serialVersionUID = -7784455849848939000L;
	
	/** {@link Key}}*/
	protected Key key = Key.RESERVED_KEY;
	/** {@link Value}}*/
	protected Value value = Value.RESERVED_VALUE;
	
	protected int ballId = -1;
	
	public Message(int ballId, Key key, Value value){
		this.ballId = ballId;
		this.key = key;
		this.value = value;
	}
	
	/**
	 * @param key ��{@link Key}����
	 * @param value ��{@link Value}����
	 */
	public void put(int ballId, Key key, Value value){
		this.ballId = ballId;
		this.key = key;
		this.value = value;
	}
	/**
	 * @return key
	 */
	
	public Key getKey(){
		return this.key;
	}
	
	/**
	 * @return value
	 */
	public Value getValue(){
		
		return this.value;
	}
	
	/**
	 * @return ballId, ָ�����ǹ����ĸ�С�����Ϣ��
	 */
	public int getBallId(){
		
		return this.ballId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String string = "msg[ ballId = " + ballId +", " +key.toString() + value.toString()+" ]";
		return string;
	}
	
	
}
