package game.sharedmemory.communication;

import java.io.Serializable;

public class IPMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5534722053999002824L;

	private final String sender_ip;
	private final int cnt;	// the order of the message in which the client issues
	
	/**
	 * constructor: {@link IPMessage} with ip information
	 * the default {@link #cnt} = -1
	 * @param ip {@link #sender_ip}
	 */
	public IPMessage(String ip)
	{
		this.sender_ip = ip;
		this.cnt = -1;
	}
	
	/**
	 * constructor: {@link IPMessage} with ip information
	 * @param ip {@link #sender_ip}
	 * @param cnt {@link #cnt}: the order of the message in which the client issues
	 */
	public IPMessage(final String ip, final int cnt)
	{
		this.sender_ip = ip;
		this.cnt = cnt;
	}
	
	/**
	 * @return {@link #sender_ip} carried in {@link IPMessage}
	 */
	public String getSenderIP()
	{
		return this.sender_ip;
	}
	
	/**
	 * @return {@link #cnt}: the order of the message in which the client issues
	 */
	public int getCnt()
	{
		return this.cnt;
	}
	
	/**
	 * String format to show: From {@link #sender_ip}[#{@link #cnt}]
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("From " ).append(this.sender_ip)
			.append("[#").append(this.cnt).append(']');
		return sb.toString();
	}
}
