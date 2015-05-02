package game.sharedmemory;

import game.Obstacle;

/**
 * @author Ares
 * @description ��ײ���
 */
public class CollisionUtil {
	
	public static float dotProduct(float[] vec1,float[] vec2)
	{
		return
			vec1[0]*vec2[0]+
			vec1[1]*vec2[1];		
	}
	//��ƽ��������ģ
	public static float mould(float[] vec)
	{
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]);
	}
	
	//������ƽ�������ļн�
	public static float angle(float[] vec1,float[] vec2)
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
	
	//����������ƽ��
	public static float calcuDisSquare(float[] p1,float[] p2)
	{
		return (p1[0]-p2[0])*(p1[0]-p2[0])+(p1[1]-p2[1])*(p1[1]-p2[1]);
	}
	
	//�����������ײ�������ķ���
	public static boolean collisionCalculate(float ballaTempXY[],AbstractBall balla,AbstractBall ballb)
	{		
		/** 
		 * ����ײֱ������B->A ��Ҳ��������������ײ�������������ߵ���������ע����������
		 * 1�����ﲻ��������B->A��A->B��ԭ���ǣ���н�ʱ��������õ�cosֵ��һ���ģ�
		 * ��sinֵû���õ�����ֱ�������ߵ��ٶȷ����õ��������ļ�����������sinֵ
		 * 2��Ҫ��֤��������Ϊ���������������λ�ò������غϣ���Ϊ���滹Ҫ����ĸ��������⣡��
		 * 
		 * �÷���û�иı����λ�ã�ֻ�ı�����ٶȡ�
		 * ע���ʱ��x,y�������������꣬������������������ϽǶ������꣡������������������
		 */	
		float BAx=ballaTempXY[0]-ballb.read(new Key("locx")).getVal();
		float BAy=ballaTempXY[1]-ballb.read(new Key("locy")).getVal();
		
		//������������ģ
		float mvBA=mould(new float[]{BAx,BAy});	
		
		//�����������ڵ�����ֱ����û����ײ
		if(mvBA>(balla.read(new Key("radius")).getVal()+ballb.read(new Key("radius")).getVal())){
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
		
		float bvx = ballb.read(new Key("vx")).getVal();
		float bvy = ballb.read(new Key("vy")).getVal();
		
		float vB=(float)Math.sqrt(bvx * bvx + bvy * bvy);
		//ƽ�з����XY���ٶ�
		float vbCollX=0;
		float vbCollY=0;
		//��ֱ�����Xy���ٶ�
		float vbVerticalX=0;
		float vbVerticalY=0;
		
		//�����ٶ�С����ֵ����Ϊ�ٶ�Ϊ�㣬���ý��зֽ������
		if(balla.getVMin() < vB)
		{
			//��B����ٶȷ�����������ײֱ������B->A�ļн�(����)
			float bAngle=angle
			(
				new float[]{bvx,bvy},
			    new float[]{BAx,BAy}
			);
			
			//��B������ײ������ٶȴ�С
			float vbColl=vB*(float)Math.cos(bAngle);
			
			//��B������ײ������ٶ�������ע��Ҫ��֤mvBA��Ϊ�㣡��
			vbCollX = (vbColl/mvBA) * BAx;
			vbCollY = (vbColl/mvBA) * BAy;
			
			//��B������ײ��ֱ������ٶ�����
			vbVerticalX = bvx - vbCollX;
			vbVerticalY = bvy - vbCollY;
		}
		//�ֽ�B���ٶ�========================================end=============== 
		
		//�ֽ�A���ٶ�========================================begin=============	
		
		//��a����ٶȴ�С
		
		float avx = balla.read(new Key("vx")).getVal();
		float avy = balla.read(new Key("vy")).getVal();
		
		float vA=(float)Math.sqrt(avx * avx + avy * avy);
		//ƽ�з����Xy���ٶ�
		float vaCollX=0;
		float vaCollY=0;
		//��ֱ�����Xy���ٶ�
		float vaVerticalX=0;
		float vaVerticalY=0;
		
		//�����ٶ�С����ֵ����Ϊ�ٶ�Ϊ�㣬���ý��зֽ������
		if(balla.getVMin() < vA)
		{
			//��A����ٶȷ�����������ײֱ������B->A�ļн�(����)
			float aAngle=angle
			(
				new float[]{avx,avy},
			    new float[]{BAx,BAy}
			);			
			
			//��A������ײ������ٶȴ�С
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//��A������ײ������ٶ�����
			vaCollX=(vaColl/mvBA)*BAx;
			vaCollY=(vaColl/mvBA)*BAy;
			
			//��A������ײ��ֱ������ٶ�����
			vaVerticalX = avx-vaCollX;
			vaVerticalY = avy-vaCollY;
		}
		//�ֽ�A���ٶ�========================================end===============
		
		//����ײ��AB����ٶ�
		//����˼��Ϊ��ֱ�����ٶȲ��䣬��ײ���������ٶȽ�������ֱ�����ٶȲ���
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
		//�˴����ò���������ײ�����Ĵ���
		//�˴����ò���������ײ�����Ĵ���
		//�˴����ò���������ײ�����Ĵ���
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
