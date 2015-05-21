package group;

public class SystemNode {
	
	/** �ڵ��IP,Ĭ��Ϊnull */
	private String nodeIp;
	public static String NODE_IP_DEFAULT = null;
	
	/** �ڵ������,Ĭ��Ϊnull */
	private String nodeName;
	public static String NODE_NAME_DEFAULT = null;
	
	public SystemNode() {
		// TODO Auto-generated constructor stub
		this.nodeIp = SystemNode.NODE_IP_DEFAULT;
		this.nodeName = SystemNode.NODE_NAME_DEFAULT;
	}
	
	public SystemNode(String nodeIp){
		this.nodeIp = nodeIp;
		this.nodeName = SystemNode.NODE_NAME_DEFAULT;
	}
	
	public SystemNode(String nodeIp, String nodeName){
		this.nodeIp = nodeIp;
		this.nodeName = nodeName;
	}
	
	public String getNodeIp(){
		return this.nodeIp;
	}
	
	public String getNodeName(){
		return this.nodeName;
	}
	
	public void setNodeIp(String nodeIp){
		this.nodeIp = nodeIp;
	}
}
