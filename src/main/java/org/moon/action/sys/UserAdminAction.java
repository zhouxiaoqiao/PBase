package org.moon.action.sys;
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
public class UserAdminAction   extends BaseAction
{
	private String user_id;

	private String password;

	private String user_name;

	private String dept_id;

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	private GeneralService gs = new GeneralService();

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
		// method stub
		try
		{

			String sql = "INSERT  INTO sec_user (username,fullname,password,org) VALUES ('"
					+ user_id
					+ "','"
					+ user_name
					+ "','"
					+ password
					+ "','"
					+ ChinaTransCode.getJspUTFSubmmit(dept_id) + "')";

			if (gs.insert(sql, null) > 0)
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
		// HttpServletResponse response = ServletActionContext.getResponse();
		try
		{

			String sql = "update sec_user set fullname='" + user_name
					+ "',password='" + password + "',org='" + dept_id
					+ "' where username='" + user_id + "'";
			if (gs.update(sql, null) > 0)
			{
				logger.info("更新成功！");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		String submitMode = request.getParameter("submitMode");
		if ("alone".equals(submitMode))
		{

			return "edit_user";
		} else
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

			if (did != null)
			{
				did = did.replaceAll(",", "','");
				String sql = "DELETE FROM  tab_user where user_id in ('" + did
						+ "')";
				int rs = gs.delete(sql, null);
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
			sql = request.getParameter("sql");
			sql = ChinaTransCode.getJspUTFSubmmit(sql);
			if (sql == null || "".equals(sql) || "null".equals(sql))
				sql = "select u.*,d.name as dept_name from  sec_user u, sec_org d where  u.org=d.id ";
			if (sortname != null && !"".equals(sortname))
			{
				jsonObj = gs.getPageQuery(sql, currPage, pageSize, sortname,
						sortorder);
			} else
				jsonObj = gs.getPageQuery(sql, currPage, pageSize, "username",
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
		String sql = "SELECT * from sec_user ";
		HttpServletRequest request = ServletActionContext.getRequest();
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 周小桥 |2014-8-2 下午1:11:33
	 * @version 0.1
	 */
	public void checkUserExits()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject msg = new JSONObject();
		try
		{
			String user_id = request.getParameter("user_id");

			if (user_id != null)
			{

				String sql = "select *from  sec_user where username ='"
						+ user_id + "'";
				List<JSONObject> rs = gs.query(sql, null);
				msg.put("success", true);
				if (rs.isEmpty())
				{
					msg.put("result", "合法帐号!");
				} else
				{
					msg.put("result", "帐号已经存在，请重新输入！");
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
	 * @author 周小桥 |2014-8-6 下午3:35:04
	 * @version 0.1
	 */
	public void getUserInf()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject msg = new JSONObject();
		try
		{
			String user_id = request.getParameter("user_id");

			if (user_id != null)
			{

				String sql = "select u.*,d.name as dept_name from  sec_user u, sec_org d where user_id='"
						+ user_id + "' and u.org=d.id";
				List<JSONObject> rs = gs.query(sql, null);
				msg.put("success", true);
				msg.put("record", rs);
				if (rs != null && rs.isEmpty())
				{
					msg.put("result", "合法帐号!");
				} else
				{
					msg.put("result", "帐号已经存在，请重新输入！");
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
 

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getDept_id()
	{
		return dept_id;
	}

	public void setDept_id(String dept_id)
	{
		this.dept_id = dept_id;
	}

}