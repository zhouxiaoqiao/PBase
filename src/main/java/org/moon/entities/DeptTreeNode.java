package org.moon.entities;

import org.moon.pagination.PageInfo;

public class DeptTreeNode extends PageInfo
{
	private Integer id;

	private String name = "";

	private String fullname = "";

	private String active = "";

	private String type = "";

	private String description = "";

	private String data = "";

	private Integer parent_org;

	private DeptTreeNode parent;

	public DeptTreeNode()
	{}

	public DeptTreeNode(String active, String name, String fullname,
			String type, String description, String data, DeptTreeNode parent)
	{

		this.name = name;

		this.fullname = fullname;
		this.active = active;
		this.type = type;
		this.description = description;

		this.data = data;
		this.parent = parent;

	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFullname()
	{
		return fullname;
	}

	public void setFullname(String fullname)
	{
		this.fullname = fullname;
	}

	public String getActive()
	{
		return active;
	}

	public void setActive(String active)
	{
		this.active = active;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public Integer getParent_org()
	{
		return parent_org;
	}

	public void setParent_org(Integer parent_org)
	{
		this.parent_org = parent_org;
		if (parent == null)// /切记 必须,否则子节点出不来
			parent = new DeptTreeNode();
		parent.setId(parent_org);
	}

	public DeptTreeNode getParent()
	{
		return parent;
	}

	public void setParent(DeptTreeNode parent)
	{
		this.parent = parent;
	}

}
