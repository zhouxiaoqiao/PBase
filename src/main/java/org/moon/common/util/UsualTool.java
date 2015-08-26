package org.moon.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsualTool
{
	// ���������ַ�
	public double StringTodouble(String str)
	{
		double all_num = 0.0000;
		if (str != null)
		{
			if (str.charAt(0) >= '0' && str.charAt(0) <= '9')
			{
				double xiashu = 0f;
				int signal = 0;
				double zhengshu = 0f;
				double e = 10.0000;
				// all_costMoney=all_costMoney+Integer.parseInt(money);
				for (int i = 0; i < str.length(); i++)
				{
					if (signal == 0 && str.charAt(i) != '.')
					{
						zhengshu = str.charAt(i) - 48;
						// System.out.println("zhengshu="+zhengshu);
						if (i > 0)
						{
							all_num = zhengshu + all_num * 10;

						} else
						{
							all_num = zhengshu;
						}
					}
					// С����
					if (str.charAt(i) == '.')
					{
						signal = 1;
						continue;
					}
					if (signal == 1)
					{
						double q = 0f;
						q = str.charAt(i) - 48;
						// System.out.println("q="+q);
						xiashu = xiashu + q / e;
						e = e * 10f;
					}
				}
				all_num = all_num + xiashu;
			} else
				all_num = 0.0;
		}
		return all_num;
	}

	public Date StringToDate(String str)
	{
		Date date = null;
		try
		{
			date = java.sql.Date.valueOf(str);
			// date=sf.parse(sf.format(date));

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return date;
	}

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

	public String DateToStringTo(Date date)
	{

		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strdate = null;
		try
		{
			strdate = sf.format(date);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return strdate;

	}
}
