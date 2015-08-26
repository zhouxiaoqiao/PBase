package org.moon.common.db;

import java.util.ArrayList;

public class SQLTool
{
	/*
	 * 适合一个where 的sql语句扩展2010-05-29
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList parm = new ArrayList<String>();

	@SuppressWarnings("rawtypes")
	public ArrayList getParm()
	{
		return parm;
	}

	@SuppressWarnings("rawtypes")
	public void setParm(ArrayList parm)
	{
		this.parm = parm;
	}

	@SuppressWarnings("unchecked")
	public String appendSql(String sql, String ifName, String way,
			String ifValue)
	{
		sql = sql.trim();
		int j = sql.length() - 1;
		int i = 1;
		int where = 0;
		char kongge = ' ';
		if (way == null)
			way = "equal";
		// 检查是否存在where (从尾到头)
		for (i = sql.length() - 1; i >= 0; i--)
		{
			// System.out.println("Q"+sql.charAt(i)+"Q");
			if (sql.charAt(i) == kongge)
				// System.out.println();
				if (sql.charAt(i) == kongge && sql.charAt(i - 1) != kongge)
				{
					if (sql.substring(i, j).trim().equals("where"))
					{
						where = 2;
						break;
					} else
					{
						j = i;
						continue;
					}
				}
		}
		// System.out.println("i1:="+i);
		if (i == -1)
		{// 到头
			sql = sql + " where ";
			where = 1;// 新添加
		}
		// 检查是否存在and (从尾到头)
		j = sql.length() - 1;
		for (i = sql.length() - 1; i >= 0; i--)
		{
			if (sql.charAt(i) == kongge && sql.charAt(i - 1) != kongge)
			{
				if (sql.substring(i, j).trim().equals("and")
						|| sql.substring(i, j).trim().equals("And"))
					break;
				else
				{
					j = i;
					continue;
				}
			}
		}
		// System.out.println("i2:="+i);
		if (i == -1 && where == 1)
		{// 不存在and,不存在where 新添加
			if (way.equals("equal"))
			{
				sql = sql + " " + ifName + "= ?";
				parm.clear();
				parm.add(ifValue);
			} else
				sql = sql + " " + ifName + " like  '%" + ifValue + "%' ";
		} else if (where == 2) // 存在where都需要加and
		{
			if (way.equals("equal"))
			{
				sql = sql + " and " + ifName + "= ? ";
				parm.clear();
				parm.add(ifValue);
			} else
				sql = sql + " and " + ifName + " like  '%" + "&ifValue" + "%' ";
		}

		return sql;
	}

	/*
	 * 排序SQL
	 */
	public String sortSQlL(String sql, String sortName, String ascDesc)
	{
		sql = sql.trim();
		int j = sql.length() - 1;
		int i = 1;
		int order = 0;
		char kongge = ' ';
		if (ascDesc == null)
		{
			ascDesc = "asc";
		}
		// 检查是否存在where (从尾到头)
		for (i = sql.length() - 1; i >= 0; i--)
		{
			// System.out.println("Q"+sql.charAt(i)+"Q");
			if (sql.charAt(i) == kongge)
				// System.out.println();
				if (sql.charAt(i) == kongge && sql.charAt(i - 1) != kongge)
				{
					if (sql.substring(i, j).trim().equals("order")
							|| (sql.substring(i, j).trim().equals("ORDER")))
					{
						order = 1;
						break;
					} else
					{
						j = i;
						continue;
					}
				}
		}
		if (order == 1)
		{
			sql = sql + sortName + ascDesc;
		} else
			sql = sql + " order by " + sortName + " " + ascDesc;
		return sql;
	}

	public String parmLike(String parmName)
	{
		parmName = "'%" + parmName + "%'";
		return parmName;
	}

