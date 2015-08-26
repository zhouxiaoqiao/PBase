/**
 * 基础类.
 *访问数据库
 * @author  周小桥
 * @since   JDK1.4
 * @history 2010-06-21 周小桥 
 */
package org.moon.common.db;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-18 下午3:54:15 | 创建
 */
public class MDBTool implements IMDBTool
{

	private static DataSource ds;

	private static Connection conn;

	private PreparedStatement psQuery;

	private ResultSet resultset;

	private PreparedStatement psUpdate;

	private static CallableStatement callprocedure;

	private static Log logger = LogFactory.getLog(MDBTool.class);

	// 构造方法
	public MDBTool()
	{
		ApplicationContext context = null;
		try
		{
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
			if (ds == null)
				ds = (DataSource) context.getBean("dataSource");
			if (conn == null || conn.isClosed())
				conn = ds.getConnection();
		} catch (Exception e)
		{
			logger.error(e);
		}
	}

	/*
	 * 操作数据库设置参数(SQl语句条件) Athor zhouxiaoqiao 2010-01-20
	 */
	private void setParamValue(PreparedStatement theStmt, List<?> params)
			throws Exception
	{
		setParamValue(theStmt, params, null);
	}

	/**
	 * @param theStmt
	 * @param params
	 * @param type
	 * @throws Exception
	 * @author 周小桥 |2014-6-18 下午3:55:43
	 * @version 0.1
	 */
	private void setParamValue(PreparedStatement theStmt, List<?> params,
			int type[]) throws Exception
	{
		if (params == null || params.size() == 0)
			return;
		if (type != null && type.length == params.size())
		{
			for (int i = 0; i < params.size(); i++)
			{
				Object value = params.get(i);
				switch (type[i])
				{
				case -4:
				case -3:
				case -2:
					if (value instanceof byte[])
					{
						InputStream is = new ByteArrayInputStream(
								(byte[]) value);
						theStmt.setBinaryStream(i + 1, is, is.available());
					}
					break;

				case -1:
					if (value instanceof String)
					{
						String content = value.toString();
						Reader reader = new StringReader(content);
						theStmt.setCharacterStream(i + 1, reader,
								content.length());
					}
					break;

				default:
					theStmt.setObject(i + 1, value);
					break;
				}
			}

		} else
		{
			for (int i = 0; i < params.size(); i++)
			{
				// sSystem.out.println(params.get(i));
				theStmt.setObject(i + 1, params.get(i));
			}

		}
	}

	/**
	 * @param theStmt
	 * @param params
	 * @param beforecall
	 * @throws Exception
	 * @author 周小桥 |2014-8-8 下午4:41:41
	 * @version 0.1
	 */
	private void setProcedureParamValue(PreparedStatement theStmt,
			List<?> params, int beforecall) throws Exception
	{
		if (params == null || params.size() == 0)
			return;

		for (int i = 0; i < params.size(); i++)
		{
			Object value = params.get(i);

			if (value instanceof Integer)
			{
				int parm = Integer.parseInt(value.toString());
				theStmt.setInt(i + 1 + beforecall, parm);
			}

			else if (value instanceof String)
			{
				String content = value.toString();
				int index = i + 1 + beforecall;
				theStmt.setString(index, content);
			} else
				theStmt.setObject(i + 1 + beforecall, value);

		}
	}

	/*
	 * 数据库查询功能 Athor zhouxiaoqiao 2010-01-20
	 */

	/**
	 * @param rs
	 * @return
	 * @throws Exception
	 * @author 周小桥 |2014-6-18 下午5:28:13
	 * @version 0.1
	 */
	private List<JSONObject> getDataSetToJson(ResultSet rs) throws Exception
	{
		ResultSetMetaData mdr = rs.getMetaData();
		List<JSONObject> records = new ArrayList<JSONObject>();
		int j = mdr.getColumnCount();
		rs.last();
		int i = rs.getRow();
		rs.first();
		int s = 0;
		while (s < i)
		{
			JSONObject row = new JSONObject();
			for (int t = 1; t <= j; t++)
			{
				String keyName = mdr.getColumnName(t);
				if (rs.getObject(keyName) instanceof Timestamp|| rs.getObject(keyName) instanceof Date)
				{

					row.put(keyName, rs.getObject(keyName).toString());
				} else
					row.put(keyName, rs.getObject(keyName));

			}

			s++;
			rs.next();
			records.add(row);
		}
		return records;
	}

