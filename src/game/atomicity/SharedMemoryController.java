package game.atomicity;

import java.util.List;

import game.sharedmemory.Ball;

/**
 * @author GengXiao
 * @since March 24,2015
 *  This is the DistributeSharedMemory operator interface,
 *  it offers two major method:read() and write().
 */
public abstract class SharedMemoryController {
	
	List<Ball> ballList;
	
	public SharedMemoryController(){ }
	
	/**
	 * @param ballId 指明要读哪个ball
	 * @return ball的状态数据
	 */
	public abstract Ball read(int ballId);
	
	/**
	 * @param ballId 指明要读哪个ball
	 * @param data 是要写的数据,data的数据格式有四种，
	 *  "[type] x y [l_x] [l_y]",type分别为'1','2','3','4',代表加速度，速度，位置以及综合状态信息
	 *  格式为"1 x y";"2 x y";"3 x y";"4 x y loc_x loc_y"
	 */
	public abstract void write(int ballId, String data);
	
	public int getBallAmount(){
		
		return ballList.size();
	}
	
	public boolean collisionCalculate(float ballaTempXY[],Ball balla,Ball ballb){
		
		float BAx=ballaTempXY[0]-ballb.getX();
		float BAy=ballaTempXY[1]-ballb.getY();
		
		//求上述向量的模
		float mvBA=mould(new float[]{BAx,BAy});	
		
		//若两球距离大于等于球直径则没有碰撞
		if(mvBA>Ball.getD()){			
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
		float vB=(float)Math.sqrt(ballb.getVX()*ballb.getVX()+ballb.getVY()*ballb.getVY());
		//平行方向的XY分速度
		float vbCollX=0;
		float vbCollY=0;
		//垂直方向的Xy分速度
		float vbVerticalX=0;
		float vbVerticalY=0;
		
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(balla.getVMIN()<vB)
		{
			//求B球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float bAngle=angle
			(
				new float[]{ballb.getVX(),ballb.getVY()},
			    new float[]{BAx,BAy}
			);
			
			//求B球在碰撞方向的速度大小
			float vbColl=vB*(float)Math.cos(bAngle);
			
			//求B球在碰撞方向的速度向量，注意要保证mvBA不为零！！
			vbCollX=(vbColl/mvBA)*BAx;
			vbCollY=(vbColl/mvBA)*BAy;
			
			//求B球在碰撞垂直方向的速度向量
			vbVerticalX=ballb.getVX()-vbCollX;
			vbVerticalY=ballb.getVY()-vbCollY;
		}
		//分解B球速度========================================end=============== 
		
		//分解A球速度========================================begin=============	
		
		//求a球的速度大小
		float vA=(float)Math.sqrt(balla.getVX()*balla.getVX()+balla.getVY()*balla.getVY());
		//平行方向的Xy分速度
		float vaCollX=0;
		float vaCollY=0;
		//垂直方向的Xy分速度
		float vaVerticalX=0;
		float vaVerticalY=0;
		
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(balla.getVMIN()<vA)
		{
			//求A球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float aAngle=angle
			(
				new float[]{balla.getVX(),balla.getVY()},
			    new float[]{BAx,BAy}
			);			
			
			//求A球在碰撞方向的速度大小
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//求A球在碰撞方向的速度向量
			vaCollX=(vaColl/mvBA)*BAx;
			vaCollY=(vaColl/mvBA)*BAy;
			
			//求A球在碰撞垂直方向的速度向量
			vaVerticalX=balla.getVX()-vaCollX;
			vaVerticalY=balla.getVY()-vaCollY;
		}
		//分解A球速度========================================end===============
		
		//求碰撞后AB球的速度
		//基本思想为垂直方向速度不变，碰撞方向两球速度交换，垂直方向速度不变
		
		
		float balla_vx = vaVerticalX+vbCollX;
		float balla_vy = vaVerticalY+vbCollY;
		
		String balla_data ="s "+balla_vx+" "+balla_vy;
		this.write(balla.getBallId(), balla_data);
		
		float ballb_vx = vbVerticalX+vaCollX;
		float ballb_vy = vbVerticalY+vaCollY;
		
		String ballb_data ="s "+ballb_vx+" "+ballb_vy;
		this.write(ballb.getBallId(), ballb_data);
		
		//========================================
		//此处调用播放桌球碰撞声音的代码
		//此处调用播放桌球碰撞声音的代码
		//此处调用播放桌球碰撞声音的代码
		//========================================
		//System.out.println("ball aaaaaaaaaaaaaaaaaaaaaaaaaaaa "+balla.vx+" ======= "+balla.vy);
		//System.out.println("ball bbbbbbbbbbbbbbbbbbbbbb "+ballb.vx+" ======= "+ballb.vy);
		return true;
		
	}
	
	
	
	public float mould(float[] vec)
	{
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]);
	}
	
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
	
	public float dotProduct(float[] vec1,float[] vec2)
	{
		return vec1[0]*vec2[0]+vec1[1]*vec2[1];
	}
	
}
