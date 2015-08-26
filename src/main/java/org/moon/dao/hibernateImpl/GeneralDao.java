package org.moon.dao.hibernateImpl;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.moon.dao.IGeneralDao;
import org.moon.pagination.Condition;
import org.moon.pagination.Operation;
import org.moon.pagination.Order;
import org.moon.pagination.OrderType;
import org.moon.pagination.PageInfo;


@Repository("generalDao")
public class GeneralDao implements IGeneralDao {
	
	private static Log log = LogFactory.getLog(GeneralDao.class);
	
	@Resource
	private HibernateTemplate hibernateTemplate;

	public void create(Object entity) throws Exception {
		log.info("create " + entity.getClass());
		hibernateTemplate.persist(entity);
	
	}

	public int delete(final PageInfo entity) throws Exception {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
		
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				// 删除模板语句
				String deleteTemplate = "DELETE #entityName# mode WHERE 1=1 #condition_hql#";
				// 参数集合
				List<Object> values = new ArrayList<Object>();
				String condition_hql = preConditionHQL(entity, values);
				if ("".equals(condition_hql)) {// 如果没有附加条件
					// 尝试对象删除
					session.delete(entity);
					return 1;
				}
				String delete = deleteTemplate.replaceAll("#condition_hql#",
						condition_hql).replaceAll("#entityName#",
						entity.getClass().getSimpleName());
				System.out.println("delete = " + delete);

				Query query = session.createQuery(delete);
				setQueryParameter(query, values);// 设置参数
				return query.executeUpdate();// 执行
			}
		});
	}

	public List<?> query(final PageInfo entity) throws Exception {
		// 面向单个对象的分页写法，对于连表分页不适用
		return hibernateTemplate.executeFind(new HibernateCallback<List<?>>() {
			
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				return executeHQL(entity, session);
				// return executeQBC(entity, session);
			}
		 
		});
	}

	public int update(final PageInfo entity) throws Exception {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				String updateTemplate = new String(
						"UPDATE #entityName# mode SET #fields# WHERE 1=1 #condition_hql# ");
				// 参数集合
				List<Object> values = new ArrayList<Object>();

				String fields = preField(entity, values);
				String condition_hql = preConditionHQL(entity, values);
				if ("".equals(condition_hql)) {// 如果没有条件
					// 尝试更新单个实体对象
					hibernateTemplate.merge(entity);
					return 1;
				}
				// 多条件更新
				String update = updateTemplate.replaceAll("#condition_hql#",
						condition_hql).replaceAll("#fields#", fields)
						.replaceAll("#entityName#",
								entity.getClass().getSimpleName());
				System.out.println("update = " + update);
				Query query = session.createQuery(update);
				setQueryParameter(query, values);
				return query.executeUpdate();
			}
		});
	}

	
	public int batchDelete(final PageInfo entity) throws Exception {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				// sql删除模板
				String deleteTemplate = new String(
						"DELETE FROM #tableName# WHERE 1=1 #condition_sql#");
				// 对应表名
				String tableName = Constants.get(entity.getClass().getName())
						.toString();
				// 参数集合
				List<Object> values = new ArrayList<Object>();
				String condition_sql = preConditionSQL(entity, values);
				if ("".equals(condition_sql)) {// 如果没有附加条件
					// 尝试对象删除
					session.delete(entity);
					return 1;
				}
				String delete = deleteTemplate.replaceAll("#condition_sql#",
						condition_sql).replaceAll("#tableName#", tableName);
				System.out.println("delete = " + delete);
				Query query = session.createSQLQuery(delete);
				setQueryParameter(query, values);// 设置参数
				return query.executeUpdate();// 执行
			}
		});

	}

	
	public int batchUpdate(final PageInfo entity) throws Exception {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				// sql更新模板
				String updateTemplate = new String(
						"UPDATE #tableName# SET #fields# WHERE 1=1 #condition_sql#");
				// 参数集合
				List<Object> values = new ArrayList<Object>();
				// 对应表名
				String tableName = Constants.get(entity.getClass().getName())
						.toString();
				String fields = preField(entity, values);
				String condition_sql = preConditionSQL(entity, values);
				if ("".equals(condition_sql)) {// 如果没有条件
					// 尝试更新单个实体对象
					hibernateTemplate.update(entity);
					return 1;
				}

				String update = updateTemplate.replaceAll("#condition_sql#",
						condition_sql).replaceAll("#fields#", fields)
						.replaceAll("#tableName#", tableName);
				System.out.println("update = " + update);
				Query query = session.createSQLQuery(update);
				setQueryParameter(query, values);// 设置参数
				return query.executeUpdate();// 执行
			}
		});
	}

	/**
	 * 处理字段语句hql,用于更新用
	 */
	public String preField(PageInfo entity, List<Object> values) {
		StringBuffer fields = new StringBuffer();
		// 解析字段
		Class<?> c = entity.getClass();
		for (Field field : c.getDeclaredFields()) {
			String fieldName = field.getName();
			try {
				field.setAccessible(true);// 设置可访问私有字段
				Object value = field.get(entity);// 获取字段值
				if (value != null) {// 只对有值的字段进行更新
					fields.append(fieldName + "=?,");
					values.add(value);// 先获取条件参数值
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}
		if (fields.length() > 0) {// 有更新的字段
			// 删掉末尾逗号
			fields.replace(fields.length() - 1, fields.length(), "");
		}
		return fields.toString();
	}

	/**
	 * 处理排序解析hql
	 */
	public String preOrder(PageInfo entity) {
		StringBuffer c = new StringBuffer();
		List<Order> orders = entity.getOrders();
		if (orders.size() > 0) {
			c.append(" ORDER BY ");
		}
		for (Order order : orders) {
			String propertyName = order.getPropertyName();
			OrderType orderType = order.getOrderType();
			c.append("mode." + propertyName + orderType + ",");
		}
		if (orders.size() > 0) {
			c.replace(c.length() - 1, c.length(), "");
		}
		return c.toString();
	}

	/**
	 * 条件解析SQL该方法作用于批量更新和批量删除使用
	 */
	public String preConditionSQL(PageInfo entity, List<Object> param) {
		StringBuffer c = new StringBuffer();
		for (Condition condition : entity.getConditions()) {
			// 唯一的区别就是该字段名字换成数据库字段
			// 终于体会到想获取某个字段对应数据库字段名字的渴望了
			// 这个时候通过注解是很方便的,应为我的数据库字段和实体字段一样，所以这里不用修改
			String propertyName = condition.getPropertyName();
			Object value = condition.getPropertyValue();
			Operation operation = condition.getOperation();
			switch (operation) {
			case LIKE:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case BETWEEN:
				Object[] params = (Object[]) value;
				c.append(" AND " + propertyName + operation + "? AND ?");
				param.add(params[0]);
				param.add(params[1]);
				break;
			case IN:
				Collection<?> values = (Collection<?>) value;
				c.append(" AND " + propertyName + operation + "(");
				for (Object object : values) {
					c.append("?,");
					param.add(object);
				}
				c.replace(c.length() - 1, c.length(), "");
				c.append(")");
				break;
			case EQ:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case GE:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case GT:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case LE:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case LT:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case NE:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			}
		}
		return c.toString();
	}

	/**
	 * 条件解析hql
	 */
	public String preConditionHQL(PageInfo entity, List<Object> param) {
		StringBuffer c = new StringBuffer();
		for (Condition condition : entity.getConditions()) {
			String propertyName = condition.getPropertyName();
			Object value = condition.getPropertyValue();
			Operation operation = condition.getOperation();
			switch (operation) {
			case LIKE:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case BETWEEN:
				Object[] params = (Object[]) value;
				c.append(" AND " + propertyName + operation + "? AND ?");
				param.add(params[0]);
				param.add(params[1]);
				break;
			case IN:
				Collection<?> values = (Collection<?>) value;
				c.append(" AND " + propertyName + operation + "(");
				for (Object object : values) {
					c.append("?,");
					param.add(object);
				}
				c.replace(c.length() - 1, c.length(), "");
				c.append(")");
				break;
			case EQ:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case GE:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case GT:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case LE:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case LT:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			case NE:
				c.append(" AND " + propertyName + operation + "?");
				param.add(value);
				break;
			}
		}
		return c.toString();
	}

	/**
	 * hql形式的解析
	 */
	public List<?> executeHQL(PageInfo entity, Session session) {
		// 统计模板语句
		String hqlCountTemplate = "SELECT COUNT(mode) FROM #entityName# mode WHERE 1=1 #condition_hql#";
		// 查询模板语句
		String hqlTemplate = "SELECT mode FROM #entityName# mode WHERE 1=1 #condition_hql# #order_hql#";
		// 参数集合
		List<Object> values = new ArrayList<Object>();

		String condition_hql = preConditionHQL(entity, values);
		String order_hql = preOrder(entity);

		if ("".equals(condition_hql)) {// 如果没有条件
			// 尝试获取单个对象
			try {
				Class<?> c = entity.getClass();
				Field field = c.getDeclaredField("id");
				field.setAccessible(true);
				Long id = (Long) field.get(entity);
				Object item = hibernateTemplate.get(c, id);
				return Arrays.asList(item);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		String hqlCount = hqlCountTemplate.replaceAll("#condition_hql#",
				condition_hql).replaceAll("#entityName#",
				entity.getClass().getSimpleName());

		String hql = hqlTemplate.replaceAll("#condition_hql#", condition_hql)
				.replaceAll("#order_hql#", order_hql).replaceAll(
						"#entityName#", entity.getClass().getSimpleName());
		System.out.println("hqlCount=" + hqlCount);
		System.out.println("hql=" + hql);
		// 获取总条数
		Query query = session.createQuery(hqlCount);
		// 设置参数
		setQueryParameter(query, values);
		Object uqResult = query.uniqueResult();
		System.out.println("满足条件的个数：" + uqResult);
		entity.setTotalItems(Integer.parseInt(uqResult.toString()));
		// 设置总页数
		setTotalPage(entity);
		// 获取结果集
		query = session.createQuery(hql);
		// 设置参数
		setQueryParameter(query, values);
		return query.setFirstResult(
				(entity.getCurrentPage() - 1) * entity.getPageSize())
				.setMaxResults(entity.getPageSize()).list();
	}

	/**
	 * 设置分页语句参数值
	 */
	public void setQueryParameter(Query query, List<Object> values) {
		for (int i = 0; i < values.size(); i++) {
			query.setParameter(i, values.get(i));
		}
	}

	/**
	 * QBC形式的解析
	 */
	public Object executeQBC(PageInfo entity, Session session) {
		Criteria qbc = session.createCriteria(entity.getClass());
		qbc.setProjection(Projections.rowCount());
		// 处理条件
		preCondition(qbc, entity.getConditions());
		// 获取总条数
		Object uqResult = qbc.uniqueResult();
		System.out.println("满足条件的个数：" + uqResult);
		entity.setTotalItems(Integer.parseInt(uqResult.toString()));
		// 设置总页数
		setTotalPage(entity);
		// 分页结果
		qbc.setProjection(null);
		// 处理排序
		preOrder(qbc, entity.getOrders());
		// 获取结果集
		return qbc.setFirstResult(
				(entity.getCurrentPage() - 1) * entity.getPageSize())
				.setMaxResults(entity.getPageSize()).list();
	}

	/**
	 * 设置总页数
	 * */
	public void setTotalPage(PageInfo pageInfo) {
		pageInfo
				.setTotalPage(pageInfo.getTotalItems() % pageInfo.getPageSize() == 0 ? pageInfo
						.getTotalItems()
						/ pageInfo.getPageSize()
						: pageInfo.getTotalItems() / pageInfo.getPageSize() + 1);
	}

	/**
	 * 处理排序qbc
	 */
	public void preOrder(Criteria qbc, List<Order> orders) {
		for (Order order : orders) {
			String propertyName = order.getPropertyName();
			switch (order.getOrderType()) {
			case ASC:
				qbc.addOrder(org.hibernate.criterion.Order.asc(propertyName));
				break;
			case DESC:
				qbc.addOrder(org.hibernate.criterion.Order.desc(propertyName));
				break;
			}
		}
	}

	/**
	 * 处理条件qbc
	 */
	public void preCondition(Criteria qbc, List<Condition> conditions) {
		for (Condition condition : conditions) {
			String propertyName = condition.getPropertyName();
			Object value = condition.getPropertyValue();
			switch (condition.getOperation()) {
			case LIKE:
				qbc.add(Restrictions.like(propertyName, value.toString(),
						MatchMode.ANYWHERE));
				break;
			case BETWEEN:
				Object[] params = (Object[]) value;
				qbc.add(Restrictions
						.between(propertyName, params[0], params[1]));
				break;
			case IN:
				Collection<?> values = (Collection<?>) value;
				qbc.add(Restrictions.in(propertyName, values));
				break;
			case EQ:
				qbc.add(Restrictions.eq(propertyName, value));
				break;
			case GE:
				qbc.add(Restrictions.ge(propertyName, value));
				break;
			case GT:
				qbc.add(Restrictions.gt(propertyName, value));
				break;
			case LE:
				qbc.add(Restrictions.le(propertyName, value));
				break;
			case LT:
				qbc.add(Restrictions.lt(propertyName, value));
				break;
			case NE:
				qbc.add(Restrictions.ne(propertyName, value));
				break;
			}
		}
	}

	
	public Object query(String queryName, Object... values) throws Exception {
		return hibernateTemplate.findByNamedQuery(queryName, values);
	}
}
