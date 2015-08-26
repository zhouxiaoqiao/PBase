package org.moon.pagination;

/**
 * sql排序类型
 * @author 陈均
 *
 */
public enum OrderType {

	ASC(" ASC "),
	DESC(" DESC ");
	
	private String desc;
	
	private OrderType(String desc){
		this.desc = desc;
	}

	@Override
	public String toString() {
		return desc;
	}
	
	
	
}
