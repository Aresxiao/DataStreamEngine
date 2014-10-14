package com.example.InputPkg;

import java.io.Serializable;

public class DataType implements Serializable{
	public char ch;
	public int i;
	public double d;
	public long t;
	public float x;
	public float y;
	public float z;
	
	public DataType(){
		
	}
	public DataType(DataType dt){
		this.ch = dt.ch;
		this.i = dt.i;
		this.d = dt.d;
		this.t = dt.t;
	}
	public void setXYZ(float x,float y,float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
