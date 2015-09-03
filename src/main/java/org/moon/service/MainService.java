package org.moon.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.moon.common.db.IMDBTool;
import org.moon.common.db.MDBTool;
import org.snaker.framework.security.entity.Menu;

/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-24 下午2:24:37 | 创建 被登录动作后调用 LoginController
 */
public class MainService
{
	private IMDBTool db = new MDBTool();

	private static Log logger = LogFactory.getLog(MainService.class);

	/**
	 * @param pid
	 * @return
	 * @author 周小桥 |2014-6-24 下午2:31:48
	 * @version 0.1
	 */
	private List<Menu> getSubMenus(String pid, String user_id)
	{
		List<Menu> menus = new ArrayList<Menu>();
		String sql = null;
		if ("admin".equals(user_id))
		{
			sql = " select m.id ,m.name ,m.url,m.action from sec_menu m where m.pid='" + pid + "' ";
		}
		else
		{
			sql = "select m.id ,m.name ,m.url,m.action from sec_menu m,tab_user_menu t where m.pid='" + pid
					+ "' and t.user_id='" + user_id + "' " + "and m.id=t.menu_id";
		}
		try
		{
			ResultSet rows = db.executeQuery(sql, null);

			while (rows.next())
			{
				Menu menu = new Menu();
				menu.setId(rows.getLong("id"));
				menu.setName(rows.getString("name"));
				menu.setUrl(rows.getString("url"));
				menu.setAction(rows.getString("action"));
				menus.add(menu);
			}

			rows.close();
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			// db.closeConnection();

		}
		return menus;
	}

