package game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Obstacle {
	
	float frame_x;
	float frame_y;
	float width;
	float heigth;
	
	public Obstacle(){
		frame_x = 0;
		frame_y = 0;
		width = 10;
		heigth = 10;
	}
	public Obstacle(float x,float y,int w,float h){
		frame_x = x;
		frame_y = y;
		width = w;
		heigth = h;
	}
	
	public void setFrameXY(float x,float y){
		frame_x = x;
		frame_y = y;
	}
	
	public void setWidthHeight(float w,float h){
		width = w;
		heigth = h;
	}
	
	public float[] getFrameXY(){
		float[] frame_xy = {frame_x,frame_y};
		return frame_xy;
	}
	
	public float[] getWidthHeight(){
		float[] widthAndHeight = {width,heigth};
		return widthAndHeight;
	}
	
	public float[][] getFourCornerLocation(){
		float[][] corners={{frame_x,frame_y},{frame_x+width,frame_y},{frame_x,frame_y+heigth},{frame_x+width,frame_y+heigth}};
		return corners;
	}
	
	public void drawSelf(Canvas canvas,Paint paint){
		paint.reset();
		paint.setColor(Color.BLUE);
		
		canvas.drawRect(frame_x, frame_y, frame_x+width, frame_y+heigth, paint);
	}
	
	
}
