package org.moon.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
	public static SimpleDateFormat dateFormat_StringToDate = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormat_DateToString = new SimpleDateFormat("yyyy��M��");

/**
 * 
 * @param date
 * @return
 * @author 周小桥 |2015-9-30 上午10:04:03
 * @version 0.1
 */
	public static Date StringToDate(String date)
	{
		if (date == null || date.equals("") || date.trim().length() < 5)
			return null;
		java.util.Date newStartTime = null;
		try
		{

			if (date.length() < 8)
			{//����ֻ������
				newStartTime = dateFormat_StringToDate.parse(date + "-01");
			}
			else
				newStartTime = dateFormat_StringToDate.parse(date);
		}
		catch (ParseException e)
		{

			e.printStackTrace();
		}

		return newStartTime;
	}

/**
 * 
 * @param d
 * @return
 * @author 周小桥 |2015-9-30 上午10:04:08
 * @version 0.1
 */
	public static String dateToString(Date d)
	{

		if (d == null)
			return "";
		return dateFormat_DateToString.format(d);
	}

/**
 * 
 * @param d
 * @return
 * @author 周小桥 |2015-9-30 上午10:04:15
 * @version 0.1
 */
	public static String dateToStringToView(Date d)
	{

		if (d == null)
			return "";
		return dateFormat_StringToDate.format(d);
	}
/**
 * 
 * @param date
 * @return
 * @author 周小桥 |2015-9-30 上午10:02:14
 * @version 0.1
 */
	public static boolean checkDate(String date)
	{
		if (date == null)
		{
			return false;
		}
		try
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			dateFormat.parse(date);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
/**
 * 
 * @param date_str
 * @return
 * @author 周小桥 |2015-9-30 上午10:00:53
 * @version 0.1
 */
	public static java.sql.Date StrngTOSQLDate(String date_str)
	{

		java.sql.Date sql_date = java.sql.Date.valueOf(date_str);
		return sql_date;
	}
/**
 * 	
 * @param str
 * @return
 * @author 周小桥 |2015-9-30 上午10:02:07
 * @version 0.1
 */
	public Timestamp StringToTimestamp(String str)
	{
		Date date = java.sql.Date.valueOf(str);
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = null;
		try
		{
			str = sf.format(date);
			ts = Timestamp.valueOf(str);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ts;

	}

}
