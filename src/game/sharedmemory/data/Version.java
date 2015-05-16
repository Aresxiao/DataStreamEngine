package game.sharedmemory.data;

import java.io.Serializable;

public class Version implements Comparable<Version>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 187206647479768753L;
	
	public static final Version RESERVED_VERSION = new Version(-1);
	
	private int seqno;	//sequence number
	
	public Version(int seqno){
		this.seqno = seqno;
	}
	
	/**
	 * 
	 * @return {@link #seqno}: the sequence number
	 */
	public int getSeqno(){
		
		return this.seqno;
	}
	
	public Version increment()
	{
		// make sure that pid has been set
		
		return new Version(this.seqno + 1);
	}
	
	@Override
	public int compareTo(Version version) {
		// TODO Auto-generated method stub
		
		if (this.seqno > version.seqno)
			return 1;
		if (this.seqno < version.seqno)
			return -1;
		
		return 0;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "version: seqno = " + seqno + ". ";
	}

	@Override
	public Version clone(){
		// TODO Auto-generated method stub
		
		Version version = new Version(this.seqno);
		return version;
	}
	
	
}
