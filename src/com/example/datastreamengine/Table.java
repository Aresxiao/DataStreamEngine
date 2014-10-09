package com.example.datastreamengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class Table {

	public void drawSelf(Canvas canvas,Paint paint){
		/*Path path = new Path();
		path.moveTo(Constant.FRAMEA_X, Constant.FRAMEA_Y);
		path.lineTo(Constant.FRAMEA_X+Constant.FRAMEA_WIDTH, Constant.FRAMEA_Y);
		path.lineTo(Constant.FRAMEA_X+Constant.FRAMEA_WIDTH, Constant.FRAMEA_Y+Constant.FRAMEA_HEIGHT);
		path.lineTo(Constant.FRAMEA_X, Constant.FRAMEA_Y+Constant.FRAMEA_HEIGHT);
		path.close();*/
		paint.reset();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		//canvas.drawPath(path, paint);
		canvas.drawRect(Constant.FRAMEA_X, Constant.FRAMEA_Y,Constant.FRAMEA_X+Constant.FRAMEA_WIDTH, 
				Constant.FRAMEA_Y+Constant.FRAMEA_HEIGHT, paint);
		
	}
}
