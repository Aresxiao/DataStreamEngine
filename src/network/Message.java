package network;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
	
	private static final long serialVersionUID = -7784455849848939000L;
	
	/** 用来存储键值对 */
	Map<String, Float> kvMap = new HashMap<String, Float>();
	
	/**
	 * @param key 是String类型
	 * @param value 是float类型
	 */
	public void put(String key,Float value){
		
		kvMap.put(key, value);
	}
	
	/**
	 * @return key的集合
	 */
	public Set<String> keySet(){
		return kvMap.keySet();
	}
	
	/**
	 * @return Message中的所有映射对
	 */
	public Set<Map.Entry<String, Float>> entrySet(){
		
		return kvMap.entrySet();
	}
	
	/**
	 * @param key 想得到的值的key
	 * @return 根据key映射的value
	 */
	public Float get(String key){
		
		return kvMap.get(key);
	}
	
}
