package com.example.InputPkg;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class IntegerInput extends DataInput {

	public IntegerInput(Scanner scan){
		type=1;
		this.scan = scan;
	}
	public IntegerInput(DataInputStream in){
		type=2;
		this.in = in;
	}
	
	@Override
	public void getInput() {
		// TODO Auto-generated method stub
		if(type == 1){
			while(scan.hasNext()){
				DataType t = new DataType();
				t.i = scan.nextInt();
				synchronized (buffer){
					buffer.add(t);
				}
			}
		}
		else if(type==2){
			while(true){
				try {
					DataType t = new DataType();
					t.i = in.readInt();
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
		//input.close();
	}
	public void run() {
		// TODO Auto-generated method stub
		getInput();
	}

}
