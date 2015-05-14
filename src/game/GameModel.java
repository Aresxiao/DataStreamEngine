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
	 * @param ax ��x����ļ��ٶ�
	 * @param ay ��y����ļ��ٶ�
	 * @description ͨ���������ٶ����޸ı���С���״̬
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
	
	//�����������ײ�������ķ���
	public boolean collisionCalculate(float ballaTempXY[],AbstractBall balla,AbstractBall ballb)
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
		Value bvalue = KVStoreInMemory.INSTANCE.getVersionValue(new Key(ballb.getBallId())).getValue();
		float[] bloc = bvalue.getLoc();
		
		float BAx = ballaTempXY[0] - bloc[0];
		float BAy = ballaTempXY[1] - bloc[1];
		
		//������������ģ
		float mvBA = mould(new float[]{BAx, BAy});	
		
		//�����������ڵ�����ֱ����û����ײ
		if(mvBA > (balla.getRadius() + ballb.getRadius())){
			
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
		float[] bv = bvalue.getV();
		
		
		float vB = (float)Math.sqrt(bv[0] * bv[0] + bv[1] * bv[1]);
		//ƽ�з����XY���ٶ�
		float vbCollX = 0;
		float vbCollY = 0;
		//��ֱ�����Xy���ٶ�
		float vbVerticalX=0;
		float vbVerticalY=0;
		
		//�����ٶ�С����ֵ����Ϊ�ٶ�Ϊ�㣬���ý��зֽ������
		if(balla.getVMin() < vB)
		{
			//��B����ٶȷ�����������ײֱ������B->A�ļн�(����)
			float bAngle=angle
			(
				new float[]{bv[0],bv[1]},
			    new float[]{BAx,BAy}
			);
			
			//��B������ײ������ٶȴ�С
			float vbColl=vB * (float)Math.cos(bAngle);
			
			//��B������ײ������ٶ�������ע��Ҫ��֤mvBA��Ϊ�㣡��
			vbCollX = (vbColl/mvBA) * BAx;
			vbCollY = (vbColl/mvBA) * BAy;
			
			//��B������ײ��ֱ������ٶ�����
			vbVerticalX = bv[0] - vbCollX;
			vbVerticalY = bv[1] - vbCollY;
		}
		//�ֽ�B���ٶ�========================================end=============== 
		
		//�ֽ�A���ٶ�========================================begin=============	
		
		//��a����ٶȴ�С
		
		Value avalue = KVStoreInMemory.INSTANCE.getVersionValue(new Key(balla.getBallId())).getValue();
		
		float[] av = avalue.getV();
		
		float vA = (float)Math.sqrt(av[0] * av[0] + av[1] * av[1]);
		//ƽ�з����Xy���ٶ�
		float vaCollX = 0;
		float vaCollY = 0;
		//��ֱ�����Xy���ٶ�
		float vaVerticalX = 0;
		float vaVerticalY = 0;
		
		//�����ٶ�С����ֵ����Ϊ�ٶ�Ϊ�㣬���ý��зֽ������
		if(balla.getVMin() < vA)
		{
			//��A����ٶȷ�����������ײֱ������B->A�ļн�(����)
			float aAngle=angle
			(
				new float[]{av[0],av[1]},
			    new float[]{BAx,BAy}
			);			
			
			//��A������ײ������ٶȴ�С
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//��A������ײ������ٶ�����
			vaCollX=(vaColl/mvBA)*BAx;
			vaCollY=(vaColl/mvBA)*BAy;
			
			//��A������ײ��ֱ������ٶ�����
			vaVerticalX = av[0]-vaCollX;
			vaVerticalY = av[1]-vaCollY;
		}
		//�ֽ�A���ٶ�========================================end===============
		
		//����ײ��AB����ٶ�
		//����˼��Ϊ��ֱ�����ٶȲ��䣬��ײ���������ٶȽ�������ֱ�����ٶȲ���
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
		//�˴����ò���������ײ�����Ĵ���
		//�˴����ò���������ײ�����Ĵ���
		//�˴����ò���������ײ�����Ĵ���
		//========================================
		//System.out.println("ball aaaaaaaaaaaaaaaaaaaaaaaaaaaa "+balla.vx+" ======= "+balla.vy);
		//System.out.println("ball bbbbbbbbbbbbbbbbbbbbbb "+ballb.vx+" ======= "+ballb.vy);
		return true;
	}
	
	//����������ƽ��
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
	//��ƽ��������ģ
	public float mould(float[] vec)
	{
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]);
	}
	
	//������ƽ�������ļн�
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
	
}
