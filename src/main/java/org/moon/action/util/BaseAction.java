package org.moon.action.util;

 

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;



public class BaseAction  
{ 

	public JSONObject result;// 返回的json

	public String pageNumber;// 每页显示的记录数

	public String pageSize;// 当前第几页

	public int pageSize_num = 0;

	public int pageNumber_num = 0;

	/**
	 * @param json
	 * @author 周小桥 |2015-6-25 上午10:02:29
	 * @version 0.1
	 */
	public void writeJson(Object json)
	{
		try
		{
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=utf-8");
		 
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e)
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
		if (pageSize != null && pageNumber != null)
		{
			pageSize_num = Integer.parseInt(pageSize);
			pageNumber_num = Integer.parseInt(pageNumber);
		}

		if (pageNumber_num > 1)
		{
			start = (pageNumber_num - 1) * pageSize_num;
			end = (pageNumber_num) * pageSize_num;
		} else
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

	public String getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(String pageSize)
	{
		this.pageSize = pageSize;
	}

}
