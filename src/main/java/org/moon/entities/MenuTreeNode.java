package org.moon.entities;

import org.moon.pagination.PageInfo;

public class MenuTreeNode extends PageInfo
{
	private Integer id;

	private String title = "";

	private String src = "";

	private String action = "";

	private String icon = "";

	private String open_icon = "";

	private String target = "";

	private String data = "";

	private Integer pid;

	private String url = "";

	private String name = "";

	private MenuTreeNode parent;

	public MenuTreeNode()
	{}

	public MenuTreeNode(String text, String src, String action, String icon,
			String open_icon, String target, String data, MenuTreeNode parent)
	{
		this.title = text;
		this.src = src;
		this.action = action;
		this.icon = icon;
		this.open_icon = open_icon;
		this.target = target;
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


	public Integer getPid()
	{
		return pid;
	}

	public void setPid(Integer pid)
	{
		this.pid = pid;
		if (parent == null)
			parent = new MenuTreeNode();
		parent.setId(pid);
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSrc()
	{
		return src;
	}

	public void setSrc(String src)
	{
		this.src = src;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	 
	public String getOpen_icon()
	{
		return open_icon;
	}

	public void setOpen_icon(String open_icon)
	{
		this.open_icon = open_icon;
	}

	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public MenuTreeNode getParent()
	{
		return parent;
	}

	public void setParent(MenuTreeNode parent)
	{
		this.parent = parent;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
