package org.moon.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pattern = "^(-)?\\d+(\\.\\d+)?$";
		System.out.println("-2.2".matches(pattern));
		System.out.println(Double.parseDouble("-2.2"));
		 
		System.out.println("&");
	 
		
		long now_timer=0;
		long payLog_timer=0;
		try
		{
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			now_timer = df.parse(df.format(new Date())).getTime();
			payLog_timer = df.parse("2014-08-06 23:55:56").getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}		
		long day = (now_timer -payLog_timer) / (24 * 60 * 60 * 1000);
		System.out.println(day);// new Date()为获取当前系统时间

		
	}

}
