package org.moon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.moon.common.db.IMDBTool;
import org.moon.common.db.MDBTool;
import org.moon.dao.IGeneralDao;
import org.moon.entities.MenuTreeNode;
import org.moon.service.inf.ITreeService;
import org.springframework.stereotype.Service;

@Service("treeService")
public class TreeServiceImpl implements ITreeService
{

	@Resource
	private IGeneralDao generalDao;

	private static Log logger = LogFactory.getLog(TreeServiceImpl.class);
	private static IMDBTool db = new MDBTool();

	public MenuTreeNode getRootNode() throws Exception
	{
		List<?> queryList = (List<?>) generalDao.query("queryRootNode");
		if (queryList.size() != 0)
		{
			return (MenuTreeNode) queryList.get(0);
		}
		return new MenuTreeNode();
	}

	/**
	 * 获取
	 */
	public void saveOrUpdateRootNode(MenuTreeNode treeNode) throws Exception
	{
		if (treeNode == null)
			return;
		List<?> list = (List<?>) generalDao.query("queryTreeNodeById", treeNode.getId());
		if (list.size() != 0)
		{
			generalDao.update(treeNode);
		}
		else
		{
			treeNode.setId(null);
			generalDao.create(treeNode);
		}
	}

	/**
	 * 
	 */
	public void addNode(MenuTreeNode treeNode) throws Exception
	{
		int id = this.getAddMenuID(treeNode.getParent_menu());
		treeNode.setId(id);
		if (treeNode != null)
			generalDao.create(treeNode);
	}

	public List<?> findChildNodes(MenuTreeNode treeNode) throws Exception
	{
		return (List<?>) generalDao.query("queryTreeNodeByParentId", treeNode.getParent().getId());
	}

	public Long countChildNodeByParentId(Integer pid) throws Exception
	{
		List<?> list = (List<?>) generalDao.query("countChildNodeByParentId", pid);
		return (Long) list.get(0);
	}

	public int deleteNode(MenuTreeNode parentNode) throws Exception
	{
		int result = 0;
		List<?> childNodes = (List<?>) generalDao.query("queryTreeNodeByParentId", parentNode.getId());
		String sql = "delete from tab_user_menu where menu_id=";
		for (Object object : childNodes)
		{
			MenuTreeNode childNode = (MenuTreeNode) object;
			result += deleteNode(childNode);
			//删除对应的用户权限
			if (childNode != null && childNode.getId() != null)
				logger.info("删除对应用户权限数:" + db.executeUpdate(sql + childNode.getId(), null));
		}
		result += generalDao.delete(parentNode);
		//删除对应的用户权限
		if (parentNode != null && parentNode.getId() != null)
			logger.info("删除对应用户权限数:" + db.executeUpdate(sql + parentNode.getId(), null));
		return result;
	}

	@Override
	public void updateNode(MenuTreeNode treeNode) throws Exception
	{
		if (treeNode == null)
			return;
		List<?> list = (List<?>) generalDao.query("queryTreeNodeById", treeNode.getId());
		if (list.size() != 0)
		{
			generalDao.update(treeNode);
		}
		else
		{
			treeNode.setId(null);
			generalDao.create(treeNode);
		}
	}

	@Override
	public MenuTreeNode queryNodeById(MenuTreeNode treeNode) throws Exception
	{
		List<?> list = (List<?>) generalDao.query("queryTreeNodeById", treeNode.getId());
		if (list.size() != 0)
			return (MenuTreeNode) list.get(0);
		return new MenuTreeNode();
	}

	/**
	 * 
	 */
	public JSONObject getUserRightMenus(String userID) throws Exception
	{
		JSONObject ms = new JSONObject();
		String sql = " select menu_id from tab_user_menu where user_id='" + userID + "'";

		try
		{
			List<JSONObject> ms_l = db.executeJSONQuery(sql, null);
			for (int i = 0; i < ms_l.size(); i++)
			{
				JSONObject mid = ms_l.get(i);
				ms.put(mid.get("menu_id"), true);
			}

		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			//db.closeConnection();

		}
		return ms;
	}

