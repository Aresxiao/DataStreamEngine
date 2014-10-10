package com.example.datastreamengine;

import android.R.integer;

public class Constant {
	//public static int SCREEN_WIDTH;//��Ļ�Ŀ��
	//public static int SCREEN_HEIGHT;//��Ļ�ĸ߶�
	
	public static float PLAYER_BALL_SIZE=38;//�����İ뾶
	public static float GOAL_BALL_SIZE=38;//Ŀ����İ뾶
	
	public static float TIME_SPAN=0.05f;//���˶���ģ��ʱ�������涨: timeSpan������>=Ball.d��
	public static float V_ATTENUATION=0.996f;//�ٶ�˥������
	public static float V_MAX=150;		//�������ٶȣ��涨���������ٶȲ����Գ���150��
	public static float V_MIN=0.5f;		//�ٶ���Сֵ�����ٶ�С�ڴ�ֵʱ��ֹͣ�˶�
	
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
