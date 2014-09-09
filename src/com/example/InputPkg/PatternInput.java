package com.example.InputPkg;

public class PatternInput {
	PatternInput instance;
	private String patternStr;
	public PatternInput(){
		
	}
	public PatternInput(String str){
		patternStr = str;
	}
	public void getPatternInput(){
		//patternStr = JOptionPane.showInputDialog(null,"Enter a pattern","Input dialog",JOptionPane.QUESTION_MESSAGE);
		//patternStr="Reg\\d";
	}
	public String getPattern(){
		String str="";
		//analysis(str);
		//System.out.println("+++++++++++++"+str+"++++++++");
		str = patternStr.substring(3);
		return str;
	}
	
	public int analysis(String str){
		if(patternStr.startsWith("Max")){
			str =str+ patternStr.trim().substring(3);
			return 1;
		}
		else if(patternStr.startsWith("Reg")){
			str =str+ patternStr.trim().substring(3);
			//System.out.println("!!!!!!!!!!!!"+str+"!!!!!!!!!!");
			return 2;
		}
		else if(patternStr.startsWith("Sum")){
			return 3;
		}
		else{
			return 4;
		}
	}
}
