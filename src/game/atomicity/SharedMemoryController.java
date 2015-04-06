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
	 * @param ballId ָ��Ҫ���ĸ�ball
	 * @return ball��״̬����
	 */
	public abstract Ball read(int ballId);
	
	/**
	 * @param ballId ָ��Ҫ���ĸ�ball
	 * @param data ��Ҫд������,data�����ݸ�ʽ�����֣�
	 *  "[type] x y [l_x] [l_y]",type�ֱ�Ϊ'1','2','3','4',������ٶȣ��ٶȣ�λ���Լ��ۺ�״̬��Ϣ
	 *  ��ʽΪ"1 x y";"2 x y";"3 x y";"4 x y loc_x loc_y"
	 */
	public abstract void write(int ballId, String data);
	
	public int getBallAmount(){
		
		return ballList.size();
	}
	
	public boolean collisionCalculate(float ballaTempXY[],Ball balla,Ball ballb){
		
		float BAx=ballaTempXY[0]-ballb.getX();
		float BAy=ballaTempXY[1]-ballb.getY();
		
		//������������ģ
		float mvBA=mould(new float[]{BAx,BAy});	
		
		//�����������ڵ�����ֱ����û����ײ
		if(mvBA>Ball.getD()){			
			return false;
		}
		/**
		 * ���������ײ���㷨Ϊ��
		 * 1�����������������ߵ�����
		 * 2����ÿ������ٶȷֽ�Ϊƽ���봹ֱ��������������
		 * 3��������ȫ������ײ��֪ʶ����ײ��ƽ�����������ٶ����򽻻�����ֱ���������ٶȲ���
		 * 4��������װ��������ٶȼ���
		 * vax=vax��ֱ+vbxƽ��    vay=vay��ֱ+vbyƽ��    
		 * vbx=vbx��ֱ+vaxƽ��    vby=vby��ֱ+vayƽ��    
		 */
		
	    //�ֽ�B���ٶ�========================================begin=============	
		
		//��b����ٶȴ�С
		float vB=(float)Math.sqrt(ballb.getVX()*ballb.getVX()+ballb.getVY()*ballb.getVY());
		//ƽ�з����XY���ٶ�
		float vbCollX=0;
		float vbCollY=0;
		//��ֱ�����Xy���ٶ�
		float vbVerticalX=0;
		float vbVerticalY=0;
		
		//�����ٶ�С����ֵ����Ϊ�ٶ�Ϊ�㣬���ý��зֽ������
		if(balla.getVMIN()<vB)
		{
			//��B����ٶȷ�����������ײֱ������B->A�ļн�(����)
			float bAngle=angle
			(
				new float[]{ballb.getVX(),ballb.getVY()},
			    new float[]{BAx,BAy}
			);
			
			//��B������ײ������ٶȴ�С
			float vbColl=vB*(float)Math.cos(bAngle);
			
			//��B������ײ������ٶ�������ע��Ҫ��֤mvBA��Ϊ�㣡��
			vbCollX=(vbColl/mvBA)*BAx;
			vbCollY=(vbColl/mvBA)*BAy;
			
			//��B������ײ��ֱ������ٶ�����
			vbVerticalX=ballb.getVX()-vbCollX;
			vbVerticalY=ballb.getVY()-vbCollY;
		}
		//�ֽ�B���ٶ�========================================end=============== 
		
		//�ֽ�A���ٶ�========================================begin=============	
		
		//��a����ٶȴ�С
		float vA=(float)Math.sqrt(balla.getVX()*balla.getVX()+balla.getVY()*balla.getVY());
		//ƽ�з����Xy���ٶ�
		float vaCollX=0;
		float vaCollY=0;
		//��ֱ�����Xy���ٶ�
		float vaVerticalX=0;
		float vaVerticalY=0;
		
		//�����ٶ�С����ֵ����Ϊ�ٶ�Ϊ�㣬���ý��зֽ������
		if(balla.getVMIN()<vA)
		{
			//��A����ٶȷ�����������ײֱ������B->A�ļн�(����)
			float aAngle=angle
			(
				new float[]{balla.getVX(),balla.getVY()},
			    new float[]{BAx,BAy}
			);			
			
			//��A������ײ������ٶȴ�С
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//��A������ײ������ٶ�����
			vaCollX=(vaColl/mvBA)*BAx;
			vaCollY=(vaColl/mvBA)*BAy;
			
			//��A������ײ��ֱ������ٶ�����
			vaVerticalX=balla.getVX()-vaCollX;
			vaVerticalY=balla.getVY()-vaCollY;
		}
		//�ֽ�A���ٶ�========================================end===============
		
		//����ײ��AB����ٶ�
		//����˼��Ϊ��ֱ�����ٶȲ��䣬��ײ���������ٶȽ�������ֱ�����ٶȲ���
		
		
		float balla_vx = vaVerticalX+vbCollX;
		float balla_vy = vaVerticalY+vbCollY;
		
		String balla_data ="s "+balla_vx+" "+balla_vy;
		this.write(balla.getBallId(), balla_data);
		
		float ballb_vx = vbVerticalX+vaCollX;
		float ballb_vy = vbVerticalY+vaCollY;
		
		String ballb_data ="s "+ballb_vx+" "+ballb_vy;
		this.write(ballb.getBallId(), ballb_data);
		
		//========================================
		//�˴����ò���������ײ�����Ĵ���
		//�˴����ò���������ײ�����Ĵ���
		//�˴����ò���������ײ�����Ĵ���
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
		//������
		float dp=dotProduct(vec1,vec2);
		//��������������ģ
		float m1=mould(vec1);
		float m2=mould(vec2);
		
		float acos=dp/(m1*m2);
		
		//Ϊ�˱������������������
		if(acos>1)
		{
			acos=1;
		}
		else if(acos<-1)
		{
			acos=-1;
		}
		//���ص��ǻ���
		return (float) Math.acos(acos);
	}
	
	public float dotProduct(float[] vec1,float[] vec2)
	{
		return vec1[0]*vec2[0]+vec1[1]*vec2[1];
	}
	
}
