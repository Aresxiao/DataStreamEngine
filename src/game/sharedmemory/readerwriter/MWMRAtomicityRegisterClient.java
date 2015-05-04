package game.sharedmemory.readerwriter;

import game.sharedmemory.communication.*;
import game.sharedmemory.data.*;

import java.util.Map;

public class MWMRAtomicityRegisterClient extends AbstractAtomicityRegisterClient{

private MWMRAtomicityRegisterClient() { }
	
	private static MWMRAtomicityRegisterClient instance = null;
	
	public static synchronized MWMRAtomicityRegisterClient INSTANCE()
	{
		if (instance == null)
			instance = new MWMRAtomicityRegisterClient();
		return instance;
	}
	
	/**
	 * {@link #get(Key)} method supporting MWMR:
	 * it consists of three phases: read_phase, local computation, and write_phase
	 */
	@Override
	public VersionValue get(Key key)
	{
//		Log.d(TAG, "Client issues a GET request ...");
		
		this.op_cnt++;

//		Log.d(TAG, "Begin to get value associated with Key = " + key.toString());
		
		// read phase: contact a quorum of the server replicas for the latest value and version
		Map<String, AtomicityMessage> read_phase_acks = this.readPhase(key);

		// local computation: extract the latest VersionValue (value and its version)
		VersionValue max_vval = this.extractMaxVValFromAcks(read_phase_acks);

		// write phase: write-back the VersionValue into a quorum of the server replicas
		this.writePhase(key, max_vval);

		// return the latest VersionValue
		return max_vval;
	}

	/**
	 * {@link #put(Key, String)} method supporting MWMR:
	 * it consists of three phases: read_phase, local computation, and write_phase
	 */
	@Override
	public VersionValue put(Key key, Value val)
	{
//		Log.d(TAG, "Client issues a PUT request ...");
		
		this.op_cnt++;

		// read phase: contact a quorum of the server replicas for the latest value and version
		Map<String, AtomicityMessage> read_phase_acks = this.readPhase(key);

		// local computation: extract the latest VersionValue; increment the version; construct the new VersionValue to write
		VersionValue max_vval = this.extractMaxVValFromAcks(read_phase_acks);

		Version max_version = max_vval.getVersion();
		VersionValue new_vval = new VersionValue(this.getNextVersion(max_version), val);

		// write phase: write-back the VersionValue into a quorum of the server replicas
		this.writePhase(key, new_vval);

		return new_vval;
	}
}
