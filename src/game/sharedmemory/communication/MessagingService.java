package game.sharedmemory.communication;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import android.util.Log;

import game.sharedmemory.communication.message.*;

import game.sharedmemory.readerwriter.AtomicRegisterServer;
import game.sharedmemory.readerwriter.RegisterControllerFactory;
import group.GroupConfig;
import group.SystemNode;
import network.OverlayNetworkFactory;

public enum MessagingService implements IReceiver{
	INSTANCE;
	
	@Override
	public void onReceive(IPMessage msg) {
		// TODO Auto-generated method stub
		if(msg.getMsgType() == IPMessage.ATOMIC_READ_PHASE_MESSAGE || 
				msg.getMsgType() == IPMessage.ATOMIC_WRITE_PHASE_MESSAGE){
			AtomicRegisterServer.INSTANCE.handleMessage(msg);
		}
		else {
			RegisterControllerFactory.INSTANCE.getRegisterController().handleMessage(msg);
		}
		
	}
	
	public void send(IPMessage msg){
		OverlayNetworkFactory.INSTANCE.getOverlayNetwork().sendMsg(msg);
	}
	
	public class Communication implements IReceiver{
		
		private final String TAG = Communication.class.getName();
		private AtomicityMessage atomicityMessage = null;
		
		private static final int NOT_SENT = 0; // message was not sent yet
		private static final int NOT_ACK = 1; // message was sent but not yet
		
		private static final int ACK = 2; // message was acknowledged
		
		// to implement the ping-pong communication mechanism
		private static final int HERE = 4;
		private static final int THERE = 8;
		
		private final int replicaNum;
		private final int procMajority; 
		
		/** 管理线程协作 */
		private CountDownLatch latch;
		
		private final Map<String, Integer> turn = new ConcurrentHashMap<String, Integer>();
		private final Map<String, Integer> status = new ConcurrentHashMap<String, Integer>();
		private final Map<String, AtomicityMessage> info = new ConcurrentHashMap<String, AtomicityMessage>();
		
		/**
		 * Custom locks for modifying {@link #turn}, {@link #status}, and
		 * {@link #info} atomically.
		 * 
		 * @see http://vanillajava.blogspot.com/2010/05/locking-concurrenthashmap-for-exclusive.html
		 * 
		 */
		private final Object[] locks = new Object[10];
		{
			for (int i = 0; i < locks.length; i++)
				locks[i] = new Object();
		}
		
		public Communication(AtomicityMessage atomicityMessage){
			this.atomicityMessage = atomicityMessage;
			this.replicaNum = GroupConfig.INSTANCE.getGroupSize();
			this.procMajority = this.replicaNum / 2 + 1;
			this.latch = new CountDownLatch(procMajority);
			
			List<SystemNode> replicaList = GroupConfig.INSTANCE.getGroupMembers();
			
			for(int i = 0; i < this.replicaNum; i++){
				
				final String replicaIP = replicaList.get(i).getNodeIp();
				final int hash = replicaIP.hashCode() & 0x7FFFFFFF;
				
				synchronized (this.locks[hash % locks.length]) {
					turn.put(replicaIP, Communication.HERE);
					status.put(replicaIP, Communication.NOT_SENT);
				}
			}
		}
		
		public Map<String, AtomicityMessage> communicate(){
			final List<SystemNode> replicaList = GroupConfig.INSTANCE.getGroupMembers();
			
			//broadcast
			for(int i = 0; i < this.procMajority; i++){
				String replicaIP = replicaList.get(i).getNodeIp();
				
				final int hash = replicaIP.hashCode() & 0x7FFFFFFF;
				
				synchronized (this.locks[hash % locks.length]) {
					if(turn.get(replicaIP) == Communication.HERE){
						atomicityMessage.setReceiverIP(replicaIP);
						MessagingService.INSTANCE.send(atomicityMessage);
						turn.put(replicaIP, Communication.THERE);
						status.put(replicaIP, Communication.NOT_ACK);
					}
				}
			}
			
			try {
				this.latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return this.info;
		}
		
		@Override
		public void onReceive(IPMessage msg) {
			// TODO Auto-generated method stub
			if(isDeprecated((AtomicityMessage)msg)){
				Log.i(TAG, "The message is obsolete: " + msg.toString());
				return;
			}
			final String fromIP = msg.getSenderIP();
			
			final int hash = fromIP.hashCode() & 0x7FFFFFFF;
			synchronized (this.locks[hash % locks.length]) {
				switch (this.status.get(fromIP)) {
					case Communication.NOT_SENT:
						atomicityMessage.setReceiverIP(fromIP);
						MessagingService.INSTANCE.send(atomicityMessage);
						this.turn.put(fromIP, Communication.THERE);
						this.status.put(fromIP, Communication.NOT_ACK);
						break;
						
					case Communication.NOT_ACK:
						this.status.put(fromIP, Communication.ACK);
						this.info.put(fromIP, (AtomicityMessage)msg);
						
						this.latch.countDown();
						break;
					default:
						break;
				}
			}
			
		}
		
		private boolean isDeprecated(final AtomicityMessage receivedMessage){
			if((this.atomicityMessage.getCnt() == receivedMessage.getCnt())
					&&(
							(this.atomicityMessage.getMsgType() == IPMessage.ATOMIC_READ_PHASE_MESSAGE
							&& receivedMessage.getMsgType() == IPMessage.ATOMIC_READ_PHASE_ACK_MESSAGE)
							||
							(this.atomicityMessage.getMsgType() == IPMessage.ATOMIC_WRITE_PHASE_MESSAGE
							&& receivedMessage.getMsgType() == IPMessage.ATOMIC_WRITE_PHASE_ACK_MESSAGE)))
				return false;
			else {
				return true;
			}
		}
		
		
	}
}
