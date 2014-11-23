package dse;

public interface DSEInterface {
	
	public void setAccelerate(double x,double y,double z);
	public void updateDSEState(String data);
	public void dataProcessFromGame(int type,String data);
}
