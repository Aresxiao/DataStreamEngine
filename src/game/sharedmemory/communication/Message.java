package game.sharedmemory.communication;

import game.sharedmemory.data.Key;

import game.sharedmemory.data.VersionValue;

import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = -7784455849848939000L;
	
	/** {@link Key}}*/
	protected Key key = Key.RESERVED_KEY;
	
	/** {@link VersionValue}}*/
	protected VersionValue versionValue = VersionValue.RESERVED_VERSIONVALUE;
	
	public Message( Key key, VersionValue versionValue){
		
		this.key = key;
		this.versionValue = versionValue;
	}
	
	/**
	 * @param key ��{@link Key}����
	 * @param value ��{@link Value}����
	 */
	public void put(Key key, VersionValue versionValue){
		
		this.key = key;
		this.versionValue = versionValue;
	}
	/**
	 * 
	 * @return key
	 */
	public Key getKey(){
		return this.key;
	}
	
	/**
	 * @return versionValue
	 */
	public VersionValue getVersionValue(){
		return this.versionValue;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String string = "msg[ ballId = " + key.toString() + versionValue.toString()+" ]";
		return string;
	}
	
	
}
