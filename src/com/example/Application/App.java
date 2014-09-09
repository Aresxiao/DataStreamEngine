package com.example.Application;

import com.example.DataProcess.ProcessStream;
import com.example.InputPkg.PatternInput;

public class App implements SendMessage {

	ProcessStream proc;
	PatternInput pat = new PatternInput();
	public App(){
		pat.getPatternInput();
	}
	public void NotifyApp(String result) {
		// TODO Auto-generated method stub
		System.out.println("result is:"+result+"\n");
	}
	
	
	public void Query(PatternInput pattern){
		
		new Thread(new Runnable() {  
            public void run() {  
                
            }  
        }).start();
	}
	public PatternInput getPat(){
		return pat;
	}

}
