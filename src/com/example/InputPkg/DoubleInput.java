package com.example.InputPkg;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class DoubleInput extends DataInput {

	public DoubleInput(Scanner scan){
		type = 1;
		this.scan = scan;
	}
	public DoubleInput(DataInputStream in){
		type = 2;
		this.in = in;
	}
	public DoubleInput(){
		type=0;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		getInput();
	}

	@Override
	public void getInput() {
		// TODO Auto-generated method stub
		if(type==1){
			while(scan.hasNext()){
				DataType t = new DataType();
				t.d = scan.nextDouble();
				synchronized (buffer){
					buffer.add(t);
				}
			}
		}
		else if(type==2){
			while(true){
				try {
					DataType t = new DataType();
					t.d = in.readDouble();
					synchronized (buffer){
						buffer.add(t);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else{}
	}

}
