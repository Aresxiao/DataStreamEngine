package main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BatteryReceiver extends BroadcastReceiver{
	
	private volatile static BatteryReceiver uniqueInstance;
	double remainBattery = 0;
	
	private BatteryReceiver(){}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Receive");
		int current = intent.getExtras().getInt("level");//获得当前电量
        int total = intent.getExtras().getInt("scale");//获得总电量
        remainBattery = current*100/total;
        System.out.println("current = "+current+","+"total = "+total);
	}
	
	public static BatteryReceiver getInstance(){
		if(uniqueInstance == null){
			synchronized (BatteryReceiver.class) {
				if(uniqueInstance == null){
					uniqueInstance = new BatteryReceiver();
				}
			}
		}
		return uniqueInstance;
	}
	
	public double getRemainBattery(){
		return remainBattery;
	}
}