	/**
	 * 前台因为采用easyui展示修改数据结构
	 * @param pid
	 * @param user_id
	 * @return
	 * @author 周小桥 |2015-9-2 上午9:21:47
	 * @version 0.1
	 */
	private List<JSONObject> getJsonSubMenus(String user_id, String pid)
	{
		List<JSONObject> menus = new ArrayList<JSONObject>();
		String sql = null;
		ResultSet rows = null;
		if ("admin".equals(user_id))
		{
			sql = " select m.id ,m.name ,m.pid,m.url,m.action from sec_menu m ";
		}
		else
		{
			sql = "select m.id ,m.name ,m.pid,m.url,m.action from sec_menu m where  m.id in ("
					+ " select t.menu_id from tab_user_menu t where t.user_id='" + user_id + "' ) or m.id= " + pid;

		}
		try
		{
			rows = db.executeQuery(sql, null);

			while (rows.next())
			{
				JSONObject menu = new JSONObject();
				menu.put("id", "" + rows.getLong("id"));
				menu.put("pid", "" + rows.getLong("pid"));
				menu.put("text", rows.getString("name"));
				menu.put("url", rows.getString("url"));
				menu.put("action", rows.getString("action"));
				//JSONObject attributes = new JSONObject();
				//menu.put("attributes", attributes);			 
				menus.add(menu);

			}

		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			// db.closeConnection();
			try
			{
				if (rows != null)
					rows.close();
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return menus;
	}

	/**
	 * 递归创建子树
	 * @param menuList
	 * @param menu
	 * @return
	 * @author 周小桥 |2015-9-2 上午9:19:57
	 * @version 0.1
	 */
	private static JSONObject createTreeMenu(List<JSONObject> menuList, JSONObject menu)
	{

		List<JSONObject> child_list = new ArrayList<JSONObject>();
		if (menuList != null && menuList.size() > 0)
		{

			for (int i = 0; i < menuList.size(); i++)
			{

				if (menu.getString("id").equals(menuList.get(i).get("pid")))
				{
					JSONObject menu_child = menuList.get(i);

					child_list.add(menu_child);
					menuList.remove(i);
					i = i - 1;
					createTreeMenu(menuList, menu_child);

				}

			}
			if (child_list.size() > 0)
			{
				menu.put("children", child_list);
				menu.put("state", "closed");
			}
		}
		return menu;
	}

	/**
	 * @param parent
	 * @param basePath
	 * @return
	 * @author 周小桥 |2014-6-24 下午2:31:55
	 * @version 0.1
	 */
	public JSONObject getMenuToHtm(String user_id, String parent, String basePath, String user_name)
	{

		JSONObject menu_j = new JSONObject();
		List<?> menus = getSubMenus(parent, user_id);
		String url = "";
		String strSub = "";
		String path_chin = "";
		int i = 1;
		strSub += "<div class='menu' id='menus'> ";
		for (int j = 0; j < menus.size(); j++)
		{
			Menu menu = (Menu) menus.get(j);
			String path_chin1 = "登录用户:" + user_name + ";您当前的位置:" + menu.getName();
			// 1级子菜单
			String divmenu = "menu" + i;
			strSub += "  <div id=" + divmenu + " class='menusel' >   " + "  <h2><a href='" + menu.getUrl() + "' >"
					+ menu.getName() + "</a></h2>   " + "  <div class='position'> ";

			List<Menu> subMenus2 = getSubMenus(menu.getId().toString(), user_id);
			if (subMenus2 != null && subMenus2.size() > 0)
			{ // 2级子菜单
				strSub += "  <ul class='clearfix typeul'>    ";// 去掉clearfix
				for (int j2 = 0; j2 < subMenus2.size(); j2++)
				{
					Menu menu2 = (Menu) subMenus2.get(j2);
					url = menu2.getUrl();
					String path_chin2 = menu2.getName();
					String action = menu2.getAction();
					path_chin = path_chin1 + ">>" + menu2.getName();
					List<Menu> subMenus3 = getSubMenus(menu2.getId().toString(), user_id);
					if ((subMenus3 == null || subMenus3.size() == 0) && subMenus2.size() > 0)
					{// 无3级菜单,有2级菜单
						strSub += "  <li><a href='javascript:void(0)' onclick=\"openPage('" + url + "','" + action
								+ "','" + path_chin + "')\" >" + menu2.getName() + "</a> ";
					}
					else
					{
						strSub += "  <li><a href='javascript:void(0)' >" + menu2.getName() + "</a> ";
					}
					if (subMenus3 != null && subMenus3.size() > 0)
					{ // 3级子菜单
						strSub += "<ul>";
						for (int j3 = 0; j3 < subMenus3.size(); j3++)
						{
							Menu menu3 = (Menu) subMenus3.get(j3);
							action = menu3.getAction();
							List<Menu> subMenus4 = getSubMenus(menu3.getId().toString(), user_id);
							url = menu3.getUrl();
							String path_chin3 = menu3.getName();
							path_chin = path_chin1 + ">>" + path_chin2 + ">>" + menu3.getName();
							if (j3 == 0 && (subMenus4 == null || subMenus4.size() == 0))
							{
								strSub += "  <li class='fli'><a href='javascript:void(0)'  onclick=\"openPage('" + url
										+ "','" + action + "','" + path_chin + "')\" >" + menu3.getName() + "</a></li>";
							}
							else if (subMenus4 == null || subMenus4.size() == 0)
							{
								strSub += "  <li class='lli'><a href='javascript:void(0)'  onclick=\"openPage('" + url
										+ "','" + action + "','" + path_chin + "')\" >" + menu3.getName()
										+ "</a></li> ";
							}
							else
							{// 存在4级子菜单
								strSub += "  <li class='lli'><a href='javascript:void(0)'>" + menu3.getName() + "</a> ";
								strSub += "<ul>";
								for (int j4 = 0; j4 < subMenus4.size(); j4++)
								{
									Menu menu4 = (Menu) subMenus4.get(j4);
									url = menu4.getUrl();
									action = menu4.getAction();
									path_chin += ">>" + menu4.getName();
									path_chin = path_chin1 + ">>" + path_chin2 + ">>" + path_chin3 + ">>"
											+ menu4.getName();
									if (j4 == 0)
										strSub += "  <li class='fli'><a href='javascript:void(0)'  onclick=\"openPage('"
												+ url
												+ "','"
												+ action
												+ "','"
												+ path_chin
												+ "')\" >"
												+ menu4.getName()
												+ "</a></li> ";
									else
										strSub += "  <li class='lli'><a href='javascript:void(0)' onclick=\"openPage('"
												+ url + "','" + action + "','" + path_chin + "')\" >" + menu4.getName()
												+ "</a></li>";
								}
								strSub += "</ul>";
								strSub += "</li> ";// 结束3级子菜单
							}
						}
						strSub += "</ul>";
						strSub += "</li>";// 结束2级子菜单
					}
					else
					{
						strSub += "</li>";// 结束2级子菜单
					}
				}
				strSub += "  </ul>   ";
			}
			else
				strSub += "  </ul>   ";// 结束1级子菜单

			strSub += "  </div><!-- position -->   " + " </div><!-- menusel --> ";
			i++;

		}
		strSub += "</div><!-- menu -->   ";

		menu_j.put("strSub", strSub);
		menu_j.put("menuLen", i);

		return menu_j;
	}

	/**
	 * @param request
	 * @param response
	 * @author 周小桥 |2014-6-27 上午9:37:02
	 * @version 0.1
	 * @throws Exception
	 */
	public String checkUser(String user_id, String passWord) throws Exception
	{
		String user_name = null;
		String sql = "SELECT fullname from sec_user where username='" + user_id + "' and passWord='" + passWord + "'";
		List<?> u_lj = db.executeJSONQuery(sql, null);
		if (u_lj != null && u_lj.size() > 0 && u_lj.get(0) != null)
		{
			JSONObject js_user = (JSONObject) u_lj.get(0);
			user_name = js_user.getString("fullname");
		}
		return user_name;

	}

	/**
	 * @param sql
	 * @param parm
	 * @return
	 * @throws Exception
	 * @author 周小桥 |2014-8-15 上午11:33:13
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

		}
		finally
		{
			// if (db != null)
			// db.closeConnection();
		}
		return ret;
	}

	/**
	 * 前台因为采用easyui展示修改数据结构
	 * @param user_id
	 * @param parent
	 * @param basePath
	 * @param user_name
	 * @return
	 * @author 周小桥 |2015-9-2 上午9:19:50
	 * @version 0.1
	 */
	public JSONObject getMenuToJson(String user_id, String parent)
	{
		JSONObject menu_root = null;
		List<JSONObject> menus = getJsonSubMenus(user_id, parent);
		for (int i = 0; i < menus.size(); i++)
		{
			if (parent.equals(menus.get(i).getString("id")))
			{
				menu_root = menus.remove(i);
				break;
			}
		}
		if (menu_root != null)
		{
			menu_root = createTreeMenu(menus, menu_root);
			menu_root.put("state", "open");//根节点展开
		}

		return menu_root;
	}
}
