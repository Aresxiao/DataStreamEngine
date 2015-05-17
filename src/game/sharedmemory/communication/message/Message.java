package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;

import game.sharedmemory.data.VersionValue;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
	
	
	/** {@link Key}}*/
	protected Key key = Key.RESERVED_KEY;
	
	/** {@link VersionValue}}*/
	protected VersionValue versionValue = VersionValue.RESERVED_VERSIONVALUE;
	
	private JSONObject jsonObject;
	
	protected int type = 0;
	
	
	
	public Message( Key key, VersionValue versionValue){
		jsonObject = new JSONObject();
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
	
	public String getJSONObjectString() {
		try {
			jsonObject.put("type", type);
			key.putJSONObject(jsonObject);
			versionValue.putJSONObject(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
	
}
