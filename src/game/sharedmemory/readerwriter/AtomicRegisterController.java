package game.sharedmemory.readerwriter;

import java.util.Map;

import android.util.Log;

import game.GameModel;
import game.sharedmemory.communication.MessagingService;
import game.sharedmemory.communication.MessagingService.Communication;
import game.sharedmemory.communication.message.AtomicityMessage;
import game.sharedmemory.communication.message.AtomicityReadPhaseMessage;
import game.sharedmemory.communication.message.AtomicityWritePhaseMessage;
import game.sharedmemory.communication.message.IPMessage;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.Version;
import game.sharedmemory.data.VersionValue;
import group.GroupConfig;

public class AtomicRegisterController extends AbstractRegisterController{
	
	private static final String TAG = AtomicRegisterController.class.getName();
	public static AtomicRegisterController instance = null;
	
	public static synchronized AtomicRegisterController INSTANCE(){
		if (instance == null)
			instance = new AtomicRegisterController();
		return instance;
	}
	
	@Override
	public VersionValue read(Key key) {
		// TODO Auto-generated method stub
		this.op_cnt++;
		//Log.i(TAG, "AtomicRegister read(): " + this.op_cnt);
		
		Map<String, AtomicityMessage> readPhaseAcks = this.readPhase(key);
		//Log.i(TAG, "after read");
		VersionValue maxVVal = this.extractMaxVValFromAcks(readPhaseAcks);
		
		this.wriatePhase(key, maxVVal);
		return maxVVal;
	}

	@Override
	public void write(Key key, Value val) {
		// TODO Auto-generated method stub
		this.op_cnt++;
		//Log.i(TAG, "AtomicRegister write(): " + this.op_cnt);
		Map<String, AtomicityMessage> readPhaseAcks = this.readPhase(key);
		
		VersionValue maxVVal = this.extractMaxVValFromAcks(readPhaseAcks);
		
		Version maxVersion = maxVVal.getVersion();
		
		VersionValue newVVal = new VersionValue(maxVersion.increment(), val);
		
		this.wriatePhase(key, newVVal);
	}
	
	@Override
	public void handleMessage(IPMessage message) {
		// TODO Auto-generated method stub
		this.comm.onReceive(message);
	}
	
}
