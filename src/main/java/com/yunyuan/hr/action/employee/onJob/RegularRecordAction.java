/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.yunyuan.hr.action.employee.onJob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.moon.action.util.BaseAction;
import org.moon.common.util.ChinaTransCode;
import org.moon.service.GeneralService;
 

/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-18 下午7:07:56 | 创建
 */
public class RegularRecordAction extends BaseAction
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


	private String eid;

	private String name;

	private String job;

	private String age;

	private String status;

	private String college;

	private String graduate_time;

	private String join_time;

	private Integer dept_id;

	private Integer salary_month;

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
			String eid = "" + System.currentTimeMillis();
			String sql = "INSERT  INTO tab_employee (eid,name,job,age,status,join_time,college,graduate_time,dept_id,salary_month) VALUES ('"
					+ eid
					+ "','"
					+ name
					+ "','"
					+ job
					+ "','"
					+ age
					+ "','"
					+ status
					+ "','"
					+ join_time
					+ "','"
					+ college
					+ "','"
					+ graduate_time + "'," + dept_id + "," + salary_month + ")";

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

			String sql = "update tab_employee set name='" + name + "',job='"
					+ job + "',age='" + age + "',status='" + status
					+ "',graduate_time='" + graduate_time + "',college='"
					+ college + "',dept_id=" + dept_id + ",salary_month="
					+ salary_month + ",join_time='" + join_time
					+ "' where eid=" + eid;
			if (ds.update(sql, null) > 0)
			{
				logger.info("更新成功！");
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
	public void delete()
	{
		JSONObject msg = new JSONObject();
		logger.info("delete");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try
		{
			setPageParm(request);
			String did = request.getParameter("deletes");

			if (did != null)
			{
				did = did.replaceAll(",", "','");
				String sql = "DELETE FROM  tab_employee where eid in ('" + did
						+ "')";
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
		String sql = null;
		try
		{
			setPageParm(request);
			sql = request.getParameter("sql");
			if (sql == null || "".equals(sql) || "null".equals(sql))
				sql = "SELECT t.*,d.name as  dept_name from tab_employee t,sec_org d where t.dept_id=d.id ";
			if (sortname != null && !"".equals(sortname))
			{
				jsonObj = ds.getPageQuery(sql, currPage, pageSize, sortname,
						sortorder);
			} else
				jsonObj = ds.getPageQuery(sql, currPage, pageSize, "eid",
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
		HttpServletRequest request = ServletActionContext.getRequest();
		//HttpServletResponse response = ServletActionContext.getResponse();
		String sql = "SELECT * from tab_employee ";
		setPageParm(request);
		if (request.getParameter("where") != null)
		{
			String whereSQL_print = request.getParameter("where");
			whereSQL_print = ChinaTransCode.getJspUTFSubmmit(whereSQL_print);
			sql = sql + whereSQL_print;
			// 转成打印传出条件语句
			whereSQL_print = whereSQL_print.replaceAll("=", ":");
			request.setAttribute("whereSQL_print", whereSQL_print);
		}
		request.setAttribute("sql", sql);

		return "success";
	}

	

	public Logger getLogger()
	{
		return logger;
	}

	public void setLogger(Logger logger)
	{
		this.logger = logger;
	}

	public String getEid()
	{
		return eid;
	}

	public void setEid(String eid)
	{
		this.eid = eid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getJob()
	{
		return job;
	}

	public void setJob(String job)
	{
		this.job = job;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getCollege()
	{
		return college;
	}

	public void setCollege(String college)
	{
		this.college = college;
	}

	public String getGraduate_time()
	{
		return graduate_time;
	}

	public void setGraduate_time(String graduate_time)
	{
		this.graduate_time = graduate_time;
	}

	public String getJoin_time()
	{
		return join_time;
	}

	public void setJoin_time(String join_time)
	{
		this.join_time = join_time;
	}

	public Integer getDept_id()
	{
		return dept_id;
	}

	public void setDept_id(Integer dept_id)
	{
		this.dept_id = dept_id;
	}

	public Integer getSalary_month()
	{
		return salary_month;
	}

	public void setSalary_month(Integer salary_month)
	{
		this.salary_month = salary_month;
	}

}