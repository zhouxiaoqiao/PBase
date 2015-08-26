package org.moon.service.inf;

import java.util.List;
import org.moon.entities.DeptTreeNode;


public interface IDeptTreeService
{

	DeptTreeNode getRootNode() throws Exception;

	void saveOrUpdateRootNode(DeptTreeNode treeNode) throws Exception;

	void addNode(DeptTreeNode treeNode) throws Exception;

	List<?> findChildNodes(DeptTreeNode treeNode) throws Exception;

	Long countChildNodeByParentId(Integer pid) throws Exception;

	int deleteNode(DeptTreeNode treeNode) throws Exception;

	void updateNode(DeptTreeNode treeNode) throws Exception;

	DeptTreeNode queryNodeById(DeptTreeNode treeNode) throws Exception;

	public String getDeptUserSQL(Integer dept_id) throws Exception;

	boolean updateMenuRight(String userID, String checkedBoxIDs,
			String uncheckedBoxIDs) throws Exception;
}
