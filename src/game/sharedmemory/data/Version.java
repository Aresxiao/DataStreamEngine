package game.sharedmemory.data;

import java.io.Serializable;

public class Version implements Comparable<Version>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 187206647479768753L;
	
	public static final Version RESERVED_VERSION = new Version(-1);
	
	private int seqno;	//sequence number
	private int pid;	//
	
	public Version(int seqno, int pid){
		this.seqno = seqno;
		this.pid = pid;
	}
	
	public Version(int seqno){
		this.seqno = seqno;
		this.pid = 0;
	}
	/**
	 * 
	 * @return {@link #seqno}: the sequence number
	 */
	public int getSeqno(){
		
		return this.seqno;
	}
	
	public Version increment(int pid)
	{
		// make sure that pid has been set
		assert pid >= 0;
		
		return new Version(this.seqno + 1, pid);
	}
	
	@Override
	public int compareTo(Version version) {
		// TODO Auto-generated method stub
		
		if (this.seqno > version.seqno)
			return 1;
		if (this.seqno < version.seqno)
			return -1;
		if (this.pid > version.pid)
			return 1;
		if (this.pid < version.pid)
			return -1;
		
		return 0;
	}
	
}
