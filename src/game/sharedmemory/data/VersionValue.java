package game.sharedmemory.data;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class VersionValue implements Comparable<VersionValue>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4865846223986022419L;

	private Version version = null;
	private Value value = null;
	
	public static final VersionValue RESERVED_VERSIONVALUE = new VersionValue(Version.RESERVED_VERSION, Value.RESERVED_VALUE);
	
	public static final VersionValue[] RESERVED_VERSION_VALUES = {RESERVED_VERSIONVALUE};
	public VersionValue(Version version, Value value){
		
		this.version = version;
		this.value = value;
	}
	
	public Version getVersion(){
		
		return this.version;
	}
	
	public Value getValue(){
		
		return this.value;
	}
	
	public static VersionValue max(VersionValue vval1, VersionValue vval2)
	{
		if (vval1.compareTo(vval2) >= 0)
			return vval1;
		return vval2;
	}
	
	public static VersionValue max(VersionValue[] vvals)
	{
		VersionValue max_vval = vvals[0];

		for (int i = 1; i < vvals.length; i++)
			if (vvals[i].compareTo(max_vval) > 0)
				max_vval = vvals[i];
		return max_vval;
	}
	
	@Override
	public int compareTo(VersionValue versionValue) {
		// TODO Auto-generated method stub
		return this.version.compareTo(versionValue.version);
	}

	
	
	@Override
	public VersionValue clone(){
		// TODO Auto-generated method stub
		
		Version ver = version.clone();
		Value val = value.clone();
		VersionValue versionValue = new VersionValue(ver, val);
		
		return versionValue;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "versionValue = " + version.toString() + value.toString() + ". ";
	}
	
	public void putJSONObject(JSONObject jsonObject, int i){
		version.putJSONObject(jsonObject, i);
		value.putJSONObject(jsonObject, i);
	}
	
}
