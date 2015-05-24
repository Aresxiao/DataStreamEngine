package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.VersionValue;

import org.json.JSONException;
import org.json.JSONObject;


public class IPMessage {
	
	public static int WEAK_MESSAGE = 1;
	public static int ATOMIC_MESSAGE = 2;
	public static int ATOMIC_READ_PHASE_MESSAGE = 3;
	public static int ATOMIC_READ_PHASE_ACK_MESSAGE = 4;
	public static int ATOMIC_WRITE_PHASE_MESSAGE = 5;
	public static int ATOMIC_WRITE_PHASE_ACK_MESSAGE = 6;
	
	protected JSONObject jsonObject = new JSONObject();
	
	/** 标记msg类型 */
	protected int msgType;
	
	/** 消息发送者ip */
	private String senderIP;
	
	private int cnt;
	
	/** {@link Key}}*/
	protected Key key = Key.RESERVED_KEY;
	
	/** {@link VersionValue}}*/
	protected VersionValue versionValue = VersionValue.RESERVED_VERSIONVALUE;
	
	public IPMessage(String ip){
		this.senderIP = ip;
	}
	
	public IPMessage(final String ip, final int cnt){
		this.senderIP = ip;
		this.cnt = cnt;
	}
	
	/**
	 * @return senderIP
	 */
	public String getSenderIP(){
		
		return this.senderIP;
	}
	
	/**
	 * @return cnt
	 */
	public int getCnt(){
		
		return this.cnt;
	}
	
	/**
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
	
	/**
	 * @return msgType
	 */
	public int getMsgType(){
		
		return this.msgType;
	}
	
	/**
	 * @return jsonObject.toString()
	 */
	public String getJSONObjectString(){
		try {
			jsonObject.put("senderIP", this.senderIP);
			jsonObject.put("cnt", this.cnt);
			jsonObject.put("msgType", this.msgType);
			key.putJSONObject(jsonObject);
			versionValue.putJSONObject(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
}
