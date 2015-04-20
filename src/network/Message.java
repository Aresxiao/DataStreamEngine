package network;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
	
	private static final long serialVersionUID = -7784455849848939000L;
	
	/** �����洢��ֵ�� */
	Map<String, Float> kvMap = new HashMap<String, Float>();
	
	/**
	 * @param key ��String����
	 * @param value ��float����
	 */
	public void put(String key,Float value){
		
		kvMap.put(key, value);
	}
	
	/**
	 * @return key�ļ���
	 */
	public Set<String> keySet(){
		return kvMap.keySet();
	}
	
	/**
	 * @return Message�е�����ӳ���
	 */
	public Set<Map.Entry<String, Float>> entrySet(){
		
		return kvMap.entrySet();
	}
	
	/**
	 * @param key ��õ���ֵ��key
	 * @return ����keyӳ���value
	 */
	public Float get(String key){
		
		return kvMap.get(key);
	}
	
}
