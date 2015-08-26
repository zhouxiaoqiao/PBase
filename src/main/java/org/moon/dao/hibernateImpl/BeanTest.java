package org.moon.dao.hibernateImpl;

import java.util.List;
import net.sf.json.JSONObject;

import org.moon.dao.IGeneralDao;
import org.moon.entities.MenuTreeNode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest
{

	public static void main(String[] args) throws Exception
	{

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/system-bean.xml");
		IGeneralDao generalDao = (IGeneralDao) context.getBean("generalDao");
		MenuTreeNode rootNode = new MenuTreeNode();
		MenuTreeNode paNode = new MenuTreeNode();
		paNode.setPid(0);
		rootNode.setParent(paNode);
		List<?> queryList = (List<?>) generalDao.query("queryRootNode");
		MenuTreeNode treeNode = new MenuTreeNode();
		treeNode.setTitle("hehe");
		// List<TreeNode> list = new ArrayList<TreeNode>();
		// JSONObject jsonObject = JSONObject.fromObject(treeNode);
		// System.out.println(jsonObject);
		// list.add(treeNode);
		// JSONArray jsonArray = JSONArray.fromObject(list);
		// System.out.println(jsonArray);

		// List<?> queryList = (List<?>) generalDao.query(
		// "queryTreeNodeByParentId", new Integer(12));
		String treeXml = listToXml(queryList);
		System.out.println(treeXml);

	}

	// 这里只需要转一级
	public static String listToXml(List<?> list)
	{
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\"?>");
		sb.append("<tree>");
		String treeTemplate = "<tree text='#text#' action='#action#' target='#target#' src='#src#' data='#data#'/>";
		for (Object object : list)
		{
			MenuTreeNode node = (MenuTreeNode) object;
			node.setParent(null);
			JSONObject jsonObject = JSONObject.fromObject(node);
			if (jsonObject != null && treeTemplate != null)
				sb.append(treeTemplate.replaceAll("#text#", node.getTitle())
						.replaceAll("#action#", "javascript:void(0);")
						.replaceAll("#target#", "javascript:void(0);")
						.replaceAll("#src#", "javascript:void(0);")
						.replaceAll("#data#", jsonObject.toString()));
		}
		sb.append("</tree>");
		return sb.toString();
	}

}
