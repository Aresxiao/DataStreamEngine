package com.example.datastreamengine;

import java.util.ArrayList;
import java.util.Random;

import com.example.DataProcess.ProcessStream;
import com.example.InputPkg.DataType;
import com.example.InputPkg.DoubleInput;
import com.example.InputPkg.PatternInput;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/*
 * @author xiaogeng
 * 
 */
public class MainActivity extends Activity implements SensorEventListener{

	SensorManager sensorManager;
	//Sensor weight;
	DoubleInput diX;
	DoubleInput diY;
	DoubleInput diZ;
	PatternInput pat;
	ProcessStream proc;
	LocationManager lm;
	Button bn=null,myTestButton=null;
	EditText myEditText;
	
	TextView tvAaccelerate;
	
	//屏幕宽度和高度
	private int tableWidth;
	private int tableHeight;
	//球大小
	private final int BALL_SIZE = 12;
	
	private final int CIRCLE_SIZE=18;
	
	Random rand = new Random();
	private int ballX = rand.nextInt(200)+20;
	private int ballY = rand.nextInt(10)+20;
	
	private int circleX = 420;
	private int circleY = 750;
	
	private double xSpeed=0;
	private double ySpeed=0;
	
	private boolean isLose = false;
	private boolean isWin = false;
	Handler handler = null;
	double sumTime = 0;
	long count = 0;
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
     // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 获得屏幕宽和高
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;
        
        Constant.initConst(tableWidth, tableHeight);
        
        final GameView gameView = new GameView(this);
        setContentView(gameView);
        
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		
		
		
		diX = new DoubleInput();
		diY = new DoubleInput();
		diZ = new DoubleInput();
		
		proc = new ProcessStream(diX,diY,diZ);
		
		handler = new Handler(){
			public void handleMessage(Message msg)
            {
                if (msg.what == 0x123)
                {
                    gameView.invalidate();
                }
            }
		};
		
		new Thread(){
			@Override
			public void run() {
				double a = 0.5;
				long count1=0;
				int flag = 0;
				double sumTime1 = 0;
				while(true){
					ArrayList<DataType> rtn = proc.clearDoubleData();
					xSpeed = xSpeed + 0.1*rtn.get(0).d*a;
					ySpeed = ySpeed + 0.1*rtn.get(1).d*a;
					long e = System.currentTimeMillis()-rtn.get(0).t;
					sumTime1 = sumTime1 + e;
					count1++;
					ballX = ballX - (int)xSpeed;
					ballY = ballY + (int)ySpeed;
					if(ballX<=BALL_SIZE||(ballX+BALL_SIZE)>=tableWidth)
						isLose = true;
					if(ballY<=BALL_SIZE||(ballY+BALL_SIZE)>=tableHeight)
						isLose = true;
					int dX = ballX - circleX;
					int dY = ballY - circleY;
					int dXY = dX*dX + dY*dY;
					int d = (int) Math.sqrt(dXY);
					if(d<(CIRCLE_SIZE-BALL_SIZE))
						isWin = true; 
					if((isLose==true||isWin==true)&&flag==0){
						flag++;
						count=count1;
						sumTime = sumTime1;
					}
					handler.sendEmptyMessage(0x123);
				}
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	boolean WinGame(){
		int dX = ballX - circleX;
		int dY = ballY - circleY;
		int dXY = dX*dX + dY*dY;
		int d = (int) Math.sqrt(dXY);
		if(d<(CIRCLE_SIZE-BALL_SIZE))
			return true;
		else 
			return false;
	}
	/*
	class GameView extends View
    {
        Paint paint = new Paint();
 
        public GameView(Context context)
        {
            super(context);
            setFocusable(true);
        }
        // 重写View的onDraw方法，实现绘画
        public void onDraw(Canvas canvas)
        {
            paint.setStyle(Paint.Style.FILL);
            // 设置去锯齿
            paint.setAntiAlias(true);
            // 如果游戏已经结束
            if (isWin)
            {
            	double delay = (double)(sumTime/count);
            	paint.setColor(Color.RED);
                paint.setTextSize(30);
                canvas.drawText("好厉害哦!", 50, 200, paint);
            }
            else if(isLose){
				double delay = (double)(sumTime/count);
				paint.setColor(Color.RED);
				paint.setTextSize(30);
				canvas.drawText("游戏已结束", 50, 200, paint);
            }
            // 如果游戏还未结束
            else
            {
            	// 设置颜色，并绘制球拍
                paint.setColor(Color.rgb(80, 80, 200));
            	//paint.setColor(Color.BLACK);
                canvas.drawCircle(circleX, circleY, CIRCLE_SIZE, paint);
                // 设置颜色，并绘制小球
                //paint.setColor(Color.rgb(240, 240, 80));
                paint.setColor(Color.BLACK);
                canvas.drawCircle(ballX, ballY, BALL_SIZE, paint);
                
            }
        }
    }
	*/
	@Override
	protected void onResume(){
		super.onResume();
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
	}
	@Override
	protected void onStop(){
		sensorManager.unregisterListener(this);
		super.onStop();
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float values[] = event.values;
		DataType dtX = new DataType();
		
		dtX.t = System.currentTimeMillis();
		DataType dtY = new DataType();
		DataType dtZ = new DataType();
		dtX.d = values[0];
		dtY.d = values[1];
		dtZ.d = values[2];
		diX.addData(dtX);
		diY.addData(dtY);
		diZ.addData(dtZ);
	}
}

