package game;

import java.util.ArrayList;

import constant.Constant;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Table {

	ArrayList<Obstacle> obstacles;
	
	public Table(){
		obstacles = new ArrayList<Obstacle>();
		Obstacle obstacle = new Obstacle();
		obstacle.setFrameXY(200, 200);
		obstacle.setWidthHeight(20, 30);
		obstacles.add(obstacle);
	}
	
	public void addObstacle(Obstacle obstacle){
		obstacles.add(obstacle);
	}
	
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}
	
	public void drawSelf(Canvas canvas,Paint paint){
		/*
		 * 
		Path path = new Path();
		path.moveTo(Constant.FRAMEA_X, Constant.FRAMEA_Y);
		path.lineTo(Constant.FRAMEA_X+Constant.FRAMEA_WIDTH, Constant.FRAMEA_Y);
		path.lineTo(Constant.FRAMEA_X+Constant.FRAMEA_WIDTH, Constant.FRAMEA_Y+Constant.FRAMEA_HEIGHT);
		path.lineTo(Constant.FRAMEA_X, Constant.FRAMEA_Y+Constant.FRAMEA_HEIGHT);
		path.close();
		canvas.drawPath(path, paint);
		*
		*/
		
		for(int i = 0;i < obstacles.size();i++){
			Obstacle obstacle = obstacles.get(i);
			obstacle.drawSelf(canvas, paint);
		}
		
		paint.reset();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		
		canvas.drawRect(Constant.FRAMEA_X, Constant.FRAMEA_Y,Constant.FRAMEA_X+Constant.FRAMEA_WIDTH, 
				Constant.FRAMEA_Y+Constant.FRAMEA_HEIGHT, paint);
		
	}
}
