package data;

import java.util.*;
import java.util.Queue;

public class SharedData {
	
	private Queue<String> queue;
	public SharedData(){
		queue = new LinkedList<String>();
	}
	
	public void addData(String string){
		queue.offer(string);
	}
	
	public String getData(){
		String string = queue.poll();
		return string;
	}
	
}
