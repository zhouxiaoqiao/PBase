package org.moon.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.snaker.framework.security.entity.Org;
import org.snaker.framework.security.entity.User;

import com.yunyuan.hr.entity.Employee;

public class ReflectUtil
{
	public Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param object
	 * @param json
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @author 周小桥 |2015-9-30 上午9:37:35
	 * @version 0.1
	 */
	public void setObjectVal(Object object, JSONObject json)
	{
		// 获得对象的类型  

		Class<?> classType = object.getClass();
		// 获得对象的所有属性  

		Field fields[] = classType.getDeclaredFields();
		// 获得和属性对应的setXXX()方法的名字  
		// 属性名称  
		for (int i = 0; i < fields.length; i++)
		{

			Field field = fields[i];
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();

			if (json.containsKey(fieldName) && json.get(fieldName) != null)
			{
				String setMethodName = "set" + firstLetter + fieldName.substring(1);
				//logger.info("setMethodName==" + setMethodName);			
				//logger.info("field.getType()==" + field.getType());
				// 调用拷贝对象的setXXX()方法  
				try
				{
					String tmp = json.getString(fieldName);
					tmp = tmp.trim();
					if (!"".equals(tmp))
					{
						Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() });

						if (field.getType() == Long.class||field.getType() == long.class)
							setMethod.invoke(object, new Object[] { Long.parseLong(tmp) });
						else if (field.getType() == int.class||field.getType() == Integer.class)
							setMethod.invoke(object, new Object[] { Integer.parseInt(tmp) });
						else if (field.getType() == float.class)
							setMethod.invoke(object, new Object[] { Float.parseFloat(tmp) });
						else if (field.getType() == double.class)
							setMethod.invoke(object, new Object[] { Double.parseDouble(tmp) });
						else if (field.getType() == java.util.Date.class)
							setMethod.invoke(object, new Object[] { DateUtil.StringToDate(tmp) });
						else if (field.getType() == java.sql.Date.class)
							setMethod.invoke(object, new Object[] { DateUtil.StrngTOSQLDate(tmp) });
						else
							setMethod.invoke(object,  new Object[] {json.get(fieldName)});
					}
					else
						logger.info(fieldName+":空值对象");
				}
				catch (SecurityException e1)
				{
					logger.error(e1);
					e1.printStackTrace();
				}
				catch (NoSuchMethodException e1)
				{
					logger.error(e1);
					e1.printStackTrace();
				}

				catch (IllegalArgumentException e)
				{

					logger.error(e);
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					logger.error(e);
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					logger.error(e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 * @author 周小桥 |2015-9-30 上午9:12:43
	 * @version 0.1
	 */
	public static void main(String[] args)
	{
		JSONObject json = new JSONObject();
		User user = new User();
		json.put("username", "zxq");
		json.put("address", "sss");
	 
		Org org = new Org(23l);
		//json.put("org", org);
		ReflectUtil rf = new ReflectUtil();

		rf.setObjectVal(user, json);
		System.out.println(user.getUsername()+"/"+user.getAddress());

	}

}
