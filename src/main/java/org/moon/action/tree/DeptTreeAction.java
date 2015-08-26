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
import org.moon.entities.DeptTreeNode;
import org.moon.service.GeneralService;
import org.moon.service.inf.IDeptTreeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Controller("DeptTreeAction")
@Scope("prototype")
public class DeptTreeAction extends ActionSupport implements Preparable,
		ModelDriven<DeptTreeNode>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1590833980932575313L;

	private Logger logger = Logger.getLogger(this.getClass());

	private DeptTreeNode treeNode = new DeptTreeNode();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private PrintWriter out;

	private String basePath;

	private String checkedBoxIDs;

	private String uncheckedBoxIDs;

	private String user_id;

	private String dept_id;

	@Resource
	private IDeptTreeService deptTreeService;

	public DeptTreeNode getTreeNode()
	{
		return treeNode;
	}

	public void setTreeNode(DeptTreeNode treeNode)
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

	public String getDept_id()
	{
		return dept_id;
	}

	public void setDept_id(String dept_id)
	{
		this.dept_id = dept_id;
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

	public DeptTreeNode getModel()
	{
		return treeNode;
	}

	// 初始化树信息
	public void init() throws Exception
	{
		DeptTreeNode rootNode = deptTreeService.getRootNode();
		treeNode.setParent(rootNode);
		// 这种写法还是不够好，1000个节点还是感觉很吃力
		// long start = System.currentTimeMillis();

		List<?> childList = deptTreeService.findChildNodes(treeNode);
		String treeXml = listToXml(childList);
		// long end = System.currentTimeMillis();
		// System.out.println("time:" + (end - start) + "毫秒");
		out.print(treeXml);
	}

	// 这里只需要转一级
	public String listToXml(List<?> list) throws Exception
	{
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\"?>");
		sb.append("<tree>");
		// user_id=request.getParameter("user_id");
		String treeTemplate = "<tree text='#text#' action='#action#' target='#target#' src='#src#' data='#data#'/>";
		// JSONObject json_men = deptTreeService.getUserRightMenus(user_id);
		for (Object object : list)
		{
			DeptTreeNode node = (DeptTreeNode) object;
			node.setParent(null);
			Long count = deptTreeService.countChildNodeByParentId(node.getId());
			String src = "";
			if (count != 0)
			{
				// System.out.println(node.getId()+"节点名字：" +
				// node.getText()+";子节点个数：" + count);
				src = basePath
						+ "Controller/loadChildNode_DeptTree.action?parent_org="
						+ node.getId() + "&amp;user_id=" + user_id;
			}
			JSONObject jsonObject = JSONObject.fromObject(node);

			sb.append(treeTemplate.replaceAll("#text#", "" + node.getName())
					.replaceAll("#action#", "javascript:void(0);")
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

		List<?> childList = deptTreeService.findChildNodes(treeNode);
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
			deptTreeService.saveOrUpdateRootNode(treeNode);
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

			// treeNode.setName(treeNode.getName());
			deptTreeService.addNode(treeNode);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// treeNode.setSrc(basePath+"/Controller/loadChildNode_DeptTree.action?parent_org="
		// + treeNode.getParent().getParent_org());
		JSONObject jsonObject = JSONObject.fromObject(treeNode);
		out.print(jsonObject);
	}

	// 加载根节点信息
	public void loadRootNode() throws Exception
	{
		try
		{
			DeptTreeNode rootNode = deptTreeService.getRootNode();
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
			int result = deptTreeService.deleteNode(treeNode);
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
			DeptTreeNode temp = new DeptTreeNode();
			DeptTreeNode tempNode = deptTreeService.queryNodeById(treeNode);
			treeNode.setName(treeNode.getName());
			if (tempNode.getParent() != null)
				treeNode.setParent_org(tempNode.getParent().getParent_org());
			else
				treeNode.setParent_org(null);
			deptTreeService.updateNode(treeNode);
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
				if (deptTreeService.updateMenuRight(user_id, checkedBoxIDs,
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
	public void queryDeptUser() throws Exception
	{
		GeneralService ds = new GeneralService();
		JSONObject jsonObj = new JSONObject();
		int currPage = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rows") != null ? Integer
				.parseInt(request.getParameter("rows")) : 1;
		String sortname = request.getParameter("sidx");
		String sortorder = request.getParameter("sord");
		dept_id = request.getParameter("dept_id");
		try
		{
			if (dept_id != null && !"".equals(dept_id))
			{
				Integer deptId = Integer.parseInt(dept_id);
				String where_sql = deptTreeService.getDeptUserSQL(deptId);
				String sql = "select * from sec_user where org in" + where_sql;
				if (sortname != null && !"".equals(sortname))
				{
					jsonObj = ds.getPageQuery(sql, currPage, pageSize,
							sortname, sortorder);
				} else
					jsonObj = ds.getPageQuery(sql, currPage, pageSize, "id",
							"desc");
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
