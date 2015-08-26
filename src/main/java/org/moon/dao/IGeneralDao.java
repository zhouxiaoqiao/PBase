package org.moon.dao;

import java.util.List;

import org.moon.pagination.PageInfo;


/**
 * 通用dao接口
 * @author 陈均
 *
 */
public interface IGeneralDao {

	/**
	 * 创建实体到数据库
	 * @param entity
	 * @throws Exception
	 */
	public void create(Object entity)throws Exception;
	
	/**
	 * 根据条件删除实体信息（排除in条件的操作）<br/>
	 * 例子：<br/>
	 * List<Condition> conditions = Arrays.asList(
				new Condition("id",new Long(2) ,Operation.EQ)
			);<br/>
		User user = new User();<br/>
		user.setConditions(conditions);<br/>
		删除单个实体:<br/>
		User user = new User(new Long(22));<br/>
		generalDao.delete(user);<br/>
	 */
	public int delete(PageInfo entity)throws Exception;
	
	/**
	 * 删除单个实体信息
	 * 批量删除实体信息,该方法只适用于in操作的<br/>
	 * 考虑性能的原因，所以单独写的一个方法,实现是基于sql实现的,<br/>
	 * 没有单独解析sql字段，实体字段必须和数据库字段名字一样<br/>
	 * 例子：<br/>
	 * 批量删除：<br/>
	 * List<Long> ids = new ArrayList<Long>();<br/>
		for (int i = 47000; i < 147000; i++) {<br/>
			ids.add(new Long(i));<br/>
		}<br/>
		List<Condition> conditions = Arrays.asList(new Condition("id", ids,<br/>
				Operation.IN));<br/>
		User user = new User();<br/>
		user.setConditions(conditions);<br/>
		int count = generalDao.batchDelete(user);<br/>
		删除单个实体:<br/>
		User user = new User(new Long(22));<br/>
		generalDao.batchDelete(user);<br/>
	 */
	public int batchDelete(PageInfo entity)throws Exception;
	
	/**
	 * 批量或者单个更新实体信息（排除in条件的操作）<br/>
	 * 例子：<br/>
	 * 对单个实体更新(可对版本进行控制)：<br/>
	 *  User user = new User(new Long(94));<br/>
		user = (User) generalDao.query(user).get(0);<br/>
		Random random = new Random();<br/>
		user.setCname("陈均"+random.nextInt());<br/>
		generalDao.update(user);<br/>
		批量更新(不能控制版本):<br/>
		List<Condition> conditions = Arrays.asList(new Condition("id",<br/>
				new Object[] { new Long(147000), new Long(157000) },<br/>
				Operation.BETWEEN));<br/>
		User user = new User();<br/>
		user.setCname("陈均");<br/>
		user.setConditions(conditions);<br/>
		int count = generalDao.update(user);<br/>
	 * 
	 */
	public int update(PageInfo entity)throws Exception;
	
	/**
	 * 批量更新实体信息,适用于in操作的<br/>
	 * 考虑性能的原因，所以单独写的一个方法,实现是基于sql实现的<br/>
	 * * 没有单独解析sql字段，实体字段必须和数据库字段名字一样<br/>
	 * 这种批量更新不考虑版本控制<br/>
	 * 例子：<br/>
	 * List<Long> ids = new ArrayList<Long>();<br/>
		for (int i = 147000; i < 157000; i++) {<br/>
			ids.add(new Long(i));<br/>
		}<br/>
		List<Condition> conditions = Arrays.asList(new Condition("id", ids,<br/>
				Operation.IN));<br/>
		User user = new User();<br/>
		user.setCname("陈均来了");<br/>
		user.setConditions(conditions);<br/>
		int count = generalDao.batchUpdate(user);<br/>
	 */
	public int batchUpdate(PageInfo entity)throws Exception;
	
	/**
	 * 查询实体信息,可以多条件，多排序查询<br/>
	 * 例子：<br/>
	 * 根据id查询单个对象<br/>
	 * User user = new User(new Long(95));<br/>
	 * user = (User) generalDao.query(user).get(0);<br/>
	 * 根据条件查询<br/>
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");<br/>
		Date startTime = dateFormat.parse("2010-02-01");<br/>
		Date endTime = dateFormat.parse("2010-03-02");<br/>
		List<Condition> conditions = Arrays.asList(<br/>
				new Condition("stuGrade","一年级", Operation.EQ),<br/>
				new Condition("stuAge", 12, Operation.GE),<br/>
				new Condition("stuAge", 19, Operation.LE), <br/>
				new Condition("stuClass","二班", Operation.EQ),<br/>
				new Condition("stuName", "%stor%",Operation.LIKE), <br/>
				new Condition("stuSex", Arrays.asList("男", "女"), Operation.IN),<br/>
				new Condition("stuTime", new Object[] { startTime, endTime },Operation.BETWEEN),<br/> 
				new Condition("stuAge",new Object[] { 14, 18 }, Operation.BETWEEN));<br/>
		List<Order> orders = Arrays.asList(<br/>
				new Order("stuName", OrderType.DESC), <br/>
				new Order("stuAge",OrderType.DESC));<br/>
		Student student = new Student();<br/>
		student.setConditions(conditions);<br/>
		student.setOrders(orders);<br/>
		List<?> list = generalDao.query(student);<br/>
	 */
	public List<?> query(PageInfo entity)throws Exception;
	
	/**
	 * 外置命名查询
	 */
	public Object query(String queryName,Object... params)throws Exception;
	
	
}