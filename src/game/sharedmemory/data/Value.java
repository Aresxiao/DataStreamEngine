package game.sharedmemory.data;

import java.io.Serializable;

public class Value implements Serializable{
	/**
	 * 
	 */
	
	private float vx;
	private float vy;
	private float locx;
	private float locy;
	
	private static final long serialVersionUID = 8979141031800309461L;
	
	public static final Value RESERVED_VALUE = new Value(0, 0, 0, 0);
	
	public Value(float locx, float locy){
		this.vx = 0;
		this.vy = 0;
		this.locx = locx;
		this.locy = locy;
	}
	
	public Value(float vx, float vy, float locx, float locy){
		
		this.vx = vx;
		this.vy = vy;
		this.locx = locx;
		this.locy = locy;
	}
	
	public float[] getV(){
		return new float[] {this.vx, this.vy};
	}
	
	/**
	 * @description set {@link #vx #vy}
	 * @param vx
	 * @param vy
	 */
	public void setV(float vx, float vy){
		this.vx = vx;
		this.vy = vy;
	}
	
	public float[] getLoc(){
		return new float[] {this.locx, this.locy};
	}
	/**
	 * @description set {@link #locx #locy}
	 * @param locx
	 * @param locy
	 */
	public void setLoc(float locx, float locy){	
		this.locx = locx;
		this.locy = locy;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "value: vx = "+ vx + ",vy = " + vy + ",locx = " + locx + ",locy = " + locy + ". ";
	}

	@Override
	public Value clone(){
		// TODO Auto-generated method stub
		
		Value value = new Value(this.vx, this.vy, this.locx, this.locy);
		return value;
	}
	
	
}
