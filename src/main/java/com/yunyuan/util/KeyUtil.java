package com.yunyuan.util;

import java.util.Random;

public class KeyUtil
{
	/**
	 * @return
	 * @author 周小桥 |2015-8-7 上午11:25:48
	 * @version 0.1
	 */
	public static long getLongID()
	{
		Random random = new Random();
		long id = System.currentTimeMillis() * 100 + Math.abs(random.nextInt())
				% 100;
		return id;
	}

}
