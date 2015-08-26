package org.moon.service.inf;

import java.util.List;

import net.sf.json.JSONObject;

import org.moon.entities.MenuTreeNode;

public interface ITreeService
{

	MenuTreeNode getRootNode() throws Exception;

	void saveOrUpdateRootNode(MenuTreeNode treeNode) throws Exception;

	void addNode(MenuTreeNode treeNode) throws Exception;

	List<?> findChildNodes(MenuTreeNode treeNode) throws Exception;

	Long countChildNodeByParentId(Integer pid) throws Exception;

	int deleteNode(MenuTreeNode treeNode) throws Exception;

	void updateNode(MenuTreeNode treeNode) throws Exception;

	MenuTreeNode queryNodeById(MenuTreeNode treeNode) throws Exception;

	JSONObject getUserRightMenus(String userID) throws Exception;

	boolean updateMenuRight(String userID, String checkedBoxIDs,
			String uncheckedBoxIDs) throws Exception;
}
