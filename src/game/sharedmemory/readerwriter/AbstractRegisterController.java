package game.sharedmemory.readerwriter;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.util.Log;

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
	public abstract void write(Key[] keys, Value[] values);
	
	public void writeRemote(Key[] keys, VersionValue[] versionValues) {
		//VersionValue[] vval = versionValues.clone();
		
		WeakMessage msg = new WeakMessage(keys, versionValues);
		
		MessagingService.INSTANCE.send(msg);
		
	}
	
	//public abstract void handleMessage(IPMessage message);
	
	public VersionValue[] extractMaxVValFromAcks(Map<String, AtomicityMessage> acks) {
		int keys_length = 0;
		int vval_length = 0;
		Set<Map.Entry<String, AtomicityMessage>> entries = acks.entrySet();
		Iterator<Entry<String, AtomicityMessage>> iterator = entries.iterator();
		if(iterator.hasNext()){
			Map.Entry<String, AtomicityMessage> entry = iterator.next();
			keys_length = entry.getValue().getKeys().length;
			vval_length = entry.getValue().getVersionValues().length;
		}
		
		VersionValue[][] vvals = new VersionValue[vval_length][acks.size()];
		
		int j = 0;
		for(AtomicityMessage msg : acks.values()){
			VersionValue[] temp_vvals = msg.getVersionValues();
			for(int i = 0; i < vval_length; i++){
				vvals[i][j] = temp_vvals[i];
			}
			j++;
		}
		
		VersionValue[] ret_vvals = new VersionValue[vval_length];
		for(int i = 0; i < acks.size(); i++){
			ret_vvals[i] = VersionValue.max(vvals[i]);
		}
		
		return ret_vvals;
	}
	
	public Map<String, AtomicityMessage> readPhase(Key[] keys){
		AtomicityMessage atomicityReadPhaseMessage = 
				new AtomicityReadPhaseMessage(GroupConfig.INSTANCE.getLocalNode().getNodeIp(), this.op_cnt, keys);
		Log.i(TAG, "readPhase(): " + this.op_cnt);
		this.comm = MessagingService.INSTANCE.new Communication(atomicityReadPhaseMessage);
		//Log.i(TAG, "after readPhase(): " + this.op_cnt);
		return this.comm.communicate();
	}
	
	public void wriatePhase(Key[] keys, VersionValue[] versionValues){ 
		
		AtomicityMessage atomicityWritePhaseMessage = 
				new AtomicityWritePhaseMessage(GroupConfig.INSTANCE.getLocalNode().getNodeIp(), this.op_cnt, keys, versionValues);
		this.comm = MessagingService.INSTANCE.new Communication(atomicityWritePhaseMessage);
		this.comm.communicate();
	}
	
}
