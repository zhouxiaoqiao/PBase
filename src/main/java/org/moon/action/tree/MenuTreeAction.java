package org.moon.action.tree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.moon.entities.MenuTreeNode;
import org.moon.service.GeneralService;
import org.moon.service.inf.ITreeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Controller("MenuTreeAction")
@Scope("prototype")
public class MenuTreeAction extends ActionSupport implements Preparable,
		ModelDriven<MenuTreeNode>
{

	private static final long serialVersionUID = -5556695714468905364L;

	private Logger logger = Logger.getLogger(this.getClass());

	private MenuTreeNode treeNode = new MenuTreeNode();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private PrintWriter out;

	private String basePath;

	private String checkedBoxIDs;

	private String uncheckedBoxIDs;

	private String user_id;

	private String menu_id;

	@Resource
	private ITreeService treeService;

	public MenuTreeNode getTreeNode()
	{
		return treeNode;
	}

	public void setTreeNode(MenuTreeNode treeNode)
	{
		this.treeNode = treeNode;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public HttpServletResponse getResponse()
	{
		return response;
	}

	public void setResponse(HttpServletResponse response)
	{
		this.response = response;
	}

	public String getCheckedBoxIDs()
	{
		return checkedBoxIDs;
	}

	public void setCheckedBoxIDs(String checkedBoxIDs)
	{
		this.checkedBoxIDs = checkedBoxIDs;
	}

	public String getUncheckedBoxIDs()
	{
		return uncheckedBoxIDs;
	}

	public void setUncheckedBoxIDs(String uncheckedBoxIDs)
	{
		this.uncheckedBoxIDs = uncheckedBoxIDs;
	}

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String getMenu_id()
	{
		return menu_id;
	}

	public void setMenu_id(String menu_id)
	{
		this.menu_id = menu_id;
	}

	/**
 * 
 */
	public void prepare() throws Exception
	{
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		out = response.getWriter();
		String path = request.getContextPath();
		basePath = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + path + "/";
	}

	public MenuTreeNode getModel()
	{
		return treeNode;
	}

	// 初始化树信息
	public void init() throws Exception
	{
		MenuTreeNode rootNode = treeService.getRootNode();
		treeNode.setParent(rootNode);
		// 这种写法还是不够好，1000个节点还是感觉很吃力
		//long start = System.currentTimeMillis();

		List<?> childList = treeService.findChildNodes(treeNode);
		String treeXml = listToXml(childList);
		//long end = System.currentTimeMillis();
		//System.out.println("time:" + (end - start) + "毫秒");
		out.print(treeXml);
	}

	// 这里只需要转一级
	public String listToXml(List<?> list) throws Exception
	{
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\"?>");
		sb.append("<tree>");
		// user_id=request.getParameter("user_id");
		String treeTemplate = "<tree text='#text#' action='#action#' target='#target#' src='#src#' data='#data#'/>";
		JSONObject json_men = treeService.getUserRightMenus(user_id);
		for (Object object : list)
		{
			MenuTreeNode node = (MenuTreeNode) object;
			node.setParent(null);
			Long count = treeService.countChildNodeByParentId(node.getId());
			String src = "";
			if (count != 0)
			{
				// System.out.println(node.getId()+"节点名字：" +
				// node.getText()+";子节点个数：" + count);
				src = basePath
						+ "/Controller/loadChildNode_MenuTree.action?parent_menu="
						+ node.getId()+"&amp;user_id="+user_id;
			}
			JSONObject jsonObject = JSONObject.fromObject(node);
			// 初始化权限菜单
			if (json_men.containsKey("" + node.getId()))
				jsonObject.put("checked", "1");//
			else
				jsonObject.put("checked", "0");//
			sb.append(treeTemplate.replaceAll("#text#", node.getTitle())
					.replaceAll("#action#", jsonObject.getString("action"))
					.replaceAll("#target#", "javascript:void(0);")
					.replaceAll("#src#", src)
					.replaceAll("#data#", jsonObject.toString()));
		}
		sb.append("</tree>");
		return sb.toString();
	}

	// 加载指定节点的子节点集合
	public void loadChildNode() throws Exception
	{
		long start = System.currentTimeMillis();
		List<?> childList = treeService.findChildNodes(treeNode);
		String treeXml = listToXml(childList);
		long end = System.currentTimeMillis();
		System.out.println("time:" + (end - start) + "毫秒");
		out.print(treeXml);
	}

	// 保存或者更新根节点信息
	public void saveOrUpdateRootNode() throws Exception
	{
		try
		{
			treeService.saveOrUpdateRootNode(treeNode);
			loadRootNode();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 添加新的节点
	public void addNode() throws Exception
	{
		try
		{

			treeNode.setName(treeNode.getTitle());
			treeService.addNode(treeNode);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		treeNode.setSrc(basePath+"/Controller/loadChildNode_MenuTree.action?parent_menu="
				+ treeNode.getParent().getId());
		JSONObject jsonObject = JSONObject.fromObject(treeNode);
		out.print(jsonObject);
	}

	// 加载根节点信息
	public void loadRootNode() throws Exception
	{
		try
		{
			MenuTreeNode rootNode = treeService.getRootNode();
			JSONObject jsonObject = JSONObject.fromObject(rootNode);
			out.print(jsonObject);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 删除节点
	public void deleteNode() throws Exception
	{
		try
		{
			int result = treeService.deleteNode(treeNode);
			out.print(result);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @throws Exception
	 * @author 周小桥 |2014-7-29 下午3:03:28
	 * @version 0.1
	 */
	public void updateNode() throws Exception
	{
		try
		{
			MenuTreeNode temp = treeNode;
			MenuTreeNode tempNode = treeService.queryNodeById(treeNode);
			treeNode.setName(treeNode.getTitle());
			treeNode.setParent_menu(tempNode.getParent().getId());
			treeService.updateNode(treeNode);
			JSONObject jsonObject = JSONObject.fromObject(temp);
			out.print(jsonObject);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @throws Exception
	 * @author 周小桥 |2014-7-29 下午3:03:32
	 * @version 0.1
	 */
	public void saveUserRight() throws Exception
	{
		try
		{
			JSONObject jsonObject = new JSONObject();
			if ((checkedBoxIDs != null && !"".equals(checkedBoxIDs))
					|| (uncheckedBoxIDs != null && !"".equals(uncheckedBoxIDs)))
			{
				if (treeService.updateMenuRight(user_id, checkedBoxIDs,
						uncheckedBoxIDs))
					jsonObject.put("success", "授权成功！");
				else
					jsonObject.put("success", "授权失败！");
			} else
				jsonObject.put("success", "授权没有改变！");
			out.print(jsonObject);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @throws Exception
	 * @author 周小桥 |2014-8-1 下午2:58:31
	 * @version 0.1
	 */
	public void queryMenuUser() throws Exception
	{
		GeneralService ds = new GeneralService();
		JSONObject jsonObj = new JSONObject();
		int currPage = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rows") != null ? Integer
				.parseInt(request.getParameter("rows")) : 1;
		String sortname = request.getParameter("sidx");
		String sortorder = request.getParameter("sord");

		try
		{
			if (menu_id != null && !"".equals(menu_id))
			{
				String sql = "select t.user_id,s.name from tab_user_menu t,sec_menu s where menu_id="+menu_id+" and s.id=t.menu_id";
						 
				if (sortname != null && !"".equals(sortname))
				{
					jsonObj = ds.getPageQuery(sql, currPage, pageSize,
							sortname, sortorder);
				} else
					jsonObj = ds.getPageQuery(sql, currPage, pageSize,
							"user_id", "desc");
				jsonObj.put("success", "查询成功！");
			}
		} catch (Exception e)
		{
			jsonObj.put("error", "查询失败！");
			logger.error(e);
		} finally
		{
			this.doJsonResponse(jsonObj);
		}
	}

	/**
	 * @param response
	 * @param JSONObj
	 * @author 周小桥 |2014-6-26 下午5:42:30
	 * @version 0.1
	 */
	private void doJsonResponse(JSONObject JSONObj)
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

}
