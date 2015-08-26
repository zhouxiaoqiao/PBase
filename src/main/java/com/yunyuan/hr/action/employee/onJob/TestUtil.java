package com.yunyuan.hr.action.employee.onJob;

import java.util.Random;

public class TestUtil
{

	/**
	 * @param args
	 * @author 周小桥 |2015-8-7 上午11:13:20
	 * @version 0.1
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Random random = new Random();//
		System.out.println(Math.abs(random.nextInt())%100 );
		long id=System.currentTimeMillis()*100+Math.abs(random.nextInt())%100;
		System.out.println(id);

	}

}
