/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package org.moon.action.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.moon.common.util.ChinaTransCode;
import org.moon.action.util.BaseAction;
import org.moon.service.GeneralService;


/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-18 下午7:07:56 | 创建
 */

public class ReportAdminAction extends BaseAction
{

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	private GeneralService ds = new GeneralService();

	private int currPage;

	private int pageSize;

	private String sortname;

	private String sortorder;

	private String report_id;

	private String report_name;

	private String templet_path;

	private String excute_sql;

	private String where_sql;

	private String remark;

	private String report_type;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public String add() throws Throwable
	{
		logger.info("add");
		HttpServletRequest request = ServletActionContext.getRequest();
		// HttpServletResponse response = ServletActionContext.getResponse();

		try
		{
			setPageParm(request);
			// demoForm = (ReportForm) form;
			String report_id = null;
			if ("1".equals(report_type))
				report_id = "report" + System.currentTimeMillis();
			else
				report_id = "proc" + System.currentTimeMillis();
			String sql = "INSERT  INTO tab_report (report_id,report_name,templet_path,report_type,excute_sql,where_sql,remark) VALUES ('"
					+ report_id
					+ "','"
					+ report_name
					+ "','"
					+ templet_path
					+ "','"
					+ report_type
					+ "','"
					+ excute_sql
					+ "','"
					+ where_sql + "','" + remark + "')";

			if (ds.insert(sql, null) > 0)
			{
				logger.info("插入成功！");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String update()
	{
		logger.info("update");
		HttpServletRequest request = ServletActionContext.getRequest();
		try
		{
			setPageParm(request);

			String sql = "update tab_report set report_name='" + report_name
					+ "',templet_path='" + templet_path + "',excute_sql='"
					+ excute_sql + "',report_type='" + report_type
					+ "',where_sql='" + where_sql + "',remark='" + remark
					+ "' where report_id='" + report_id + "'";
			int ret = ds.update(sql, null);
			if (ret > 0)
			{
				logger.info("更新成功！");
			} else if (ret == 0)
			{
				logger.info("值未变化,不需更新！");
			} else
				logger.info("更新失败！");
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @author 周小桥 |2014-8-9 下午3:40:31
	 * @version 0.1
	 */
	public String updateRun()
	{
		logger.info("updateRun");
		HttpServletRequest request = ServletActionContext.getRequest();
		setPageParm(request);
	 
		try
		{
 
			String sql = "update tab_report set report_name='"
					+ report_name + "',templet_path='"
					+ templet_path + "',excute_sql='"
					+ excute_sql + "',where_sql='"
					+ where_sql + "',remark='" + remark
					+ "' where report_id='" + report_id + "'";
			int ret = ds.update(sql, null);
			if (ret > 0)
			{
				logger.info("更新成功！");
			} else if (ret == 0)
			{
				logger.info("值未变化,不需更新！");
			} else
				logger.info("更新失败！");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public void delete()
	{
		logger.info("delete");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject msg = new JSONObject();
		try
		{
			String did = request.getParameter("deletes");
			setPageParm(request);
			if (did != null)
			{
				did = did.replaceAll(",", "','");
				String sql = "DELETE FROM  tab_report where report_id in ('"
						+ did + "')";
				int rs = ds.delete(sql, null);
				if (rs > 0)
				{
					msg.put("success", "删除" + rs + "条数据 成功！");
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			this.doJsonResponse(response, msg);
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 周小桥 |2014-6-18 下午7:06:56
	 * @version 0.1
	 */
	public void initPage()
	{
		logger.info("initPage");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = new JSONObject();

		int currPage = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rows") != null ? Integer
				.parseInt(request.getParameter("rows")) : 1;
		String sortname = request.getParameter("sidx");
		String sortorder = request.getParameter("sord");
		String sql = null;
		try
		{
			setPageParm(request);
			sql = request.getParameter("sql");
			String report_type = request.getParameter("report_type");
			if (sql == null || "".equals(sql) || "null".equals(sql))
				sql = "SELECT * from tab_report where report_type='"
						+ report_type + "'";
			if (sortname != null && !"".equals(sortname))
			{
				jsonObj = ds.getPageQuery(sql, currPage, pageSize, sortname,
						sortorder);
			} else
				jsonObj = ds.getPageQuery(sql, currPage, pageSize, "report_id",
						"desc");
			jsonObj.put("success", "查询成功！");
		} catch (Exception e)
		{
			jsonObj.put("error", "查询失败！");
			logger.error(e);
		} finally
		{
			this.doJsonResponse(response, jsonObj);
		}

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 周小桥 |2014-6-27 下午5:15:07
	 * @version 0.1
	 */
	public String doFind()
	{
		logger.info("doFind");
		String sql = "SELECT * from tab_report ";
		HttpServletRequest request = ServletActionContext.getRequest();

		if (request.getParameter("where") != null)
		{
			String whereSQL_print = request.getParameter("where");
			whereSQL_print = ChinaTransCode.getJspUTFSubmmit(whereSQL_print);
			whereSQL_print = whereSQL_print.replaceAll("%", "%25");
			sql = sql + whereSQL_print;
			// 转成打印传出条件语句
			whereSQL_print = whereSQL_print.replaceAll("=", ":");

			request.setAttribute("whereSQL_print", whereSQL_print);
		}
		request.setAttribute("sql", sql);
		if (sql.contains("report_type=2"))
			return "proc_success";
		else
			return "success";
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 周小桥 |2014-8-11 下午2:58:21
	 * @version 0.1
	 */
	public void excuteProc()
	{
		JSONObject msg = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try
		{
			String procedureName = request.getParameter("procedureName");
			String inparams = request.getParameter("inparams");
			List<String> inparamList = new ArrayList<String>();
			String a[] = inparams.split(",");
			for (int i = 0; i < a.length; i++)
			{
				String b[] = a[i].split(":");
				if (b.length == 2)
					inparamList.add(b[1]);
			}
			if (ds.executeProcedure(procedureName, inparamList, null) == 0)
				msg.put("success", "执行成功!");
			else
				msg.put("success", "执行失败!");
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			this.doJsonResponse(response, msg);
		}
	}

	/**
	 * @param response
	 * @param JSONObj
	 * @author 周小桥 |2014-6-26 下午5:42:30
	 * @version 0.1
	 */
	private void doJsonResponse(HttpServletResponse response, JSONObject JSONObj)
	{
		// 设置字符编码
		response.setCharacterEncoding("UTF-8");
		// 返回json对象（通过PrintWriter输出）
		try
		{
			String key = "RESPCODE";
			if (!JSONObj.containsKey(key))
			{
				JSONObj.put(key, "0000");
			}

			String resp = (String) JSONObj.get(key);

			key = "RESPMSG";
			if (!"0000".equals(resp) && !JSONObj.containsKey(key))
			{

				JSONObj.put(key, "操作错误");
			}

			response.getWriter().print(JSONObj);
		} catch (IOException e)
		{

			logger.error("写JSON返回数据出错.");
			logger.error(e);
		}
	}

	/**
	 * @param request
	 * @author 周小桥 |2014-8-18 上午10:35:45
	 * @version 0.1
	 */
	private void setPageParm(HttpServletRequest request)
	{

		currPage = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		pageSize = request.getParameter("rows") != null ? Integer
				.parseInt(request.getParameter("rows")) : 1;
		sortname = request.getParameter("sidx");
		sortorder = request.getParameter("sord");
		request.setAttribute("page", currPage);
		request.setAttribute("rows", pageSize);
		request.setAttribute("sidx", sortname);
		request.setAttribute("sord", sortorder);
	}

	public String getSortname()
	{
		return sortname;
	}

	public void setSortname(String sortname)
	{
		this.sortname = sortname;
	}

	public String getSortorder()
	{
		return sortorder;
	}

	public void setSortorder(String sortorder)
	{
		this.sortorder = sortorder;
	}

	public String getReport_id()
	{
		return report_id;
	}

	public void setReport_id(String report_id)
	{
		this.report_id = report_id;
	}

	public String getReport_name()
	{
		return report_name;
	}

	public void setReport_name(String report_name)
	{
		this.report_name = report_name;
	}

	public String getTemplet_path()
	{
		return templet_path;
	}

	public void setTemplet_path(String templet_path)
	{
		this.templet_path = templet_path;
	}

	public String getExcute_sql()
	{
		return excute_sql;
	}

	public void setExcute_sql(String excute_sql)
	{
		this.excute_sql = excute_sql;
	}

	public String getWhere_sql()
	{
		return where_sql;
	}

	public void setWhere_sql(String where_sql)
	{
		this.where_sql = where_sql;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getReport_type()
	{
		return report_type;
	}

	public void setReport_type(String report_type)
	{
		this.report_type = report_type;
	}

}