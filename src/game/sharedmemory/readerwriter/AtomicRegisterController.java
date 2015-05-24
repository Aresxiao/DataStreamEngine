package game.sharedmemory.readerwriter;

import java.util.Map;

import game.sharedmemory.communication.message.AtomicityMessage;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.VersionValue;

public class AtomicRegisterController extends AbstractRegisterController{
	
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
		
		Map<String, AtomicityMessage> readPhaseAcks = this.readPhase(key);
		
		VersionValue maxVVal = this.extractMaxVValFromAcks(readPhaseAcks);
		return null;
	}

	@Override
	public void write(Key key, Value val) {
		// TODO Auto-generated method stub
		this.op_cnt++;
		
	}
	
}
