package org.moon.dao.hibernateImpl;

import java.util.HashMap;
import java.util.Map;
import org.moon.entities.MenuTreeNode;

/**
 * 常量类，负责所有的常量
 * @author 陈均
 */
public class Constants
{

	private static Map<String, Object> map = new HashMap<String, Object>();

	static
	{
		map.put(MenuTreeNode.class.getName(), "treeNode");// TreeNode类对应的表名字
		map.put("pk", "id");// 数据库所有的表都有一个主键，主键的名字为id
	}

	public static Object get(String key)
	{
		return map.get(key);
	}

}
