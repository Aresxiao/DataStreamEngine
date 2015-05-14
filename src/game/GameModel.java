package game;

import game.sharedmemory.*;
import game.sharedmemory.communication.message.Message;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;
import game.sharedmemory.data.kvstore.KVStoreInMemory;
import game.sharedmemory.readerwriter.RegisterControllerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;


import constant.Constant;


public enum GameModel {
	
	INSTANCE;
	
	private static final String TAG = GameModel.class.getName();
	
	BallGoThread ballGoThread = new BallGoThread();
	
	List<AbstractBall> ballList = new ArrayList<AbstractBall>();
	Table table = new Table();
	
	public void initialize(){
		
		ballList.add(new GoalBall(Constant.TABLE_WIDTH/2-Constant.GOAL_BALL_SIZE/2,
				Constant.TABLE_HEIGHT/2-Constant.GOAL_BALL_SIZE/2, this));
		ballList.add(new PlayerBall(20, 20, this));
		ballList.add(new PlayerBall(140, 20, this));
		
		ballGoThread.setFlag(true);
		ballGoThread.start();
	}
	
	public void stopThread(){
		
		ballGoThread.setFlag(false);
	}
	
	/**
	 * @param ax 是x方向的加速度
	 * @param ay 是y方向的加速度
	 * @description 通过重力加速度来修改本地小球的状态
	 */
	public void onSensorChanged(float ax,float ay){
		AbstractBall ball = ballList.get(Constant.LOCAL_BALL_ID);
		float[] v = ball.calBallSpeedByAccelerate(ax, ay);
		
		Key key = new Key(Constant.LOCAL_BALL_ID);
		
		Value value = RegisterControllerFactory.INSTANCE.getRegisterController().read(key).getValue();
		value.setV(v[0], v[1]);
		RegisterControllerFactory.INSTANCE.getRegisterController().write(key, value);
	}
	
	public void overGame(){
		stopThread();
	}
	
	public List<AbstractBall> getBalls(){
		
		return this.ballList;
	}
	
