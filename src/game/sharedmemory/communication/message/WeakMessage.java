package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;

import game.sharedmemory.data.VersionValue;

import org.json.JSONException;


public class WeakMessage extends IPMessage{
	
	public WeakMessage( Key key, VersionValue versionValue){
		super(null, 0);
		this.msgType = IPMessage.WEAK_MESSAGE;
		
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String string = "msg[ ballId = " + key.toString() + versionValue.toString()+" ]";
		return string;
	}
	
	/**
	 * @return jsonObject.toString()
	 */
	public String getJSONObjectString() {
		try {
			jsonObject.put("msgType", msgType);
			key.putJSONObject(jsonObject);
			versionValue.putJSONObject(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
	
}
