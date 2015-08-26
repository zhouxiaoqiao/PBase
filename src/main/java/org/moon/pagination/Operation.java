package org.moon.pagination;
/**
 * sql各种条件操作类型
 * @author 陈均
 *
 */
public enum Operation {
	IN(" IN "),
	EQ(" = "),
	GT(" > "),
	LT(" < "),
	NE(" != "),
	GE(" >= "),
	LE(" <= "),
	BETWEEN(" BETWEEN "),
	LIKE(" LIKE ");
	
	private String desc;
	
	private Operation(String desc){
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
	
	
	
}
