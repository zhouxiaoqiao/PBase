package org.moon.action.sys;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.moon.action.util.BaseAction;
import org.moon.service.MainService;

public class MainAction extends BaseAction
{

	private MainService mainService = new MainService();
	private String user_id;

	/**
	 * 
	 * 
	 * @author 周小桥 |2015-9-2 下午2:59:09
	 * @version 0.1
	 */
	public void getMenuTree()
	{

		List<JSONObject> json = new ArrayList<JSONObject>();
		JSONObject menu_j = mainService.getMenuToJson(user_id, "0");
		json.add(menu_j);
		try
		{
			writeJson(json);
		}
		catch (Exception e)
		{
			 
			e.printStackTrace();
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

}
