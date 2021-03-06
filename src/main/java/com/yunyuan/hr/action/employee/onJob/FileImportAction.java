/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.yunyuan.hr.action.employee.onJob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.moon.common.util.ChinaTransCode;
import org.moon.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.yunyuan.hr.service.EmployeeService;
import com.yunyuan.util.FileAdminUtil;

/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-18 下午7:07:56 | 创建
 */
public class FileImportAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -949122781455369656L;
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

	//注入 对象
	@Autowired
	private EmployeeService employeeService;

	private int currPage;

	private int pageSize;

	private String sortname;

	private String sortorder;

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

	protected File upload_file;

	private String import_path;

	private String fileName;

	/**
	 * 
	 * @return
	 * @author 周小桥 |2015-9-24 下午3:25:14
	 * @version 0.1
	 */
	public void getDownloadFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		logger.info("getDownloadFile");
		 HttpServletRequest request = ServletActionContext.getRequest();
		 HttpServletResponse response=ServletActionContext.getResponse();
		 FileAdminUtil fdu=new FileAdminUtil();
		 fdu.downloadFile(request,response, "employee_file.xls");
		 

		// out.clear();
		// out = pageContext.pushBody();
	 
		//return "success";  
	}

	// 下载
	public String downloadFile() throws Exception
	{
		return "success";
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public String importExcel() throws Throwable
	{
		logger.info("importExcel");
		//HttpServletRequest request = ServletActionContext.getRequest();
		//String import_path=request.getParameter("import_path");
		//HttpServletResponse response = ServletActionContext.getResponse();
		try
		{
			employeeService.importEmployeeFile(upload_file);
			//FileAdmin.uploadFile(request, upload_file, fileName);
			//setPageParm(request);
			
		}
		catch (Exception e)
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

			String sql = "update tab_employee set name='" + name + "',job='" + job + "',age='" + age + "',status='"
					+ status + "',graduate_time='" + graduate_time + "',college='" + college + "',dept_id=" + dept_id
					+ ",salary_month=" + salary_month + ",join_time='" + join_time + "' where eid=" + eid;
			if (ds.update(sql, null) > 0)
			{
				logger.info("更新成功！");
			}
		}
		catch (Exception e)
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
				String sql = "DELETE FROM  tab_employee where eid in ('" + did + "')";
				int rs = ds.delete(sql, null);
				if (rs > 0)
				{
					msg.put("success", "删除" + rs + "条数据 成功！");
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
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
			if (dept_id == null)
			{
				dept_id = 200;
			}
			if (sql == null || "".equals(sql) || "null".equals(sql))
				sql = "SELECT e.*,s.name as  dept_name from tab_employee e,sec_org s where e.dept_id  in (select id  from sec_org g where FIND_IN_SET(g.id, getChildLst("
						+ dept_id + ")))  and e.dept_id=s.id   ";
			if (sortname != null && !"".equals(sortname))
			{
				jsonObj = ds.getPageQuery(sql, currPage, pageSize, sortname, sortorder);
			}
			else
				jsonObj = ds.getPageQuery(sql, currPage, pageSize, "eid", "desc");
			jsonObj.put("success", "查询成功！");
		}
		catch (Exception e)
		{
			jsonObj.put("error", "查询失败！");
			logger.error(e);
		}
		finally
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
		}
		catch (IOException e)
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

		currPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		pageSize = request.getParameter("rows") != null ? Integer.parseInt(request.getParameter("rows")) : 1;
		sortname = request.getParameter("sidx");
		sortorder = request.getParameter("sord");
		request.setAttribute("page", currPage);
		request.setAttribute("rows", pageSize);
		request.setAttribute("sidx", sortname);
		request.setAttribute("sord", sortorder);
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

	public File getUpload_file()
	{
		return upload_file;
	}

	public void setUpload_file(File upload_file)
	{
		this.upload_file = upload_file;
	}

	public String getImport_path()
	{
		return import_path;
	}

	public void setImport_path(String import_path)
	{
		this.import_path = import_path;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

}