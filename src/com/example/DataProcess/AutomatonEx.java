package com.example.DataProcess;

import java.util.Set;

import dk.brics.automaton.*;

public class AutomatonEx {

	Automaton automaton;
	
	Set<State> acceptState;
	State state;
	
	AutomatonEx(String s ){
		//System.out.println("======"+s+"======");
		automaton = new RegExp(s).toAutomaton();
		state = automaton.getInitialState();
		acceptState = automaton.getAcceptStates();
		
	}
	public boolean onlineMatch(char c){
		State tmpState = state.step(c);
		if(tmpState!=null){
			state=tmpState;
			if(acceptState.contains(state)){
				return true;
			}
		}
		return false;
	}
}