	/**
	 * @param userID
	 * @param checkedBoxIDs
	 * @param uncheckedBoxIDs
	 * @throws Throwable
	 * @author 周小桥 |2014-7-29 下午3:59:48
	 * @version 0.1
	 */
	public boolean updateMenuRight(String userID, String checkedBoxIDs, String uncheckedBoxIDs) 
	{
		boolean ret = false;
		if (userID != null && (checkedBoxIDs != null || uncheckedBoxIDs != null))
		{

			try
			{
				Integer menu_id = 0;
				if (checkedBoxIDs != null)
				{
					String[] a_c = checkedBoxIDs.split(",");
					for (int i = 0; i < a_c.length; i++)
					{
						if (a_c[i] != null && !"".equals(a_c[i]))
						{
							menu_id = Integer.parseInt(a_c[i]);
							List<JSONObject> rs = db.executeJSONQuery(" select * from tab_user_menu where user_id='"
									+ userID + "' and menu_id=" + menu_id, null);
							if (rs == null || rs.isEmpty())
							{
								String insertSql = "insert into tab_user_menu(user_id,menu_id) values('" + userID
										+ "'," + menu_id + ")";
								if (db.executeUpdate(insertSql, null) > 0)
									ret = true;
							}
							else
								ret = true;
						}
					}
				}
				// ////////////////////////////////////////////
				if (uncheckedBoxIDs != null)
				{
					String[] a_u = uncheckedBoxIDs.split(",");
					for (int i = 0; i < a_u.length; i++)
					{
						if (a_u[i] != null && !"".equals(a_u[i]))
						{
							menu_id = Integer.parseInt(a_u[i]);
							String delSql = "delete from tab_user_menu where user_id='" + userID + "' and menu_id="
									+ menu_id;
							if (db.executeUpdate(delSql, null) > 0)
								ret = true;
						}
					}
				}
			}
			catch (Exception e)
			{
				logger.error(e);
			}
			finally
			{
				//				if (db != null)
				//					db.closeConnection();
			}
		}
		return ret;
	}
/**
 * 
 * @param userID
 * @return
 * @author 周小桥 |2015-10-9 下午2:33:49
 * @version 0.1
 */
	public boolean deleteUserMenuRight(String userID)  
	{
		boolean ret = false;
		 
		String delSql = "delete from tab_user_menu where user_id='" + userID + "' ";
			 
		try
		{
			if (db.executeUpdate(delSql, null) > 0)
				ret = true;
		}
		catch (Exception e)
		{
			logger.equals(e);
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @param pid
	 * @return
	 * @author 周小桥 |2014-7-26 下午5:33:54
	 * @version 0.1
	 */
	private int getAddMenuID(Integer pid)
	{

		int addMenuID = 0;
		int menu_size = 10;
		try
		{
			List<JSONObject> m_l = this.getMenus("select max(id) id from sec_menu where  parent_menu=" + pid);

			if (m_l != null && m_l.size() > 0)
			{
				JSONObject jsonObj = (JSONObject) m_l.get(0);
				if (jsonObj.containsKey("id"))
				{
					addMenuID = jsonObj.getInt("id") + 1;
					if (addMenuID % menu_size == 0)
					{
						int i = 1;
						for (; i <= menu_size; i++)
						{
							addMenuID = addMenuID - i;
							m_l = this.getMenus("select  id from sec_menu where parent_menu=" + pid + " and id=" + ""
									+ addMenuID);
							if (m_l == null)
								break;
						}
						if (i > menu_size)
							throw new Exception("子菜单超过" + menu_size + "个,越界！");
					}
				}
				else
					addMenuID = pid * menu_size;
			}
			else
				addMenuID = pid * menu_size;

		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			// db.closeConnection();

		}
		return addMenuID;
	}

	/**
	 * @param sql
	 * @return
	 * @author 周小桥 |2014-7-26 下午5:34:16
	 * @version 0.1
	 * @throws Exception
	 */
	private List<JSONObject> getMenus(String sql) throws Exception
	{
		List<JSONObject> menus = null;
		try
		{
			menus = db.executeJSONQuery(sql, null);

		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			//db.closeConnection();

		}
		return menus;
	}
}
