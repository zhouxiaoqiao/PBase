package org.snaker.framework.security.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 菜单实体类，继承抽象安全实体类
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "SEC_MENU")
public class Menu extends SecurityEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3488405380107404492L;

	// 菜单资源的根菜单标识为0
	public static final Long ROOT_MENU = 0L;

	// 菜单名称
	private String name;

	// 菜单描述
	private String description;

	// 排序字段
	private Integer orderby;

	// 上级菜单
	private Menu pid;

	// 菜单链接
	private String url;

	private String title;

	private String target;

	private String icon;

	private String iconopen;

	private String opened;

	private String src;

	private String action;

	private String open_icon;

	private String data;

	// 子菜单列表（多对多关联）

	private List<Menu> subMenus = new ArrayList<Menu>();

	public Menu()
	{}

	/**
	 * 构造函数，参数为主键ID
	 * @param id
	 */
	public Menu(Long id)
	{
		this.id = id;
	}

	/**
	 * 构造函数，辅助hql查询
	 * @param id
	 * @param name
	 * @param description
	 */
	public Menu(Long id, String name, String description, Integer orderby,
			String url, String title, String target, String icon,
			String iconopen, String opened, String src, String action,
			String open_icon, String data)

	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.orderby = orderby;
		this.url = url;
		this.title = title;
		this.target = target;
		this.icon = icon;
		this.iconopen = iconopen;
		this.opened = opened;
		this.src = src;
		this.action = action;
		this.open_icon = open_icon;
		this.data = data;

	}

	@Column(name = "name", nullable = false, length = 200)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "description", length = 500)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}



	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "pid")
	public List<Menu> getSubMenus()
	{
		return subMenus;
	}

	@ManyToOne
	@JoinColumn(name = "pid")
	public Menu getPid()
	{
		return pid;
	}

	public void setPid(Menu pid)
	{
		this.pid = pid;
	}

	public void setSubMenus(List<Menu> subMenus)
	{
		this.subMenus = subMenus;
	}

	@Column(name = "orderby")
	public Integer getOrderby()
	{
		return orderby;
	}

	public void setOrderby(Integer orderby)
	{
		this.orderby = orderby;
	}

	@Column(name = "url", length = 100)
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@Column(name = "title", length = 100)
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Column(name = "target", length = 100)
	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	@Column(name = "icon", length = 100)
	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	@Column(name = "iconopen", length = 100)
	public String getIconopen()
	{
		return iconopen;
	}

	public void setIconopen(String iconopen)
	{
		this.iconopen = iconopen;
	}

	@Column(name = "opened", length = 100)
	public String getOpened()
	{
		return opened;
	}

	public void setOpened(String opened)
	{
		this.opened = opened;
	}

	@Column(name = "src", length = 100)
	public String getSrc()
	{
		return src;
	}

	public void setSrc(String src)
	{
		this.src = src;
	}

	@Column(name = "action", length = 100)
	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	@Column(name = "open_icon", length = 100)
	public String getOpen_icon()
	{
		return open_icon;
	}

	public void setOpen_icon(String open_icon)
	{
		this.open_icon = open_icon;
	}

	@Column(name = "data", length = 100)
	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

}