	// 大于2个以上的关键字删除
	@SuppressWarnings("rawtypes")
	public int deleteRecords(String table, ArrayList IDlist,
			ArrayList inputValue)
	{
		String sql = "delete " + table;
		int deteleNum = 0;
		if (IDlist != null && IDlist.size() > 0)
		{
			sql = sql + " where ";
			for (int i = 0; i < IDlist.size(); i++)
			{
				if (i == 0)
					sql = sql + IDlist.get(i) + "= ? ";
				else
					sql = sql + " and " + IDlist.get(i) + "= ? ";
			}
			MDBTool db = null;
			try
			{
				db = new MDBTool();
				deteleNum = db.executeUpdate(sql, inputValue);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return deteleNum;
	}

	/**
	 * @param sql
	 * @param orderKey
	 * @param start 从0开始的
	 * @param pageSize
	 * @return
	 * @author 周小桥 |2014-6-25 下午3:46:13
	 * @version 0.1
	 */
	public static String getPageMySQL(String sql, String orderKey,
			String upORDown, String start, String pageSize)
	{
		if (sql != null && !"".equals(upORDown) && upORDown != null)
			sql = sql + " ORDER BY " + orderKey + " " + upORDown + "  limit "
					+ start + " , " + pageSize;
		else
			sql = sql + " ORDER BY " + orderKey + " desc limit " + start
					+ " , " + pageSize;
		return sql;

	}

	/**
	 * @param sql
	 * @return
	 * @author 周小桥 |2014-6-25 下午3:45:32
	 * @version 0.1
	 */
	public static String getCountSQL(String sql)
	{
		if (sql != null)
			sql = "select count(*) total " + sql.substring(sql.indexOf("from"));

		return sql;

	}

	/**
	 * @param sql
	 * @param start
	 * @param pageSize
	 * @return
	 * @author 周小桥 |2014-6-25 下午3:42:46
	 * @version 0.1
	 */
	public static String getPageByOracle(String sql, String start,
			String pageSize)
	{
		String tmp = sql;
		sql = sql.replaceFirst("select", "");
		if (!sql.contains("where"))
			// select * from (select rownum no,* from userlist where rownum<=m)
			// where no>=n;
			sql = "select * from ( select  rownum no," + tmp
					+ " where rownum<=" + pageSize + ") where no >=" + start;
		else
			sql = "select * from ( select  rownum no," + tmp + " and rownum<="
					+ pageSize + ") where no >=" + start;
		return sql;

	}

	/**
	 * 递归查询
	 * @param tab
	 * @param parentKey
	 * @param childKey
	 * @param val
	 * @param include_self
	 * @return
	 * @author 周小桥 |2014-8-14 下午4:21:21
	 * @version 0.1
	 */
	public static String getRecuSQl(String tab, String parentKey,
			String childKey, Integer val, boolean include_self)
	{
		String recuSQl = null;
		if (tab != null && parentKey != null && childKey != null && val != null)
		{
			if (include_self)
			{
				recuSQl = "select t2." + childKey + ",  t2." + parentKey
						+ " from " + tab + " t1, " + tab + " t2  "
						+ " where t1." + childKey + "=t2." + parentKey
						+ " and (t1." + childKey + "=" + val + " or t1."
						+ parentKey + "=" + val + ") union " + " select t3."
						+ childKey + ",  t3." + parentKey + " " + " from "
						+ tab + " t3 where t3." + childKey + "=" + val;
			} else
			{
				recuSQl = "select t2." + childKey + ", t2.name, t2."
						+ parentKey + " from " + tab + " t1, " + tab + " t2  "
						+ " where t1." + childKey + "=t2." + parentKey
						+ "  and (t1." + childKey + "=" + val + " or t1."
						+ parentKey + "=" + val + ")";
			}
		}
		return recuSQl;
	}

	/**
	 * @param updateKeyName
	 * @param keyType
	 * @param keyValue
	 * @return
	 * @author 周小桥 |2015-8-7 下午3:47:45
	 * @version 0.1
	 */
	public static String appendUpdateSql(String before_sql,
			String updateKeyName, String keyType, String keyValue)
	{
		String up_sql = "";
		if (updateKeyName != null && !"".equals(keyValue))
		{
			if ("String".equals(keyType))
				up_sql = updateKeyName + "='" + keyValue + "'";
			else if ("Date".equals(keyType) || "date".equals(keyType))
				up_sql = updateKeyName + "= str_to_date('" + keyValue
						+ "', '%Y-%m-%d')";
			else
				up_sql = updateKeyName + "=" + keyValue;
		}

		if (before_sql != null && !"".equals(before_sql))
		{
			if (!"".equals(up_sql))
				before_sql = before_sql + "," + up_sql;

		} else
			before_sql = up_sql;
		return before_sql;
	}

	public static void main(String args[])
	{
		// String sql = "select * from (select * from tab_employee) qq";
		// System.out.println(SQLTool.getCountSQL(sql));
		// System.out.println(SQLTool.getPageMySQL(sql, "eid", "desc", "1",
		// "3"));

		System.out.println(appendUpdateSql("a=b","name", "date", ""));

	}

}
