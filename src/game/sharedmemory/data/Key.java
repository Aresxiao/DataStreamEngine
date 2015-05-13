package game.sharedmemory.data;


import java.io.Serializable;

public class Key implements Serializable{
	
	/**
	 * auto generate serialVersionUID
	 */
	private static final long serialVersionUID = -8774229832514559805L;
	
	public static final Key RESERVED_KEY = new Key(-1);
	
	int id;
	//private String key_str;
	
	public Key(int id){
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		
		return this.id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "key:"+id;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (! (obj instanceof Key))
			return false;
		
		Key key = (Key) obj;
		return this.id == key.id? true:false;
	}
	
}