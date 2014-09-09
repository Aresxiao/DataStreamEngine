package com.example.InputPkg;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class DataInput implements Runnable{
	protected ArrayList<DataType> buffer;
	protected int type;
	protected DataInputStream in;
	protected Scanner scan;
	
	public DataInput(){
		buffer = new ArrayList<DataType>();
	}
	
	public abstract void getInput();
	public void addData(DataType dt){
		DataType d = new DataType(dt);
		synchronized (buffer){
			buffer.add(d);
		}
	}
	//public abstract void getInput(DataInputStream input);
	public DataType getSingleData(){
		while(buffer.size()<=0){
			
		}
		DataType t;
		synchronized (buffer){
			t = buffer.get(0);
			buffer.remove(0);
		}
		return t;
	}
	
	public DataType getLastData(){
		while(buffer.size()<=0){
			
		}
		DataType t;
		synchronized (buffer){
			int index = buffer.size() - 1;
			t = buffer.get(index);
			buffer.remove(index);
		}
		return t;
	}

}
