package com.example.datastreamengine;

public class Constant {
	public static int SCREEN_WIDTH;//屏幕的宽度
	public static int SCREEN_HEIGHT;//屏幕的高度
	
	public static float PLAYER_BALL_SIZE=24;//玩家球的半径
	public static float GOAL_BALL_SIZE=38;//目标球的半径
	
	public static float TIME_SPAN=0.05f;//球运动的模拟时间间隔（规定: timeSpan不可以>=Ball.d）
	public static float V_ATTENUATION=0.996f;//速度衰减比例
	public static float V_MAX=150;
	public static float V_MIN=1.5f;
	
	
	
}
