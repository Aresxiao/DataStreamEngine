package dse;

public interface DSEInterface {
	
	public void setAccelerate(float x,float y,float z);
	public void updateDSEState(int type,String data);
	public void dataProcessFromGame(int type,String data);
}
