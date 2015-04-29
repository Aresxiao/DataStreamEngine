package game.sharedmemory;


import java.io.Serializable;

public class Key implements Serializable{
	
	/**
	 * auto generate serialVersionUID
	 */
	private static final long serialVersionUID = -8774229832514559805L;
	
	public static final Key RESERVED_KEY = new Key("RESERVED_KEY");
	
	private String key_str;
	
	public Key(String key_str){
		this.key_str = key_str;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "key:"+key_str;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (! (obj instanceof Key))
			return false;
		
		Key key = (Key) obj;
		return this.key_str.equals(key.key_str);
	}
	
}
