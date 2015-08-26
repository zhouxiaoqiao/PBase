package org.moon.common.util;

import java.io.UnsupportedEncodingException;

/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-28 下午12:09:53 | 创建
 */
public class ChinaTransCode
{
	/**
	 * @param jsp_china
	 * @return
	 * @author 周小桥 |2014-6-28 下午12:13:47
	 * @version 0.1
	 */
	public static String getJspUTFSubmmit(String jsp_china)
	{
		String java_china = null;
		if (jsp_china != null)
			try
			{
				java_china = new String(jsp_china.getBytes("ISO-8859-1"),
						"utf-8");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}

		return java_china;

	}
}
