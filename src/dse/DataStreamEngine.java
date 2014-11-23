package dse;

/**
 * 
 * This class is the main part of engine.It can do some processing.
 * 
 * @author GengXiao
 * 
 */
public class DataStreamEngine implements DSEInterface{
	
	double accelearate_x,accelearate_y,accelearate_z;
	
	public void setAccelerate(double x, double y, double z) {
		// TODO Auto-generated method stub
		this.accelearate_x = x;
		this.accelearate_y = y;
		this.accelearate_z = z;
	}
	
	public void updateDSEState(String data) {
		// TODO Auto-generated method stub
		String[] strArray = data.split(",");
		
	}
	
	/**
	 * 这里有很多种处理方式
	 * @param type为int类型，指明要进行什么样的处理，data为String类型，为所要进行处理的数据。
	 */
	public void dataProcessFromGame(int type, String data) {
		// TODO Auto-generated method stub
		
	}
	
	
}
