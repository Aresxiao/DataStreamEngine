package game;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Obstacle {
	
	int frame_x;
	int frame_y;
	int width;
	int heigth;
	
	public Obstacle(){
		frame_x = 0;
		frame_y = 0;
		width = 10;
		heigth = 10;
	}
	public Obstacle(int x,int y,int w,int h){
		frame_x = x;
		frame_y = y;
		width = w;
		heigth = h;
	}
	
	public void setFrameXY(int x,int y){
		frame_x = x;
		frame_y = y;
	}
	
	public void setWidthHeight(int w,int h){
		width = w;
		heigth = h;
	}
	
	public int[] getFrameXY(){
		int[] frame_xy = {frame_x,frame_y};
		return frame_xy;
	}
	
	public int[] getWidthHeight(){
		int[] widthAndHeight = {width,heigth};
		return widthAndHeight;
	}
	
	public void drawSelf(Canvas canvas,Paint paint){
		
	}
	
	
}
