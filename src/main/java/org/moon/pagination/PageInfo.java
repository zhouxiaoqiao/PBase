package org.moon.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息类
 * 
 * @author 陈均
 * 
 */
public abstract class PageInfo {

	/** 当前第几页,默认第一页 */
	private int currentPage = 1;
	/** 总页数,默认0页 */
	private int totalPage = 0;
	/** 总条数 */
	private int totalItems = 0;
	/** 每页显示多少条 */
	private int pageSize = 10;
	/** 条件集合 */
	protected List<Condition> conditions = new ArrayList<Condition>();
	/** 排序集合 */
	protected List<Order> orders = new ArrayList<Order>();
	
	
	

	public PageInfo() {

	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
