package org.moon.action.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;


public class BaseAction  
{
	private Logger logger = Logger.getLogger(this.getClass());
	public JSONObject result;// 返回的json

	public String pageNumber;// 每页显示的记录数

	public int pageSize;// 每页显示的记录数

	public int pageSize_num = 0;

	public int pageNumber_num = 0;

	public int currPage;

	public String sortname;

	public String sortorder;

	/**
	 * @param json
	 * @author 周小桥 |2015-6-25 上午10:02:29
	 * @version 0.1
	 */
	public void writeJson(Object json)
	{
		try
		{
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter()
					.write(JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss"));
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param json_obj
	 * @throws IOException
	 * @author 周小桥 |2015-5-13 下午2:37:06
	 * @version 0.1
	 */
	public void writeJson(String json_obj) throws IOException
	{
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("text/javascript;charset=UTF-8");
		PrintWriter writer = res.getWriter();
		writer.print(json_obj);
		writer.flush();
		writer.close();
	}

	/**
	 * @return
	 * @author 周小桥 |2015-6-25 上午10:02:35
	 * @version 0.1
	 */
	public JSONObject getPageParam()
	{
		JSONObject ret = new JSONObject();
		int start = 0;
		int end = 0;
//		if (pageSize != null && pageNumber != null)
//		{
//			pageSize_num = Integer.parseInt(pageSize);
//			pageNumber_num = Integer.parseInt(pageNumber);
//		}

		if (pageNumber_num > 1)
		{
			start = (pageNumber_num - 1) * pageSize_num;
			end = (pageNumber_num) * pageSize_num;
		}
		else
		{
			start = 0;
			end = pageSize_num;
		}
		ret.put("start", start);
		ret.put("end", end);
		return ret;
	}

	/**
	 * @param rows_json
	 * @param total
	 * @return
	 * @author 周小桥 |2015-6-25 上午10:10:43
	 * @version 0.1
	 */
	public JSONObject setPageParam(List<JSONObject> rows_json, int total)
	{
		JSONObject data_json = new JSONObject();
		data_json.put("result", rows_json);// rows键 存放每页记录 list
		data_json.put("total", total);
		data_json.put("pageSize", this.pageSize);
		data_json.put("pageNumber", this.pageNumber);
		return data_json;
	}
	/**
	 * 
	 * @param request
	 * @author 周小桥 |2015-10-5 上午10:23:20
	 * @version 0.1
	 */
	public void setPageParm(HttpServletRequest request)
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
	/**
	 * @param response
	 * @param JSONObj
	 * @author 周小桥 |2014-6-26 下午5:42:30
	 * @version 0.1
	 */
	public void doJsonResponse(HttpServletResponse response, JSONObject JSONObj)
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
	 * 
	 * @param rows_json
	 * @param total
	 * @return
	 * @author 周小桥 |2015-7-6 下午2:33:56
	 * @version 0.1
	 */
	public JSONObject setPageParam(JSONArray rows_json, int total)
	{
		JSONObject data_json = new JSONObject();
		data_json.put("result", rows_json);// rows键 存放每页记录 list
		data_json.put("total", total);
		data_json.put("pageSize", this.pageSize);
		data_json.put("pageNumber", this.pageNumber);
		return data_json;
	}

	public JSONObject getResult()
	{
		return result;
	}

	public void setResult(JSONObject result)
	{
		this.result = result;
	}

	public String getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(String pageNumber)
	{
		this.pageNumber = pageNumber;
	}

 

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getPageSize_num()
	{
		return pageSize_num;
	}

	public void setPageSize_num(int pageSize_num)
	{
		this.pageSize_num = pageSize_num;
	}

	public int getPageNumber_num()
	{
		return pageNumber_num;
	}

	public void setPageNumber_num(int pageNumber_num)
	{
		this.pageNumber_num = pageNumber_num;
	}

	public int getCurrPage()
	{
		return currPage;
	}

	public void setCurrPage(int currPage)
	{
		this.currPage = currPage;
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

}
