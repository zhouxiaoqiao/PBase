package org.moon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.moon.common.db.IMDBTool;
import org.moon.common.db.MDBTool;
import org.moon.common.db.SQLTool;
import org.moon.dao.IGeneralDao;
import org.moon.entities.DeptTreeNode;
import org.moon.service.inf.IDeptTreeService;
import org.springframework.stereotype.Service;

@Service("deptTreeService")
public class DeptTreeServiceImpl implements IDeptTreeService
{

	@Resource
	private IGeneralDao generalDao;

	private static Log logger = LogFactory.getLog(DeptTreeServiceImpl.class);

	private static IMDBTool db = new MDBTool();

	public DeptTreeNode getRootNode() throws Exception
	{
		List<?> queryList = (List<?>) generalDao.query("queryDeptRootNode");
		if (queryList.size() != 0)
		{
			return (DeptTreeNode) queryList.get(0);
		}
		return new DeptTreeNode();
	}

	/**
	 * 获取
	 */
	public void saveOrUpdateRootNode(DeptTreeNode treeNode) throws Exception
	{
		if (treeNode == null)
			return;
		List<?> list = (List<?>) generalDao.query("queryDeptTreeNodeById",
				treeNode.getId());
		if (list.size() != 0)
		{
			generalDao.update(treeNode);
		} else
		{
			treeNode.setId(null);
			generalDao.create(treeNode);
		}
	}

	/**
	 * 
	 */
	public void addNode(DeptTreeNode treeNode) throws Exception
	{
		int id = this.getAddDeptID(treeNode.getParent_org());
		treeNode.setId(id);
		if (treeNode != null)
			generalDao.create(treeNode);
	}

	public List<?> findChildNodes(DeptTreeNode treeNode) throws Exception
	{

		return (List<?>) generalDao.query("queryDeptTreeNodeByParentId",
				treeNode.getParent().getId());
	}

	public Long countChildNodeByParentId(Integer pid) throws Exception
	{
		List<?> list = (List<?>) generalDao.query(
				"countDeptChildNodeByParentId", pid);
		return (Long) list.get(0);
	}

	public int deleteNode(DeptTreeNode parentNode) throws Exception
	{
		int result = 0;
		List<?> childNodes = (List<?>) generalDao.query(
				"queryDeptTreeNodeByParentId", parentNode.getId());
		String sql = "delete from tab_user_menu where menu_id=";
		for (Object object : childNodes)
		{
			DeptTreeNode childNode = (DeptTreeNode) object;
			result += deleteNode(childNode);
			// 删除对应的用户权限
			if (childNode != null && childNode.getId() != null)
				logger.info("删除对应用户权限数:"
						+ db.executeUpdate(sql + childNode.getId(), null));
		}
		result += generalDao.delete(parentNode);
		// 删除对应的用户权限
		if (parentNode != null && parentNode.getId() != null)
			logger.info("删除对应用户权限数:"
					+ db.executeUpdate(sql + parentNode.getId(), null));
		return result;
	}

	@Override
	public void updateNode(DeptTreeNode treeNode) throws Exception
	{
		if (treeNode == null)
			return;
		List<?> list = (List<?>) generalDao.query("queryDeptTreeNodeById",
				treeNode.getId());
		if (list.size() != 0)
		{
			generalDao.update(treeNode);
		} else
		{
			treeNode.setId(null);
			generalDao.create(treeNode);
		}
	}

	@Override
	public DeptTreeNode queryNodeById(DeptTreeNode treeNode) throws Exception
	{
		List<?> list = (List<?>) generalDao.query("queryDeptTreeNodeById",
				treeNode.getId());
		if (list.size() != 0)
			return (DeptTreeNode) list.get(0);
		return new DeptTreeNode();
	}

	/**
	 * @param userID
	 * @param checkedBoxIDs
	 * @param uncheckedBoxIDs
	 * @throws Throwable
	 * @author 周小桥 |2014-7-29 下午3:59:48
	 * @version 0.1
	 */
	public boolean updateMenuRight(String userID, String checkedBoxIDs,
			String uncheckedBoxIDs) throws Exception
	{
		boolean ret = false;
		if (userID != null
				&& (checkedBoxIDs != null || uncheckedBoxIDs != null))
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
							List<JSONObject> rs = db.executeJSONQuery(
									" select * from tab_user_menu where user_id='"
											+ userID + "' and menu_id="
											+ menu_id, null);
							if (rs == null || rs.isEmpty())
							{
								String insertSql = "insert into tab_user_menu(user_id,menu_id) values('"
										+ userID + "'," + menu_id + ")";
								if (db.executeUpdate(insertSql, null) > 0)
									ret = true;
							} else
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
							String delSql = "delete from tab_user_menu where user_id='"
									+ userID + "' and menu_id=" + menu_id;
							if (db.executeUpdate(delSql, null) > 0)
								ret = true;
						}
					}
				}
			} catch (Exception e)
			{
				logger.error(e);
			} finally
			{
				// if (db != null)
				// db.closeConnection();
			}
		}
		return ret;
	}

	/**
	 * @param pid
	 * @return
	 * @author 周小桥 |2014-7-26 下午5:33:54
	 * @version 0.1
	 */
	private int getAddDeptID(Integer pid)
	{

		int addMenuID = 0;
		int menu_size = 10;
		try
		{
			List<JSONObject> m_l = this
					.getMenus("select max(id) dept_id from sec_org where  parent_org="
							+ pid);

			if (m_l != null && m_l.size() > 0)
			{
				JSONObject jsonObj = (JSONObject) m_l.get(0);
				if (jsonObj.containsKey("dept_id"))
				{
					addMenuID = jsonObj.getInt("dept_id") + 1;
					if (addMenuID % menu_size == 0)
					{
						int i = 1;
						for (; i <= menu_size; i++)
						{
							addMenuID = addMenuID - i;
							m_l = this
									.getMenus("select  id from sec_org where  parent_org="
											+ pid + " and id=" + "" + addMenuID);
							if (m_l == null)
								break;
						}
						if (i > menu_size)
							throw new Exception("子菜单超过" + menu_size + "个,越界！");
					}
				} else
					addMenuID = pid * menu_size;
			} else
				addMenuID = pid * menu_size;

		} catch (Exception e)
		{
			logger.error(e);
		} finally
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

		} catch (Exception e)
		{
			logger.error(e);
		} finally
		{
			// db.closeConnection();

		}
		return menus;
	}

	/**
	 * @param dept_id
	 * @return
	 * @throws Exception
	 * @author 周小桥 |2014-8-14 下午5:02:01
	 * @version 0.1
	 */
	public String getDeptUserSQL(Integer dept_id) throws Exception
	{

		String re_sql = SQLTool.getRecuSQl("sec_org", "parent_org", "id",
				dept_id, true);
		String where_sql = null;
		try
		{
			List<JSONObject> ms_l = db.executeJSONQuery(re_sql, null);
			if (ms_l != null && ms_l.size() > 0)
			{
				where_sql = "( ";
				for (int i = 0; i < ms_l.size(); i++)
				{
					JSONObject mid = ms_l.get(i);
					if (i == 0)
						where_sql = where_sql + mid.get("id");
					else
						where_sql = where_sql + "," + mid.get("id");
				}
				where_sql = where_sql + ")";
			}

		} catch (Exception e)
		{
			logger.error(e);
		} finally
		{
			// db.closeConnection();

		}
		return where_sql;
	}
}
