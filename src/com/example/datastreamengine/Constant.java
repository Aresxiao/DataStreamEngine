package com.example.datastreamengine;

import android.R.integer;

public class Constant {
	//public static int SCREEN_WIDTH;//屏幕的宽度
	//public static int SCREEN_HEIGHT;//屏幕的高度
	
	public static float PLAYER_BALL_SIZE=38;//玩家球的半径
	public static float GOAL_BALL_SIZE=38;//目标球的半径
	
	public static float TIME_SPAN=0.05f;//球运动的模拟时间间隔（规定: timeSpan不可以>=Ball.d）
	public static float V_ATTENUATION=0.996f;//速度衰减比例
	public static float V_MAX=150;		//球的最大速度（规定：球的最大速度不可以超过150）
	public static float V_MIN=0.5f;		//速度最小值，当速度小于此值时球停止运动
	
	public static float X_OFFSET;
	public static float Y_OFFSET;
	
	public static int TABLE_WIDTH;
	public static int TABLE_HEIGHT;
	
	
	
	public static int FRAMEA_X;
	public static int FRAMEA_Y;
	public static int FRAMEA_HEIGHT;
	public static int FRAMEA_WIDTH;
	
	public static void initConst(int screenWidth,int screenHeight){
		TABLE_WIDTH = screenWidth;
		TABLE_HEIGHT = screenHeight;
		
		FRAMEA_HEIGHT = (int) (GOAL_BALL_SIZE+20);
		FRAMEA_WIDTH = (int) (GOAL_BALL_SIZE+36);
		FRAMEA_X = TABLE_WIDTH/2-FRAMEA_WIDTH/2;
		FRAMEA_Y = 0;
	}
}
