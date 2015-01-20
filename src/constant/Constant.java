package constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import android.os.Environment;

public class Constant {
	public static final boolean isDebug = true;
	//public static int SCREEN_WIDTH;//屏幕的宽度
	//public static int SCREEN_HEIGHT;//屏幕的高度
	
	public static Object MUTEX_OBJECT = new Object();
	
	public static float PLAYER_BALL_SIZE=80;//玩家球的半径
	public static float GOAL_BALL_SIZE=80;//目标球的半径
	
	public static float TIME_SPAN=0.05f;//球运动的模拟时间间隔（规定: timeSpan不可以>=Ball.d）
	public static float V_ATTENUATION=0.996f;//速度衰减比例
	public static float V_MAX=150;		//球的最大速度（规定：球的最大速度不可以超过150）
	public static float V_MIN=0.5f;		//速度最小值，当速度小于此值时球停止运动
	public static float SENSITIVITY = 4;		//小球运动的灵敏度
	
	public static float X_OFFSET;
	public static float Y_OFFSET;
	
	public static int TABLE_WIDTH;
	public static int TABLE_HEIGHT;
	
	public static int SERVERFLAG;
	public static int PORT=9000;
	public static String HOST_IP;
	
	public static int NFRAMEA_X;
	public static int NFRAMEA_Y;
	public static int NFRAMEA_HEIGHT;
	public static int NFRAMEA_WIDTH;
	public static int SFRAMEA_X;
	public static int SFRAMEA_Y;
	public static int SFRAMEA_HEIGHT;
	public static int SFRAMEA_WIDTH;
	public static int LOCAL_BALL_ID=1;
	public static int CLIENT_BALL_ID=2;
	
	public static String WINNTEXT = "1球获胜，好机智，是否继续?";
	public static String WINSTEXT = "2求获胜，好厉害，是否继续?";
	public static float WINTEXTLOCX;
	public static float WINTEXTLOCY;
	
	public static void initConst(int screenWidth,int screenHeight){
		TABLE_WIDTH = screenWidth;
		TABLE_HEIGHT = screenHeight;
		
		NFRAMEA_HEIGHT = (int) (GOAL_BALL_SIZE+50);
		NFRAMEA_WIDTH = (int) (GOAL_BALL_SIZE+66);
		NFRAMEA_X = TABLE_WIDTH/2-NFRAMEA_WIDTH/2;
		NFRAMEA_Y = 0;
		
		SFRAMEA_HEIGHT = (int) (GOAL_BALL_SIZE+50);
		SFRAMEA_WIDTH = (int) (GOAL_BALL_SIZE+66);
		SFRAMEA_X = TABLE_WIDTH/2 - NFRAMEA_WIDTH/2;
		SFRAMEA_Y = TABLE_HEIGHT - SFRAMEA_HEIGHT;
		WINTEXTLOCX = TABLE_WIDTH/4;
		WINTEXTLOCY = TABLE_HEIGHT/3;
		//writeConfig();
		readConfig();
	}
	
	public static void readConfig(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File sdCardDir = Environment.getExternalStorageDirectory();
			String fileName = sdCardDir.getPath()+"/DSEconf.txt";
			try {
				FileInputStream fis = new FileInputStream(fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				
				String tempString;
				tempString = br.readLine();
				SERVERFLAG=Integer.parseInt(tempString);
				
				tempString = br.readLine();
				HOST_IP = tempString;
				tempString = br.readLine();
				LOCAL_BALL_ID = Integer.parseInt(tempString);
				tempString = br.readLine();
				CLIENT_BALL_ID = Integer.parseInt(tempString);
				br.close();
				if(Constant.isDebug)
					System.out.println("read from sdcard "+SERVERFLAG+","+HOST_IP+","+LOCAL_BALL_ID+","+CLIENT_BALL_ID);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("failed read file from sdcard,not have file");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("failed read file from sdcard");
				e.printStackTrace();
			}
		}
	}
}
