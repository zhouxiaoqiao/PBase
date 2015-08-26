package org.moon.pagination;


/**
 * 条件操作类
 * 对于BETWEEN操作，该值是一个对象数组<br/>
 * 对于IN操作，该值是一个实现了Collection接口的集合类
 * @author 陈均
 *
 */
public class Condition {
	/**属性名*/
	private String propertyName;
	/**属性值*/
	private Object propertyValue;
	/**操作符*/
	private Operation operation;

	/**构造子*/
	public Condition() {
	}
	
	/**
	 * 
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param operation 操作符
	 */
	public Condition(String propertyName, Object propertyValue,
			Operation operation) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.operation = operation;
	}



	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	
}
