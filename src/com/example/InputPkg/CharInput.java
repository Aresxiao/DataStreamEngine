package com.example.InputPkg;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class CharInput extends DataInput {

	public CharInput(Scanner scan){
		//super();
		type=1;
		this.scan = scan;
	}
	public CharInput(DataInputStream in){
		//super();
		type=2;
		this.in = in;
	}
	
	@Override
	public void getInput() {
		// TODO Auto-generated method stub
		if(type==1){
			while(scan.hasNext()){
				String str = scan.next();
				char ch[] = str.toCharArray();
				for(int i=0;i<ch.length;i++){
					DataType t = new DataType();
					t.ch = ch[i];
					synchronized (buffer){
						buffer.add(t);
					}
				}
			}
		}
		else if(type==2){
			while(true){
				
				try {
					DataType t = new DataType();
					t.ch = in.readChar();
					synchronized (buffer){
						buffer.add(t);
						System.out.print("-------"+buffer.get(0).ch);
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
	public String toString() {
		String str = "";
		for(int i = 0; i < buffer.size();i++){
			DataType c = buffer.get(i);
			str = str + c.ch;
		}
		return str;
    }
	public void run() {
		// TODO Auto-generated method stub
		getInput();
	}

}