	/**
	 * @author 周小桥 |2014-8-8 下午4:41:57
	 * @version 0.1
	 */
	private void closeStatement()
	{
		try
		{
			if (resultset != null)
			{
				resultset.close();
				resultset = null;
			}
		} catch (Exception exp)
		{
			resultset = null;
		}
		try
		{
			if (psQuery != null)
			{
				psQuery.close();
				psQuery = null;
			}
		} catch (Exception e1)
		{
			psQuery = null;
		}
		try
		{
			if (psUpdate != null)
			{
				psUpdate.close();
				psUpdate = null;
			}
		} catch (Exception e1)
		{
			psUpdate = null;
		}
	}

	// 关闭数据库连接
	/**
	 * 
	 */
	public void closeConnection() throws Exception
	{
		try
		{
			this.closeStatement();
			if (conn != null)
			{
				conn.close();
			}
		} catch (Exception e)
		{
			System.err
					.println("\u5173\u95ED\u6570\u636E\u5E93\u8FDE\u63A5\u51FA\u9519\uFF1A"
							+ e.getMessage());
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public ResultSet executeQuery(String sql, List parm) throws Exception
	{

		try
		{
			if (conn == null || conn.isClosed())
				conn = ds.getConnection();
			psQuery = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			setParamValue(psQuery, parm);
			resultset = psQuery.executeQuery();
		} catch (Exception e)
		{
			logger.error(e);
		} finally
		{

		}
		return resultset;
	}

	/**
	 * @param sql
	 * @param parm
	 * @return
	 * @throws Exception
	 * @author 周小桥 |2014-6-18 下午5:31:50
	 * @version 0.1
	 */
	public List<JSONObject> executeJSONQuery(String sql, List<String> parm)
			throws Exception
	{
		List<JSONObject> records = null;
		try
		{
			if (conn == null || conn.isClosed())
				conn = ds.getConnection();
			psQuery = conn
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			setParamValue(psQuery, parm);
			resultset = psQuery.executeQuery();
			records = getDataSetToJson(resultset);
		} catch (Exception e)
		{
			logger.error(e);
		} finally
		{

		}
		return records;
	}

	/*
	 * 数据库更新/插入/删除功能 Athor zhouxiaoqiao 2010-01-20
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(noRollbackFor = Exception.class)
	public int executeUpdate(String sql, List parm) throws Exception
	{
		int updaterecords = 0;
		try
		{
			if (conn == null || conn.isClosed())
				conn = ds.getConnection();
			psUpdate = conn
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			setParamValue(psUpdate, parm);
			updaterecords = psUpdate.executeUpdate();
		} catch (Exception e)
		{
			logger.error(e);
		} finally
		{

		}
		return updaterecords;
	}

	/*
	 * public ResultSet executeQueryProcedure(String procedure, List params)
	 * throws Exception { return executeQueryProcedure(procedure, params, true);
	 * }
	 */
	/**
	 * @param procedureName
	 * @param params
	 * @param scrollable
	 * @return
	 * @throws Exception
	 * @author 周小桥 |2014-8-8 下午4:42:59
	 * @version 0.1
	 */
	public ResultSet executeQueryProcedure(String procedureName,
			List<?> params, boolean scrollable) throws Exception
	{
		if (conn == null || conn.isClosed())
			conn = ds.getConnection();
		logger.info("调用存储过程！");
		if (scrollable)
			callprocedure = conn.prepareCall(procedureName, 1005, 1007);
		else
			callprocedure = conn.prepareCall(procedureName);
		setParamValue(callprocedure, params);
		resultset = callprocedure.executeQuery();
		return resultset;
	}

	// 对文件流操作，例如图片存取
	@SuppressWarnings("rawtypes")
	@Transactional(noRollbackFor = Exception.class)
	public int executeStreamUpdate(String sql, List parm, FileInputStream stream)
			throws Exception
	{
		int updaterecords = 0;
		try
		{
			if (conn == null || conn.isClosed())
				conn = ds.getConnection();
			psUpdate = conn
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			setParamValue(psUpdate, parm);
			psUpdate.setBinaryStream(parm.size() + 1, stream,
					stream.available());
			updaterecords = psUpdate.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return updaterecords;
	}

	/*
	 * 执行存储过程函数procedureName:过程名,inparams输入参数 outparams过程输出参数,returnparams函数返回参数
	 * 注意只能允许存放一个参数 返回结果存放在returnOutparams Study 2010/6/19 周小桥
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<?> executeProcedure(String procedureName,
			List<String> inparams, List<String> outparams,
			List<String> returnparams) throws Exception
	{
		ArrayList returnOutparams = new ArrayList();
		int returnParam = 0;
		try
		{
			if (returnparams != null && returnparams.size() > 0)
			{
				procedureName = "{ ? = call " + procedureName;
				returnParam = 1;// 为函数调用准备,函数有返回值
			} else
				procedureName = "{ call " + procedureName;
			String tempStr = "";
			if (inparams != null && inparams.size() > 0)
			{

				for (int i = 0; i < inparams.size(); i++)
					if (i == 0)
						tempStr = "(?";
					else
						tempStr = tempStr + ",?";
			}
			if (outparams != null && outparams.size() > 0)
			{
				for (int i = 0; i < outparams.size(); i++)
					if (i == 0 && inparams == null)
						tempStr = "(?";
					else
						tempStr = tempStr + ",?";
			}
			if ((inparams == null || inparams.size() == 0)
					&& (outparams == null || outparams.size() == 0))
				tempStr = "(";
			tempStr = tempStr + ")";

			procedureName = procedureName + tempStr + " }";
			// 设置参数
			if (conn == null || conn.isClosed())
				conn = ds.getConnection();
			callprocedure = conn.prepareCall(procedureName);
			// System.out.println("inparams="+inparams.size());
			if (returnParam == 1)// 判断前面call是否有 ?
				callprocedure.registerOutParameter(1, java.sql.Types.INTEGER);
			int inparamsNums = 0;
			// 设置输入参数
			if (inparams != null && inparams.size() > 0)
			{
				for (int i = 0; i < inparams.size(); i++)
				{
					this.setProcedureParamValue(callprocedure, inparams,
							returnParam);
				}
				inparamsNums = inparams.size();
			}

			// 注册输出参数
			if (outparams != null && outparams.size() > 0)
			{
				for (int j = 0; j < outparams.size(); j++)
				{
					callprocedure.registerOutParameter(inparamsNums + 1 + j, 4);
					// System.out.println("inparamsNum + 1+j="+inparamsNums +
					// 1+j);
				}
			}
			callprocedure.execute();
			if (returnParam == 1)
				outparams.add(0, returnparams.get(0));// 首行插入返回参数处理函数有返回值时
			// System.out.println(outparams.size());
			for (int j = 0; j < outparams.size(); j++)
			{
				if (outparams == null || outparams.size() == 0)
					break;
				Object value = outparams.get(j);
				if (value instanceof Integer)
				{
					returnOutparams.add(callprocedure.getInt(inparamsNums + 1
							+ j - returnParam));// 往前移returnParam位
					continue;
				} else if (value instanceof String)
				{
					returnOutparams.add(callprocedure.getString(inparamsNums
							+ 1 + j - returnParam));
					continue;
				} else
				{
					returnOutparams.add(callprocedure.getString(inparamsNums
							+ 1 + j));
					continue;
				}
			}
			callprocedure.close();
		} catch (Exception e)
		{
			this.closeConnection();
		}
		return returnOutparams;
	}

}
