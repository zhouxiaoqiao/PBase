package com.yunyuan.util;

import javax.servlet.http.HttpServletRequest;

public class PageUtil
{
	private String basePath;
	private String sql;
	private int page;
	private int rows;
	private String sortname;
	private String sortorder;
	private String url;
	private String whereSQL_print="no";
	private String page_parm="";
	/**
	 * 
	 * @param request
	 * @param action
	 */
	public PageUtil(HttpServletRequest request, String action)
	{
		String path = request.getContextPath();
		basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		url = basePath + action;
		sql = null;
		if (request.getAttribute("sql") != null)
			sql = (String) request.getAttribute("sql");

		if (request.getAttribute("whereSQL_print") != null)
		{
			whereSQL_print = (String) request.getAttribute("whereSQL_print");
		}
		
		if (request.getAttribute("page") != null)
		{
			page = (Integer) request.getAttribute("page");
			page_parm="&page="+page;
		}
		if (request.getAttribute("rows") != null)
		{
			rows = (Integer) request.getAttribute("rows") ;
			page_parm=page_parm+"&rows="+rows;
		}
		if (request.getAttribute("sortname") != null)
		{
			sortname = (String) request.getAttribute("sortname");
		}
		
		if (request.getAttribute("sortorder") != null)
		{
			sortorder = (String) request.getAttribute("sortorder");
			page_parm=page_parm+"&sortorder="+sortorder;
		}
		
	}

	
	
	public String getBasePath()
	{
		return basePath;
	}

	public void setBasePath(String basePath)
	{
		this.basePath = basePath;
	}

	public String getSql()
	{
		return sql;
	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getWhereSQL_print()
	{
		return whereSQL_print;
	}

	public void setWhereSQL_print(String whereSQL_print)
	{
		this.whereSQL_print = whereSQL_print;
	}

	
	public String getPage_parm()
	{
		return page_parm;
	}



	public void setPage_parm(String page_parm)
	{
		this.page_parm = page_parm;
	}



	/**
	 * @param args
	 * @author 周小桥 |2014-8-18 上午10:45:32
	 * @version 0.1
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
