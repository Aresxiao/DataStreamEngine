package game.sharedmemory.communication.message;

import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.Version;
import game.sharedmemory.data.VersionValue;

import org.json.JSONException;
import org.json.JSONObject;


public class IPMessage {
	
	public static final int WEAK_MESSAGE = 1;
	public static final int ATOMIC_MESSAGE = 2;
	public static final int ATOMIC_READ_PHASE_MESSAGE = 3;
	public static final int ATOMIC_READ_PHASE_ACK_MESSAGE = 4;
	public static final int ATOMIC_WRITE_PHASE_MESSAGE = 5;
	public static final int ATOMIC_WRITE_PHASE_ACK_MESSAGE = 6;
	
	public static final String DEFAULT_WORD = "DEFAULT_WORD";
	
	protected JSONObject jsonObject = new JSONObject();
	
	/** 标记msg类型 */
	protected int msgType;
	
	/** 消息发送者ip */
	private String senderIP;
	
	private String receiverIP;
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
	
	public void setReceiverIP(String receiverIP){
		
		this.receiverIP = receiverIP;
	}
	
	public String getReceiverIP(){
		
		return this.receiverIP;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "msgType = " + msgType + "ip = " + senderIP + ",cnt = " + cnt + ",msgType = " + msgType + ",key = "
				+ key.toString() + ",versionValue = " + versionValue.toString();
		return str;
	}
	
	public static IPMessage getIPMessageFromJSON(JSONObject json){
		IPMessage message = null;
		try {
			
			int type = json.getInt("msgType");
			int id = json.getInt("key.id");
			int cnt = json.getInt("cnt");
			int seqno = json.getInt("version.seqno");
			String ip = json.getString("senderIP");
			float vx = (float) json.getDouble("value.vx");
			float vy = (float) json.getDouble("value.vy");
			float locx = (float) json.getDouble("value.locx");
			float locy = (float) json.getDouble("value.locy");
			
			Key key = new Key(id);
			Version version = new Version(seqno);
			Value value = new Value(vx, vy, locx, locy);
			VersionValue versionValue = new VersionValue(version, value);
			
			switch (type) {
				case IPMessage.WEAK_MESSAGE:
					message = new WeakMessage(key, versionValue);
					break;
					
				case IPMessage.ATOMIC_READ_PHASE_MESSAGE:
					message = new AtomicityReadPhaseMessage(ip, cnt, key);
					break;
					
				case IPMessage.ATOMIC_READ_PHASE_ACK_MESSAGE:
					message = new AtomicityReadPhaseAckMessage(ip, cnt, key, versionValue);
					break;
					
				case IPMessage.ATOMIC_WRITE_PHASE_MESSAGE:
					message = new AtomicityWritePhaseMessage(ip, cnt, key, versionValue);
					break;
					
				case IPMessage.ATOMIC_WRITE_PHASE_ACK_MESSAGE:
					message = new AtomicityWritePhaseAckMessage(ip, cnt);
					break;
				default:
					break;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
}
