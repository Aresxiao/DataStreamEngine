package group;

import java.util.*;

public enum GroupConfig {
	INSTANCE;
	
	private List<SystemNode> replicaList = new ArrayList<SystemNode>();
	
	public int getGroupSize(){
		
		return this.replicaList.size();
	}
	
	public List<SystemNode> getGroupMembers(){
		
		return this.replicaList;
	}
	
	public void addMember(SystemNode node){
		
		replicaList.add(node);
	}
	
	public List<String> getMemberIPList(){
		
		List<String> ipList = new ArrayList<String>();
		for(SystemNode node : replicaList)
			ipList.add(node.getNodeIp());
		return ipList;
	}
}
