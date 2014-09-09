package com.example.InputPkg;

public class DataType {
	public char ch;
	public int i;
	public double d;
	public long t;
	public DataType(){
		
	}
	public DataType(DataType dt){
		this.ch = dt.ch;
		this.i = dt.i;
		this.d = dt.d;
		this.t = dt.t;
	}
}
