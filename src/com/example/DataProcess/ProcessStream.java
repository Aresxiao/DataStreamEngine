package com.example.DataProcess;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.Toast;

import com.example.InputPkg.DataInput;
import com.example.InputPkg.DataType;
import com.example.InputPkg.PatternInput;

public class ProcessStream implements Runnable{
	Activity app;
	DataInput[] dataInList;
	PatternInput pat;
	//int procType;
	
	public ProcessStream(DataInput ...dataIn){
		dataInList = dataIn;
	}
	
	public ProcessStream(Activity a,PatternInput p,DataInput ...dataIn){
		dataInList = dataIn;
		pat = p;
		//procType = t;
		app = a;
	}
	
	public ArrayList<DataType> clearDoubleData(){
		
		ArrayList<DataType> rtn = new ArrayList<DataType>();
		for(int i = 0;i<dataInList.length;i++){
			rtn.add(dataInList[i].getSingleData());
		}
		return rtn;
	}
	
	public void matchChar(DataInput dataStream, PatternInput pattern){
		AutomatonEx automaton = new AutomatonEx(pattern.getPattern());
		AutomatonEx tmpAutomaton = automaton;
		while(true){
			DataType tmp = dataStream.getSingleData();
			if(automaton.onlineMatch(tmp.ch)){
				//app.NotifyApp("char match");
				automaton = tmpAutomaton;
				System.out.print("=========================\n");
			}
		}
	}
	
	public int matchDoubleSum(PatternInput pattern,DataInput... dataStream ){
		double sum;
		DataInput[] dataStreamArray;
		int index=0;
		dataStreamArray = new DataInput[dataStream.length];
		System.out.println("matchDoubleSum --into the function--");
		for(DataInput item : dataStream){
			dataStreamArray[index] = item;
			index++;
		}
		System.out.println("matchDoubleSum --not bug--");
		while(true){
			sum=0;
			for(int i=0;i<index;i++){
				DataType p = dataStreamArray[i].getSingleData();
				sum = sum+p.d;
			}
			if(sum>Double.parseDouble(pattern.getPattern())){
				System.out.println("matchDoubleSum --get the result--");
				//Toast.makeText(getApplicationContext(), "已经达到预期效果", Toast.LENGTH_SHORT).show();
				
				return 0;
			}
		}
	}
	
	public int matchIntegerMax(PatternInput pattern,DataInput... dataStream){
		int max;
		DataInput[] dataStreamArray;
		int index = 0;
		dataStreamArray = new DataInput[dataStream.length]; 
		for(DataInput item : dataStream){
			dataStreamArray[index] = item;
			index++;
		}
		DataType tmp = dataStreamArray[0].getSingleData();
		max=tmp.i;
		int flag=0;
		while(true){
			for(int i=0;i<index;i++){
				if(flag==0){
					flag=1;
				}
				else{
					DataType p = dataStreamArray[i].getSingleData();
					if(max<p.i)
						max = p.i;
				}
			}
			if(max>Integer.parseInt(pattern.getPattern())){
				//app.NotifyApp("Integer max match");
				return max;
			}
		}
	}
	public double matchIntegerAvg(PatternInput pattern,DataInput... dataStream){
		double avg;
		DataInput[] dataStreamArray ;
		int index=0;
		dataStreamArray = new DataInput[dataStream.length];
		for(DataInput item : dataStream){
			dataStreamArray[index] = item;
			index++;
		}
		double sum = 0;
		for(int i = 0;i<index;i++){
			DataType tmp = dataStreamArray[i].getSingleData();
			sum += tmp.i;
		}
		avg = sum/index;
		if(avg==Double.parseDouble(pattern.getPattern())){
			//app.NotifyApp("Integer avg match");
			return avg;			//call the interface of app.
		}
		return -1;
	}

	public void Test(){
		Toast.makeText(app.getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		String str="";
		System.out.println("--ProcessStream thread run--");
		System.out.println("pat is "+pat.getPattern()+" choose "+pat.analysis(str));
		//Test();
		//Toast.makeText(app.getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
		if(pat.analysis(str)==3){
			matchDoubleSum(pat,dataInList);
			
		}
		else if(pat.analysis(str)==1){
			
			matchIntegerMax(pat,dataInList);
		}
		else{}
	}
}
