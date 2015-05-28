package game.sharedmemory.readerwriter;

import java.util.Map;

import game.sharedmemory.communication.*;
import game.sharedmemory.communication.MessagingService.Communication;
import game.sharedmemory.communication.message.AtomicityMessage;
import game.sharedmemory.communication.message.AtomicityReadPhaseMessage;
import game.sharedmemory.communication.message.AtomicityWritePhaseMessage;
import game.sharedmemory.communication.message.IPMessage;

import game.sharedmemory.communication.message.WeakMessage;
import game.sharedmemory.data.*;
import group.GroupConfig;

public abstract class AbstractRegisterController implements IRegister,IMessageHandler{
	
	private static final String TAG = AbstractRegisterController.class.getName();
	
	protected int op_cnt = 0;
	
	protected Communication comm = null;
	
	@Override
	public abstract VersionValue read(Key key);
	
	@Override
	public abstract void write(Key key, Value val);
	
	public void writeRemote(Key key,VersionValue versionValue){	
		VersionValue vval = versionValue.clone();
		
		WeakMessage msg = new WeakMessage(key, vval);
		
		MessagingService.INSTANCE.send(msg);
		
	}
	
	//public abstract void handleMessage(IPMessage message);
	
	public VersionValue extractMaxVValFromAcks(Map<String, AtomicityMessage> acks){
		VersionValue[] vvals = new VersionValue[acks.size()];
		
		int vcnt = 0;
		for(AtomicityMessage msg : acks.values()){
			vvals[vcnt] = msg.getVersionValue();
			vcnt++;
		}
		
		VersionValue maxVVal = VersionValue.max(vvals);
		return maxVVal;
	}
	
	public Map<String, AtomicityMessage> readPhase(Key key){
		AtomicityMessage atomicityReadPhaseMessage = 
				new AtomicityReadPhaseMessage(GroupConfig.INSTANCE.getLocalNode().getNodeIp(), this.op_cnt, key);
		
		this.comm = MessagingService.INSTANCE.new Communication(atomicityReadPhaseMessage);
		return this.comm.communicate();
	}
	
	public void wriatePhase(Key key, VersionValue versionValue){
		AtomicityMessage atomicityWritePhaseMessage = 
				new AtomicityWritePhaseMessage(GroupConfig.INSTANCE.getLocalNode().getNodeIp(), this.op_cnt, key, versionValue);
		this.comm = MessagingService.INSTANCE.new Communication(atomicityWritePhaseMessage);
		this.comm.communicate();
	}
	
}
