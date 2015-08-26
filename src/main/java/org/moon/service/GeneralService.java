package org.moon.service;

import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.moon.common.db.IMDBTool;
import org.moon.common.db.MDBTool;
import org.moon.common.db.SQLTool;

/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-18 下午6:42:44 | 创建
 */
public class GeneralService
{
	private IMDBTool db = new MDBTool();

	private static Log logger = LogFactory.getLog(GeneralService.class);

	/**
	 * @param sql
	 * @param parm
	 * @return
	 * @throws Throwable
	 * @author 周小桥 |2014-6-24 下午4:28:46
	 * @version 0.1
	 */
	public int insert(String sql, List<?> parm) throws Throwable
	{
		int ret = 0;
		try
		{
			logger.info("insert:" + sql);
			ret = db.executeUpdate(sql, parm);

		}
		catch (Exception e)
		{
			logger.error(e);
			logger.info(e.getStackTrace());
		}
		finally
		{
			//			if (db != null)
			//				db.closeConnection();
		}
		return ret;
	}

	/**
	 * @param sql
	 * @return
	 * @author 周小桥 |2014-6-18 下午6:50:47
	 * @version 0.1
	 */
	public JSONObject getPageQuery(String sql, int currPage, int pageSize, String orderKey, String upORDown)
			throws Exception
	{
		JSONObject jsonObj = null;
		List<JSONObject> recods = null;
		try
		{
			String start_loaction = "" + (currPage - 1) * pageSize;// S
			jsonObj = new JSONObject();
			String acount_sql = SQLTool.getCountSQL(sql);
			List<JSONObject> All_recods = db.executeJSONQuery(acount_sql, null);
			String total = All_recods.get(0).getString("total");
			if (total != null && pageSize > 0)
			{
				int rs = Integer.parseInt(total);
				if (rs % pageSize == 0)
					jsonObj.put("total", rs / pageSize); // 总页数
				else
					jsonObj.put("total", 1 + rs / pageSize); // 总页数
				jsonObj.put("page", currPage); // 当前页
				sql = SQLTool.getPageMySQL(sql, orderKey, upORDown, start_loaction, "" + pageSize);
				recods = db.executeJSONQuery(sql, null);
				if (recods != null)
				{

					jsonObj.put("rows", recods);
				}
				jsonObj.put("records", rs);
			}

		}
		catch (Exception e)
		{
			logger.error(e);
			logger.info(e.getStackTrace());
		}
		finally
		{
			//if (db != null)
			//	db.closeConnection();
		}
		return jsonObj;
	}

	/**
	 * @param sql
	 * @param parm
	 * @return
	 * @author 周小桥 |2014-6-24 下午4:31:56
	 * @version 0.1
	 */
	public int update(String sql, List<String> parm) throws Exception
	{
		int ret = 0;
		try
		{
			logger.info("update:" + sql);
			ret = db.executeUpdate(sql, parm);

		}
		catch (Exception e)
		{
			logger.error(e);
			logger.info(e.getStackTrace());
		}
		finally
		{
			//	if (db != null)
			//  	db.closeConnection();
		}
		return ret;
	}

	/**
	 * @param sql
	 * @param parm
	 * @return
	 * @author 周小桥 |2014-6-24 下午4:32:52
	 * @version 0.1
	 */
	public int delete(String sql, List<String> parm) throws Exception
	{
		int ret = 0;
		try
		{
			logger.info("delete:" + sql);
			ret = db.executeUpdate(sql, parm);

		}
		catch (Exception e)
		{
			logger.error(e);
			logger.info(e.getStackTrace());
		}
		finally
		{
			//			if (db != null)
			//				db.closeConnection();
		}
		return ret;
	}

	/**
	 * 
	 * @param sql
	 * @param parm
	 * @return
	 * @throws Exception
	 * @author 周小桥 |2014-8-2 下午4:22:33
	 * @version 0.1
	 */
	public List<JSONObject> query(String sql, List<String> parm) throws Exception
	{
		List<JSONObject> ret = null;
		try
		{
			logger.info("query:" + sql);
			ret = db.executeJSONQuery(sql, null);

		}
		catch (Exception e)
		{
			logger.error(e);
			logger.info(e.getStackTrace());
		}
		finally
		{
			//			if (db != null)
			//				db.closeConnection();
		}
		return ret;
	}

	/**
	 * 
	 * @param procedureName
	 * @param inparams
	 * @param returnparams
	 * @return
	 * @author 周小桥 |2014-8-11 下午2:48:20
	 * @version 0.1
	 */
	public int executeProcedure(String procedureName, List<String> inparams, List<String> returnparams)
	{
		int ret = 1;
		try
		{
			  db.executeProcedure(procedureName, inparams, null, returnparams);
			  logger.info("成功执行存储过程:"+procedureName);
			  ret=0;
		}
		catch (Exception e)
		{

			logger.error(e);
			logger.info(e.getStackTrace());
			ret=-1;
		}
		return ret;
	}
}
