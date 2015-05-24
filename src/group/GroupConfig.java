package group;

import java.util.*;

import constant.Constant;

public enum GroupConfig {
	INSTANCE;
	
	SystemNode localNode;
	
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
	
	public void setLocalNode(SystemNode localNode){
		this.localNode = localNode;
	}
	
	public SystemNode getLocalNode(){
		
		return this.localNode;
	}
}