	//两个球进行碰撞物理计算的方法
	public boolean collisionCalculate(float ballaTempXY[],AbstractBall balla,AbstractBall ballb)
	{		
		/** 
		 * 求碰撞直线向量B->A （也就是两个参与碰撞的桌球球心连线的向量），注意两个问题
		 * 1、这里不区分向量B->A和A->B的原因是，求夹角时用两者求得的cos值是一样的，
		 * 而sin值没有用到，求垂直于连心线的速度分量用的是向量的减法，而不是sin值
		 * 2、要保证该向量不为零向量，即两球的位置不可以重合，因为后面还要做分母，会出问题！！
		 * 
		 * 该方法没有改变球的位置，只改变球的速度。
		 * 注意此时的x,y，不是球心坐标，而是球外接正方形左上角顶点坐标！！！！！！！！！！
		 */	
		Value bvalue = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballb.getBallId())).getValue();
		float[] bloc = bvalue.getLoc();
		
		float BAx = ballaTempXY[0] - bloc[0];
		float BAy = ballaTempXY[1] - bloc[1];
		
		//求上述向量的模
		float mvBA = mould(new float[]{BAx, BAy});	
		
		//若两球距离大于等于球直径则没有碰撞
		if(mvBA > (balla.getRadius() + ballb.getRadius())){
			
			return false;
		}
		/**
		 * 两球进行碰撞的算法为：
		 * 1、计算两球球心连线的向量
		 * 2、将每个球的速度分解为平行与垂直此向量的两部分
		 * 3、根据完全弹性碰撞的知识，碰撞后平行于向量的速度两球交换，垂直于向量的速度不变
		 * 4、重新组装两球的新速度即：
		 * vax=vax垂直+vbx平行    vay=vay垂直+vby平行    
		 * vbx=vbx垂直+vax平行    vby=vby垂直+vay平行    
		 */
		
	    //分解B球速度========================================begin=============	
		
		//求b球的速度大小
		float[] bv = bvalue.getV();
		
		
		float vB = (float)Math.sqrt(bv[0] * bv[0] + bv[1] * bv[1]);
		//平行方向的XY分速度
		float vbCollX = 0;
		float vbCollY = 0;
		//垂直方向的Xy分速度
		float vbVerticalX=0;
		float vbVerticalY=0;
		
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(balla.getVMin() < vB)
		{
			//求B球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float bAngle=angle
			(
				new float[]{bv[0],bv[1]},
			    new float[]{BAx,BAy}
			);
			
			//求B球在碰撞方向的速度大小
			float vbColl=vB * (float)Math.cos(bAngle);
			
			//求B球在碰撞方向的速度向量，注意要保证mvBA不为零！！
			vbCollX = (vbColl/mvBA) * BAx;
			vbCollY = (vbColl/mvBA) * BAy;
			
			//求B球在碰撞垂直方向的速度向量
			vbVerticalX = bv[0] - vbCollX;
			vbVerticalY = bv[1] - vbCollY;
		}
		//分解B球速度========================================end=============== 
		
		//分解A球速度========================================begin=============	
		
		//求a球的速度大小
		
		Value avalue = KVStoreInMemory.INSTANCE.getVersionValue(new Key(balla.getBallId())).getValue();
		
		float[] av = avalue.getV();
		
		float vA = (float)Math.sqrt(av[0] * av[0] + av[1] * av[1]);
		//平行方向的Xy分速度
		float vaCollX = 0;
		float vaCollY = 0;
		//垂直方向的Xy分速度
		float vaVerticalX = 0;
		float vaVerticalY = 0;
		
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(balla.getVMin() < vA)
		{
			//求A球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float aAngle=angle
			(
				new float[]{av[0],av[1]},
			    new float[]{BAx,BAy}
			);			
			
			//求A球在碰撞方向的速度大小
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//求A球在碰撞方向的速度向量
			vaCollX=(vaColl/mvBA)*BAx;
			vaCollY=(vaColl/mvBA)*BAy;
			
			//求A球在碰撞垂直方向的速度向量
			vaVerticalX = av[0]-vaCollX;
			vaVerticalY = av[1]-vaCollY;
		}
		//分解A球速度========================================end===============
		
		//求碰撞后AB球的速度
		//基本思想为垂直方向速度不变，碰撞方向两球速度交换，垂直方向速度不变
		av[0] = vaVerticalX + vbCollX;
		av[1] = vaVerticalY + vbCollY;
		Log.i(TAG, "balla.id = "+balla.getBallId()+",ballb.id = "+ballb.getBallId());
		avalue.setV(av[0], av[1]);
		RegisterControllerFactory.INSTANCE.getRegisterController().write(new Key(balla.getBallId()), avalue);
		
		bv[0] = vbVerticalX + vaCollX;
		bv[1] = vbVerticalY + vaCollY;
		
		bvalue.setV(bv[0], bv[1]);
		RegisterControllerFactory.INSTANCE.getRegisterController().write(new Key(ballb.getBallId()), bvalue);
		System.exit(0);
		//========================================
		//此处调用播放桌球碰撞声音的代码
		//此处调用播放桌球碰撞声音的代码
		//此处调用播放桌球碰撞声音的代码
		//========================================
		//System.out.println("ball aaaaaaaaaaaaaaaaaaaaaaaaaaaa "+balla.vx+" ======= "+balla.vy);
		//System.out.println("ball bbbbbbbbbbbbbbbbbbbbbb "+ballb.vx+" ======= "+ballb.vy);
		return true;
	}
	
	//球两点距离的平方
	public float calcuDisSquare(float[] p1,float[] p2)
	{
		return (p1[0]-p2[0])*(p1[0]-p2[0])+(p1[1]-p2[1])*(p1[1]-p2[1]);
	}
	
	public float dotProduct(float[] vec1,float[] vec2)
	{
		return
			vec1[0]*vec2[0]+
			vec1[1]*vec2[1];		
	}
	//求平面向量的模
	public float mould(float[] vec)
	{
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]);
	}
	
	//求两个平面向量的夹角
	public float angle(float[] vec1,float[] vec2)
	{
		//先求点积
		float dp=dotProduct(vec1,vec2);
		//再求两个向量的模
		float m1=mould(vec1);
		float m2=mould(vec2);
		
		float acos=dp/(m1*m2);
		
		//为了避免计算误差带来的问题
		if(acos>1)
		{
			acos=1;
		}
		else if(acos<-1)
		{
			acos=-1;
		}
		//返回的是弧度
		return (float) Math.acos(acos);
	}
	
}
