package game.sharedmemory;

import game.Obstacle;
import game.sharedmemory.data.Key;
import game.sharedmemory.data.Value;

/**
 * @author Ares
 * @description 碰撞检测
 */
public class CollisionUtil {
	
	public static float dotProduct(float[] vec1,float[] vec2)
	{
		return
			vec1[0]*vec2[0]+
			vec1[1]*vec2[1];		
	}
	//求平面向量的模
	public static float mould(float[] vec)
	{
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]);
	}
	
	//求两个平面向量的夹角
	public static float angle(float[] vec1,float[] vec2)
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
	
	//球两点距离的平方
	public static float calcuDisSquare(float[] p1,float[] p2)
	{
		return (p1[0]-p2[0])*(p1[0]-p2[0])+(p1[1]-p2[1])*(p1[1]-p2[1]);
	}
	
	//两个球进行碰撞物理计算的方法
	public static boolean collisionCalculate(float ballaTempXY[],AbstractBall balla,AbstractBall ballb)
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
		float BAx=ballaTempXY[0]-ballb.read(new Key("locx")).getVal();
		float BAy=ballaTempXY[1]-ballb.read(new Key("locy")).getVal();
		
		//求上述向量的模
		float mvBA=mould(new float[]{BAx,BAy});	
		
		//若两球距离大于等于球直径则没有碰撞
		if(mvBA>(balla.read(new Key("radius")).getVal()+ballb.read(new Key("radius")).getVal())){
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
		
		float bvx = ballb.read(new Key("vx")).getVal();
		float bvy = ballb.read(new Key("vy")).getVal();
		
		float vB=(float)Math.sqrt(bvx * bvx + bvy * bvy);
		//平行方向的XY分速度
		float vbCollX=0;
		float vbCollY=0;
		//垂直方向的Xy分速度
		float vbVerticalX=0;
		float vbVerticalY=0;
		
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(balla.getVMin() < vB)
		{
			//求B球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float bAngle=angle
			(
				new float[]{bvx,bvy},
			    new float[]{BAx,BAy}
			);
			
			//求B球在碰撞方向的速度大小
			float vbColl=vB*(float)Math.cos(bAngle);
			
			//求B球在碰撞方向的速度向量，注意要保证mvBA不为零！！
			vbCollX = (vbColl/mvBA) * BAx;
			vbCollY = (vbColl/mvBA) * BAy;
			
			//求B球在碰撞垂直方向的速度向量
			vbVerticalX = bvx - vbCollX;
			vbVerticalY = bvy - vbCollY;
		}
		//分解B球速度========================================end=============== 
		
		//分解A球速度========================================begin=============	
		
		//求a球的速度大小
		
		float avx = balla.read(new Key("vx")).getVal();
		float avy = balla.read(new Key("vy")).getVal();
		
		float vA=(float)Math.sqrt(avx * avx + avy * avy);
		//平行方向的Xy分速度
		float vaCollX=0;
		float vaCollY=0;
		//垂直方向的Xy分速度
		float vaVerticalX=0;
		float vaVerticalY=0;
		
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(balla.getVMin() < vA)
		{
			//求A球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float aAngle=angle
			(
				new float[]{avx,avy},
			    new float[]{BAx,BAy}
			);			
			
			//求A球在碰撞方向的速度大小
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//求A球在碰撞方向的速度向量
			vaCollX=(vaColl/mvBA)*BAx;
			vaCollY=(vaColl/mvBA)*BAy;
			
			//求A球在碰撞垂直方向的速度向量
			vaVerticalX = avx-vaCollX;
			vaVerticalY = avy-vaCollY;
		}
		//分解A球速度========================================end===============
		
		//求碰撞后AB球的速度
		//基本思想为垂直方向速度不变，碰撞方向两球速度交换，垂直方向速度不变
		avx=vaVerticalX+vbCollX;
		avy=vaVerticalY+vbCollY;
		Value valueaLocx = balla.read(new Key("locx"));
		Value valueaLocy = balla.read(new Key("locy"));
		valueaLocx.setSendCount(2);
		valueaLocy.setSendCount(2);
		balla.write(new Key("locx"), valueaLocx);
		balla.write(new Key("locy"), valueaLocy);
		balla.write(new Key("vx"), new Value(avx,2));
		balla.write(new Key("vy"), new Value(avy,2));
		
		bvx=vbVerticalX+vaCollX;
		bvy=vbVerticalY+vaCollY;
		
		Value valuebLocx = ballb.read(new Key("locx"));
		Value valuebLocy = ballb.read(new Key("locy"));
		valuebLocx.setSendCount(2);
		valuebLocy.setSendCount(2);
		ballb.write(new Key("locx"), valuebLocx);
		ballb.write(new Key("locy"), valuebLocy);
		ballb.write(new Key("vx"), new Value(bvx,2));
		ballb.write(new Key("vy"), new Value(bvy,2));
		
		//========================================
		//此处调用播放桌球碰撞声音的代码
		//此处调用播放桌球碰撞声音的代码
		//此处调用播放桌球碰撞声音的代码
		//========================================
		//System.out.println("ball aaaaaaaaaaaaaaaaaaaaaaaaaaaa "+balla.vx+" ======= "+balla.vy);
		//System.out.println("ball bbbbbbbbbbbbbbbbbbbbbb "+ballb.vx+" ======= "+ballb.vy);
		return true;
	}
	
	public static boolean collisionCalculate(Obstacle obstacle,float[] p,Ball ball){
		float[] frameXY = obstacle.getFrameXY();
		float[] widthAndHeight = obstacle.getWidthHeight();
		float radius = ball.getRadius();
		if((p[1]+radius)>frameXY[1]&&p[1]<(frameXY[1]+widthAndHeight[1])){
			if(p[0]<(frameXY[0]+widthAndHeight[0])&&(p[0]+radius)>frameXY[0]){
				ball.vx = -ball.vx;
				return true;
			}
			else {
				return false;
			}
		}
		if(p[0]<(frameXY[0]+widthAndHeight[0])&&(p[0]+radius)>frameXY[0]){
			if((p[1]+radius)>frameXY[1]&&p[1]<(frameXY[1]+widthAndHeight[1])){
				ball.vy = -ball.vy;
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
}
